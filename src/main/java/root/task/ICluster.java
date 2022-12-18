package root.task;

import java.util.List;

public interface ICluster extends IPoint {
    List<ICluster> getChildren();

    List<IPoint> getPoints();

    List<IPoint> findRoute();

    int getDepth();
}
