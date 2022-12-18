package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;
import root.util.MathUtils;

import java.util.LinkedList;
import java.util.List;

public class MyToolSet extends BasicToolSet {
    public MyToolSet(int clusterPower) {
        super(clusterPower);
    }

    @Override
    protected double getDistance(ICluster cluster1, ICluster cluster2) {
        List<IPoint> points1 = cluster1.getPoints();
        List<IPoint> points2 = cluster2.getPoints();
        double minDistance = Double.MAX_VALUE;
        for (IPoint p1 : points1) {
            for (IPoint p2 : points2) {
                double distance = MathUtils.getDistance(p1, p2);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    @Override
    public IPoint getCenter(Cluster cluster) {
        return cluster.getChildren().get(0);
    }

    @Override
    protected List<ICluster> doReduce(List<ICluster> clusters) {
        double radius = (getMaxDistance(clusters) + getMinDistance(clusters)) / 2;
        List<List<ICluster>> groups = new LinkedList<>();

        List<ICluster> unused = new LinkedList<>(clusters);

        while (!unused.isEmpty()) {
            ICluster cluster = unused.get(0);
            getGroupToAdd(groups, cluster, radius).add(cluster);
            unused.remove(cluster);
        }

        return groups.stream().map((group) -> new Cluster(group, this)).map(ICluster.class::cast).toList();
    }

    private List<ICluster> getGroupToAdd(List<List<ICluster>> groups, ICluster cluster, double radius) {
        List<ICluster> toAdd = null;
        double minDistance = 0;
        for (List<ICluster> group : groups.stream().filter((group) -> MathUtils.getDistance(group.get(0), cluster) < radius).toList()) {
            double distance = getDistance(group.get(0), cluster);
            if (distance < minDistance) {
                minDistance = distance;
                toAdd = group;
            }
        }
        if (toAdd == null) {
            toAdd = new LinkedList<>();
            groups.add(toAdd);
        }
        return toAdd;
    }

    private double getMaxDistance(List<ICluster> clusters) {
        double maxDistance = 0;
        for (int i = 0; i < clusters.size(); i++) {
            ICluster c1 = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                ICluster c2 = clusters.get(j);
                double distance = getDistance(c1, c2);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }
        return maxDistance;
    }

    private double getMinDistance(List<ICluster> clusters) {
        double minDistance = 0;
        for (int i = 0; i < clusters.size(); i++) {
            ICluster c1 = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                ICluster c2 = clusters.get(j);
                double distance = getDistance(c1, c2);
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }
}
