import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static Region inputRegion = new Region();

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
    char type;

    if (axis == 0) {
      points = pm.sortByX(points); // raczej nie przejdzie... (!!!)
      type = 'v';
    } else {
      points = pm.sortByY(points); // raczej nie przejdzie... (!!!)
      type = 'h';
    }

    median = getMedian(axis, points);
    System.out.println("MEDIAN: " + median);

    ArrayList<Point> leftList = new ArrayList<Point>();
    ArrayList<Point> rightList = new ArrayList<Point>();

    for (int i = 0; i < points.size(); i++) {
      Point tmpPoint = points.get(i);
      double value = 0;

      if (axis == 0) value = tmpPoint.getX();
      else value = tmpPoint.getY();

      if (value <= median) leftList.add(tmpPoint);
      else rightList.add(tmpPoint);
    }

    displayLeftAndRight(d, leftList, rightList);

    // Node leftSubtree = kd_tree(leftList, d+1);
    // Node rightSubtree = kd_tree(rightList, d+1);
    Node newNode = Node.createNewNode(median, type, null, kd_tree(leftList, d+1), kd_tree(rightList, d+1));

    // System.out.println(node.getLeft().getPoint() + ", " + node.getRight().getPoint());
    return newNode;
  }

  public static void setRegions(Node node) {
    if (!node.isLeaf()) {
      if (node.getParent() != null) {
        System.out.println("___ L:" + node.isLeft() + ", ___ R:" + node.isRight());

        node.setRegion(node.getParent().getRegion());
        Region nodeRegion = node.getRegion();
        Double location = node.getParent().getLocation();

        if (nodeRegion != null && location != null) {
          if (node.getType() == 'h') {
            if (node.isLeft())
              nodeRegion.x_max = location;
            else
              nodeRegion.x_min = location;
          } else {
            if (node.isLeft())
              nodeRegion.y_max = location;
            else
              nodeRegion.y_min = location;
          }
          node.setRegion(nodeRegion);
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

  public String spaces(int amount) {
    String spaces = "";
    for (int i = 0; i < amount; i++) spaces += " ";
    return spaces;
  }

  public static void displayTree(Node node, String prefix) {
    //System.out.println("LOC: " + node.getLocation());
    if (!node.isLeaf()) {
      System.out.println(prefix + node + ", (" + node.getType() + ") mediana: " + node.getLocation() + ", " + node.getRegion().toString());
      if (node.getLeft() != null) displayTree(node.getLeft(), prefix + "|   ");
      if (node.getRight() != null) displayTree(node.getRight(), prefix + "|   ");
    } else {
      System.out.println(prefix + node.getPoint().toString() + " (" + node.getType() + ")");
    }
  }

  public static boolean liesInRegion(Region r, Point p) {
    boolean pointInRegion = false;
    if (p.getX() >= r.x_min && p.getX() <= r.x_max) {
      if (p.getY() >= r.y_min && p.getY() <= r.y_max) {
        pointInRegion = true;
      }
    }
    return pointInRegion;
  }

  public static void reportOne(Point point) {
    System.out.println("Report One: " + point.toString());
  }

  public static void reportSubtree(Node node) {
    System.out.println("Report Subtree:");
    displayTree(node, "");
  }

  public static boolean fullyContained(Region node, Region input) {
    if (node != null && input != null) {
      double x0 = input.x_min;
      double y0 = input.y_min;
      double width0 = input.x_max - x0;
      double height0 = input.y_max - y0;

      double x1 = node.x_min;
      double y1 = node.y_min;
      double width1 = node.x_max - x1;
      double height1 = node.y_max - y1;

      boolean cond1 = (x1 >= x0) && (y1 >= y0);
      boolean cond2 = (x1 + width1) <= (x0 + width0);
      boolean cond3 = (y1 + height1) <= (y0 + height0);

      return cond1 && cond2 && cond3;
    } else {
      return false;
    }
  }

  public static boolean intersects(Region node, Region input) {
    if (node != null && input != null) {
      Point input_topRight = new Point(input.x_max, input.y_max);
      Point input_bottomLeft = new Point(input.x_min, input.y_min);
      Point node_topRight = new Point(node.x_max, node.y_max);
      Point node_bottomLeft = new Point(node.x_min, node.y_min);

      if (input_topRight.getY() < node_bottomLeft.getY()
      || input_bottomLeft.getY() > node_topRight.getY()) {
        return false;
      }
      if (input_topRight.getX() < node_bottomLeft.getX()
      || input_bottomLeft.getX() > node_topRight.getX()) {
        return false;
      }
      return true;
    } else {
      return false;
    }
  }

  public static void search_kd_tree(Node node, Region input) {
    if (node.isLeaf()) {
      if (liesInRegion(input, node.getPoint())) {
        reportOne(node.getPoint());
      }
    } else {
      if (node.getLeft() != null) {
        if (fullyContained(node.getLeft().getRegion(), input)) {
          reportSubtree(node.getLeft());
        } else if (intersects(node.getLeft().getRegion(), input)) {
          search_kd_tree(node.getLeft(), input);
        }
      }

      if (node.getRight() != null) {
        if (fullyContained(node.getRight().getRegion(), input)) {
          reportSubtree(node.getRight());
        } else if (intersects(node.getRight().getRegion(), input)) {
          search_kd_tree(node.getRight(), input);
        }
      }
    }
  }

  public static void dealWithRegion(String[] args) {
    if (args.length >= 5) {
      double x1 = Double.parseDouble(args[1]);
      double x2 = Double.parseDouble(args[2]);
      double y1 = Double.parseDouble(args[3]);
      double y2 = Double.parseDouble(args[4]);
      double tmp;

      if (x1 > x2) { tmp = x1; x1 = x2; x2 = tmp; }
      if (y1 > y2) { tmp = y1; y1 = y2; y2 = tmp; }

      inputRegion = new Region(x1, x2, y1, y2);
      System.out.println("OBSZAR ZAPYTANIA: " + inputRegion.toString());

      search_kd_tree(tree, inputRegion);
    } else {
      System.out.println("Nie podano obszaru zapytania, pomijam...");
    }
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);
    points = fm.loadPoints(inputFileName);

    pm.checkPoints(points);
    pm.displayPoints(points);

    tree = kd_tree(points, 0);
    tree.setRegion(new Region(-10.0, 10.0, -10.0, 10.0));
    setRegions(tree);

    System.out.print("\n");
    System.out.println("kD-Drzewo:");
    System.out.println("--------------------------------------------------");
    displayTree(tree, "");
    System.out.println("--------------------------------------------------\n");

    dealWithRegion(args);

    // Region r1 = new Region(1.0, 5.0, 1.0, 5.0);
    // Region r2 = new Region(2.0, 6.0, 2.0, 6.0);
    //
    // System.out.println("INTERSECTS:      " + intersects(r1, r2));
    // System.out.println("FULLY CONTAINED: " + fullyContained(r1, r2));

    Window.display();
  }
}
