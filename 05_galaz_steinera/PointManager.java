import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PointManager {
  static VisualManager vm = new VisualManager();

  public static void checkPoints(ArrayList<Point> points) {
    if (points.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

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
    vm.title("Number of points: " + points.size(), App.horizontalLength);
    displayHalfPoints("X", points);
    displayHalfPoints("Y", points);
  }

  public static double sumXY(Point point) {
    return point.getX() + point.getY();
  }

  public boolean equals(Point p1, Point p2) {
    if (p1.getX() != p2.getX()) return false;
    if (p1.getY() != p2.getY()) return false;
    return true;
  }

  public static ArrayList<Point> sortPoints(ArrayList<Point> pointsByY) {
    Collections.sort(pointsByY, new Comparator<Point>() {
      @Override
      public int compare(Point p1, Point p2) {
        if (sumXY(p1) > sumXY(p2) || (sumXY(p1) == sumXY(p2) && p1.getX() < p2.getX()))
          return -1;
        else if (sumXY(p1) < sumXY(p2))
          return 1;
        else
          return 0;
      }
    });
    return pointsByY;
  }
}
