package notjoe.pulse.common.content.entity;

import io.vavr.collection.Vector;
import io.vavr.control.Option;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import notjoe.pulse.api.musictheory.Melody;
import notjoe.pulse.api.musictheory.Note;
import notjoe.pulse.client.particle.PulseParticles;
import notjoe.pulse.common.capability.NoteHandler;
import notjoe.pulse.common.capability.PulseCapabilities;
import notjoe.pulse.common.content.ModContent;
import notjoe.pulse.common.content.entity.base.ConstantVelocityEntity;
import notjoe.pulse.common.util.Configuration;

public class EntityNote extends ConstantVelocityEntity {
    private static final float NOTE_VELOCITY = 0.75f - 0.0001f; // Raytracing issues occur if this number is too nice.

    private boolean playerSpawned = false;
    private int color = 0xFFFFFF;
    private Note note = Note.C_LOW;
    private Melody melody = Melody.empty();
    private Vec3d startPos;

    private EntityNote(World world, Vec3d normalizedVelocity) {
        super(world, normalizedVelocity);
        setSize(0f, 0f);
    }

    public EntityNote(World world, EntityPlayer player) {
        this(world, player.getLookVec().scale(NOTE_VELOCITY));

        startPos = player.getPositionVector().addVector(0, player.getEyeHeight(), 0);
        setPosition(startPos.x, startPos.y, startPos.z);

        playerSpawned = true;
    }

    public EntityNote(World world, BlockPos emitPos, EnumFacing emitFace, Note note) {
        this(world, new Vec3d(emitFace.getDirectionVec()));
        startPos = getEmitPosition(emitPos, emitFace);
        setPosition(startPos.x, startPos.y, startPos.z);
        this.note = note;
    }

    public EntityNote(World world, BlockPos emitPos, EnumFacing emitFace, EntityNote previous, Note next) {
        this(world, new Vec3d(emitFace.getDirectionVec()));
        startPos = getEmitPosition(emitPos, emitFace);
        setPosition(startPos.x, startPos.y, startPos.z);
        melody = previous.getMelodyWithCurrent();
        note = next;

        if (melody.size() > Configuration.maxNoteTransfers) {
            setDead();
        }
    }

    public void playSound() {
        if (!getEntityWorld().isRemote) {
            getEntityWorld().playSound(null, posX, posY, posZ, ModContent.TUNING_FORK_SOUND, SoundCategory.BLOCKS, 1.0f, (float) note.getPitchRatio());
        }
    }

    private Vec3d getEmitPosition(BlockPos pos, EnumFacing face) {
        return new Vec3d(pos.getX(), pos.getY(), pos.getZ()).add(new Vec3d(face.getDirectionVec())).addVector(0.5, 0.5, 0.5);
    }

    private void spawnMovementParticles() {
        PulseParticles.renderNote(world, posX, posY, posZ, 0, 0, 0, note.getColor(), 0);
        for (int i = 1; i < Configuration.noteRenderSteps; i++) {
            double positionPercent = (double) i / Configuration.noteRenderSteps;
            PulseParticles.renderNote(world,
                    weightedAverage(positionPercent, posX, lastTickPosX, true),
                    weightedAverage(positionPercent, posY, lastTickPosY, true),
                    weightedAverage(positionPercent, posZ, lastTickPosZ, true),
                    0, 0, 0, note.getColor(), positionPercent);
            PulseParticles.renderNote(world,
                    weightedAverage(positionPercent, posX, lastTickPosX, false),
                    weightedAverage(positionPercent, posY, lastTickPosY, false),
                    weightedAverage(positionPercent, posZ, lastTickPosZ, false),
                    0, 0, 0, note.getColor(), positionPercent);
        }
    }

    private double weightedAverage(double percent, double current, double previous, boolean forward) {
        double currentWeight = (forward ? percent : (1 - percent)) * current;
        double previousWeight = (forward ? (1 - percent) : percent) * previous;
        return currentWeight + previousWeight;
    }


    private void spawnHitParticles() {
        Vec3d velocity = getConstantVelocityNormalized();
        for (int i = 0; i < (rand.nextInt(3) + 4); i++) {
            getEntityWorld().spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY, posZ,
                    (-velocity.x * (rand.nextDouble() / 6)) + (rand.nextGaussian() / 10),
                    (-velocity.y * (rand.nextDouble() / 6)) + (rand.nextGaussian() / 10),
                    (-velocity.z * (rand.nextDouble() / 6)) + (rand.nextGaussian() / 10));
        }
    }

    public Melody getMelodyWithCurrent() {
        int distance = (int) Math.ceil(startPos.distanceTo(getPositionVector()));
        return melody.append(distance, note);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (getEntityWorld().isRemote) {
            spawnMovementParticles();
        }

        if (ticksExisted > Configuration.maxNoteTicks) {
            setDead();
        }
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        Option<NoteHandler> noteHandler;

        if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
            noteHandler = getNoteHandlerFromBlock(result.getBlockPos(), result.sideHit);
            spawnHitParticles();
        } else if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
            noteHandler = getNoteHandlerFromEntity(result.entityHit);
            spawnHitParticles();
        } else {
            noteHandler = Option.none();
        }

        noteHandler.forEach(handler -> handler.receiveNote(this, result.sideHit));
    }

    private Option<NoteHandler> getNoteHandlerFromBlock(BlockPos pos, EnumFacing face) {
        Option<TileEntity> tileEntityOption = Option.of(getEntityWorld().getTileEntity(pos));
        if (!tileEntityOption.isDefined()) {
            return Option.none();
        }

        TileEntity tileEntity = tileEntityOption.get();
        if (tileEntity.hasCapability(PulseCapabilities.NOTE_HANDLER, face)) {
            return Option.of(tileEntity.getCapability(PulseCapabilities.NOTE_HANDLER, face));
        }

        return Option.none();
    }

    private Option<NoteHandler> getNoteHandlerFromEntity(Entity entity) {
        if (entity.hasCapability(PulseCapabilities.NOTE_HANDLER, null)) {
            return Option.of(entity.getCapability(PulseCapabilities.NOTE_HANDLER, null));
        }

        return Option.none();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag("note", note.writeToNbt());
        compound.setTag("melody", melody.writeToNbt());
        compound.setDouble("start_x", startPos.x);
        compound.setDouble("start_y", startPos.y);
        compound.setDouble("start_z", startPos.z);
        compound.setBoolean("player_spawned", playerSpawned);
        compound.setInteger("color", color);
        return compound;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        note = Note.readFromNbt(nbt.getCompoundTag("note")).getOrElse(Note.C_LOW);
        melody = Melody.readFromNbt(nbt.getCompoundTag("melody")).getOrElse(new Melody(Vector.empty()));
        startPos = new Vec3d(nbt.getDouble("start_x"), nbt.getDouble("start_y"), nbt.getDouble("start_z"));
        playerSpawned = nbt.getBoolean("player_spawned");
        color = nbt.getInteger("color");

    }

    public Note getNote() {
        return note;
    }
}
