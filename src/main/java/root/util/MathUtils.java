package root.util;

import root.task.IPoint;

public class MathUtils {
    public static double getDistance(IPoint p1, IPoint p2) {
        double a = p1.getX() - p2.getX();
        double b = p1.getY() - p2.getY();
        return Math.sqrt(a * a + b * b);
    }

    public static int sign(double d) {
        if (d < 0) return -1;
        if (d > 0) return 1;
        return 0;
    }
}
