public class EdgeManager {
  public static Edge prepareEdge(Point point1, Point point2) {
    Edge edge = new Edge();
    edge.setPoint1(point1);
    edge.setPoint2(point2);
    return edge;
  }
}
