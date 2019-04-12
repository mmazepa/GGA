public class Point {
  private double x;
  private double y;
  private double d;
  private double alpha;

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

  public void setD(double d) {
    this.d = d;
  }
  public double getD() {
    return d;
  }

  public void setAlpha(double alpha) {
    this.alpha = alpha;
  }
  public double getAlpha() {
    return alpha;
  }

  public String toString() {
    return "(" + (double)Math.round(x*1000)/1000 + ", " + (double)Math.round(y*1000)/1000 + ")";
  }
}
