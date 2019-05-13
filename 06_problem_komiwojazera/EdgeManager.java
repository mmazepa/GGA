import java.util.ArrayList;

public class EdgeManager {
  public static Edge prepareEdge(Point point1, Point point2) {
    Edge edge = new Edge();
    edge.setPoint1(point1);
    edge.setPoint2(point2);
    return edge;
  }

  public static ArrayList<Edge> prepareFullGraph(ArrayList<Point> points) {
    ArrayList<Edge> fullGraph = new ArrayList<Edge>();
    for (int i = 0; i < points.size(); i++) {
      for (int j = i+1; j < points.size(); j++) {
        Edge tmpEdge = prepareEdge(points.get(i), points.get(j));
        fullGraph.add(tmpEdge);
        System.out.println("   [" + (i+1) + "," + (j+1) + "]: " + tmpEdge);
      }
      System.out.println("   -------");
    }
    return fullGraph;
  }
}
