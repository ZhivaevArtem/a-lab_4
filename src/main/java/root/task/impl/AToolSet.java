package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;
import root.task.IToolSet;

import java.util.Collections;
import java.util.List;

public abstract class AToolSet implements IToolSet {
    private final int maxDepth = 32;

    @Override
    public final ICluster reduce(List<IPoint> points) {
        List<ICluster> clusters = points.stream().map(SingletonCluster::new).map(ICluster.class::cast).toList();

        while (clusters.size() > 1) {
            clusters = doReduce(clusters);
            if (clusters.get(0).getDepth() >= maxDepth - 1) {
                clusters = Collections.singletonList(new Cluster(clusters, this));
            }
        }

        return clusters.get(0);
    }

    protected abstract List<ICluster> doReduce(List<ICluster> clusters);
}
