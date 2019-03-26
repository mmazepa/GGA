import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static ArrayList<Point> pointsByX = new ArrayList<Point>();
  public static ArrayList<Point> pointsByY = new ArrayList<Point>();

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

  public static void arrayDisplayer(String title, ArrayList<Point> array) {
    System.out.println(title + ":");
    pm.displayPoints(array);
  }

  public static boolean compareTwoPoints(Point p1, Point p2) {
    if ((p1.getX() < p2.getX()) || (p1.getX() == p2.getX() && p1.getY() < p2.getY()))
    return true;
  else if (p1.getX() > p2.getX() || (p1.getX() == p2.getX() && p1.getY() > p2.getY()))
    return false;
  else
    return true;
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    int d = 0;

    if (points.size() == 1) {
      System.out.println("There is only one point, returning leaf...");
      // return that one leaf here...
      System.exit(0);
    } else if (points.size() == 0) {
      System.out.println("There are no points provided...");
      System.exit(0);
    }

    Window.display();
  }
}
