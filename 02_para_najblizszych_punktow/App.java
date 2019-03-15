import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static int middle = 0;
  public static Point middlePoint;

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

    ArrayList<Point> pointsByX = pm.sortByX(new ArrayList(points));
    ArrayList<Point> pointsByY = pm.sortByY(new ArrayList(points));

    setMiddle(pointsByX);
    System.out.println(middle + " : " + middlePoint.toString());

    System.out.println("S:");
    pm.displayPoints(points);
    System.out.println("SX:");
    pm.displayPoints(pointsByX);
    System.out.println("SY:");
    pm.displayPoints(pointsByY);

    ArrayList<Point> s1x = new ArrayList<Point>();
    ArrayList<Point> s1y = new ArrayList<Point>();
    ArrayList<Point> s2x = new ArrayList<Point>();
    ArrayList<Point> s2y = new ArrayList<Point>();

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

    Window.display();
  }
}
