public class Point {
  private double x;
  private double y;
  boolean isUpper;
  boolean isLower;

  public Point() {}

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void setX(double x) {
    this.x = x;
  }
  public double getX() {
    return x;
  }

  public void setY(double y) {
    this.y = y;
  }
  public double getY() {
    return y;
  }

  public void setIsUpper(boolean isUpper) {
    this.isUpper = isUpper;
  }
  public boolean getIsUpper() {
    return isUpper;
  }

  public void setIsLower(boolean isLower) {
    this.isLower = isLower;
  }
  public boolean getIsLower() {
    return isLower;
  }

  public String toString() {
    return "(" + (double)Math.round(x*1000)/1000 + ", " + (double)Math.round(y*1000)/1000 + ")";
  }
}
