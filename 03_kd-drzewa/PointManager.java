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
