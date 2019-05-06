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

  public static void calculateNewHospitalsWithOpimizedDistance(int k) {
    if (k > 1 && k <= points.size()+1)
      calculateNewHospitals(k);
    calculateOptimizedDistance();
  }

  public static void calculateOptimizedDistance() {
    for (Point point : points) {
      for (Point hospital : hospitals) {
        double dist = pm.getDistance(point, hospital);
        if (furthestDistance < dist) {
          if (optimizedDistance != 0.0)
            optimizedDistance = Math.min(optimizedDistance, dist);
          else
            optimizedDistance = dist;
        }
      }
      optimizedDistances.add(optimizedDistance);
      optimizedDistance = 0.0;
    }
    optimizedDistance = pm.getMaximum(optimizedDistances);
  }

  public static double calculateOptimizedDistanceForOnePoint(Point point) {
    double optimizedDistance = 0.0;
    for (Point hospital : hospitals) {
      double dist = pm.getDistance(point, hospital);
      if (optimizedDistance != 0.0)
        optimizedDistance = Math.min(optimizedDistance, dist);
      else
        optimizedDistance = dist;
    }
    return optimizedDistance;
  }

  public static void calculateNewHospitals(int k) {
    for (int j = 1; j < k; j++) {
      Point furthest = hospitals.get(j-1);
      int counter = 1;
      for (Point point : points) {
        double dist = calculateOptimizedDistanceForOnePoint(point);
        if (furthestDistance < dist) {
          System.out.println("      [" + (counter) + "] " + point + " : " + dist);
          furthestDistance = dist;
          furthest = point;
        }
        counter++;
      }
      furthestDistance = 0.0;

      hospitals.add(furthest);
      points.remove(furthest);
      System.out.println("   SZPITAL "  + (j+1) + ": " + furthest);
    }
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

    if (hospitalsAmount < 1) exitOnPurpose("Nie może być mniej niż 1 szpital!");

    if (hospitalsAmount == points.size()) {
      hospitals.addAll(points);
      points.clear();
    } else {
      Point firstHospital = points.get(0);
      hospitals.add(firstHospital);
      points.remove(firstHospital);
      System.out.println("   SZPITAL "  + 1 + ": " + firstHospital);

      calculateNewHospitalsWithOpimizedDistance(hospitalsAmount);
    }

    vm.horizontalLine(horizontalLength);

    vm.title("SZPITALE", horizontalLength);
    int hospitalCount = 1;
    for (Point hospital : hospitals) {
      System.out.println("   SZPITAL " + hospitalCount + ": " + hospital);
      hospitalCount++;
    }

    vm.displayFramed("r = " + optimizedDistance);

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
