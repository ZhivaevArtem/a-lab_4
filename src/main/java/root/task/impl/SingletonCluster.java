package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;

import java.util.Collections;
import java.util.List;

public class SingletonCluster implements ICluster {
    private final IPoint point;

    public SingletonCluster(IPoint point) {
        this.point = point;
    }

    @Override
    public List<ICluster> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public List<IPoint> getPoints() {
        return Collections.singletonList(point);
    }

    @Override
    public List<IPoint> findRoute() {
        return Collections.singletonList(point);
    }

    @Override
    public int getDepth() {
        return 1;
    }

    @Override
    public double getX() {
        return point.getX();
    }

    @Override
    public double getY() {
        return point.getY();
    }
}
