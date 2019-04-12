import java.util.ArrayList;
import java.util.Stack;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static Stack<Point> stack = new Stack<Point>();
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

  public static double alphaValue(Point point, Point minPoint) {
    minPoint = new Point(0,0);
    // Point point = new Point(p.getX()-minPoint.getX(), p.getY()-minPoint.getY());

    if (point.getX() >= minPoint.getX() && point.getY() >= minPoint.getY())
      return point.getY()/point.getD();
    else if (point.getX() < minPoint.getX() && point.getY() >= minPoint.getY())
      return 2-(point.getY()/point.getD());
    else if (point.getX() < minPoint.getX() && point.getY() < minPoint.getY())
      return 2+(Math.abs(point.getY())/point.getD());
    else if (point.getX() >= minPoint.getX() && point.getY() < minPoint.getY())
      return 4-(Math.abs(point.getY())/point.getD());
    else return 0.0;
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
      System.out.println("   MIN: " + minPoint);
      vm.horizontalLine(horizontalLength);

      for (Point point : points) {
        point.setD(Math.abs(point.getX()) + Math.abs(point.getY()));
        point.setAlpha(alphaValue(point, minPoint));
      }
      minPoint.setAlpha(0.0);

      points = pm.sortByAlpha(points);
      pm.displayPoints(points);

      stack.push(points.get(0));
      stack.push(points.get(1));
      stack.push(points.get(2));

      for (int i = 3; i < points.size(); i++) {
        while (stack.size() > 1 && getOrientation(stack.elementAt(stack.size()-2), stack.elementAt(stack.size()-1), points.get(i)) != 2) {
          System.out.println("____STACK POP:  " + stack.peek());
          stack.pop();
          printStack(stack);
        }
        System.out.println("____STACK PUSH: " + points.get(i));
        stack.push(points.get(i));
        printStack(stack);
      }
    } else {
      System.out.println("  mniej niż 3 punkty, obliczenia nie mają sensu...");
    }

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
