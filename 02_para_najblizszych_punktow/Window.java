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

  public static void drawDashedMiddleLine(Graphics g) {
    Graphics2D g2d = (Graphics2D) g.create();
    Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
    g2d.setStroke(dashed);
    g2d.drawLine(toInt(50+(App.middlePoint.getX()*50)+6), 0, toInt(50+(App.middlePoint.getX()*50)+6), size);
    g2d.dispose();
  }

  public void paintComponent(Graphics g) {
    PointManager pm = new PointManager();
    g.setFont(g.getFont().deriveFont(10.0f));

    drawDashedMiddleLine(g);

    for (int i = 0; i < App.points.size(); i++) {
      drawPoint(g, App.points.get(i), Color.BLACK);
    }
  }

  public static void drawPoint(Graphics g, Point p, Color color) {
    g.setColor(color);
    g.setFont(g.getFont().deriveFont(20.0f));
    g.drawString(pointSign, toInt(50+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)));
    g.setFont(g.getFont().deriveFont(10.0f));
    g.drawString(p.toString(), toInt(25+(p.getX()*50)+10), toInt(0.9*size-(p.getY()*50)-15));
    g.setColor(Color.BLACK);
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
