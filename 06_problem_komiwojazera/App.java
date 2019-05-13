import java.util.ArrayList;

public class App {
  public static ArrayList<Point> points = new ArrayList<Point>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static PointManager pm = new PointManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

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

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    points = fm.loadPoints(inputFileName);
    pm.checkPoints(points);

    pm.displayPoints(points);
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM KOMIWOJAŻERA", horizontalLength);

    System.out.println("   Algorytm w budowie...");

    vm.horizontalLine(horizontalLength);
    Window.display();
  }
}
