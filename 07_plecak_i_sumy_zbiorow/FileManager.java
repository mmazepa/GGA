import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;

public class FileManager {
  public static Item stringToItem(String stringToSplit) {
    String[] splitted = stringToSplit.split(",");
    Item item = new Item(Double.parseDouble(splitted[0]), Double.parseDouble(splitted[1]));
    return item;
  }

  public static ArrayList<Item> loadItems(String fileName) {
    ArrayList<Item> items = new ArrayList<Item>();
    try {
      FileInputStream fileInputStream = new FileInputStream(fileName);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

      String line;
      while ((line = bufferedReader.readLine()) != null) {
          items.add(stringToItem(line));
      }
    } catch (Exception e) {
      // e.printStackTrace();
      if (items.size() == 0)
        App.exitOnPurpose("Plik \"" + fileName + "\" nie został znaleziony.");
      else
        App.exitOnPurpose("Wystąpił błąd, upewnij się, że wprowadziłeś przedmioty właściwie.");
      System.exit(0);
    }
    return items;
  }
}
