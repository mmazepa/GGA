import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static int hospitalsAmount = 0;

  public static ArrayList<Point> hospitals = new ArrayList<Point>();
  public static double furthestDistance = 0.0;
  public static double optimizedDistance = 0.0;

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
    calculateNewHospitals(k);
    calculateOptimizedDistance();
  }

  public static void calculateOptimizedDistance() {
    for (Point point : points) {
      double dist = calculateOptimizedDistanceForOnePoint(point, hospitals.get(hospitals.size()-1));
      optimizedDistance = Math.max(optimizedDistance, dist);
    }
  }

  public static double calculateOptimizedDistanceForOnePoint(Point point, Point hospital) {
    double optimizedDistance = point.getDist();
    double dist = pm.getDistance(point, hospital);
    if (optimizedDistance != 0.0)
      optimizedDistance = Math.min(optimizedDistance, dist);
    else
      optimizedDistance = dist;
    point.setDist(optimizedDistance);
    return optimizedDistance;
  }

  public static void calculateNewHospitals(int k) {
    for (int j = 1; j < k; j++) {
      Point furthest = new Point();
      int counter = 1;
      for (Point point : points) {
        double dist = calculateOptimizedDistanceForOnePoint(point, hospitals.get(j-1));
        if (furthestDistance < dist) {
          System.out.println("     *[" + (counter) + "] " + point + " : " + dist);
          furthestDistance = dist;
          furthest = point;
        } else {
          System.out.println("      [" + (counter) + "] " + point + " : " + dist);
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

    vm.title("SZPITALE: " + hospitals.size(), horizontalLength);
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
