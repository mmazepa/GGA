import java.util.ArrayList;

public class Node {
  private double location;
  private char type;
  private Point point;
  private Node parent;
  private Node left;
  private Node right;
  private Region region;

  public Node() {}

  public Node(double location, char type, Point point, Node left, Node right) {
    this.location = location;
    this.type = type;
    this.point = point;
    this.left = left;
    this.right = right;
  }

  public static Node createNewNode(double location, char type, Point point, Node left, Node right) {
    Node node = new Node(location, type, point, left, right);
    if (left != null) {
      left.setParent(node);
      node.setLeft(left);
    }
    if (right != null) {
      right.setParent(node);
      node.setRight(right);
    }
    return node;
  }

  public void setLocation(double location) {
    this.location = location;
  }
  public double getLocation() {
    return location;
  }

  public void setType(char type) {
    this.type = type;
  }
  public char getType() {
    return type;
  }

  public void setPoint(Point point) {
    this.point = point;
  }
  public Point getPoint() {
    return point;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }
  public Node getParent() {
    return parent;
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

  public void setRegion(Region region) {
    // this.region = region;
    this.region = new Region();
    this.region.x_min = region.x_min;
    this.region.x_max = region.x_max;
    this.region.y_min = region.y_min;
    this.region.y_max = region.y_max;
  }
  public Region getRegion() {
    return region;
  }

  public boolean isLeft() {
    return this == this.parent.left;
  }

  public boolean isRight() {
    return this == this.parent.right;
  }

  public boolean isLeaf() {
    return this.left == null && this.right == null;
  }
}
