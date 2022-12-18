package root;

import root.model.TaskData;
import root.model.TaskSolution;
import root.solver.Solver;
import root.task.IToolSet;
import root.task.impl.BasicToolSet;
import root.task.impl.ToolSetAssert;
import root.util.CollectionUtils;
import root.util.FormatUtils;
import root.util.TaskReader;

public class Main {
    public static void main(String[] args) {
        for (TaskData taskData : new TaskReader().readTasks()) {
            IToolSet toolSet = new ToolSetAssert(new BasicToolSet(4));
            TaskSolution solution = new Solver(toolSet).solve(taskData);
            assert CollectionUtils.sameElements(taskData.points(), solution.route());
            String taskName = taskData.name();
            double path = solution.getPath();
            System.out.println(taskName + ": " + FormatUtils.formatDouble(path));
            System.out.println("========================================================");
//            System.out.println(solution.route().size() + " " + solution.route().stream().map((point) -> point.getX() + " " + point.getY()).collect(Collectors.joining(" ")));
            System.out.println("========================================================");
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}
