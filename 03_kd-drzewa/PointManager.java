import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PointManager {
  public static void displayHalfPoints(String sign, ArrayList<Point> points) {
    System.out.print("   " + sign + ": ");
    for (Point point : points) {
      double value = 0;
      if (sign.equals("X")) value = point.getX();
      else if (sign.equals("Y")) value = point.getY();

      if (value < 10) System.out.print(" ");
      System.out.print(value + " ");
    }
    System.out.print("\n");
  }

  public static void displayPoints(ArrayList<Point> points) {
    System.out.println("Number of points: " + points.size());
    displayHalfPoints("X", points);
    displayHalfPoints("Y", points);
  }

  public static void checkPoints(ArrayList<Point> points) {
    if (!checkIfDifferentAbscissaeAndOrdinates(points)) {
      App.exitOnPurpose("Punkty nie mają różnych odciętych i/lub rzędnych!");
    }

    if (points.size() == 0) App.exitOnPurpose("Nie podano punktów.");
  }

  public static boolean checkIfDifferentAbscissaeAndOrdinates(ArrayList<Point> points) {
    boolean isValid = true;
    for (int i = 0; i < points.size(); i++) {
      for (int j = 0; j < points.size(); j++) {
        if (i != j) {
          Point point1 = points.get(i);
          Point point2 = points.get(j);
          if (point1.getX() == point2.getX() || point1.getY() == point2.getY()) {
            System.out.println(point1.toString() + ", " + point2.toString());
            isValid = false;
          }
        }
      }
    }
    return isValid;
  }

  public static ArrayList<Point> sortByX(ArrayList<Point> pointsByX) {
    Collections.sort(pointsByX, new Comparator<Point>() {
      @Override
      public int compare(Point p1, Point p2) {
        if ((p1.getX() < p2.getX()) || (p1.getX() == p2.getX() && p1.getY() < p2.getY()))
          return -1;
        else if (p1.getX() > p2.getX())
          return 1;
        else
          return 0;
      }
    });
    return pointsByX;
  }

  public static ArrayList<Point> sortByY(ArrayList<Point> pointsByY) {
    Collections.sort(pointsByY, new Comparator<Point>() {
      @Override
      public int compare(Point p1, Point p2) {
        if ((p1.getY() < p2.getY()) || (p1.getY() == p2.getY() && p1.getX() < p2.getX()))
          return -1;
        else if (p1.getY() > p2.getY())
          return 1;
        else
          return 0;
      }
    });
    return pointsByY;
  }
}
