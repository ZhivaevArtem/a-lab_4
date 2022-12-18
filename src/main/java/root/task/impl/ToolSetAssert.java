package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;
import root.task.IToolSet;
import root.util.CollectionUtils;

import java.util.List;

public class ToolSetAssert implements IToolSet {
    private final IToolSet toolSet;

    public ToolSetAssert(IToolSet toolSet) {
        this.toolSet = toolSet;
    }

    @Override
    public List<ICluster> findRoute(List<ICluster> clusters) {
        List<ICluster> out = toolSet.findRoute(clusters);

        assert CollectionUtils.sameElements(clusters, out);

        return out;
    }

    @Override
    public List<IPoint> join(List<List<IPoint>> groups) {
        List<IPoint> out = toolSet.join(groups);
        List<IPoint> all = groups.stream().flatMap(List::stream).toList();

        assert CollectionUtils.sameElements(out, all);

        return out;
    }

    @Override
    public IPoint getCenter(Cluster cluster) {
        return toolSet.getCenter(cluster);
    }

    @Override
    public ICluster reduce(List<IPoint> points) {
        ICluster out = toolSet.reduce(points);

        assert CollectionUtils.sameElements(out.getPoints(), points);

        return out;
    }
}
