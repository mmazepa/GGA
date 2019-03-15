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

  public static Point lowest_s3_p1;
  public static Point lowest_s3_p2;

  public static ArrayList<Point> pointsByX = new ArrayList<Point>();
  public static ArrayList<Point> pointsByY = new ArrayList<Point>();

  public static ArrayList<Point> s1x = new ArrayList<Point>();
  public static ArrayList<Point> s1y = new ArrayList<Point>();
  public static ArrayList<Point> s2x = new ArrayList<Point>();
  public static ArrayList<Point> s2y = new ArrayList<Point>();

  public static ArrayList<Point> s3_1 = new ArrayList<Point>();
  public static ArrayList<Point> s3_2 = new ArrayList<Point>();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      System.out.println("Nie wybrano pliku.");
      System.exit(0);
    }
  }

  public static void setMiddle(ArrayList<Point> pointsByX) {
    middle = points.size()/2;
    middlePoint = pointsByX.get(middle-1);
  }

  public static void arrayDisplayer(String title, ArrayList<Point> array) {
    System.out.println(title + ":");
    pm.displayPoints(array);
  }

  public static ArrayList<Point> fillTheThirdArray(String side, ArrayList<Point> firstArray, ArrayList<Point> thirdArray) {
    double difference = 0;
    for (Point point : firstArray) {
      if (side == "S1") difference = middlePoint.getX() - point.getX();
      else if (side == "S2") difference = point.getX() - middlePoint.getX();

      if (difference <= lowest) thirdArray.add(point);
    }
    return thirdArray;
  }

  public static void displayAnswer(boolean isThirdNotEmpty, double lowest_s1, double lowest_s2, double lowest_s3) {
    System.out.print("\n");
    System.out.println("LOWEST DISTANCE:");
    System.out.println("   S1: " + lowest_s1 + ", S2: " + lowest_s2);
    if (isThirdNotEmpty) {
      System.out.println("   S3: " + lowest_s3);
    }
    System.out.println("ANSWER:");
    if (isThirdNotEmpty) {
      System.out.println("   lowest = min(" + lowest_s1 + ", " + lowest_s2 + ", " + lowest_s3 + ")");
    } else {
      System.out.println("   lowest = min(" + lowest_s1 + ", " + lowest_s2 + ")");
    }
    System.out.println("   lowest = " + lowest);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pointsByX = pm.sortByX(new ArrayList(points));
    pointsByY = pm.sortByY(new ArrayList(points));

    setMiddle(pointsByX);
    System.out.println("Middle: " + middle + ", " + middlePoint.toString());

    arrayDisplayer("S", points);
    arrayDisplayer("SX", pointsByX);
    arrayDisplayer("SY", pointsByY);

    s1x.addAll(pointsByX.subList(0, middle));
    s1y.addAll(pointsByY.subList(0, middle));
    s2x.addAll(pointsByX.subList(middle, pointsByX.size()));
    s2y.addAll(pointsByY.subList(middle, pointsByY.size()));

    arrayDisplayer("S1X", s1x);
    arrayDisplayer("S1Y", s1y);
    arrayDisplayer("S2X", s2x);
    arrayDisplayer("S2Y", s2y);

    double lowest_s1 = pm.getLowestDistance("S1", s1x);
    double lowest_s2 = pm.getLowestDistance("S2", s2x);

    lowest = (lowest_s1 < lowest_s2) ? lowest_s1 : lowest_s2;

    fillTheThirdArray("S1", s1x, s3_1);
    fillTheThirdArray("S2", s2x, s3_2);

    double lowest_s3 = pm.getLowestDistanceFromThirdArray(s3_1, s3_2, lowest);

    if (s3_1.size() > 0 && s3_2.size() > 0) {
      arrayDisplayer("S3_1", s3_1);
      arrayDisplayer("S3_2", s3_2);
      lowest = (lowest < lowest_s3) ? lowest : lowest_s3;
    }

    boolean isThirdNotEmpty = (s3_1.size() > 0 && s3_2.size() > 0);
    displayAnswer(isThirdNotEmpty, lowest_s1, lowest_s2, lowest_s3);
    Window.display();
  }
}
