package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;

import java.util.Collections;
import java.util.List;

public class DummyToolSet extends AToolSet {
    @Override
    public List<ICluster> findRoute(List<ICluster> clusters) {
        return clusters;
    }

    @Override
    public List<IPoint> join(List<List<IPoint>> groups) {
        return groups.stream().flatMap(List::stream).toList();
    }

    @Override
    public IPoint getCenter(Cluster cluster) {
        return new Point(cluster.getChildren().get(0));
    }

    @Override
    protected List<ICluster> doReduce(List<ICluster> clusters) {
        return Collections.singletonList(new Cluster(clusters, this));
    }
}
