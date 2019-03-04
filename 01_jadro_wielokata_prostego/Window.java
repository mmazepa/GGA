import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
  private static int size = 600;

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  public void drawMinMax(Graphics g, Color color, Point point) {
    String minOrMax = new String();
    if (color == Color.RED) { minOrMax = "min"; }
    else if (color == Color.BLUE) { minOrMax = "max"; }
    g.setColor(color);
    g.drawString("o", toInt(50+(point.getX()*50)-3), toInt(0.9*size-(point.getY()*50)+5));
    g.drawLine(0, toInt(0.9*size-(point.getY()*50)), size, toInt(0.9*size-(point.getY()*50)));
    g.drawString(minOrMax+"="+point.getY(), toInt(point.getY()+20), toInt(0.9*size-(point.getY()*50)));
  }

  public void paintComponent(Graphics g) {
    Polygon p = new Polygon();
    Polygon cp = new Polygon();

    for (int i = 0; i < App.corePolygon.size(); i++) {
      double x = App.corePolygon.get(i).getX();
      double y = App.corePolygon.get(i).getY();
      cp.addPoint(toInt(50+(x*50)), toInt(0.9*size-(y*50)));
    }
    g.setColor(Color.LIGHT_GRAY);
    g.drawPolygon(cp);
    g.fillPolygon(cp);

    for (int i = 0; i < App.polygon.size(); i++) {
      double x = App.polygon.get(i).getX();
      double y = App.polygon.get(i).getY();
      p.addPoint(toInt(50+(x*50)), toInt(0.9*size-(y*50)));
      g.setColor(Color.BLACK);
      g.drawString("o", toInt(50+(x*50)-3), toInt(0.9*size-(y*50)+5));
      g.setColor(Color.DARK_GRAY);
      g.drawString(App.polygon.get(i).toString(), toInt(25+(x*50)), toInt(0.9*size-(y*50)-5));
    }
    g.setColor(Color.BLACK);
    g.drawPolygon(p);

    drawMinMax(g, Color.RED, App.min);
    drawMinMax(g, Color.BLUE, App.max);

    for (int i = 0; i < App.newPoints.size(); i++) {
      drawNewPoint(g, App.newPoints.get(i));
    }
  }

  public static void drawNewPoint(Graphics g, Point p) {
    g.setColor(Color.MAGENTA);
    g.drawString("o", toInt(50+(p.getX()*50)-3), toInt(0.9*size-(p.getY()*50)+5));
    g.drawString(p.toString(), toInt(25+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)-5));
  }

  public static void display() {
    JFrame frame = new JFrame();
    frame.setTitle("Jądro wielokąta prostego");
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
