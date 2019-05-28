import java.util.ArrayList;

public class ItemManager {
  public static VisualManager vm = new VisualManager();

  public static void checkItems(ArrayList<Item> items) {
    if (items.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayItems(ArrayList<Item> items) {
    vm.title("Liczba przedmiotów: " + items.size(), App.horizontalLength);
    System.out.println("   ╭──────┬─────────┬─────────╮");
    System.out.println("   │ L.P. │ Rozmiar │ Wartość │");
    System.out.println("   ├──────┼─────────┼─────────┤");
    for (int i = 0; i < items.size(); i++) {
      System.out.println(String.format("   │ %4d │ %7.2f │ %7.2f │", (i+1), items.get(i).getSize(), items.get(i).getValue()));
    }
    System.out.println("   ╰──────┴─────────┴─────────╯");
  }
}
