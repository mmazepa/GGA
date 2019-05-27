public class Item {
  private double size;
  private double value;

  public Item() {}

  public Item(double size, double value) {
    this.size = size;
    this.value = value;
  }

  public void setSize(double size) {
    this.size = size;
  }
  public double getSize() {
    return size;
  }

  public void setValue(double value) {
    this.value = value;
  }
  public double getValue() {
    return value;
  }
}
