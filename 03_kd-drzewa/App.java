import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static ArrayList<Double> lines = new ArrayList<Double>();

  public static Node tree = new Node();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      System.out.println("Nie wybrano pliku.");
      System.exit(0);
    }
  }

  public static void arrayDisplayer(String title, ArrayList<Point> array) {
    System.out.println(title + ":");
    pm.displayPoints(array);
  }

  public static boolean compareTwoPoints(Point p1, Point p2) {
    if ((p1.getX() < p2.getX()) || (p1.getX() == p2.getX() && p1.getY() < p2.getY()))
    return true;
  else if (p1.getX() > p2.getX() || (p1.getX() == p2.getX() && p1.getY() > p2.getY()))
    return false;
  else
    return true;
  }

  public static double getMedian(int axis, ArrayList<Point> points) {
    double median = 0;
    int half = points.size()/2;
    if (axis == 0)
      median = points.get(half).getX()/2;
    else
      median = (points.get(half).getY() + points.get(half-1).getY())/2;
    return median;
  }

  public static void displayLeftAndRight(int d, ArrayList<Point> left, ArrayList<Point> right) {
    System.out.println("d=" + d + "; LEFT LIST:");
    pm.displayPoints(left);
    System.out.println("d=" + d + "; RIGHT LIST:");
    pm.displayPoints(right);
  }

  public static Node kd_tree(ArrayList<Point> points, int d) {
    System.out.println("=====> ROZMIAR: " + points.size());

    int axis = d%2;
    double median = 0;

    if (points.size() == 1) return new Node(median, points.get(0), null, null);
    else if (points.size() == 0) return null;

    median = getMedian(axis, points);
    System.out.println("MEDIAN: " + median);
    lines.add(median);

    ArrayList<Point> leftList = new ArrayList<Point>();
    ArrayList<Point> rightList = new ArrayList<Point>();

    if (points.size() > 2) {
      for (int i = 0; i < points.size(); i++) {
        Point tmpPoint = points.get(i);
        double value = 0;
        if (axis == 0) {
          value = tmpPoint.getX();
        } else {
          value = tmpPoint.getY();
        }
        if (value <= median) leftList.add(tmpPoint);
        else rightList.add(tmpPoint);
      }
    } else {
      leftList.add(points.get(0));
      rightList.add(points.get(1));
    }

    displayLeftAndRight(d, leftList, rightList);

    return new Node(median, null, kd_tree(leftList, d+1), kd_tree(rightList, d+1));
  }

  public static void exitOnPurpose(String purpose) {
    System.out.println(purpose);
    System.exit(0);
  }

  public static void displayTree(Node node) {
    //System.out.println("NODE: " + node.getLeft() + ", " + node.getRight());
    if (!node.isLeaf()) {
      displayTree(node.getLeft());
      displayTree(node.getRight());
    } else {
      System.out.println(node.getPoint().toString());
    }
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);
    points = fm.loadPoints(inputFileName);

    if (points.size() == 1) {
      exitOnPurpose("There is only one point, returning leaf...");
      // System.out.println("There is only one point, returning leaf...");
      // return that one leaf here...
    } else if (points.size() == 0) {
      exitOnPurpose("There are no points provided...");
    }

    points = pm.sortByX(points);
    pm.displayPoints(points);

    int d = 0;
    tree = kd_tree(points, d);
    displayTree(tree);

    Window.display();
  }
}
