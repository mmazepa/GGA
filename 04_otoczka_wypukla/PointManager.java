import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PointManager {
  static VisualManager vm = new VisualManager();

  public static void checkPoints(ArrayList<Point> points) {
    if (points.size() == 0) App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayPoints(ArrayList<Point> points) {
    vm.title("Liczba punktów: " + points.size(), App.horizontalLength);
    for (Point point : points)
      System.out.println("   " + point + ", " + point.getAlpha());
  }

  public static double getDistance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
  }

  public static boolean comparePoints(Point p1, Point p2) {
    if (p1.getY() < p2.getY())
      return true;
    else if (p1.getY() == p2.getY() && p1.getX() < p2.getX())
      return true;
    else
      return false;
  }

  public static Point findMin(ArrayList<Point> points) {
    Point minPoint = points.get(0);
    for (int i = 1; i < points.size(); i++) {
      if (!comparePoints(minPoint, points.get(i)))
        minPoint = points.get(i);
    }
    return minPoint;
  }

  public static ArrayList<Point> sortByAlpha(ArrayList<Point> pointsByAlpha) {
    MergeSort ms = new MergeSort();
    return ms.mergeSort(pointsByAlpha);
  }
}
