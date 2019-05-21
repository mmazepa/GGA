import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static ArrayList<Edge> edges = new ArrayList<Edge>();
  public static String inputFileName = new String();

  public static double[][] distanceMatrix;
  public static ArrayList<Integer> optimalPath = new ArrayList<Integer>();
  public static double optimalDistance = Double.MAX_VALUE;
  public static ArrayList<Edge> edgesPath = new ArrayList<Edge>();

  private final int N;
  private final int START_NODE;
  private final int FINISHED_STATE;

  private boolean ranSolver = false;

  public static EdgeManager em = new EdgeManager();
  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 78;

  public App(double[][] distanceMatrix) {
    this(0, distanceMatrix);
  }

  public App(int startNode, double[][] distanceMatrix) {
    this.distanceMatrix = distanceMatrix;
    N = distanceMatrix.length;
    START_NODE = startNode;
    FINISHED_STATE = (1 << N) - 1;
  }

  public ArrayList<Integer> getPath() {
    if (!ranSolver) solve();
    return optimalPath;
  }

  public double getPathCost() {
    if (!ranSolver) solve();
    return optimalDistance;
  }

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0)
      inputFileName = args[0];
    else
      exitOnPurpose("Nie wybrano pliku.");
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public void solve() {
    int state = 1 << START_NODE;
    Double[][] memo = new Double[N][1 << N];
    Integer[][] prev = new Integer[N][1 << N];
    optimalDistance = algorithmTSP(START_NODE, state, memo, prev);

    int index = START_NODE;
    while (true) {
      optimalPath.add(index);
      Integer nextIndex = prev[index][state];
      if (nextIndex == null) break;
      int nextState = state | (1 << nextIndex);
      state = nextState;
      index = nextIndex;
    }
    optimalPath.add(START_NODE);
    ranSolver = true;
  }

  private double algorithmTSP(int i, int state, Double[][] memo, Integer[][] prev) {
    if (state == FINISHED_STATE) return distanceMatrix[i][START_NODE];
    if (memo[i][state] != null) return memo[i][state];

    double minCost = Double.MAX_VALUE;
    int index = -1;
    for (int next = 0; next < N; next++) {
      if ((state & (1 << next)) != 0) continue;

      int nextState = state | (1 << next);
      double newCost = distanceMatrix[i][next] + algorithmTSP(next, nextState, memo, prev);
      if (newCost < minCost) {
        minCost = newCost;
        index = next;
      }
    }
    prev[i][state] = index;
    return memo[i][state] = minCost;
  }

  public static double[][] fillDistanceMatrix(ArrayList<Point> points, double[][] distanceMatrix) {
    for (int i = 0; i < distanceMatrix.length; i++) {
      for (int j = 1+i; j < distanceMatrix[i].length; j++) {
        double tmpDist = pm.getDistance(points.get(i), points.get(j));
        distanceMatrix[i][j] = tmpDist;
        distanceMatrix[j][i] = tmpDist;
      }
    }
    return distanceMatrix;
  }

  public static ArrayList<Edge> translatePathForDisplayer(ArrayList<Integer> path) {
    ArrayList<Edge> translated = new ArrayList<Edge>();
    for (int i = 0; i < path.size()-1; i++) {
      translated.add(em.prepareEdge(points.get(path.get(i)), points.get(path.get(i+1))));
    }
    return translated;
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    if (points.size() <= 3)
      App.exitOnPurpose("Obliczenia nie mają sensu dla 3 lub mniej punktów.");

    edges = em.prepareFullGraph(points);
    vm.horizontalLine(horizontalLength);

    System.out.println("   ILOŚĆ PUNKTÓW: " + points.size());
    System.out.println("   SUMA KRAWĘDZI: " + edges.size());
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM KOMIWOJAŻERA", horizontalLength);

    distanceMatrix = new double[points.size()][points.size()];
    distanceMatrix = fillDistanceMatrix(points, distanceMatrix);

    em.displayDistanceMatrix(distanceMatrix);
    vm.horizontalLine(horizontalLength);

    App solver = new App(distanceMatrix);
    optimalPath = solver.getPath();
    optimalDistance = solver.getPathCost();

    vm.displayFramed("Cykl: " + optimalPath);
    vm.displayFramed("Koszt: " + pm.round(optimalDistance));

    edgesPath = translatePathForDisplayer(optimalPath);
    vm.horizontalLine(horizontalLength);

    Window.display();
  }
}
