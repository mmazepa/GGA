import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static Region inputRegion = new Region();

  public static Region boxRegion = new Region(0.0, 10.0, 0.0, 10.0);
  public static Node tree = new Node();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();


  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      System.out.println("Nie wybrano pliku.");
      System.exit(0);
    }
  }

  public static Node kd_tree(ArrayList<Point> points, int d) {
    System.out.println("=====> ROZMIAR: " + points.size());

    if (points.size() == 1) return Node.createNewNode(0, 'l', points.get(0), null, null);
    else if (points.size() == 0) return null;

    int axis = d%2;
    double median = 0;
    char type;

    if (axis == 0) {
      type = 'v';
    } else {
      type = 'h';
    }

    median = pm.getMedian(axis, points);
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

    pm.displayLeftAndRight(d, leftList, rightList);

    Node leftSubtree = kd_tree(leftList, d+1);
    Node rightSubtree = kd_tree(rightList, d+1);
    Node newNode = Node.createNewNode(median, type, null, leftSubtree, rightSubtree);
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
            if (node.isLeft()) nodeRegion.x_max = location;
            else nodeRegion.x_min = location;
          } else {
            if (node.isLeft()) nodeRegion.y_max = location;
            else nodeRegion.y_min = location;
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

  public static boolean liesInRegion(Region r, Point p) {
    boolean pointInRegion = false;
    if (p.getX() >= r.x_min && p.getX() <= r.x_max
    && p.getY() >= r.y_min && p.getY() <= r.y_max) {
      pointInRegion = true;
    }
    return pointInRegion;
  }

  public static void reportOne(Point point) {
    vm.displayFramed("Zgłaszam punkt", point.toString());
  }

  public static void reportSubtree(Node node) {
    vm.displayFramed("Zgłaszam całe poddrzewo");
    node.print(0);
  }

  public static boolean fullyContained(Region node, Region input) {
    if (node != null && input != null) {
      Point input_topRight = new Point(input.x_max, input.y_max);
      Point input_bottomLeft = new Point(input.x_min, input.y_min);
      Point node_topRight = new Point(node.x_max, node.y_max);
      Point node_bottomLeft = new Point(node.x_min, node.y_min);

      boolean cond1 = (node_bottomLeft.getX() >= input_bottomLeft.getX())
      && (node_bottomLeft.getY() >= input_bottomLeft.getY());

      boolean cond2 = (node_topRight.getX() <= input_topRight.getX())
      && (node_topRight.getY() <= input_topRight.getY());

      return cond1 && cond2;
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

  public static void checkLeaf(Node node) {
    if (liesInRegion(inputRegion, node.getPoint())) {
      reportOne(node.getPoint());
    }
  }

  public static void checkSubtree(Node node) {
    if (fullyContained(node.getRegion(), inputRegion)) {
      reportSubtree(node);
    } else if (intersects(node.getRegion(), inputRegion)) {
      search_kd_tree(node, inputRegion);
    } else {
      if (node.isLeaf()) checkLeaf(node);
    }
  }

  public static void search_kd_tree(Node node, Region inputRegion) {
    if (node.isLeaf()) {
      checkLeaf(node);
    } else {
      checkSubtree(node.getLeft());
      checkSubtree(node.getRight());
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
      System.out.print("\n");
      vm.displayFramed("OBSZAR ZAPYTANIA: " + inputRegion.toString());

      System.out.print("\n");
      vm.title("Raport");
      search_kd_tree(tree, inputRegion);
      vm.horizontalLine(50);
      System.out.print("\n");
    } else {
      System.out.println("Nie podano obszaru zapytania, pomijam...");
    }
  }

  public static void exitOnPurpose(String purpose) {
    System.out.println(purpose);
    System.exit(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);
    points = fm.loadPoints(inputFileName);

    pm.checkPoints(points);
    pm.displayPoints(points);

    tree = kd_tree(points, 0);
    tree.setRegion(boxRegion);
    setRegions(tree);

    System.out.print("\n");
    vm.title("kD-drzewo");
    tree.print(0);
    vm.horizontalLine(50);

    dealWithRegion(args);

    System.out.println("Koniec...");
    Window.display();
  }
}
