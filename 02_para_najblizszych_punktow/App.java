import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static int middle = 0;
  public static Point middlePoint;
  public static double lowest = 0;

  public static Point lowest_s1_p1;
  public static Point lowest_s1_p2;
  public static Point lowest_s2_p1;
  public static Point lowest_s2_p2;

  public static ArrayList<Point> pointsByX = new ArrayList<Point>();
  public static ArrayList<Point> pointsByY = new ArrayList<Point>();

  public static ArrayList<Point> s1x = new ArrayList<Point>();
  public static ArrayList<Point> s1y = new ArrayList<Point>();
  public static ArrayList<Point> s2x = new ArrayList<Point>();
  public static ArrayList<Point> s2y = new ArrayList<Point>();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();

  public static void setMiddle(ArrayList<Point> pointsByX) {
    middle = points.size()/2;
    middlePoint = pointsByX.get(middle-1);
  }

  public static void main(String args[]) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      System.out.println("Nie wybrano pliku.");
      System.exit(0);
    }

    points = fm.loadPoints(inputFileName);
    pointsByX = pm.sortByX(new ArrayList(points));
    pointsByY = pm.sortByY(new ArrayList(points));

    setMiddle(pointsByX);
    System.out.println(middle + " : " + middlePoint.toString());

    System.out.println("S:");
    pm.displayPoints(points);
    System.out.println("SX:");
    pm.displayPoints(pointsByX);
    System.out.println("SY:");
    pm.displayPoints(pointsByY);

    s1x.addAll(pointsByX.subList(0, middle));
    s1y.addAll(pointsByY.subList(0, middle));
    s2x.addAll(pointsByX.subList(middle, pointsByX.size()));
    s2y.addAll(pointsByY.subList(middle, pointsByY.size()));

    System.out.println("S1X:");
    pm.displayPoints(s1x);
    System.out.println("S1Y:");
    pm.displayPoints(s1y);
    System.out.println("S2X:");
    pm.displayPoints(s2x);
    System.out.println("S1Y:");
    pm.displayPoints(s2y);

    double lowest_s1 = pm.getLowestDistance("S1", s1x);
    double lowest_s2 = pm.getLowestDistance("S2", s2x);

    System.out.print("\n");
    System.out.println("LOWEST DISTANCE:");
    System.out.println("   S1: " + lowest_s1 + ", S2: " + lowest_s2);

    System.out.println("ANSWER:");
    lowest = (lowest_s1 < lowest_s2) ? lowest_s1 : lowest_s2;
    System.out.println("   " + lowest);

    Window.display();
  }
}
