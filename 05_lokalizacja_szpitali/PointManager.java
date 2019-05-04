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
    vm.title("Liczba punktów: " + points.size(), App.horizontalLength);
    displayHalfPoints("X", points);
    displayHalfPoints("Y", points);
  }

  public static double getDistance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
  }

  public static Point getCentroid(ArrayList<Point> hospitals) {
    double centroidX = 0.0;
    double centroidY = 0.0;
    for (Point hospital : hospitals) {
      centroidX += hospital.getX();
      centroidY += hospital.getY();
    }
    return new Point(centroidX/hospitals.size(), centroidY/hospitals.size());
  }

  public static double getGroupDistance(ArrayList<Point> hospitals, Point point) {
    Point centroid = getCentroid(hospitals);
    return getDistance(centroid, point);
  }

  public static double getMinimum(ArrayList<Double> distances) {
    double minimum = distances.get(0);
    for (Double distance : distances) {
      System.out.println(distance + " < " + minimum + " ? " + (distance < minimum));
      if (distance < minimum) minimum = distance;
    }
    return minimum;
  }

  public static double getMinimumIndex(ArrayList<Double> distances) {
    double minimum = distances.get(0);
    int index = 0;
    for (int i = 0; i < distances.size(); i++) {
      System.out.println(distances.get(i) + " < " + minimum + " ? " + (distances.get(i) < minimum));
      if (distances.get(i) < minimum) {
        minimum = distances.get(i);
        index = i;
      }
    }
    return index;
  }

  public static double getMaximum(ArrayList<Double> distances) {
    double maximum = distances.get(0);
    for (Double distance : distances) {
      System.out.println(distance + " > " + maximum + " ? " + (distance > maximum));
      if (distance > maximum) maximum = distance;
    }
    return maximum;
  }
}
