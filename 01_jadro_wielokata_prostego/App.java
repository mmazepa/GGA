import java.util.ArrayList;

public class App {
    public static ArrayList<Point> polygon = new ArrayList<Point>();
    public static ArrayList<Point> corePolygon = new ArrayList<Point>();
    public static ArrayList<Point> newPoints = new ArrayList<Point>();
    public static String inputFileName = new String();

    public static Point min = new Point(0, 0);
    public static Point max = new Point(0, 0);

    public static ArrayList<Point> edgesBetween(Point p1, Point p2, Point p3) {
      ArrayList<Point> edges = new ArrayList<Point>();
      edges.add(p1);
      edges.add(p2);
      edges.add(p3);
      return edges;
    }

    public static String printOne(int num, Point p) {
      return "P" + num + p.toString();
    }

    public static String printThree(int num, int max, ArrayList<Point> edges) {
      int[] nums = new int[] { num + 1, num + 2, num + 3 };
      if (num == max - 2) {
        nums[2] = 1;
      } else if (num == max - 1) {
        nums[1] = 1;
        nums[2] = 2;
      }
      String result = printOne(nums[0], edges.get(0)) + ", " +
                      printOne(nums[1], edges.get(1)) + ", " +
                      printOne(nums[2], edges.get(2));
      return result;
    }

    public static ArrayList<Point> getEdges(int num, ArrayList<Point> polygon) {
      ArrayList<Point> edges = new ArrayList<Point>();
      if (num < polygon.size() - 2) {
        edges = edgesBetween(polygon.get(num), polygon.get(num+1), polygon.get(num+2));
      } else if (num == polygon.size() - 2) {
        edges = edgesBetween(polygon.get(num), polygon.get(num+1), polygon.get(0));
      } else if (num == polygon.size() - 1) {
        edges = edgesBetween(polygon.get(num), polygon.get(0), polygon.get(1));
      }
      return edges;
    }

    public static int checkOrientation(ArrayList<Point> edges) {
      Point p1 = edges.get(0);
      Point p2 = edges.get(1);
      Point p3 = edges.get(2);

      double val = (p2.getY() - p1.getY()) * (p3.getX() - p2.getX()) - (p2.getX() - p1.getX()) * (p3.getY() - p2.getY());
      if (val == 0) return 0;
      return (val > 0)? 1: 2;
    }

    public static void searchForMinMax() {
      for (int i = 0; i < polygon.size(); i++) {
        ArrayList<Point> edges = new ArrayList<Point>();
        edges = getEdges(i,  polygon);
        System.out.print((i+1) + ". " + printThree(i, polygon.size(), edges));

        int o = checkOrientation(edges);
        if (o == 0) {
          System.out.print(" -> WSPÓŁLINIOWE\n");
        } else if (o == 1) {
          System.out.print(" -> W PRAWO\n");
          if (edges.get(1).getY() >= max.getY() && isMinMax(edges).equals("max"))
            max = edges.get(1);
          if (edges.get(1).getY() <= min.getY() && isMinMax(edges).equals("min"))
            min = edges.get(1);
        } else if (o == 2) {
          System.out.print(" -> W LEWO\n");
        }
      }
    }

    public static void initializeMinMax() {
      min = polygon.get(0);
      max = polygon.get(0);
      for (int i = 0; i < polygon.size(); i++) {
        if (polygon.get(i).getY() > min.getY()) min = polygon.get(i);
        if (polygon.get(i).getY() < max.getY()) max = polygon.get(i);
      }
    }

    public static String isMinMax(ArrayList<Point> edges) {
      String result = new String();
      if (edges.get(0).getY() >= edges.get(1).getY() && edges.get(2).getY() >= edges.get(1).getY()) {
        result = "min";
      } else if (edges.get(0).getY() <= edges.get(1).getY() && edges.get(2).getY() <= edges.get(1).getY()) {
        result = "max";
      }
      return result;
    }

    public static void displayMinMax() {
      System.out.print("\n");
      System.out.println("min: " + min.getY() + ", max: " + max.getY());
      System.out.print("\n");
    }

    public static ArrayList<Point> calculateNewPoint(int index, String minOrMax, Point p1, Point p2) {
      Point newPoint = new Point(-1, -1);
      ArrayList<Point> newPoints = new ArrayList<Point>();
      double newX;
      Point minMax = new Point();

      if (minOrMax.equals("min")) minMax = min;
      else if (minOrMax.equals("max")) minMax = max;

      if ((p1.getY() < minMax.getY()) && (p2.getY() > minMax.getY())) {
        newX = (((minMax.getY() - p1.getY()) * (p2.getX() - p1.getX())) / (p2.getY() - p1.getY())) + p1.getX();
        newPoint = new Point(newX, minMax.getY());
      } else if ((p1.getY() > minMax.getY()) && (p2.getY() < minMax.getY())) {
        newX = (((minMax.getY() - p1.getY()) * (p2.getX() - p1.getX())) / (p2.getY() - p1.getY())) + p1.getX();
        newPoint = new Point(newX, minMax.getY());
      }

      // System.out.print("[" + minOrMax + "] " + p1.toString() + ", " + p2.toString());
      if (newPoint.getX() != -1 && newPoint.getY() != -1) {
        // System.out.print(" -----> [" + newPoint.toString() + "]");
        newPoints.add(newPoint);
        corePolygon.add(index+1, newPoint);
      }
      // System.out.print("\n");

      return newPoints;
    }

