package root.task;

import root.task.impl.Cluster;

import java.util.List;

public interface IToolSet {
    List<ICluster> findRoute(List<ICluster> clusters);

    List<IPoint> join(List<List<IPoint>> groups);

    IPoint getCenter(Cluster cluster);

    ICluster reduce(List<IPoint> points);
}
