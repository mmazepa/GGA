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

  public static void drawLine(String direction, Graphics g, double median, Color color, int min, int max) {
    g.setColor(color);
    int x = toInt(50+(median*50)+6);
    int y = toInt(0.9*size-(median*50)-8);

    if (direction.equals("vertical"))
      g.drawLine(x, min, x, max);
    else if (direction.equals("horizontal"))
      g.drawLine(min, y, max, y);
  }

  public static void drawTreeLines(Graphics g, Node node) {
    if (node.getType() != 'l') System.out.println("DRAWING: " + node.getType() + " -> " + node.getLocation());

    int min = 0;
    int max = size;

    if (node.getType() == 'v') {
      drawLine("vertical", g, node.getLocation(), vColor, min, max);
    } else if (node.getType() == 'h') {
      drawLine("horizontal", g, node.getLocation(), hColor, min, max);
    }

    if (!node.isLeaf()) {
      if (node.getLeft() != null) drawTreeLines(g, node.getLeft());
      if (node.getRight() != null) drawTreeLines(g, node.getRight());
    }
  }

  public void paintComponent(Graphics g) {
    g.setFont(g.getFont().deriveFont(10.0f));
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
