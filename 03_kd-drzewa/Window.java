import java.util.ArrayList;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
  private static int size = 600;
  private static String pointSign = "•";

  // private static Color vColor = Color.GREEN;
  private static Color vColor = new Color(0, 200, 0);
  // private static Color hColor = Color.BLUE;
  private static Color hColor = new Color(0, 0, 255);

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  public static void drawPoint(Graphics g, Point p, Color color) {
    g.setColor(color);
    g.setFont(g.getFont().deriveFont(20.0f));
    g.drawString(pointSign, toInt(50+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)));
    g.setFont(g.getFont().deriveFont(10.0f));
    g.drawString(p.toString(), toInt(25+(p.getX()*50)+10), toInt(0.9*size-(p.getY()*50)-15));
    g.setColor(Color.BLACK);
  }

  public static void drawAllPoints(Graphics g, ArrayList<Point> points, Color color) {
    for (int i = 0; i < points.size(); i++) {
      drawPoint(g, points.get(i), color);
    }
  }

  public static void drawLine(String direction, Graphics g, double median, Color color, double min, double max) {
    g.setColor(color);
    int x = toInt(50+(median*50)+6);
    int y = toInt(0.9*size-(median*50)-8);

    // min = 0;
    // max = size;

    if (direction.equals("vertical")) {
      min = toInt(50+(min*50)+6);
      max = toInt(50+(max*50)+6);
      g.drawLine(x, (int)min, x, (int)max);
    } else if (direction.equals("horizontal")) {
      min = toInt(0.9*size-(min*50)-8);
      max = toInt(0.9*size-(max*50)-8);
      g.drawLine((int)min, y, (int)max, y);
    }
  }

  public static void drawTreeLines(Graphics g, Node node) {
    //if (node.getType() != 'l') System.out.println("DRAWING: " + node.getType() + " -> " + node.getLocation());

    double min, max;

    if (node.getType() == 'v') {
      min = node.getRegion().x_min;
      max = node.getRegion().x_max;
      drawLine("vertical", g, node.getLocation(), vColor, min, max);
    } else if (node.getType() == 'h') {
      min = node.getRegion().y_min;
      max = node.getRegion().y_max;
      drawLine("horizontal", g, node.getLocation(), hColor, min, max);
    }

    if (!node.isLeaf()) {
      if (node.getLeft() != null) drawTreeLines(g, node.getLeft());
      if (node.getRight() != null) drawTreeLines(g, node.getRight());
    }
  }

  public static void drawRegionPoints(Graphics g, Region region, Color color) {
    drawPoint(g, new Point(region.x_min, region.y_min), color);
    drawPoint(g, new Point(region.x_min, region.y_max), color);
    drawPoint(g, new Point(region.x_max, region.y_min), color);
    drawPoint(g, new Point(region.x_max, region.y_max), color);
  }

  public static void drawInputRegion(Graphics g) {
    int rect_x = toInt(50+(App.inputRegion.x_min*50)+6);
    int rect_y = toInt(0.9*size-(App.inputRegion.y_max*50)-7);
    int rect_x_max = toInt(50+(App.inputRegion.x_max*50)+6);
    int rect_y_max = toInt(0.9*size-(App.inputRegion.y_min*50)-7);
    int rect_width = Math.abs(rect_x_max - rect_x);
    int rect_height = Math.abs(rect_y_max - rect_y);

    //System.out.println(rect_x + ", " + rect_y + ", " + rect_x_max + ", " + rect_y_max + ", " + rect_width + ", " + rect_height);

    g.setColor(new Color(245, 222, 179));
    g.fillRect(rect_x, rect_y, rect_width, rect_height);
    g.setColor(Color.BLACK);

    drawRegionPoints(g, App.inputRegion, new Color(139, 69, 19));
  }

  public static void drawTreeRegions(Graphics g, Node node, int colNum) {
    if (!node.isLeaf()) {
      int rect_x = toInt(50+(node.getRegion().x_min*50)+6);
      int rect_y = toInt(0.9*size-(node.getRegion().y_max*50)-7);
      int rect_x_max = toInt(50+(node.getRegion().x_max*50)+6);
      int rect_y_max = toInt(0.9*size-(node.getRegion().y_min*50)-7);
      int rect_width = Math.abs(rect_x_max - rect_x);
      int rect_height = Math.abs(rect_y_max - rect_y);

      g.setColor(new Color(200, 250, (colNum*2)%255));
      g.fillRect(rect_x, rect_y, rect_width, rect_height);
      g.setColor(Color.BLACK);

      drawRegionPoints(g, node.getRegion(), new Color(153, 153, 0));

      if (node.getLeft() != null) drawTreeRegions(g, node.getLeft(), colNum+10);
      if (node.getRight() != null) drawTreeRegions(g, node.getRight(), colNum+10);
    }
  }

  public void paintComponent(Graphics g) {
    g.setFont(g.getFont().deriveFont(10.0f));
    //drawTreeRegions(g, App.tree, 50);
    if (App.inputRegion.isNotNull()) drawInputRegion(g);
    drawTreeLines(g, App.tree);
    drawAllPoints(g, App.points, Color.BLACK);
  }

  public static void display() {
    JFrame frame = new JFrame();
    frame.setTitle("kD-drzewa");
    frame.setSize(size, size);
    frame.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
          System.exit(0);
       }
    });
    Container contentPane = frame.getContentPane();
    contentPane.add(new Window());
    frame.show();
  }
}
