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
        System.out.print("   [" + (i+1) + "," + (j+1) + "]: " + tmpEdge);

        double dist = PointManager.getDistance(points.get(i), points.get(j));
        double rounded = (double) Math.round(dist*100)/100;
        String result = String.format("%5.2f", rounded);
        System.out.print(" : " + result + "\n");
      }
      System.out.println("   -------");
    }
    return fullGraph;
  }

  public static void displayDistanceMatrix(double[][] distanceMatrix) {
    System.out.print("      ");
    for (int i = 0; i < distanceMatrix.length; i++) {
      String result = String.format("%5s", "[" + (i+1) + "]");
      System.out.print(" " + result);
    }
    System.out.print("\n");

    for (int i = 0; i < distanceMatrix.length; i++) {
      String result = String.format("%5s", "[" + (i+1) + "]");
      System.out.print(" " + result);
      for (int j = 0; j < distanceMatrix[i].length; j++) {
        if (distanceMatrix[i][j] != 0.0) {
          double rounded = (double) Math.round(distanceMatrix[i][j]*100)/100;
          result = String.format("%5.2f", rounded);
          System.out.print(" " + result);
        } else {
          System.out.print("     -");
        }
      }
      System.out.print("\n");
    }
  }
}
