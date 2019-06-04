import java.util.ArrayList;

public class App {
  public static ArrayList<Integer> numbers = new ArrayList<Integer>();
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

  public static ArrayList<Integer> circledPlus(ArrayList<Integer> list, int number) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < list.size(); i++) {
      result.add(list.get(i) + number);
    }
    return result;
  }

  public static ArrayList<Integer> removeDuplicates(ArrayList<Integer> numbers) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < numbers.size()-1; i++) {
      if (numbers.get(i) != numbers.get(i+1))
        result.add(numbers.get(i));
    }
    if (numbers.get(numbers.size()-1) != result.get(result.size()-1))
      result.add(numbers.get(numbers.size()-1));
    return result;
  }

  public static ArrayList<Integer> merge(ArrayList<Integer> l1, ArrayList<Integer> l2) {
    ArrayList<Integer> l3 = new ArrayList<Integer>();
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

    ArrayList<Integer> rest;
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

  public static ArrayList<Integer> trim(ArrayList<Integer> numbers, double delta) {
    ArrayList<Integer> L_out = new ArrayList<Integer>();
    L_out.add(numbers.get(0));
    int tmp = numbers.get(0);

    for (int i = 1; i < numbers.size(); i++) {
      if (tmp < ((1-delta) * numbers.get(i))) {
        L_out.add(numbers.get(i));
        tmp = numbers.get(i);
      }
    }
    return L_out;
  }

  public static ArrayList<Integer> removeTooBigNumbers(ArrayList<Integer> numbers, int number) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    for (int i = 0; i < numbers.size(); i++) {
      if (numbers.get(i) <= number)
        result.add(numbers.get(i));
    }
    return result;
  }

  public static int subsetSumFPTAS(ArrayList<Integer> numbers, double epsilon, int limit) {
    double delta = epsilon/numbers.size();

    vm.displayFramed("Delta: " + epsilon + "/" + numbers.size() + " = " + am.round(delta,5));

    ArrayList<Integer> tmp = new ArrayList<Integer>();
    tmp.add(0);

    vm.horizontalLine(horizontalLength);
    am.displayNumbers(tmp, 0);
    vm.horizontalLine(horizontalLength);

    for (int i = 0; i < numbers.size(); i++) {
      System.out.println("   [start]: " + tmp);
      System.out.println("   [xi]:    " + numbers.get(i));

      tmp = merge(tmp, circledPlus(tmp, numbers.get(i)));
      System.out.println("   [merge]: " + tmp);

      tmp = trim(tmp, delta);
      System.out.println("   [trim]:  " + tmp);

      tmp = removeTooBigNumbers(tmp, limit);
      System.out.println("   [limit]: " + tmp);

      vm.horizontalLine(horizontalLength);
      am.displayNumbers(tmp, (i+1));
      vm.horizontalLine(horizontalLength);
    }
    return tmp.get(tmp.size()-1);
  }

  public static void main(String args[]) {
    checkIfFileNameIsPassed(args);

    numbers = fm.loadNumbers(inputFileName);
    am.checkNumbers(numbers);

    if (args.length < 2) exitOnPurpose("Nie podano ograniczenia.");
    if (args.length < 3) exitOnPurpose("Nie podano wartości epsilon.");

    vm.horizontalLine(horizontalLength);

    int limit = (int)Double.parseDouble(args[1]);
    double epsilon = Double.parseDouble(args[2]);

    if (limit == 0) exitOnPurpose("Limit równy 0 nie ma sensu.");

    vm.title("PROBLEM SUMY ZBIORÓW", horizontalLength);
    vm.displayFramed("Ograniczenie: " + limit + ", Epsilon: " + epsilon);

    vm.title("LISTA POCZĄTKOWA", horizontalLength);
    am.displayNumbers(numbers);

    MergeSort ms = new MergeSort();
    numbers = ms.mergeSort(numbers);

    vm.title("LISTA POSORTOWANA", horizontalLength);
    am.displayNumbers(numbers);

    vm.title("ALGORYTM FPTAS", horizontalLength);
    int result = subsetSumFPTAS(numbers, epsilon, limit);

    vm.title("KONIEC ALGORYTMU", horizontalLength);
    vm.displayFramed("Wynik końcowy: " + result);
    vm.horizontalLine(horizontalLength);
  }
}
