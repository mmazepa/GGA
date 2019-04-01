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

  private static Color vColor = new Color(0, 200, 0);
  private static Color hColor = new Color(0, 0, 255);

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  public static Point preparePoint(Point p) {
    p.setX(toInt(50+(p.getX()*50)+6));
    p.setY(toInt(0.9*size-(p.getY()*50)-8));
    return p;
  }

  public static void drawBox(Graphics g, Region r, Color color1, Color color2) {
    Point p1 = preparePoint(new Point(r.x_min, r.y_min));
    Point p2 = preparePoint(new Point(r.x_max, r.y_min));
    Point p3 = preparePoint(new Point(r.x_max, r.y_max));
    Point p4 = preparePoint(new Point(r.x_min, r.y_max));

    g.setColor(color2);
    g.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
    g.setColor(color1);
    g.drawLine((int)p2.getX(), (int)p2.getY(), (int)p3.getX(), (int)p3.getY());
    g.setColor(color2);
    g.drawLine((int)p3.getX(), (int)p3.getY(), (int)p4.getX(), (int)p4.getY());
    g.setColor(color1);
    g.drawLine((int)p4.getX(), (int)p4.getY(), (int)p1.getX(), (int)p1.getY());
    g.setColor(Color.BLACK);
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

  public static void drawRegion(Graphics g, Region region) {
    drawBox(g, region, vColor, hColor);
  }

  public static void drawTreeLines(Graphics g, Node node) {
    if (node.getType() == 'v') drawRegion(g, node.getRegion());
    else if (node.getType() == 'h') drawRegion(g, node.getRegion());

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

    g.setColor(new Color(245, 222, 179));
    g.fillRect(rect_x, rect_y, rect_width, rect_height);
    g.setColor(Color.BLACK);

    drawRegionPoints(g, App.inputRegion, new Color(139, 69, 19));
  }

  public void paintComponent(Graphics g) {
    g.setFont(g.getFont().deriveFont(10.0f));
    if (App.inputRegion.isNotNull()) drawInputRegion(g);
    drawTreeLines(g, App.tree);
    drawBox(g, new Region(0.0, 10.0, 0.0, 10.0), Color.BLACK, Color.BLACK);
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
