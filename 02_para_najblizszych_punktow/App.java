import java.util.ArrayList;

public class App {
    public static String inputFileName = new String();
    public static ArrayList<Point> points = new ArrayList<Point>();

    public static double calculateDistance(Point p1, Point p2) {
      return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public static void displayPoints(ArrayList<Point> points) {
      for (Point point : points) {
        System.out.println(point.toString());
      }
    }

    public static void main(String args[]) {
      if (args.length > 0) {
        inputFileName = args[0];
      } else {
        System.out.println("Nie wybrano pliku.");
        System.exit(0);
      }

      FileManager fm = new FileManager();
      points = fm.loadPoints(inputFileName);
      displayPoints(points);

      Window.display();
    }
}
