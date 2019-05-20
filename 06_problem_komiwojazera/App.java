import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static ArrayList<Edge> edges = new ArrayList<Edge>();
  public static String inputFileName = new String();

  public static double[][] distanceMatrix;
  public static double optimalDistance = Double.MAX_VALUE;
  public static String optimalPath = "";
  public static ArrayList<Point> finalPath = new ArrayList<Point>();
  public static ArrayList<Edge> edgesPath = new ArrayList<Edge>();

  public static EdgeManager em = new EdgeManager();
  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 78;

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static double[][] fillDistanceMatrix(ArrayList<Point> points, double[][] distanceMatrix) {
    for (int i = 0; i < distanceMatrix.length; i++) {
      for (int j = 0; j < distanceMatrix[i].length; j++) {
        if (i != j)
          distanceMatrix[i][j] = pm.getDistance(points.get(i), points.get(j));
        else
          distanceMatrix[i][j] = 0.0;
      }
    }
    return distanceMatrix;
  }

  public static double algorithmTSP(int initial, int[] vertices, String path, double costUntilHere) {
    path = path + Integer.toString(initial) + "-";
    int length = vertices.length;
    double newCostUntilHere;

    if (length == 0) {
      newCostUntilHere = costUntilHere + distanceMatrix[initial][0];

      if (newCostUntilHere < optimalDistance){
        optimalDistance = newCostUntilHere;
        optimalPath = path + "0";
      }
      return (distanceMatrix[initial][0]);
    } else if (costUntilHere > optimalDistance) {
      return 0;
    } else {
      int[][] newVertices = new int[length][(length - 1)];
      double costCurrentNode, costChild;
      double bestCost = Double.MAX_VALUE;

      for (int i = 0; i < length; i++) {
        for (int j = 0, k = 0; j < length; j++, k++) {
          if (j == i) {
            k--;
            continue;
          }
          newVertices[i][k] = vertices[j];
        }

        costCurrentNode = distanceMatrix[initial][vertices[i]];
        newCostUntilHere = costCurrentNode + costUntilHere;
        costChild = algorithmTSP(vertices[i], newVertices[i], path, newCostUntilHere);

        double totalCost = costChild + costCurrentNode;
        if (totalCost < bestCost) bestCost = totalCost;
      }
      return bestCost;
    }
  }

  public static ArrayList<Point> translatePath(String path) {
    ArrayList<Point> translated = new ArrayList<Point>();
    String[] splitted = path.split("-");
    for (int i = 0; i < splitted.length; i++) {
      translated.add(points.get(Integer.parseInt(splitted[i])));
    }
    return translated;
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    edges = em.prepareFullGraph(points);
    vm.horizontalLine(horizontalLength);

    System.out.println("   ILOŚĆ PUNKTÓW: " + points.size());
    System.out.println("   SUMA KRAWĘDZI: " + edges.size());
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM KOMIWOJAŻERA", horizontalLength);

    String path = "";
    int[] vertices = new int[points.size()-1];

    for (int i = 1; i < points.size(); i++) {
      vertices[i - 1] = i;
    }

    distanceMatrix = new double[points.size()][points.size()];
    distanceMatrix = fillDistanceMatrix(points, distanceMatrix);

    em.displayDistanceMatrix(distanceMatrix);
    vm.horizontalLine(horizontalLength);

    algorithmTSP(0, vertices, path, 0);

    finalPath = translatePath(optimalPath);
    vm.displayFramed("Cykl:  " + optimalPath);

    String[] splittedPath = optimalPath.split("-");

    for (int i = 0; i < finalPath.size(); i++) {
      System.out.println(String.format("   %5s%15s", "[" + splittedPath[i] + "]", finalPath.get(i)));
      Edge tmpEdge = new Edge();
      if (i < finalPath.size()-1)
        tmpEdge = new Edge(finalPath.get(i), finalPath.get(i+1));
      else
        tmpEdge = new Edge(finalPath.get(i), finalPath.get(0));
      edgesPath.add(tmpEdge);
    }

    vm.displayFramed("Koszt:  " + optimalDistance);
    vm.horizontalLine(horizontalLength);

    Window.display();
  }
}
