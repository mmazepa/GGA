import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.awt.Polygon;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static int hospitalsAmount = 0;

  public static ArrayList<Point> hospitals = new ArrayList<Point>();
  public static double furthestDistance = 0.0;
  public static double optimizedDistance = 0.0;

  public static ArrayList<Double> furthestDistances = new ArrayList<Double>();
  public static ArrayList<Double> optimizedDistances = new ArrayList<Double>();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
      if (args.length > 1) {
        try {
          hospitalsAmount = Integer.parseInt(args[1]);
        } catch (NumberFormatException nfe) {
          exitOnPurpose("Ilość szpitali nieprawidłowa, oczekiwano liczby naturalnej.");
        }
      } else {
        exitOnPurpose("Nie podano ilości szpitali.");
      }
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static void calculateOptimizedDistance() {
    for (Point point : points) {
      System.out.println("---");
      for (Point hospital : hospitals) {
        double dist = pm.getDistance(point, hospital);
        if (optimizedDistance != 0.0)
          optimizedDistance = Math.min(optimizedDistance, dist);
        else
          optimizedDistance = dist;
        System.out.println(point + " -> " + hospital + " : " + dist);
      }
      optimizedDistances.add(optimizedDistance);
      optimizedDistance = 0.0;
    }
    System.out.println("---");
    optimizedDistance = pm.getMaximum(optimizedDistances);
  }

  public static void calculateNewHospitals(int k) {
    for (int j = 1; j < k; j++) {
      Point furthest = hospitals.get(j-1);
      int counter = 1;
      for (Point point : points) {
        double dist = pm.getGroupDistance(hospitals, point);
        System.out.println("   [" + (counter) + "] " + point + " : " + dist);
        if (dist > furthestDistance) {
          if (furthestDistance != 0.0)
            furthestDistance = Math.max(dist, furthestDistance);
          else
            furthestDistance = dist;
          furthest = point;
        }
        counter++;
      }
      furthestDistances.add(furthestDistance);
      furthestDistance = 0.0;

      hospitals.add(furthest);
      points.remove(furthest);
      System.out.println("SZPITAL "  + (j) + ": " + furthest);
    }
    furthestDistance = pm.getMinimum(furthestDistances);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    if (hospitalsAmount > points.size()) {
      vm.title("Punktów: " + points.size() + ", Szpitali: " + hospitalsAmount, horizontalLength);
      exitOnPurpose("Szpitali nie może być więcej niż miast.");
    }

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM LOKALIZACJI SZPITALI", horizontalLength);

    int k = hospitalsAmount;
    Point firstHospital = points.get(0);

    hospitals.add(firstHospital);
    points.remove(firstHospital);
    System.out.println("SZPITAL "  + 1 + ": " + firstHospital);

    calculateNewHospitals(k);
    calculateOptimizedDistance();

    vm.horizontalLine(horizontalLength);

    vm.title("ODLEGŁOŚCI", horizontalLength);
    System.out.println("   ODLEGŁOŚĆ _________: " + furthestDistance);
    System.out.println("   ODLEGŁOŚĆ OPTYMALNA: " + optimizedDistance);

    vm.title("SZPITALE", horizontalLength);
    int hospitalCount = 1;
    for (Point hospital : hospitals) {
      System.out.println("   SZPITAL " + hospitalCount + ": " + hospital);
      hospitalCount++;
    }

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
