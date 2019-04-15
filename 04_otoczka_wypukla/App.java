import java.util.ArrayList;
import java.util.Stack;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static ArrayList<Point> pointsCopy = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static Stack<Point> stack = new Stack<Point>();
  public static Point minPoint = new Point();

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) inputFileName = args[0];
    else exitOnPurpose("Nie wybrano pliku.");
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static double getOrientation(Point p1, Point p2, Point p3) {
    System.out.print("    " + p1.toString() + ", " + p2.toString() + ", " + p3.toString());

    double orientation = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
    int o = 0;

    if (orientation == 0) {
      o = 0;
      System.out.print(" ---> WSPÓŁLINIOWE\n");
    } else if (orientation > 0) {
      o = 1;
      System.out.print(" ---> W PRAWO\n");
    } else if (orientation < 0) {
      o = 2;
      System.out.print(" ---> W LEWO\n");
    }

    return o;
  }

  public static double alphaValue(Point point, Point minPoint) {
    return Math.atan2(point.getY()-minPoint.getY(), point.getX()-minPoint.getX());
  }

 public static ArrayList<Point> removePointsWithSameAlpha(ArrayList<Point> points) {
   int counter = 0;
   for (int i = points.size()-1; i > 1; i--) {
     if (points.get(i).getAlpha() == points.get(i-1).getAlpha()) {
       double dist1 = pm.getDistance(points.get(0), points.get(i));
       double dist2 = pm.getDistance(points.get(0), points.get(i-1));
       if (dist1 > dist2) {
         System.out.println("   Usuwam: " + points.get(i-1));
         points.remove(points.get(i-1));
       } else {
         System.out.println("   Usuwam: " + points.get(i));
         points.remove(points.get(i));
       }
       counter++;
     }
   }
   vm.horizontalLine(horizontalLength);

   System.out.println("   Usunięto punktów: " + counter);
   System.out.println("   Zostało punktów:  " + points.size());
   return points;
 }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);
    vm.horizontalLine(horizontalLength);

    vm.title("OTOCZKA WYPUKŁA - SKAN GRAHAMA", horizontalLength);

    minPoint = pm.findMin(points);
    System.out.println("   MIN: " + minPoint);
    vm.horizontalLine(horizontalLength);

    for (Point point : points) {
      point.setAlpha(alphaValue(point, minPoint));
    }

    points = pm.sortByAlpha(points);
    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    pointsCopy.addAll(points);
    points = removePointsWithSameAlpha(points);
    vm.horizontalLine(horizontalLength);

    if (points.size() >= 3) {
      System.out.println("[1]: STACK PUSH: " + points.get(0));
      stack.push(points.get(0));
      System.out.println("[2]: STACK PUSH: " + points.get(1));
      stack.push(points.get(1));
      System.out.println("[3]: STACK PUSH: " + points.get(2));
      stack.push(points.get(2));

      vm.printStack(stack, "STOS");

      for (int i = 3; i < points.size(); i++) {
        System.out.println("[" + (i+1) + "]: " + points.get(i));
        while (stack.size() > 1 && getOrientation(stack.elementAt(stack.size()-2), stack.elementAt(stack.size()-1), points.get(i)) != 2) {
          System.out.println("    STACK POP:  " + stack.peek());
          stack.pop();
        }
        System.out.println("    STACK PUSH: " + points.get(i));
        stack.push(points.get(i));
        vm.printStack(stack, "STOS");
      }
    } else {
      stack.addAll(points);
      vm.printStack(stack, "STOS");
    }

    vm.horizontalLine(horizontalLength);
    vm.title("EFEKT KOŃCOWY", horizontalLength);
    vm.printStack(stack, "OTOCZKA");
    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
