public class VisualManager {
  public static String frameIt(String sign, int howMany) {
    String frame = sign;
    while (frame.length() < howMany) frame += sign;
    return frame;
  }

  public static void displayFramed(String text) {
    System.out.println("╔" + frameIt("═", text.length()+2) + "╗");
    System.out.println("║ " + text + " ║ ");
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

  public static String fromLeftAndRight(String text, int num) {
    String left = "";
    String right = "";
    while (left.length() < num && right.length() < num) {
      left += "─";
      right += "─";
    }
    return left + " " + text + " " + right;
  }

  public static void log(String label, String text) {
    String logString = "["+ label + "]: " + text;
    displayFramed(logString);
  }
}
