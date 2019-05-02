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
  public static double optimizedDistance = 0.0;

  public static ArrayList<Double> furthestDistances = new ArrayList<Double>();

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
    Point firstHospital = points.get(0);
    hospitals.add(firstHospital);
    points.remove(firstHospital);
    System.out.println("HOSPITAL "  + 1 + ": " + firstHospital);

    for (int j = 1; j < k; j++) {
      Point furthest = hospitals.get(j-1);
      furthestDistance = 0.0;
      // System.out.println("HOSPITAL "  + j + ": " + furthest);
      for (int i = 0; i < points.size(); i++) {
        double dist = pm.getGroupDistance(hospitals, points.get(i));
        System.out.println("   [" + (i+1) + "] " + points.get(i) + " : " + dist);
        if (dist > furthestDistance) {
          if (furthestDistance != 0.0)
            furthestDistance = Math.max(dist, furthestDistance);
          else
            furthestDistance = dist;
          furthest = points.get(i);
        }
      }

      furthestDistances.add(furthestDistance);
      optimizedDistance = pm.getMinimum(furthestDistances);

      hospitals.add(furthest);
      points.remove(furthest);
      System.out.println("HOSPITAL "  + (j+1) + ": " + furthest);
    }

    vm.horizontalLine(horizontalLength);

    vm.title("ODLEGŁOŚCI", horizontalLength);
    System.out.println("   DIST_FUR: " + furthestDistance);
    System.out.println("   DIST_OPT: " + optimizedDistance);

    vm.title("SZPITALE", horizontalLength);
    for (Point hospital : hospitals) {
      System.out.println("   H: " + hospital);
    }

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
