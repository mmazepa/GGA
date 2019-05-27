import java.util.ArrayList;

public class App {
  public static ArrayList<Item> items = new ArrayList<Item>();
  public static String inputFileName = new String();

  public static FileManager fm = new FileManager();
  public static ItemManager im = new ItemManager();
  public static VisualManager vm = new VisualManager();

  public static int horizontalLength = 60;

  public static void checkIfFileNameIsPassed(String[] args) {
    if (args.length > 0)
      inputFileName = args[0];
    else
      exitOnPurpose("Nie wybrano pliku.");
  }

  public static void exitOnPurpose(String purpose) {
    vm.log("exit", purpose);
    System.exit(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    items = fm.loadItems(inputFileName);
    im.checkItems(items);

    im.displayItems(items);
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM PLECAKOWY", horizontalLength);
    System.out.println("   Problem plecakowy w budowie...");
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM SUMY ZBIORÓW", horizontalLength);
    System.out.println("   Problem sumy zbiorów w budowie...");
    vm.horizontalLine(horizontalLength);

    vm.title("EKPERYMENTALNE PORÓWNANIE WYNIKÓW", horizontalLength);
    System.out.println("   Eksperymentalne porównanie wyników w budowie...");
    vm.horizontalLine(horizontalLength);
  }
}