    public static void displayPolygon(ArrayList<Point> polygonToDisplay) {
      for (int i = 0; i < polygonToDisplay.size(); i++) {
        System.out.println("   " + (i+1) + ". " + polygonToDisplay.get(i).toString());
      }
    }

    public static void prepareCorePolygon() {
      // System.out.println(polygon.size() + " + " + newPoints.size() + " = " + corePolygon.size());

      // System.out.println("WIELOKĄT Z JĄDREM:");
      // displayPolygon(corePolygon);

      for (int i = corePolygon.size()-1; i >= 0; i--) {
        Point tmpPoint = corePolygon.get(i);
        if (tmpPoint.getY() > min.getY() || tmpPoint.getY() < max.getY()){
          corePolygon.remove(i);
        }
      }

      // System.out.println("SAMO JĄDRO:");
      // displayPolygon(corePolygon);
    }

    public static double calculateLength(Point p1, Point p2) {
      return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public static double calculatePerimeter(ArrayList<Point> points) {
      double perimeter = 0;
      for (int i = 0; i < points.size(); i++) {
        if (i == points.size()-1) {
          perimeter += calculateLength(points.get(i), points.get(0));
          break;
        } else {
          perimeter += calculateLength(points.get(i), points.get(i+1));
        }
      }
      return (double)Math.round(perimeter*1000)/1000;
    }

    public static void checkPolygon() {
      if (polygon.size() < 3 && polygon.size() > 0) {
        System.out.println("JĄDRO: NIE!");
        System.exit(0);
      } else if (polygon.size() == 0) {
        System.out.println("Nie podano punktów.");
        System.exit(0);
      }
    }

    public static void checkForCore() {
      if (max.getY() <= min.getY()) {
        System.out.println("JĄDRO: TAK!");

        int counter = 0;
        corePolygon.addAll(polygon);

        for (int i = 0; i < polygon.size(); i++) {
          ArrayList<Point> retrievedPoints = new ArrayList<Point>();

          if (i < polygon.size()-1) {
            if (polygon.get(i).getY() > max.getY() && polygon.get(i+1).getY() < max.getY()) {
              retrievedPoints.addAll(calculateNewPoint(i + counter, "max", polygon.get(i), polygon.get(i+1)));
            } else {
              retrievedPoints.addAll(calculateNewPoint(i + counter-1, "max", polygon.get(i), polygon.get(i+1)));
            }
            counter += retrievedPoints.size();

            if (polygon.get(i).getY() > min.getY() && polygon.get(i+1).getY() < min.getY()) {
              retrievedPoints.addAll(calculateNewPoint(i + counter-1, "min", polygon.get(i), polygon.get(i+1)));
            } else {
              retrievedPoints.addAll(calculateNewPoint(i + counter, "min", polygon.get(i), polygon.get(i+1)));
            }
            counter += retrievedPoints.size();
          } else {
            retrievedPoints.addAll(calculateNewPoint(i + counter-1, "max", polygon.get(polygon.size()-1), polygon.get(0)));
            retrievedPoints.addAll(calculateNewPoint(i + counter-1, "min", polygon.get(polygon.size()-1), polygon.get(0)));
          }

          if (retrievedPoints.size() > 0) {
            newPoints.add(retrievedPoints.get(0));
            if (retrievedPoints.size() == 2) {
              newPoints.add(retrievedPoints.get(1));
            }
          }
        }
      } else {
        System.out.println("JĄDRO: NIE!");
      }
    }

    public static void displayPerimeter() {
      if (min.getY() >= max.getY()) {
        System.out.println("OBWÓD: " + calculatePerimeter(corePolygon));
      } else {
        System.out.println("OBWÓD: 0.0");
      }
    }

    public static void main(String args[]) {
      if (args.length > 0) {
        inputFileName = args[0];
      } else {
        System.out.println("Nie wybrano pliku.");
        System.exit(0);
      }

      FileManager fm = new FileManager();
      polygon = fm.preparePolygon(inputFileName);

      checkPolygon();

      initializeMinMax();
      displayMinMax();
      searchForMinMax();
      displayMinMax();

      checkForCore();
      prepareCorePolygon();
      displayPerimeter();

      Window.display();
    }
}
