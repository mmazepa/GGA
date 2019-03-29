import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

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

  public static double medianByX(ArrayList<Point> points) {
    double median = 0;
    int half = points.size()/2;
    if (points.size()%2 == 0)
      median = (points.get(half).getX() + points.get(half-1).getX())/2;
    else
      median = points.get(half).getX();
    return median;
  }

  public static double medianByY(ArrayList<Point> points) {
    double median = 0;
    int half = points.size()/2;
    if (points.size()%2 == 0)
      median = (points.get(half).getY() + points.get(half-1).getY())/2;
    else
      median = points.get(half).getY();
    return median;
  }

  public static double getMedian(int axis, ArrayList<Point> points) {
    double median = 0;
    if (axis == 0) median = medianByX(points);
    else median = medianByY(points);
    return median;
  }

  public static void displayLeftAndRight(int d, ArrayList<Point> left, ArrayList<Point> right) {
    arrayDisplayer("d=" + d + "; LEFT LIST", left);
    arrayDisplayer("d=" + d + "; RIGHT LIST", right);
  }

  public static Node kd_tree(ArrayList<Point> points, int d) {
    System.out.println("=====> ROZMIAR: " + points.size());

    if (points.size() == 1) return Node.createNewNode(0, 'l', points.get(0), null, null);
    else if (points.size() == 0) return null;

    int axis = d%2;
    double median = 0;

    median = getMedian(axis, points);
    System.out.println("MEDIAN: " + median);

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

    char type;
    if (d%2 == 0) type = 'v';
    else type = 'h';

    // Node leftSubtree = kd_tree(leftList, d+1);
    // Node rightSubtree = kd_tree(rightList, d+1);
    Node newNode = Node.createNewNode(median, type, null, kd_tree(leftList, d+1), kd_tree(rightList, d+1));

    // System.out.println(node.getLeft().getPoint() + ", " + node.getRight().getPoint());
    return newNode;
  }

  public static void setRegions(Node node) {
    if (!node.isLeaf()) {
      if (node.getParent() != null) {
        System.out.println("___ L:" + node.isLeft() + ", R:" + node.isRight());

        if (node.getType() == 'v') {
          // if (node.isLeft()) node.getRegion().x_max = node.getPoint().getX();
          // else               node.getRegion().x_min = node.getPoint().getX();
        } else {
          // if (node.isLeft()) node.getRegion().y_max = node.getPoint().getY();
          // else               node.getRegion().y_min = node.getPoint().getY();
        }
      } else {
        System.out.println("___ Root!");
      }

      if (node.getLeft() != null) setRegions(node.getLeft());
      if (node.getRight() != null) setRegions(node.getRight());
    }
  }

  public static void exitOnPurpose(String purpose) {
    System.out.println(purpose);
    System.exit(0);
  }

  public static void testTree(Node node) {
    // System.out.println(node.getLocation() + ", " + node.getLeft().getLocation() + ", " + node.getRight().getLocation());
    boolean cond1, cond2, cond3, test;
    String intro, valid, notValid;

    intro = "Node and children checked, ";
    valid = "everything is okay!";
    notValid = "something is wrong!";

    // if (node.getLeft() != null) {
    //   cond1 = node.getLocation() >= node.getLeft().getLocation();
    //   // test = cond1;
    //   System.out.print(" " + intro + (cond1 ? valid : notValid) + "\n");
    // }
    // if (node.getRight() != null) {
    //   cond2 = node.getLocation() < node.getRight().getLocation();
    //   cond3 = node.getRight().getLocation() == 0.0;
    //   test = cond1 && (cond2 || cond3);
    //   System.out.print(" " + intro + (test ? valid : notValid) + "\n");
    // }
  }

  public static void displayTree(Node node) {
    //System.out.println("NODE: " + node.getLeft() + ", " + node.getRight());
    if (!node.isLeaf()) {
      System.out.println(node + ", (" + node.getType() + ") ===> " + node.getLocation());
      // testTree(node);
      if (node.getLeft() != null) displayTree(node.getLeft());
      if (node.getRight() != null) displayTree(node.getRight());
      // System.out.println("(" + node + ", " + node.getLeft() + ", " + node.getRight() + ")");
    } else {
      // System.out.println("(" + node.getPoint().toString() + ", " + node.getLeft() + ", " + node.getRight() + ")");
      System.out.println(node.getPoint().toString() + " (" + node.getType() + ") ===> " + node.getLocation());
    }
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);
    points = fm.loadPoints(inputFileName);

    // if (points.size() == 1) {
    //   exitOnPurpose("There is only one point, returning leaf...");
    //   // System.out.println("There is only one point, returning leaf...");
    //   // return that one leaf here...
    // } else
    if (points.size() == 0) {
      exitOnPurpose("There are no points provided...");
    }

    points = pm.sortByX(points);
    pm.displayPoints(points);

    int d = 0;
    tree = kd_tree(points, d);
    setRegions(tree);

    System.out.print("\n");
    System.out.println("kD-Drzewo:");
    System.out.println("----------");
    displayTree(tree);

    Window.display();
  }
}
