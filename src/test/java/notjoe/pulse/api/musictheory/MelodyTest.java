package notjoe.pulse.api.musictheory;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static notjoe.pulse.api.musictheory.Note.*;

class MelodyTest {
    @Test
    void GivenTwoEqualMelodies_EqualsReturnsTrue() {
        Vector<Tuple2<Integer, Note>> content = Vector.of(
                Tuple.of(3, C_LOW),
                Tuple.of(2, D_FLAT),
                Tuple.of(1, D)
        );

        Melody first = new Melody(content);
        Melody second = new Melody(content);

        Assert.assertTrue("Equality works forwards", first.equals(second));
        Assert.assertTrue("Equality works backwards", second.equals(first));
    }

    @Test
    void GivenTwoEmptyMelodies_EqualsReturnsTrue() {
        Assert.assertTrue("Two empty melodies are equal", Melody.empty().equals(Melody.empty()));
    }

    @Test
    void GivenTwoDifferentMelodies_EqualsReturnsFalse() {
        Vector<Tuple2<Integer, Note>> content = Vector.of(
                Tuple.of(3, A),
                Tuple.of(2, E),
                Tuple.of(7, C_HIGH)
        );

        Melody first = Melody.empty();
        Melody second = new Melody(content);

        Assert.assertFalse("Equality works forwards", first.equals(second));
        Assert.assertFalse("Equality works backwards", second.equals(first));
    }

    @Test
    void VarargsConstructorYieldsSameResultAsNormalConstructor() {
        Vector<Tuple2<Integer, Note>> content = Vector.of(
                Tuple.of(3, A),
                Tuple.of(2, E),
                Tuple.of(7, C_HIGH)
        );

        Melody first = new Melody(content);
        Melody second = new Melody(
                3, A,
                2, E,
                7, C_HIGH
        );

        Assert.assertTrue("Varargs constructor works", first.equals(second));
    }

    @Test
    void GivenOddCountVarargsArguments_ConstructorThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Melody(1, A, 1));
    }
}