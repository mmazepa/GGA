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
}
