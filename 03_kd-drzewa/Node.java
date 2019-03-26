public class Node {
  private double location;
  private Point point;
  private Node left;
  private Node right;

  public Node() {}

  public Node(double location, Point point, Node left, Node right) {
    this.location = location;
    this.point = point;
    this.left = left;
    this.right = right;
  }

  public void setLocation(double location) {
    this.location = location;
  }
  public double getLocation() {
    return location;
  }

  public void setPoint(Point point) {
    this.point = point;
  }
  public Point getPoint() {
    return point;
  }

  public void setLeft(Node left) {
    this.left = left;
  }
  public Node getLeft() {
    return left;
  }

  public void setRight(Node right) {
    this.right = right;
  }
  public Node getRight() {
    return right;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }

  // public String toString() {
  //   return label + point.toString();
  // }
}
