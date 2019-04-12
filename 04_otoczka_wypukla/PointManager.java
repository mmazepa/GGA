import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PointManager {
  static VisualManager vm = new VisualManager();

  public static void checkPoints(ArrayList<Point> points) {
    if (points.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayPoints(ArrayList<Point> points) {
    vm.title("Number of points: " + points.size(), App.horizontalLength);
    for (Point point : points) {
      System.out.println("   " + point + ", " + point.getAlpha());
    }
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
