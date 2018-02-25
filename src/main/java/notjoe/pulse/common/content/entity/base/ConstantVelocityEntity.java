package notjoe.pulse.common.content.entity.base;

import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class ConstantVelocityEntity extends EntityThrowable {
    private Vec3d constantVelocity;

    public ConstantVelocityEntity(World worldIn, Vec3d constantVelocity) {
        super(worldIn);
        this.constantVelocity = constantVelocity;
    }

    public ConstantVelocityEntity(World worldIn, double xVelocity, double yVelocity, double zVelocity) {
        super(worldIn);
        constantVelocity = new Vec3d(xVelocity, yVelocity, zVelocity);
    }

    public Vec3d getConstantVelocity() {
        return constantVelocity;
    }

    public Vec3d getConstantVelocityNormalized() {
        return constantVelocity.normalize();
    }

    @Override
    public void onUpdate() {
        lastTickPosX = posX;
        lastTickPosY = posY;
        lastTickPosZ = posZ;

        Vec3d position = new Vec3d(posX, posY, posZ);
        Vec3d nextPosition = new Vec3d(posX + constantVelocity.x, posY + constantVelocity.y, posZ + constantVelocity.z);
        RayTraceResult rayTraceResult = world.rayTraceBlocks(position, nextPosition);

        posX += constantVelocity.x;
        posY += constantVelocity.y;
        posZ += constantVelocity.z;

        if (rayTraceResult != null) {
            onImpact(rayTraceResult);
            setDead();
        }

        setPosition(posX, posY, posZ);
    }
}
