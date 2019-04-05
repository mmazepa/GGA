import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0) {
      inputFileName = args[0];
    } else {
      exitOnPurpose("Nie wybrano pliku.");
    }
  }

  public static void exitOnPurpose(String purpose) {
    System.out.println(purpose);
    System.exit(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);
    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    vm.horizontalLine(50);
    Window.display();
  }
}
