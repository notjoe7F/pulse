package notjoe.pulse.api.musictheory;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import io.vavr.control.Try;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Melody {
    private final Vector<Tuple2<Integer, Note>> components;

    public static Try<Melody> readFromNbt(NBTTagCompound compound) {
        return Try.of(() -> readFromNbtUnsafe(compound));
    }

    public static Melody empty() {
        return new Melody(Vector.empty());
    }

    private static Melody readFromNbtUnsafe(NBTTagCompound compound) {
        int length = compound.getInteger("length");
        Vector<Tuple2<Integer, Note>> compoundComponents = Vector.ofAll(Stream.range(0, length).map(index -> {
            String value = compound.getString(index.toString());
            String[] valueParts = value.split(";");
            return Tuple.of(Integer.parseInt(valueParts[1]), Note.valueOf(valueParts[2]));
        }));
        return new Melody(compoundComponents);
    }

    public Melody(Vector<Tuple2<Integer, Note>> components) {
        this.components = components;
    }

    public Melody(Object... rawComponents) {
        if ((rawComponents.length % 2) != 0) {
            throw new IllegalArgumentException("Odd number of components. Every part of the melody needs an Integer and a Note.");
        }

        components = Vector.of(rawComponents).grouped(2).map(group -> Tuple.of((Integer) group.get(0), (Note) group.get(1))).toVector();
    }

    public Melody append(int duration, Note note) {
        return new Melody(components.append(Tuple.of(duration, note)));
    }

    public NBTTagCompound writeToNbt() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("length", components.length());

        components.zipWithIndex().forEach(noteIndexTuple -> {
            String key = noteIndexTuple._2.toString();
            Note note = noteIndexTuple._1._2;
            int noteLength = noteIndexTuple._1._1;
            String noteStr = note.name() + ";" + noteLength;
            compound.setString(key, noteStr);
        });

        return compound;
    }

    public int size() {
        return components.size();
    }

    public Vector<Tuple2<Integer, Note>> getComponents() {
        return components;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        components.forEach(noteTuple -> {
            builder.append(noteTuple._1);
            builder.append(noteTuple._2.name());
        });

        return builder.build();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Melody)) {
            return false;
        }

        Melody other = (Melody) obj;

        if (other.size() == 0 && size() == 0) {
            return true;
        }

        Vector<Boolean> equalities = getComponents().zipWith(other.getComponents(), (first, second) -> first._2 == second._2 && first._1.equals(second._1));

        return !equalities.contains(false) && !equalities.isEmpty();
    }
}
