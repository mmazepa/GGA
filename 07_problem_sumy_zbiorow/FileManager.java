import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class FileManager {
  public static double stringToNumber(String stringToDouble) {
    return Double.parseDouble(stringToDouble);
  }

  public static ArrayList<Double> loadNumbers(String fileName) {
    ArrayList<Double> numbers = new ArrayList<Double>();
    try {
      FileInputStream fileInputStream = new FileInputStream(fileName);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String line;
      while ((line = bufferedReader.readLine()) != null) {
          numbers.add(stringToNumber(line));
      }
    } catch (Exception e) {
      // e.printStackTrace();
      if (numbers.size() == 0)
        App.exitOnPurpose("Plik \"" + fileName + "\" nie został znaleziony.");
      else
        App.exitOnPurpose("Wystąpił błąd, upewnij się, że wprowadziłeś przedmioty właściwie.");
      System.exit(0);
    }
    return numbers;
  }
}
