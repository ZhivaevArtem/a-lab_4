package root;

import root.model.TaskData;
import root.model.TaskSolution;
import root.solver.Solver;
import root.task.IToolSet;
import root.task.impl.BasicToolSet;
import root.task.impl.MyToolSet;
import root.task.impl.ToolSetAssert;
import root.util.CollectionUtils;
import root.util.TaskReader;

public class Main {
    public static void main(String[] args) {
        for (TaskData taskData : new TaskReader().readTasks()) {
            IToolSet basicToolSet = new ToolSetAssert(new BasicToolSet(4));
            TaskSolution basicSolution = new Solver(basicToolSet).solve(taskData);

            IToolSet myToolSet = new ToolSetAssert(new MyToolSet(4));
            TaskSolution mySolution = new Solver(myToolSet).solve(taskData);

            assert CollectionUtils.sameElements(taskData.points(), basicSolution.route());

            String taskName = taskData.name();
            System.out.println("taskName = " + taskName);
            System.out.println("basicSolution.getPath() = " + basicSolution.getPath());
            System.out.println("mySolution.getPath() = " + mySolution.getPath());
            double boost = basicSolution.getPath() / mySolution.getPath();
            System.out.println("boost = " + boost);
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
        }
    }
}
