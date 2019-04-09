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

  public static void setLowerAndUpper(Point point, boolean lower, boolean upper) {
    point.setIsLower(lower);
    point.setIsUpper(upper);
  }

  public static void setPreviousAndNext(Point point, Point previous, Point next) {
    point.setPrevious(previous);
    point.setNext(next);
  }

  public static void setLowerUpperAndNeighbours(ArrayList<Point> points, Point left, Point right) {
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

    for (int i = 0; i < lower.size(); i++) {
      setLowerAndUpper(lower.get(i), true, false);
      if (i == 0) {
        setLowerAndUpper(lower.get(i), false, false);
        setPreviousAndNext(lower.get(0), null, lower.get(1));
      } else if (i > 0 && i < lower.size()-1) {
        setPreviousAndNext(lower.get(i), lower.get(i-1), lower.get(i+1));
      } else if (i == lower.size()-1) {
        setPreviousAndNext(lower.get(i), lower.get(i-1), upper.get(0));
      }
    }

    for (int i = 0; i < upper.size(); i++) {
      setLowerAndUpper(upper.get(i), false, true);
      if (i == 0) {
        setLowerAndUpper(upper.get(i), false, false);
        setPreviousAndNext(upper.get(0), upper.get(1), null);
      } else if (i > 0 && i < upper.size()-1) {
        setPreviousAndNext(upper.get(i), upper.get(i+1), upper.get(i-1));
      } else if (i == upper.size()-1) {
        setPreviousAndNext(upper.get(i), lower.get(0), upper.get(i-1));
      }
    }

    // for (Point point : points) System.out.println(point.toString() + ", " + point.getIsUpper() + ", " + point.getIsLower());
    // for (Point point : points) System.out.println(point.toString() + " -> " + point.getPrevious() + ", " + point.getNext());
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

  public static boolean isEdgeConnectingWithNeighbour(Point p1, Point p2) {
    if (p1.getPrevious() == p2) return true;
    if (p1.getNext() == p2) return true;
    return false;
  }

  public static boolean isEdgeValid(Point p1, Point p2) {
    Point p3 = new Point();

    if (p2.getIsUpper() && !p2.getIsLower()) p3 = p2.getPrevious();
    else if (p2.getIsLower() && !p2.getIsUpper()) p3 = p2.getNext();

    boolean cond1 = isEdgeConnectingWithNeighbour(p1, p2);
    boolean cond2 = getOrientation(p1, p2, p3) == 2;
    // boolean cond3 = p1 != p2 && p1 != p2.getPrevious() && p1 != p2.getNext();
    return !cond1 && cond2;// && cond3;

  }

  public static void printStack(Stack stack) {
    String stackString = new String();
    stackString += "STOS | ";
    for (int i = 0; i < stack.size(); i++) {
      stackString += stack.elementAt(i) + " ";
    }
    vm.displayFramed(stackString);
  }

  public static void sweepTriangulation(ArrayList<Point> points) {
    Stack<Point> stack = new Stack<Point>();
    stack.push(points.get(0));
    stack.push(points.get(1));

    Point point = new Point();
    Point tmpPoint = new Point();

    for (int i = 2; i < points.size()-1; i++) {
      printStack(stack);
      System.out.println("V[" + i + "] = " + points.get(i).toString());

      if ((points.get(i).getIsLower() && stack.peek().getIsUpper())
      || (points.get(i).getIsUpper() && stack.peek().getIsLower())) {
        point = stack.pop();
        tmpPoint = point;
        while (!stack.empty()) {
          Edge edge = em.prepareEdge(points.get(i), tmpPoint);
          if (!isEdgeConnectingWithNeighbour(points.get(i), tmpPoint)) {
            edges.add(edge);
            System.out.println("    Dokładam, bo na przeciwnym łańcuchu!");
          }
          tmpPoint = stack.pop();
        }
        stack.push(point);
        stack.push(points.get(i));
      } else {
        point = stack.pop();
        tmpPoint = point;
        while (!stack.empty()) {
          if (isEdgeValid(points.get(i), tmpPoint)) {
            System.out.println("    W środku: dokładam!");
            Edge edge = em.prepareEdge(points.get(i), tmpPoint);
            edges.add(edge);
            System.out.println("    Dokładam, bo na tym samym łańcuchu!");
          } else {
            System.out.println("    Poza: nie dokładam!");
            break;
          }
          tmpPoint = stack.pop();
        }
        stack.push(point);
        stack.push(points.get(i));
      }
    }

    printStack(stack);

    vm.title("Poza for'em!", horizontalLength);
    Point vn = points.get(points.size()-1);
    System.out.println("V[" + (points.size()-1) + "] = " + vn.toString());

    printStack(stack);
    stack.pop();
    printStack(stack);
    while (!stack.empty()) {
      point = stack.pop();
      if (stack.empty()) break;
      Edge edge = em.prepareEdge(vn, point);
      //if (isEdgeValid(vn, point)) {
        edges.add(edge);
        System.out.println("    Dokładam!");
      // } else {
      //   System.out.println("    Nie dokładam!");
      // }
      printStack(stack);
    }
    printStack(stack);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    ArrayList<Point> sortedByX = new ArrayList<Point>();
    sortedByX.addAll(points);
    sortedByX = pm.sortByX(sortedByX);

    vm.horizontalLine(horizontalLength);
    pm.displayPoints(sortedByX);

    vm.horizontalLine(horizontalLength);

    vm.title("TRIANGULACJA", horizontalLength);
    if (points.size() > 3) {
      setLowerUpperAndNeighbours(points, sortedByX.get(0), sortedByX.get(sortedByX.size()-1));
      sweepTriangulation(sortedByX);
    } else {
      System.out.println("  3 punkty lub mniej, triangulacja nie ma sensu...");
    }

    vm.title("Uzyskane krawędzie" , horizontalLength);
    for (Edge e : edges)
      System.out.println("[" + (edges.indexOf(e)+1) + "] " + e.toString());

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
