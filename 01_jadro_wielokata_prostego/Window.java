import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel {
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Polygon p = new Polygon();
    for (int i = 0; i < App.polygon.size(); i++) {
      int x = App.polygon.get(i).getX();
      int y = App.polygon.get(i).getY();
      p.addPoint(50+(x*50), 450-(y*50));
      g.setColor(Color.BLACK);
      g.drawString("o", 50+(x*50)-3, 450-(y*50)+5);
      g.setColor(Color.DARK_GRAY);
      g.drawString("(" + x + ", " + y + ")", 35+(x*50), 450-(y*50)-5);
    }
    g.setColor(Color.BLACK);
    g.drawPolygon(p);

    Point min = App.min;
    Point max = App.max;

    g.setColor(Color.RED);
    g.drawString("o", 50+(min.getX()*50)-3, 450-(min.getY()*50)+5);
    g.drawLine(0, 450-(min.getY()*50), 500, 450-(min.getY()*50));
    g.drawString("min="+min.getY(), 20, 450-(min.getY()*50));

    g.setColor(Color.BLUE);
    g.drawString("o", 50+(max.getX()*50)-3, 450-(max.getY()*50)+5);
    g.drawLine(0, 450-(max.getY()*50), 500, 450-(max.getY()*50));
    g.drawString("max="+max.getY(), 20, 450-(max.getY()*50));
  }

  public static void display() {
    JFrame frame = new JFrame();
    frame.setTitle("Jądro wielokąta prostego");
    frame.setSize(500, 500);
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
