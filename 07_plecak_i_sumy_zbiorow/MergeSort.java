import java.util.ArrayList;

public class MergeSort {
  public ArrayList<Double> mergeSort(ArrayList<Double> numbers) {
    ArrayList<Double> left = new ArrayList<Double>();
    ArrayList<Double> right = new ArrayList<Double>();
    int center;

    if (numbers.size() == 1) {
      return numbers;
    } else {
      center = numbers.size()/2;

      for (int i = 0; i < center; i++) left.add(numbers.get(i));
      for (int i = center; i < numbers.size(); i++) right.add(numbers.get(i));

      left = mergeSort(left);
      right = mergeSort(right);

      numbers = merge(left, right, numbers);
    }
    return numbers;
  }

  public boolean sortConditions(ArrayList<Double> left, ArrayList<Double> right, int leftIndex, int rightIndex) {
    double numLeft = left.get(leftIndex);
    double numRight = right.get(rightIndex);
    return (numLeft < numRight);
  }

  public ArrayList<Double> merge(ArrayList<Double> left, ArrayList<Double> right, ArrayList<Double> numbers) {
    int leftIndex = 0;
    int rightIndex = 0;
    int numbersIndex = 0;

    while (leftIndex < left.size() && rightIndex < right.size()) {
      if (sortConditions(left, right, leftIndex, rightIndex)) {
        numbers.set(numbersIndex, left.get(leftIndex));
        leftIndex++;
      } else {
        numbers.set(numbersIndex, right.get(rightIndex));
        rightIndex++;
      }
      numbersIndex++;
    }

    ArrayList<Double> rest;
    int restIndex;
    if (leftIndex >= left.size()) {
      rest = right;
      restIndex = rightIndex;
    } else {
      rest = left;
      restIndex = leftIndex;
    }

    for (int i = restIndex; i < rest.size(); i++) {
      numbers.set(numbersIndex, rest.get(i));
      numbersIndex++;
    }
    return numbers;
  }
}
