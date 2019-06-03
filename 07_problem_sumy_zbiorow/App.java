import java.util.ArrayList;

public class App {
  public static ArrayList<Double> numbers = new ArrayList<Double>();
  public static String inputFileName = new String();

  public static AppManager am = new AppManager();
  public static FileManager fm = new FileManager();
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

  public static ArrayList<Double> merge(ArrayList<Double> l1, ArrayList<Double> l2) {
    ArrayList<Double> l3 = new ArrayList<Double>();
    int l1_Index = 0;
    int l2_Index = 0;

    while (l1_Index < l1.size() && l2_Index < l2.size()) {
      if (l1.get(l1_Index) < l2.get(l2_Index)) {
        l3.add(l1.get(l1_Index));
        l1_Index++;
      } else {
        l3.add(l2.get(l2_Index));
        l2_Index++;
      }
    }

    ArrayList<Double> rest;
    int restIndex;
    if (l1_Index >= l1.size()) {
      rest = l2;
      restIndex = l2_Index;
    } else {
      rest = l1;
      restIndex = l1_Index;
    }

    for (int i = restIndex; i < rest.size(); i++) {
      l3.add(rest.get(i));
    }

    return removeDuplicates(l3);
  }

  public static ArrayList<Double> trim(ArrayList<Double> numbers, double delta) {
    ArrayList<Double> L_out = new ArrayList<Double>();
    L_out.add(numbers.get(0));
    double tmp = numbers.get(0);

    for (int i = 1; i < numbers.size(); i++) {
      if (tmp < ((1-delta) * numbers.get(i))) {
        L_out.add(numbers.get(i));
        tmp = numbers.get(i);
      }
    }
    return L_out;
  }

  public static ArrayList<Double> removeTooBigNumbers(ArrayList<Double> numbers, double number) {
    ArrayList<Double> result = new ArrayList<Double>();
    for (int i = 0; i < numbers.size(); i++) {
      if (numbers.get(i) <= number)
        result.add(numbers.get(i));
    }
    return result;
  }

  public static ArrayList<Double> subsetSumFPTAS(ArrayList<Double> numbers, double delta, double limit) {
    ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
    for (int i = 0; i < numbers.size(); i++) {
      double xi = numbers.get(i);
      numbers = merge(numbers, circledPlus(numbers, xi));
      numbers = trim(numbers, delta);
      numbers = removeTooBigNumbers(numbers, limit);
      result.add(numbers);
      am.displayNumbers(numbers);
    }
    return result.get(0);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    numbers = fm.loadNumbers(inputFileName);
    am.checkNumbers(numbers);

    if (args.length < 2) exitOnPurpose("Nie podano ograniczenia.");


    vm.horizontalLine(horizontalLength);

    double limit = Double.parseDouble(args[1]);

    vm.title("PROBLEM SUMY ZBIORÓW", horizontalLength);
    vm.displayFramed("Ograniczenie: " + limit);

    vm.title("LISTA POCZĄTKOWA", horizontalLength);
    am.displayNumbers(numbers);

    MergeSort ms = new MergeSort();
    numbers = ms.mergeSort(numbers);

    vm.title("LISTA POSORTOWANA", horizontalLength);
    am.displayNumbers(numbers);

    vm.title("LISTY MIĘDZYCZASOWE", horizontalLength);

    double delta = 0.5;
    ArrayList<Double> result = subsetSumFPTAS(numbers, delta, limit);

    vm.title("LISTA KOŃCOWA", horizontalLength);
    am.displayNumbers(result);

    vm.displayFramed("Wynik końcowy: " + result.get(result.size()-1));

    vm.horizontalLine(horizontalLength);
  }
}
