package root.task.impl;

import root.task.IPoint;
import root.util.FormatUtils;

public class Point implements IPoint {
    private final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(IPoint point) {
        x = point.getX();
        y = point.getY();
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public String toString() {
        return String.format("(%s; %s)", FormatUtils.formatDouble(x), FormatUtils.formatDouble(y));
    }

    @Override
    public double getY() {
        return y;
    }
}
