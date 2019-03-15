import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PointManager {
  public static void displayPoints(ArrayList<Point> points) {
    System.out.println("Number of points: " + points.size());
    // System.out.println("Index of middle:  " + points.size()/2);
    for (Point point : points) {
      System.out.println("   " + point.toString());
    }
  }

  public static double getDistance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
  }

  public static double getLowestDistance(String area, ArrayList<Point> points) {
    double lowestDistance = getDistance(points.get(0), points.get(1));
    for (int i = 0; i < points.size(); i++) {
      for (int j = 0; j < points.size(); j++) {
        if (points.get(i) != points.get(j) && getDistance(points.get(i), points.get(j)) < lowestDistance) {
          lowestDistance = getDistance(points.get(i), points.get(j));
          if (area == "S1") {
            App.lowest_s1_p1 = points.get(i);
            App.lowest_s1_p2 = points.get(j);
          } else if (area == "S2") {
            App.lowest_s2_p1 = points.get(i);
            App.lowest_s2_p2 = points.get(j);
          }
        }
      }
    }
    return lowestDistance;
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
