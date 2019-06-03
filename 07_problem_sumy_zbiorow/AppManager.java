import java.util.ArrayList;

public class AppManager {
  public static void checkNumbers(ArrayList<Double> numbers) {
    if (numbers.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayNumbers(ArrayList<Double> numbers) {
    System.out.print("  ");
    for (int i = 0; i < numbers.size(); i++)
      System.out.print(" " + numbers.get(i));
    System.out.print("\n");
  }
}
