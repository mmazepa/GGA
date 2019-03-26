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

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  // public static void drawAreaRectangle(Graphics g, Color color) {
  //   g.setColor(cm.LIGHT_GRAY);
  //   g.fillRect(toInt(50+((App.middlePoint.getX()-App.lowest_s1_s2)*50)+6), 0, toInt(((2*App.lowest_s1_s2)*50)+1), size);
  //
  //   drawDashedVerticalLine(g, App.middlePoint.getX(), cm.BLACK);
  //   drawDashedVerticalLine(g, App.middlePoint.getX() - App.lowest_s1_s2, color);
  //   drawDashedVerticalLine(g, App.middlePoint.getX() + App.lowest_s1_s2, color);
  // }

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

  public void paintComponent(Graphics g) {
    g.setFont(g.getFont().deriveFont(10.0f));
    //drawAreaRectangle(g, cm.GRAY);

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
