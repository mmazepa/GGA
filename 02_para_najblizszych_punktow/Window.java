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

  public void paintComponent(Graphics g) {
    for (int i = 0; i < App.points.size(); i++) {
      drawPoint(g, App.points.get(i));
    }
  }

  public static void drawPoint(Graphics g, Point p) {
    g.setColor(Color.BLACK);
    g.drawString("o", toInt(50+(p.getX()*50)-3), toInt(0.9*size-(p.getY()*50)+5));
    g.drawString(p.toString(), toInt(25+(p.getX()*50)), toInt(0.9*size-(p.getY()*50)-5));
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
