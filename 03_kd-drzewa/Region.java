public class Region {
  Double x_min;
  Double x_max;
  Double y_min;
  Double y_max;

  public Region (Double x1, Double y1, Double x2, Double y2) {
    x_min = x1;
    y_min = y1;
    x_max = x2;
    y_max = y2;
  }

  public Region (Region r) {
    this(r.x_min, r.y_min, r.x_max, r.y_max);
  }

  static final Double minValue = Double.MIN_VALUE;
  static final Double maxValue = Double.MAX_VALUE;
  static final Region max = new Region(minValue, minValue, maxValue, maxValue);
}
