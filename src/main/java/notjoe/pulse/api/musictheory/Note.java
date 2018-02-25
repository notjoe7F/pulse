package notjoe.pulse.api.musictheory;

import io.vavr.control.Try;
import net.minecraft.nbt.NBTTagCompound;

import java.awt.*;

/**
 * Defines the notes in a traditional 12-note (C) chromatic scale as well as their pitch ratios, used when playing them
 * as sounds through Minecraft.
 * https://en.wikipedia.org/wiki/Equal_temperament#Comparison_to_just_intonation
 */
public enum Note {
    C_LOW(1d),
    D_FLAT(Math.pow(2, 1.0/12)),
    D(Math.pow(2, 2.0/12)),
    E_FLAT(Math.pow(2, 3.0/12)),
    E(Math.pow(2, 4.0/12)),
    F(Math.pow(2, 5.0/12)),
    G_FLAT(Math.pow(2, 6.0/12)),
    G(Math.pow(2, 7.0/12)),
    A_FLAT(Math.pow(2, 8.0/12)),
    A(Math.pow(2, 9.0/12)),
    B_FLAT(Math.pow(2, 10.0/12)),
    B(Math.pow(2, 11.0/12)),
    C_HIGH(2d);

    private final double pitchRatio; // These values can probably be calculated instead of stored eventually

    Note(double pitchRatio) {
        this.pitchRatio = pitchRatio;
    }

    public double getPitchRatio() {
        return pitchRatio;
    }

    public NBTTagCompound writeToNbt() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setString("notename", name());
        return compound;
    }

    public Color getColor() {
        return Color.getHSBColor((float) ordinal() / values().length, 1, 1);
    }

    public static Try<Note> readFromNbt(NBTTagCompound noteCompound) {
        return Try.of(() -> Note.valueOf(noteCompound.getString("notename")));
    }
}
