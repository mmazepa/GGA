import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.awt.Polygon;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static ArrayList<Point> hospitals = new ArrayList<Point>();
  public static double furthestDistance = 0.0;

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM LOKALIZACJI SZPITALI", horizontalLength);

    int k = 3;
    hospitals.add(points.get(0));

    for (int j = 1; j < k; j++) {
      Point furthest = points.get(1);
      for (int i = 2; i < points.size(); i++) {
        double dist = pm.getDistance(points.get(i), furthest);
        if (dist > furthestDistance) {
          if (furthestDistance != 0.0)
            furthestDistance = Math.min(dist, furthestDistance);
          else
            furthestDistance = dist;
          furthest = points.get(i);
        }
      }
      hospitals.add(furthest);
    }

    System.out.println("DIST: " + furthestDistance);
    for (Point hospital : hospitals) {
      System.out.println("   H: " + hospital);
    }

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
