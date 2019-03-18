import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();
  public static int middle = 0;
  public static Point middlePoint;
  public static double lowest = 0;
  public static double lowest_s1_s2;

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

  public static void fillPartialArrays() {
    s1x.addAll(pointsByX.subList(0, middle));
    s2x.addAll(pointsByX.subList(middle, pointsByX.size()));

    for (int i = 0; i < pointsByY.size(); i++) {
      if (s1x.contains(pointsByY.get(i))) s1y.add(pointsByY.get(i));
      if (s2x.contains(pointsByY.get(i))) s2y.add(pointsByY.get(i));
    }
  }

  public static ArrayList<Point> fillTheThirdArray(ArrayList<Point> firstArray, ArrayList<Point> thirdArray) {
    double difference = 0;
    for (Point point : firstArray) {
      difference = Math.abs(middlePoint.getX() - point.getX());
      if (difference <= lowest_s1_s2) thirdArray.add(point);
    }
    return thirdArray;
  }

  public static String showTwo(Point p1, Point p2) {
    return p1.toString() + ", " + p2.toString();
  }

  public static void showResult(String area, double lowest, Point p1, Point p2) {
    System.out.println("   " + area + ": " + lowest + " ---> " + showTwo(p1, p2));
  }

  public static void answerPart1_LowestDistance(boolean isThirdValid, double lowest_s1, double lowest_s2, double lowest_s3) {
    System.out.println("LOWEST DISTANCE:");
    if (lowest_s1 != -1 && lowest_s1_p1 != null && lowest_s1_p2 != null)
      showResult("S1", lowest_s1, lowest_s1_p1, lowest_s1_p2);
    if (lowest_s2 != -1 && lowest_s2_p1 != null && lowest_s2_p2 != null)
      showResult("S2", lowest_s2, lowest_s2_p1, lowest_s2_p2);
    if (isThirdValid && lowest_s3 != -1 && lowest_s3_p1 != null && lowest_s3_p2 != null) {
      showResult("S3", lowest_s3, lowest_s3_p1, lowest_s3_p2);
    }
  }

  public static void answerPart2_Calculation(boolean isThirdValid, double lowest_s1, double lowest_s2, double lowest_s3) {
    System.out.println("CALCULATION:");
    if (isThirdValid) {
      System.out.println("   lowest = min(" + lowest_s1 + ", " + lowest_s2 + ", " + lowest_s3 + ")");
    } else {
      System.out.println("   lowest = min(" + lowest_s1 + ", " + lowest_s2 + ")");
    }
  }

  public static int whichOneIsTheLowest(boolean isThirdValid, double[] lowestValues) {
    int minIndex = 0;
    double min = lowestValues[0];
    for (int i = 0; i < lowestValues.length; i++) {
      if (lowestValues[i] < min) {
        if (i == 2 && !isThirdValid) break;
        minIndex = i;
        min = lowestValues[i];
      }
    }
    return minIndex;
  }

  public static void answerPart3_ClosestPoints(boolean isThirdValid, double lowest_s1, double lowest_s2, double lowest_s3) {
    Point[] lowestPoints = { lowest_s1_p1, lowest_s1_p2, lowest_s2_p1, lowest_s2_p2, lowest_s3_p1, lowest_s3_p2 };
    double[] lowestValues = { lowest_s1, lowest_s2, lowest_s3 };

    System.out.println("ANSWER:");
    int minIndex = whichOneIsTheLowest(isThirdValid, lowestValues);
    System.out.println("   closest points: " + showTwo(lowestPoints[minIndex*2], lowestPoints[minIndex*2+1]));
  }

  public static void displayAnswer(boolean isThirdValid, double lowest_s1, double lowest_s2, double lowest_s3) {
    System.out.print("\n");
    answerPart1_LowestDistance(isThirdValid, lowest_s1, lowest_s2, lowest_s3);
    answerPart2_Calculation(isThirdValid, lowest_s1, lowest_s2, lowest_s3);
    answerPart3_ClosestPoints(isThirdValid, lowest_s1, lowest_s2, lowest_s3);
    System.out.println("   lowest = " + lowest);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);

    if (points.size() == 1) {
      System.out.println("There is only one point, can't calculate distance.");
      System.exit(0);
    }

    pointsByX = pm.sortByX(new ArrayList(points));
    pointsByY = pm.sortByY(new ArrayList(points));

    setMiddle(pointsByX);
    System.out.println("Middle: " + middle + ", " + middlePoint.toString());

    arrayDisplayer("S", points);
    arrayDisplayer("SX", pointsByX);
    arrayDisplayer("SY", pointsByY);

    fillPartialArrays();

    arrayDisplayer("S1X", s1x);
    arrayDisplayer("S1Y", s1y);
    arrayDisplayer("S2X", s2x);
    arrayDisplayer("S2Y", s2y);

    double lowest_s1 = pm.getLowestDistance("S1", s1x);
    double lowest_s2 = pm.getLowestDistance("S2", s2x);

    if (lowest_s1 != -1 && lowest_s2 != -1) lowest_s1_s2 = Math.min(lowest_s1, lowest_s2);
    else if (lowest_s1 == -1 && lowest_s2 == -1) lowest_s1_s2 = -1;
    else if (lowest_s1 == -1) lowest_s1_s2 = lowest_s2;
    else if (lowest_s2 == -1) lowest_s1_s2 = lowest_s2;

    fillTheThirdArray(s1y, s3_1);
    fillTheThirdArray(s2y, s3_2);

    double lowest_s3 = pm.getLowestDistanceFromThirdArray(s3_1, s3_2, lowest_s1_s2);
    boolean isThirdValid = (s3_1.size() > 0 && s3_2.size() > 0) && (lowest_s3 != -1);

    if (isThirdValid) {
      arrayDisplayer("S3_1", s3_1);
      arrayDisplayer("S3_2", s3_2);
      lowest = Math.min(lowest_s1_s2, lowest_s3);
    } else {
      lowest = lowest_s1_s2;
    }

    if (points.size() == 3) {
      System.out.println("ANSWER:");
      double dist1 = pm.getDistance(points.get(0), points.get(1));
      double dist2 = pm.getDistance(points.get(1), points.get(2));
      double dist3 = pm.getDistance(points.get(2), points.get(0));
      double min = Math.min(dist1, dist2);
      min = Math.min(min, dist3);
      if (min == dist1) {
        System.out.println("   closest points: " + showTwo(points.get(0), points.get(1)));
        System.out.println("   lowest = " + pm.getDistance(points.get(0), points.get(1)));
      } else if (min == dist2) {
        System.out.println("   closest points: " + showTwo(points.get(1), points.get(2)));
        System.out.println("   lowest = " + pm.getDistance(points.get(1), points.get(2)));
      } else if (min == dist3) {
        System.out.println("   closest points: " + showTwo(points.get(2), points.get(0)));
        System.out.println("   lowest = " + pm.getDistance(points.get(2), points.get(0)));
      }
      System.exit(0);
    } else if (points.size() == 2) {
      System.out.println("ANSWER:");
      System.out.println("   closest points: " + showTwo(points.get(0), points.get(1)));
      System.out.println("   lowest = " + pm.getDistance(points.get(0), points.get(1)));
      System.exit(0);
    } else if (points.size() == 2) {
      System.out.println("ANSWER:");
      System.out.println("   closest points: " + showTwo(points.get(0), points.get(1)));
      System.out.println("   lowest = " + pm.getDistance(points.get(0), points.get(1)));
      System.exit(0);
    } else {
      displayAnswer(isThirdValid, lowest_s1, lowest_s2, lowest_s3);
    }
    Window.display();
  }
}
