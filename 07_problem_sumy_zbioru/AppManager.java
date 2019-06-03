import java.util.ArrayList;
import java.math.RoundingMode;
import java.math.BigDecimal;

public class AppManager {
  public static void checkNumbers(ArrayList<Integer> numbers) {
    if (numbers.size() == 0)
      App.exitOnPurpose("Nie podano punktów.");
  }

  public static void displayNumbers(ArrayList<Integer> numbers) {
    displayNumbers(numbers, -1);
  }

  public static void displayNumbers(ArrayList<Integer> numbers, int index) {
    if (index != -1)
      System.out.print("   [" + index + "]");
    else
      System.out.print("   ");
    for (int i = 0; i < numbers.size(); i++)
      System.out.print(" " + numbers.get(i));
    System.out.print("\n");
  }

  public static double round(double value, int places) {
    if (places < 0) throw new IllegalArgumentException();
    return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
  }
}
