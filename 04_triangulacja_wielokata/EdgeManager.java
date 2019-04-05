import java.util.ArrayList;

public class EdgeManager {
  public static Edge prepareEdge(Point point1, Point point2) {
    Edge edge = new Edge();
    edge.setPoint1(point1);
    edge.setPoint2(point2);
    return edge;
  }

  public static ArrayList<Edge> addEdge(ArrayList<Point> points) {
    ArrayList<Edge> edges = new ArrayList<Edge>();
    for (int i = 0; i < points.size(); i++) {
      Edge edge;
      if (i < points.size()-1)
        edge = prepareEdge(points.get(i), points.get(i+1));
      else
        edge = prepareEdge(points.get(i), points.get(0));
      edges.add(edge);
    }
    return edges;
  }
}
