import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class App {

    public static ArrayList<Point> polygon = new ArrayList<Point>();

    public static void main(String args[]) {
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        
        try {
            fileInputStream = new FileInputStream("input.txt");
            inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);
            String s;

            while ((s = bufferedReader.readLine()) != null) {
                String[] var = s.split(",");
                Point p = new Point(Double.parseDouble(var[0]), Double.parseDouble(var[1]));
                polygon.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 3; i++)
            System.out.println("P(" + polygon.get(i).getX() + ", " + polygon.get(i).getY() + ")");
    }
}