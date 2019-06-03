import java.util.ArrayList;

public class MergeSort {
  public ArrayList<Integer> mergeSort(ArrayList<Integer> numbers) {
    ArrayList<Integer> left = new ArrayList<Integer>();
    ArrayList<Integer> right = new ArrayList<Integer>();
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

  public boolean sortConditions(ArrayList<Integer> left, ArrayList<Integer> right, int leftIndex, int rightIndex) {
    double numLeft = left.get(leftIndex);
    double numRight = right.get(rightIndex);
    return (numLeft < numRight);
  }

  public ArrayList<Integer> merge(ArrayList<Integer> left, ArrayList<Integer> right, ArrayList<Integer> numbers) {
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

    ArrayList<Integer> rest;
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
