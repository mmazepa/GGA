import java.util.ArrayList;

public class App {
    public static ArrayList<Point> polygon = new ArrayList<Point>();
    public static String inputFileName = new String();

    public static double min = 0;
    public static double max = 0;

    public static ArrayList<Point> edgesBetween(Point p1, Point p2, Point p3) {
      ArrayList<Point> edges = new ArrayList<Point>();
      edges.add(p1);
      edges.add(p2);
      edges.add(p3);
      return edges;
    }

    public static String printOne(int num, Point p) {
      return "P" + num + "(" + p.getX() + ", " + p.getY() + ")";
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

    public static void setMinMax() {
      min = polygon.get(0).getY();
      max = polygon.get(0).getY();
      for (int i = 0; i < polygon.size(); i++) {
        if (polygon.get(i).getY() > min) min = polygon.get(i).getY();
        if (polygon.get(i).getY() < max) max = polygon.get(i).getY();
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

    public static void main(String args[]) {
      if (args.length > 0) {
        inputFileName = args[0];
      } else {
        System.out.println("No File Selected.");
        System.exit(0);
      }

      FileManager fm = new FileManager();
      polygon = fm.preparePolygon(inputFileName);

      if (polygon.size() < 4 && polygon.size() > 0) {
        System.out.println("CORE: YES!");
        System.exit(0);
      } else if (polygon.size() == 0) {
        System.out.println("No points provided.");
        System.exit(0);
      } else {
        System.out.println("CORE: DON'T KNOW YET...");
      }

      setMinMax();
      System.out.println("min: " + min + ", max: " + max);

      for (int i = 0; i < polygon.size(); i++) {
        ArrayList<Point> edges = new ArrayList<Point>();
        edges = getEdges(i,  polygon);
        System.out.print((i+1) + ". " + printThree(i, polygon.size(), edges));

        int o = checkOrientation(edges);
        if (o == 0) {
          System.out.print(" -> COLLINEAR\n");
        } else if (o == 1) {
          System.out.print(" -> RIGHT\n");
          if (edges.get(1).getY() > max && isMinMax(edges).equals("max")) max = edges.get(1).getY();
          if (edges.get(1).getY() < min && isMinMax(edges).equals("min")) min = edges.get(1).getY();
        } else if (o == 2) {
          System.out.print(" -> LEFT\n");
        }
      }
      System.out.println("min: " + min + ", max: " + max);
      if (max <= min) System.out.println("CORE: YES!");
      else System.out.println("CORE: NO!");
    }
}
