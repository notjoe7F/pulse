package notjoe.pulse.common.util;

import io.vavr.collection.Stream;
import io.vavr.collection.Vector;
import net.minecraft.util.math.Vec3d;

public final class Geometry {
    private Geometry() { }

    public static Vector<Vec3d> nPointsBetween(Vec3d source, Vec3d dest, int steps) {
        double distance = source.distanceTo(dest);
        Vec3d unitFromFirstToSecond = dest.subtract(source).normalize();
        double stepSize = distance / steps;
        return Stream.range(0, steps)
                .map(i -> source.add(unitFromFirstToSecond.add(unitFromFirstToSecond.scale(stepSize * i))))
                .toVector();
    }
}
