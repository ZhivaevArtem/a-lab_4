package root.solver;

import root.model.TaskData;
import root.model.TaskSolution;
import root.task.ICluster;
import root.task.IPoint;
import root.task.IToolSet;
import root.util.CollectionUtils;

import java.util.List;

public class Solver {
    private final IToolSet toolSet;

    public Solver(IToolSet toolSet) {
        this.toolSet = toolSet;
    }

    public TaskSolution solve(TaskData taskData) {
        List<IPoint> points = taskData.points();
        ICluster masterCluster = toolSet.reduce(points);
        List<IPoint> route = masterCluster.findRoute();

        assert CollectionUtils.sameElements(route, points);

        return new TaskSolution(route);
    }
}
