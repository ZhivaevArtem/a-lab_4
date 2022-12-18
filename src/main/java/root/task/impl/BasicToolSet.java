package root.task.impl;

import root.task.ICluster;
import root.task.IPoint;
import root.util.CyclicList;
import root.util.MathUtils;
import root.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class BasicToolSet extends AToolSet {
    protected final int clusterPower;

    public BasicToolSet(int clusterPower) {
        this.clusterPower = clusterPower;
    }

    @Override
    public List<ICluster> findRoute(List<ICluster> clusters) {
        List<ICluster> unused = new LinkedList<>(clusters);
        List<ICluster> route = new ArrayList<>(clusters.size());
        route.add(unused.remove(0));

        while (!unused.isEmpty()) {
            ICluster next = getClosestCluster(unused, route.get(route.size() - 1));
            route.add(next);
            unused.remove(next);
        }

        return route;
    }

    @Override
    public List<IPoint> join(List<List<IPoint>> groups) {
        if (groups.size() == 1) return groups.stream().flatMap(List::stream).toList();
        List<IPoint> out = new ArrayList<>(groups.stream().map(List::size).reduce(Integer::sum).get());
        out.addAll(groups.get(0));
        out = new CyclicList<>(out);

        for (List<IPoint> group : groups.stream().skip(1).toList()) {
            group = new CyclicList<>(group);
            IPoint outEnd = null, groupStart = null;
            double minDistance = Double.MAX_VALUE;
            for (IPoint outPoint : out) {
                for (IPoint groupPoint : group) {
                    double distance = MathUtils.getDistance(outPoint, groupPoint);
                    if (distance < minDistance) {
                        minDistance = distance;
                        outEnd = outPoint;
                        groupStart = groupPoint;
                    }
                }
            }
            List<IPoint> newOut = new ArrayList<>(out.size());
            int outGap = out.indexOf(outEnd) + 1;
            int groupGap = group.indexOf(groupStart);
            for (int i = 0; i < out.size(); i++) {
                newOut.add(out.get(outGap + i));
            }
            for (int i = 0; i < group.size(); i++) {
                newOut.add(group.get(groupGap + i));
            }
            out = new CyclicList<>(newOut);
        }

        return out;
    }

    @Override
    public IPoint getCenter(Cluster cluster) {
        double x = 0, y = 0;
        for (ICluster child : cluster.getChildren()) {
            x += child.getX();
            y += child.getY();
        }
        return new Point(x / cluster.getChildren().size(), y / cluster.getChildren().size());
    }

    @Override
    protected List<ICluster> doReduce(List<ICluster> clusters) {
        int clustersCount = (clusters.size() - 1) / clusterPower + 1;
        if (clustersCount == 1) return Collections.singletonList(new Cluster(clusters, this));

        List<ICluster> hearths = new ArrayList<>(clustersCount);
        List<ICluster> unused = new LinkedList<>(clusters);

        Pair<ICluster, ICluster> twoMostDistant = getTwoMostDistant(unused);
        hearths.add(twoMostDistant.getFirst());
        hearths.add(twoMostDistant.getSecond());
        unused.removeAll(hearths);

        while (hearths.size() < clustersCount) {
            ICluster mostDistant = getMostDistant(unused, hearths);
            hearths.add(mostDistant);
            unused.remove(mostDistant);
        }

        List<List<ICluster>> groups = hearths.stream().map((hearth) -> {
            List<ICluster> group = new ArrayList<>(clusterPower);
            group.add(hearth);
            return group;
        }).toList();

        while (!unused.isEmpty()) {
            ICluster cluster = unused.get(0);
            if (addClusterToGroup(groups.stream()
                                        .filter((g) -> g.size() < clusterPower)
                                        .sorted((g1, g2) -> MathUtils.sign(getDistance(cluster, g1.get(0)) - getDistance(cluster, g2.get(0))))
                                        .toList(),
                    cluster, unused)) {
                unused.remove(cluster);
                continue;
            }
            unused.add(unused.remove(0));
        }

        return groups.stream().map((group) -> new Cluster(group, this)).map(ICluster.class::cast).toList();
    }

    protected double getDistance(ICluster cluster1, ICluster cluster2) {
        return MathUtils.getDistance(cluster1, cluster2);
    }

    private boolean addClusterToGroup(List<List<ICluster>> groups, ICluster cluster, List<ICluster> unused) {
        for (List<ICluster> group : groups) {
            if (getClosestCluster(unused, group.get(0)) == cluster) {
                group.add(cluster);
                return true;
            }
        }
        return false;
    }

    private ICluster getClosestCluster(List<ICluster> clusters, ICluster hearth) {
        double minDistance = Double.MAX_VALUE;
        ICluster cluster = null;
        for (ICluster c : clusters) {
            double distance = getDistance(c, hearth);
            if (distance < minDistance) {
                cluster = c;
                minDistance = distance;
            }
        }
        return cluster;
    }

    private List<ICluster> getClosestGroup(List<List<ICluster>> groups, ICluster cluster) {
        double minDistance = Double.MAX_VALUE;
        List<ICluster> group = null;
        for (List<ICluster> g : groups) {
            double distance = getDistance(g.get(0), cluster);
            if (distance < minDistance) {
                minDistance = distance;
                group = g;
            }
        }
        return group;
    }

    private Pair<ICluster, ICluster> getTwoMostDistant(List<ICluster> clusters) {
        ICluster cluster1 = null, cluster2 = null;
        double maxDistance = 0;
        for (int i = 0; i < clusters.size(); i++) {
            ICluster c1 = clusters.get(i);
            for (int j = i + 1; j < clusters.size(); j++) {
                ICluster c2 = clusters.get(j);
                double distance = getDistance(c1, c2);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    cluster1 = c1;
                    cluster2 = c2;
                }
            }
        }
        return Pair.of(cluster1, cluster2);
    }

    private ICluster getMostDistant(List<ICluster> clusters, List<ICluster> hearths) {
        double maxDistance = 0;
        ICluster cluster = null;

        for (ICluster c : clusters) {
            double distance = hearths.stream().map((o) -> getDistance(o, c)).reduce(Double::sum).get();
            if (distance > maxDistance) {
                maxDistance = distance;
                cluster = c;
            }
        }

        return cluster;
    }
}
