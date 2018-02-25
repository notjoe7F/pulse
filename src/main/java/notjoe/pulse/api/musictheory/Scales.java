package notjoe.pulse.api.musictheory;

import io.vavr.collection.Vector;

import static notjoe.pulse.api.musictheory.Note.*;

public enum Scales implements Scale {
    PENTATONIC_MAJOR(C_LOW, D, E, G, A, C_HIGH),
    MAJOR(C_LOW, D, E, F, G, A, B, C_HIGH),
    NATURAL_MINOR(C_LOW, D, E_FLAT, F, G_FLAT, A_FLAT, B_FLAT, C_HIGH),
    CHROMATIC(Note.values());

    private final Vector<Note> scaleNotes;

    Scales(Note... scaleNotes) {
        this.scaleNotes = Vector.of(scaleNotes);
    }

    @Override
    public Note offset(Note from, int amount) {
        int fromIndex = scaleNotes.indexOf(from);
        if (fromIndex == -1) {
            return scaleNotes.getOrElse(Note.C_LOW);
        }

        return scaleNotes.get((fromIndex + amount) % scaleNotes.size());
    }

    @Override
    public String getUnlocalizedName() {
        return "pulse.scale." + name().toLowerCase();
    }
}
