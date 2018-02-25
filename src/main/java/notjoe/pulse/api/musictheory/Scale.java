package notjoe.pulse.api.musictheory;

public interface Scale {
    Note offset(Note start, int amount);
    String getUnlocalizedName();
}
