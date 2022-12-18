package root.model;

import root.task.IPoint;

import java.util.List;
import java.util.stream.Collectors;

public record TaskData(List<IPoint> points, String name) {
    @Override
    public String toString() {
        return points.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
}
