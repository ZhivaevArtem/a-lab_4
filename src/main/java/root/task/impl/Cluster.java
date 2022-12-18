package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;
import root.task.IToolSet;

import java.util.List;

public class Cluster implements ICluster {
    private final List<ICluster> children;
    private final IToolSet toolSet;
    private final IPoint center;

    public Cluster(List<ICluster> children, IToolSet toolSet) {
        this.children = children;
        this.toolSet = toolSet;
        this.center = toolSet.getCenter(this);
    }

    @Override
    public List<ICluster> getChildren() {
        return children;
    }

    @Override
    public List<IPoint> getPoints() {
        return children.stream()
                .map(ICluster::getPoints)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public List<IPoint> findRoute() {
        List<ICluster> path = toolSet.findRoute(children);
        List<List<IPoint>> groups = path.stream().map(ICluster::findRoute).toList();
        return toolSet.join(groups);
    }

    @Override
    public int getDepth() {
        return children.get(0).getDepth() + 1;
    }

    @Override
    public double getX() {
        return center.getX();
    }

    @Override
    public double getY() {
        return center.getY();
    }
}
