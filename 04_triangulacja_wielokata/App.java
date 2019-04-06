import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.awt.Polygon;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static ArrayList<Edge> edges = new ArrayList<Edge>();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static EdgeManager em = new EdgeManager();
  public static VisualManager vm = new VisualManager();

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    System.out.println(purpose);
    System.exit(0);
  }

  public static Point lowerAndUpper(Point point, boolean lower, boolean upper) {
    point.setIsLower(lower);
    point.setIsUpper(upper);
    return point;
  }

  public static void setLowerAndUpper(ArrayList<Point> points, Point left, Point right) {
    while (points.get(0) != left) Collections.rotate(points, 1);

    // System.out.println("POINTS: " + points);

    int indexOfRight = 0;
    for (int i = 0; i < points.size(); i++) {
      if (points.get(i) == right) indexOfRight = i;
    }

    List<Point> lower = new ArrayList<Point>();
    List<Point> upper = new ArrayList<Point>();

    lower = points.subList(0, indexOfRight);
    upper = points.subList(indexOfRight, points.size());

    // System.out.println("LOWER:  " + lower);
    // System.out.println("UPPER:  " + upper);

    for (Point point : lower) point = lowerAndUpper(point, true, false);
    for (Point point : upper) point = lowerAndUpper(point, false, true);
  }

  public static double getOrientation(Point p1, Point p2, Point p3) {
    double orientation = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
    int o = 0;

    System.out.print("    " + p1.toString() + ", " + p2.toString() + ", " + p3.toString());

    if (orientation == 0) o = 0;
    else if (orientation > 0) o = 1;
    else if (orientation < 0) o = 2;

    if (o == 0) System.out.print(" ---> WSPÓŁLINIOWE\n");
    else if (o == 1) System.out.print(" ---> W PRAWO\n");
    else if (o == 2) System.out.print(" ---> W LEWO\n");

    return o;
  }

  public static void sweepTriangulation(ArrayList<Point> points) {
    Stack<Point> stack = new Stack<Point>();
    stack.push(points.get(0));
    stack.push(points.get(1));

    Point point = new Point();

    for (int i = 2; i < points.size(); i++) {
      System.out.println("[" + i + "] " + points.get(i).toString());
      if ((points.get(i).getIsLower() && stack.peek().getIsUpper())
      || (points.get(i).getIsUpper() && stack.peek().getIsLower())) {
        Point tmpPoint = new Point();
        while (!stack.empty()) {
          tmpPoint = stack.pop();
          if (stack.empty()) break;
          Edge edge = em.prepareEdge(points.get(i), tmpPoint);
          edges.add(edge);
        }
        stack.push(tmpPoint);
        stack.push(points.get(i));
      } else {
        Point tmpPoint3 = stack.pop();
        while (!stack.empty()) {
          tmpPoint3 = stack.pop();
          if (getOrientation(points.get(i), tmpPoint3, points.get(i-1)) == 2) {
            Edge edge = em.prepareEdge(points.get(i), tmpPoint3);
            edges.add(edge);
          } else break;
        }
        stack.push(tmpPoint3);
        stack.push(points.get(i));
        point = tmpPoint3;
      }
    }

    // System.out.println(stack.empty());

    stack.pop();
    while (!stack.empty()) {
      Point tmpPoint4 = stack.pop();
      if (stack.empty()) break;
      Edge edge = em.prepareEdge(point, tmpPoint4);
      edges.add(edge);
    }

    // System.out.println(stack.empty());
    // vn -> 2 ... n-1
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    ArrayList<Point> sortedByX = new ArrayList<Point>();
    sortedByX.addAll(points);
    sortedByX = pm.sortByX(sortedByX);

    vm.horizontalLine(50);
    pm.displayPoints(sortedByX);

    vm.horizontalLine(50);

    vm.title("TRIANGULACJA");
    if (points.size() > 3) {
      setLowerAndUpper(points, sortedByX.get(0), sortedByX.get(sortedByX.size()-1));
      sweepTriangulation(sortedByX);
    } else {
      System.out.println("  3 punkty lub mniej, triangulacja nie ma sensu...");
    }

    for (Edge e : edges) System.out.println(e.toString());

    vm.horizontalLine(50);
    Window.display();
  }
}
