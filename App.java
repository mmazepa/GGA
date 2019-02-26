import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class App {

    public static ArrayList<Point> polygon = new ArrayList<Point>();
    public static String inputFileName = "input.txt";

    public static void stringToPoint(String stringToSplit) {
        String[] splitted = stringToSplit.split(",");
        Point p = new Point(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]));
        polygon.add(p);
    }

    public static void preparePolygon() {
        try {
            FileInputStream fileInputStream = new FileInputStream(inputFileName);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringToPoint(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {    
        preparePolygon();

        for (int i = 0; i < polygon.size(); i++)
            System.out.println("P(" + polygon.get(i).getX() + ", " + polygon.get(i).getY() + ")");
    }
}