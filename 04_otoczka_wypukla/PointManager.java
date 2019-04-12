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
      else if (sign.equals("D")) value = point.getD();
      else if (sign.equals("A")) value = point.getAlpha();

      if (value < 10) System.out.print(" ");
      if ((sign.equals("D") || sign.equals("A")) && value == 0.0)
        System.out.print("  - ");
      else
        System.out.print(value + " ");
    }
    System.out.print("\n");
  }

  public static void displayPoints(ArrayList<Point> points) {
    vm.title("Number of points: " + points.size(), App.horizontalLength);
    displayHalfPoints("X", points);
    displayHalfPoints("Y", points);
    displayHalfPoints("D", points);
    displayHalfPoints("A", points);
  }

  public static Point findMin(ArrayList<Point> points) {
    Point minPoint = points.get(0);
    for (int i = 1; i < points.size(); i++) {
      if (minPoint.getY() < points.get(i).getY()) {
        minPoint = minPoint;
      } else if (minPoint.getY() == points.get(i).getY()
      && minPoint.getX() < points.get(i).getX()) {
        minPoint = minPoint;
      } else {
        minPoint = points.get(i);
      }
    }
    return minPoint;
  }

  public static ArrayList<Point> sortByAlpha(ArrayList<Point> pointsByAlpha) {
    Collections.sort(pointsByAlpha, new Comparator<Point>() {
      @Override
      public int compare(Point p1, Point p2) {
        if (p1.getAlpha() < p2.getAlpha() || (p1.getAlpha() == p2.getAlpha() && p1.getX() < p2.getX()))
          return -1;
        else if (p1.getAlpha() > p2.getAlpha())
          return 1;
        else
          return 0;
      }
    });
    return pointsByAlpha;
  }
}
