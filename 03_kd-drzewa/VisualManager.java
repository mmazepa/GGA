public class VisualManager {
  public static String frameIt(String sign, int howMany) {
    String frame = sign;
    while (frame.length() < howMany) frame += sign;
    return frame;
  }

  public static void displayFramed(String text) {
    displayFramed(text, "");
  }

  public static void displayFramed(String text, String point) {
    System.out.println("╔" + frameIt("═", text.length()+2) + "╗");
    System.out.println("║ " + text + " ║ " + point);
    System.out.println("╚" + frameIt("═", text.length()+2) + "╝");
  }

  public static void title(String text) {
    System.out.println("╭" + frameIt("─", text.length()+2) + "╮");
    System.out.println("│ " + text + " │");
    String lastLine = "╰" + frameIt("─", text.length()+2) + "┴";
    while (lastLine.length() < 50) lastLine += "─";
    System.out.println(lastLine);
  }

  public static void horizontalLine(int amount) {
    String line = "─";
    while (line.length() < amount) line += "─";
    System.out.println(line);
  }
}
