import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class FileManager {
  public static Point stringToPoint(String stringToSplit) {
    String[] splitted = stringToSplit.split(",");
    Point p = new Point(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]));
    return p;
  }

  public static ArrayList<Point> loadPoints(String fileName) {
    ArrayList<Point> points = new ArrayList<Point>();
    try {
      FileInputStream fileInputStream = new FileInputStream(fileName);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String line;
      while ((line = bufferedReader.readLine()) != null) {
          points.add(stringToPoint(line));
      }
    } catch (Exception e) {
      // e.printStackTrace();
      App.exitOnPurpose("Plik \"" + fileName + "\" nie został znaleziony.");
      System.exit(0);
    }
    return points;
  }
}
