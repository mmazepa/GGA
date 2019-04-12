import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;
import java.awt.Polygon;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static Point minPoint = new Point();

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

  public static double getOrientation(Point p1, Point p2, Point p3) {
    System.out.print("    " + p1.toString() + ", " + p2.toString() + ", " + p3.toString());

    double orientation = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
    int o = 0;

    if (orientation == 0) o = 0;
    else if (orientation > 0) o = 1;
    else if (orientation < 0) o = 2;

    if (o == 0) System.out.print(" ---> WSPÓŁLINIOWE\n");
    else if (o == 1) System.out.print(" ---> W PRAWO\n");
    else if (o == 2) System.out.print(" ---> W LEWO\n");

    return o;
  }

  public static void printStack(Stack stack) {
    String stackString = "STOS | ";
    for (int i = 0; i < stack.size(); i++)
      stackString += stack.elementAt(i) + " ";
    vm.displayFramed(stackString);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    vm.horizontalLine(horizontalLength);
    pm.displayPoints(points);

    vm.horizontalLine(horizontalLength);

    vm.title("OTOCZKA WYPUKŁA - SKAN GRAHAMA", horizontalLength);
    if (points.size() >= 3) {
      minPoint = pm.findMin(points);
      System.out.println("MIN: " + minPoint);
    } else {
      System.out.println("  mniej niż 3 punkty, obliczenia nie mają sensu...");
    }

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
