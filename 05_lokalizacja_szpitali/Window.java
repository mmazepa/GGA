import java.util.ArrayList;

import java.awt.Color;
import java.awt.Font;
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
  private static String title = "Problem lokalizacji szpitali";
  private static String author = "Mariusz Mazepa © 2019";
  Color mainColor = new Color(100, 50, 100);

  public static int toInt(double num) {
    int integer = (int) Math.round(num);
    return integer;
  }

  public static int prepareX(double x) {
    return toInt(50+(x*50));
  }

  public static int prepareY(double y) {
    return toInt(0.925*size-(y*50)-5);
  }

  public static Point preparePoint(Point p) {
    Point newPoint = new Point();
    newPoint.setX(prepareX(p.getX()));
    newPoint.setY(prepareY(p.getY()));
    return newPoint;
  }

  public static void drawCustomString(Graphics g, String text, Point point, int x_shift, int y_shift) {
    g.drawString(text, toInt(point.getX() + x_shift), toInt(point.getY() + y_shift));
  }

  public static void drawBox(Graphics g, Point leftBottom, Point topRight, Color color) {
    Point p1 = preparePoint(new Point(leftBottom.getX(), leftBottom.getY()));
    Point p2 = preparePoint(new Point(topRight.getX(), leftBottom.getY()));
    Point p3 = preparePoint(new Point(topRight.getX(), topRight.getY()));
    Point p4 = preparePoint(new Point(leftBottom.getX(), topRight.getY()));

    g.setColor(color);
    g.drawLine(toInt(p1.getX()+6), toInt(p1.getY()-7), toInt(p2.getX()+6), toInt(p2.getY()-7));
    g.drawLine(toInt(p2.getX()+6), toInt(p2.getY()-7), toInt(p3.getX()+6), toInt(p3.getY()-7));
    g.drawLine(toInt(p3.getX()+6), toInt(p3.getY()-7), toInt(p4.getX()+6), toInt(p4.getY()-7));
    g.drawLine(toInt(p4.getX()+6), toInt(p4.getY()-7), toInt(p1.getX()+6), toInt(p1.getY()-7));
    g.setColor(Color.BLACK);
  }

  public static void fillBox(Graphics g, Point leftBottom, Point topRight, Color color) {
    int width = Math.abs(prepareX(topRight.getX()) - prepareX(leftBottom.getX()));
    int height = Math.abs(prepareY(topRight.getY()) - prepareY(leftBottom.getY()));

    g.setColor(color);
    g.fillRect(prepareX(leftBottom.getX())+6, prepareY(topRight.getY())-7, width, height);
    g.setColor(Color.BLACK);
  }

  public static void drawPoint(Graphics g, Point p, Color color) {
    g.setColor(color);
    Point p_adjusted = preparePoint(p);
    g.setFont(g.getFont().deriveFont(20.0f));
    drawCustomString(g, pointSign, p_adjusted, 0, 0);
    g.setFont(g.getFont().deriveFont(10.0f));
    drawCustomString(g, p.toString(), p_adjusted, -15, -15);
    g.setColor(Color.BLACK);
  }

  public static void drawAllPoints(Graphics g, ArrayList<Point> points, Color color) {
    for (int i = 0; i < points.size(); i++) {
      drawPoint(g, points.get(i), color);
    }
  }

  public Point calculateShadows(Point p, double x_shift, double y_shift) {
    Point shadow = new Point(p.getX(), p.getY());
    shadow.setX(shadow.getX() + x_shift);
    shadow.setY(shadow.getY() + y_shift);
    return shadow;
  }

  public void prepareMainBox(Graphics g) {
    Point min_box = new Point(0.0, 0.0);
    Point max_box = new Point(10.0, 10.0);
    Point min_shadow = calculateShadows(min_box, 0.1, -0.1);
    Point max_shadow = calculateShadows(max_box, 0.1, -0.1);

    fillBox(g, min_shadow, max_shadow, mainColor.darker());
    fillBox(g, min_box, max_box, Color.WHITE);
    drawBox(g, min_box, max_box, Color.BLACK);
  }

  public void drawText(Graphics g, String text, Point p, Color color) {
    g.setColor(color);
    drawCustomString(g, text, p, -240, -20);
    g.setColor(Color.BLACK);
  }

  public void drawTitleWithShadow(Graphics g, Point titlePoint, Point titleShadowPoint) {
    String framedTitle = VisualManager.fromLeftAndRight(title, 9);
    g.setFont(g.getFont().deriveFont(20.0f));

    drawText(g, framedTitle, titleShadowPoint, mainColor.darker());
    drawText(g, framedTitle, titlePoint, Color.WHITE);
  }

  public void paintComponent(Graphics g) {
    Point min_border = new Point(-1.5, -1.5);
    Point max_border = new Point(11.0, 11.0);

    Point titlePoint = preparePoint(new Point(5.0, 10));
    Point titleShadowPoint = calculateShadows(titlePoint, 3, 2);
    Point authorPoint = preparePoint(new Point(10,0));

    g.setFont(g.getFont().deriveFont(10.0f));
    fillBox(g, min_border, max_border, mainColor);
    prepareMainBox(g);

    drawAllPoints(g, App.points, Color.BLACK);

    drawTitleWithShadow(g, titlePoint, titleShadowPoint);
    g.setFont(g.getFont().deriveFont(Font.BOLD, 10.0f));
    drawCustomString(g, author, authorPoint, -135, -15);
  }

  public static void display() {
    JFrame frame = new JFrame();
    frame.setTitle(title);
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
