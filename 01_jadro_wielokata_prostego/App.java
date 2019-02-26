import java.util.ArrayList;

public class App {

    public static ArrayList<Point> polygon = new ArrayList<Point>();
    public static String inputFileName = "input.txt";

    public static void main(String args[]) {
      FileManager fm = new FileManager();
      polygon = fm.getData(inputFileName);

      for (int i = 0; i < polygon.size(); i++)
          System.out.println("P(" + polygon.get(i).getX() + ", " + polygon.get(i).getY() + ")");
    }
}
