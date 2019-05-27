import java.util.ArrayList;

public class ItemManager {
  public static VisualManager vm = new VisualManager();

  public static void checkItems(ArrayList<Item> items) {
    if (items.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayItems(ArrayList<Item> items) {
    vm.title("Liczba przedmiotów: " + items.size(), App.horizontalLength);
    for (int i = 0; i < items.size(); i++) {
      System.out.println((i+1) + ": " + items.get(i).getSize() + ", " + items.get(i).getValue());
    }
  }
}
