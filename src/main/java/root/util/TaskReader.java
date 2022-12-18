package root.util;

import root.Main;
import root.model.TaskData;
import root.task.IPoint;
import root.task.impl.Point;

import java.io.File;
import java.util.*;
import java.util.stream.Stream;

public class TaskReader {
    public List<TaskData> readTasks() {
        String resourcePath = Main.class.getClassLoader().getResource("").getPath();
        List<File> files = Stream.of(new File(resourcePath).listFiles())
                .filter(File::isFile)
                .toList();
        return files.stream().map((file) -> {
            try {
                Scanner scanner = new Scanner(new File(file.getAbsolutePath())).useLocale(Locale.US);

                String name = scanner.nextLine().replaceAll(" ", "").replace("NAME:", "");
                while (!scanner.nextLine().startsWith("TYPE"));
                int dimension = Integer.parseInt(scanner.nextLine().replaceAll(" ", "").replace("DIMENSION:", ""));
                while (!scanner.nextLine().equals("NODE_COORD_SECTION"));

                List<IPoint> points = new ArrayList<>(dimension);
                for (int i = 0; i < dimension; i++) {
                    scanner.nextInt();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();
                    points.add(new Point(x, y));
                }

                return new TaskData(points, name);
            } catch (Throwable e) {
                return null;
            }
        }).sorted(Comparator.comparing((taskData) -> taskData.points().size())).toList();
    }
}
