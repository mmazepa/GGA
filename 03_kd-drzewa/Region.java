public class Region {
  Double x_min;
  Double x_max;
  Double y_min;
  Double y_max;

  public Region() {}

  public Region(Double x1, Double x2, Double y1, Double y2) {
    x_min = x1;
    x_max = x2;
    y_min = y1;
    y_max = y2;
  }

  public Region(Region r) {
    this(r.x_min, r.x_max, r.y_min, r.y_max);
  }

  static final Double minValue = Double.MIN_VALUE;
  static final Double maxValue = Double.MAX_VALUE;
  static final Region max = new Region(minValue, minValue, maxValue, maxValue);

  public boolean isNotNull() {
    return (this.x_min != null) && (this.x_max != null) && (this.y_min != null) && (this.y_max != null);
  }

  public String toString() {
    // double x1 = Math.round(this.x_min*1000)/1000;
    // double x2 = Math.round(this.x_max*1000)/1000;
    // double y1 = Math.round(this.y_min*1000)/1000;
    // double y2 = Math.round(this.y_max*1000)/1000;
    double x1 = this.x_min;
    double x2 = this.x_max;
    double y1 = this.y_min;
    double y2 = this.y_max;
    return "[ X: " + x1 + ", " + x2 + " | Y: " + y1 + ", " + y2 + "]";
  }
}
