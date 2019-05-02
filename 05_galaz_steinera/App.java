import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.awt.Polygon;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static ArrayList<Point> pointsCopy = new ArrayList<Point>();
  public static ArrayList<Point> newPoints = new ArrayList<Point>();
  public static ArrayList<Edge> edges = new ArrayList<Edge>();
  public static String inputFileName = new String();

  // public static ArrayList<Point> Q = new ArrayList<Point>();
  // public static ArrayList<Point> G = new ArrayList<Point>();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static EdgeManager em = new EdgeManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    pm.sortPoints(points);
    pointsCopy.addAll(points);

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    vm.title("PROSTOKĄTNA GAŁĄŹ STEINERA", horizontalLength);

    // Q.addAll(points);
    // G.addAll(points);
    //
    // while (Q.size() > 1) {
    //   // newPoints.clear();
    //   for (int i = 0; i < Q.size()-1; i++) {
    //     //if (Q.get(i))
    //     Point p1 = Q.get(i);
    //     Point p2 = Q.get(i+1);
    //     // if (pm.sumXY(p1) == pm.sumXY(p2)) {
    //       Q.remove(p1);
    //       Q.remove(p2);
    //       Point newPoint = new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
    //       newPoints.add(newPoint);
    //       // G.add(newPoint);
    //       Q.add(newPoint);
    //       pm.sortPoints(Q);
    //       edges.add(em.prepareEdge(newPoint, p1));
    //       edges.add(em.prepareEdge(newPoint, p2));
    //     // }
    //   }
    //   System.out.println(Q.size());
    // }

    for (int i = 0; i < points.size()-1; i++) {
      Point newPoint = new Point();
      Point p1 = points.get(i);
      Point p2 = points.get(i+1);

      System.out.println(p1 + ", " + p2 + " ---> " + (pm.sumXY(p1)>pm.sumXY(p2)));

      newPoint = new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));

      if (pm.equals(p2, points.get(points.size()-1)))
        newPoint = new Point(Math.max(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));
      else
        newPoint = new Point(Math.min(p1.getX(), p2.getX()), Math.min(p1.getY(), p2.getY()));

      if (!pm.equals(newPoint, p1) && !pm.equals(newPoint, p2)) {
        newPoints.add(newPoint);
        points.add(newPoint);
        pm.sortPoints(points);
      }

      if (!pm.equals(newPoint, p1)) {
        edges.add(em.prepareEdge(newPoint, p1));
        continue;
      }

      if (!pm.equals(newPoint, p2)) {
        edges.add(em.prepareEdge(newPoint, p2));
        continue;
      }
    }

    for (Edge edge: edges) System.out.println("   " + edge);

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
