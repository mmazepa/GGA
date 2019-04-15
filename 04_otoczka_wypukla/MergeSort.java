import java.util.ArrayList;

public class MergeSort {
  public ArrayList<Point> mergeSort(ArrayList<Point> points) {
    ArrayList<Point> left = new ArrayList<Point>();
    ArrayList<Point> right = new ArrayList<Point>();
    int center;

    if (points.size() == 1) {
      return points;
    } else {
      center = points.size()/2;

      for (int i = 0; i < center; i++) left.add(points.get(i));
      for (int i = center; i < points.size(); i++) right.add(points.get(i));

      left  = mergeSort(left);
      right = mergeSort(right);

      merge(left, right, points);
    }
    return points;
  }

  public boolean sortConditions(ArrayList<Point> left, ArrayList<Point> right, int leftIndex, int rightIndex) {
    double alphaLeft = left.get(leftIndex).getAlpha();
    double alphaRight = right.get(rightIndex).getAlpha();
    double xLeft = left.get(leftIndex).getX();
    double xRight = right.get(rightIndex).getX();

    return (alphaLeft < alphaRight) || ((alphaLeft == alphaRight) && (xLeft < xRight));
  }

  public void merge(ArrayList<Point> left, ArrayList<Point> right, ArrayList<Point> points) {
    int leftIndex = 0;
    int rightIndex = 0;
    int pointsIndex = 0;

    while (leftIndex < left.size() && rightIndex < right.size()) {
      if (sortConditions(left, right, leftIndex, rightIndex)) {
        points.set(pointsIndex, left.get(leftIndex));
        leftIndex++;
      } else {
        points.set(pointsIndex, right.get(rightIndex));
        rightIndex++;
      }
      pointsIndex++;
    }

    ArrayList<Point> rest;
    int restIndex;
    if (leftIndex >= left.size()) {
      rest = right;
      restIndex = rightIndex;
    } else {
      rest = left;
      restIndex = leftIndex;
    }

    for (int i = restIndex; i < rest.size(); i++) {
      points.set(pointsIndex, rest.get(i));
      pointsIndex++;
    }
  }
}
