import java.util.ArrayList;
import java.util.Stack;

public class VisualManager {
  public static String frameIt(String sign, int howMany) {
    String frame = sign;
    while (frame.length() < howMany) frame += sign;
    return frame;
  }

  public static void displayFramed(String text) {
    System.out.println("╔" + frameIt("═", text.length()+2) + "╗");
    System.out.println("║ " + text + " ║");
    System.out.println("╚" + frameIt("═", text.length()+2) + "╝");
  }

  public static void title(String text, int length) {
    System.out.println("╭" + frameIt("─", text.length()+2) + "╮");
    System.out.println("│ " + text + " │");
    String lastLine = "╰" + frameIt("─", text.length()+2) + "┴";
    while (lastLine.length() < length) lastLine += "─";
    System.out.println(lastLine);
  }

  public static void printStack(Stack stack, String title) {
    ArrayList<String> stackLines = new ArrayList<String>();

    String stackName = "║ " + title + " ║ ";
    String stackString = stackName;

    for (int i = 0; i < stack.size(); i++) {
      stackString += stack.elementAt(i) + " ";
      if ((i != 0 && i%4 == 0) || i == stack.size()-1) {
        stackLines.add(stackString);
        stackString = "║";
        while (stackString.length() < title.length()+3) stackString += " ";
        stackString += "║ ";
      }
    }

    int longest = 0;
    for (String line : stackLines)
      if (line.length() >= longest) longest = line.length();

    String firstLine = "╔" + frameIt("═", longest-1) + "╗";
    String lastLine = "╚" + frameIt("═", longest-1) + "╝";

    firstLine = firstLine.substring(0,stackName.length()-2)+'╦'+firstLine.substring(stackName.length()-1);
    lastLine = lastLine.substring(0,stackName.length()-2)+'╩'+lastLine.substring(stackName.length()-1);

    System.out.println(firstLine);
    for (String line : stackLines) {
      while (line.length() < longest) line += " ";
      System.out.println(line + "║");
    }
    System.out.println(lastLine);
  }

  public static void horizontalLine(int amount) {
    String line = "─";
    while (line.length() < amount) line += "─";
    System.out.println(line);
  }

  public static void horizontalLine(int amount, String sign) {
    String line = sign;
    while (line.length() < amount) line += sign;
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
