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
  private static ColorManager cm = new ColorManager();

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  public static void drawDashedVerticalLine(Graphics g, double x, Color color) {
    Graphics2D g2d = (Graphics2D) g.create();
    Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    g2d.setColor(color);
    g2d.setStroke(dashed);
    g2d.drawLine(toInt(50+(x*50)+6), 0, toInt(50+(x*50)+6), size);
    g2d.dispose();
  }

  public static void drawAreaRectangle(Graphics g, Color color) {
    g.setColor(cm.LIGHT_GRAY);
    g.fillRect(toInt(50+((App.middlePoint.getX()-App.lowest)*50)+6), 0, toInt(((2*App.lowest)*50)+1), size);

    drawDashedVerticalLine(g, App.middlePoint.getX(), cm.BLACK);
    drawDashedVerticalLine(g, App.middlePoint.getX() - App.lowest, color);
    drawDashedVerticalLine(g, App.middlePoint.getX() + App.lowest, color);
  }

  public static void drawAllPoints(Graphics g, ArrayList<Point> points, Color color) {
    for (int i = 0; i < points.size(); i++) {
      drawPoint(g, points.get(i), color);
    }
  }

  public static void connectPoints(Graphics g, Point p1, Point p2, Color color) {
    g.setColor(color);
    g.drawLine(toInt(50+(p1.getX()*50)+5), toInt(0.9*size-(p1.getY()*50)-7), toInt(50+(p2.getX()*50)+5), toInt(0.9*size-(p2.getY()*50))-7);
    g.setColor(cm.BLACK);
  }

  public static void drawAndConnect(Graphics g, Point p1, Point p2, Color color1, Color color2) {
    connectPoints(g, p1, p2, color1);
    drawSpecialPoint(g, p1, color1, color2);
    drawSpecialPoint(g, p2, color1, color2);
  }

  public void paintComponent(Graphics g) {
    g.setFont(g.getFont().deriveFont(10.0f));
    drawAreaRectangle(g, cm.GRAY);

    drawAllPoints(g, App.points, cm.BLACK);
    drawAllPoints(g, App.s3_1, cm.RED);
    drawAllPoints(g, App.s3_2, cm.BLUE);

    drawAndConnect(g, App.lowest_s1_p1, App.lowest_s1_p2, cm.RED, cm.PINK);
    drawAndConnect(g, App.lowest_s2_p1, App.lowest_s2_p2, cm.BLUE, cm.CYAN);
    if (App.lowest_s3_p1 != null && App.lowest_s3_p2 != null) {
      drawAndConnect(g, App.lowest_s3_p1, App.lowest_s3_p2, cm.DARK_GREEN, cm.GREEN);
    }
  }

  public static void drawPoint(Graphics g, Point p, Color color) {
    g.setColor(color);
    g.setFont(g.getFont().deriveFont(20.0f));
    g.drawString(pointSign, toInt(50+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)));
    g.setFont(g.getFont().deriveFont(10.0f));
    g.drawString(p.toString(), toInt(25+(p.getX()*50)+10), toInt(0.9*size-(p.getY()*50)-15));
    g.setColor(cm.BLACK);
  }

  public static void drawSpecialPoint(Graphics g, Point p, Color color1, Color color2) {
    g.setColor(color1);
    g.setFont(g.getFont().deriveFont(30.0f));
    g.drawString(pointSign, toInt(50+(p.getX()*50)-4), toInt(0.9*size-(p.getY()*50))+3);
    g.setColor(color2);
    g.setFont(g.getFont().deriveFont(20.0f));
    g.drawString(pointSign, toInt(50+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)));
    g.setColor(cm.BLACK);
  }

  public static void display() {
    JFrame frame = new JFrame();
    frame.setTitle("Para najbliższych punktów");
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
