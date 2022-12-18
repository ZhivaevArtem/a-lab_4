package root.model;

import root.task.IPoint;
import root.util.MathUtils;

import java.util.List;

public record TaskSolution(List<IPoint> route) {
    public double getPath() {
        double path = 0;
        IPoint last = this.route.get(0);
        for (IPoint point : this.route.stream().skip(1).toList()) {
            path += MathUtils.getDistance(last, point);
            last = point;
        }
        return path;
    }

    @Override
    public String toString() {
        return String.format("%s %s", getPath(), route());
    }
}
