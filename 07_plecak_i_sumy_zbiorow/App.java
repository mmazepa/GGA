import java.util.ArrayList;

public class App {
  public static ArrayList<Item> items = new ArrayList<Item>();
  public static ArrayList<Double> numbers = new ArrayList<Double>();
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

  public static ArrayList<Double> circledPlus(ArrayList<Double> list, Double number) {
    ArrayList<Double> result = new ArrayList<Double>();
    for (int i = 0; i < list.size(); i++) {
      result.add(list.get(i) + number);
    }
    return result;
  }

  public static void displayNumbers(ArrayList<Double> numbers) {
    System.out.print("  ");
    for (int i = 0; i < numbers.size(); i++)
      System.out.print(" " + numbers.get(i));
    System.out.print("\n");
  }

  public static ArrayList<Double> removeDuplicates(ArrayList<Double> numbers) {
    ArrayList<Double> result = new ArrayList<Double>();
    for (int i = 0; i < numbers.size()-1; i++) {
      if (Double.compare(numbers.get(i), numbers.get(i+1)) != 0)
        result.add(numbers.get(i));
    }
    if (Double.compare(numbers.get(numbers.size()-1), result.get(result.size()-1)) != 0)
      result.add(numbers.get(numbers.size()-1));
    return result;
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    items = fm.loadItems(inputFileName);
    im.checkItems(items);

    if (args.length < 2) exitOnPurpose("Nie podano ograniczenia.");

    im.displayItems(items);
    vm.horizontalLine(horizontalLength);

    vm.displayFramed("Ograniczenie: " + args[1]);
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM PLECAKOWY", horizontalLength);
    System.out.println("   Problem plecakowy w budowie...");
    vm.horizontalLine(horizontalLength);

    vm.title("PROBLEM SUMY ZBIORÓW", horizontalLength);

    ArrayList<Double> doubled = new ArrayList<Double>();
    MergeSort ms = new MergeSort();
    for (int i = 0; i < items.size(); i++) {
      numbers.add(items.get(i).getValue());
      doubled.add(items.get(i).getValue());
      doubled.add(items.get(i).getSize());
    }
    numbers = ms.mergeSort(numbers);
    System.out.println("LISTA POCZĄTKOWA:");
    displayNumbers(numbers);

    double xi = numbers.get(5);
    numbers = ms.merge(numbers, circledPlus(numbers, xi), doubled);

    System.out.println("PLUS W KÓŁKU (" + xi + "):");
    displayNumbers(numbers);

    numbers = removeDuplicates(numbers);

    System.out.println("PO USUNIĘCIU DUPLIKATÓW:");
    displayNumbers(numbers);

    System.out.print("\n");
    System.out.println("   Problem sumy zbiorów w budowie...");
    vm.horizontalLine(horizontalLength);

    vm.title("EKPERYMENTALNE PORÓWNANIE WYNIKÓW", horizontalLength);
    System.out.println("   Eksperymentalne porównanie wyników w budowie...");
    vm.horizontalLine(horizontalLength);
  }
}
