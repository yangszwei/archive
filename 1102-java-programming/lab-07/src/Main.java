import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入等第：");
        String grade = scanner.nextLine();
        grade = grade.toUpperCase();

        String range = switch (grade) {
            case "A+" -> "90 ~ 100";
            case "A" -> "85 ~ 89";
            case "A-" -> "80 ~ 84";
            case "B+" -> "77 ~ 79";
            case "B" -> "73 ~ 76";
            case "B-" -> "70 ~ 72";
            case "C+" -> "67 ~ 69";
            case "C" -> "63 ~ 66";
            case "C-" -> "60 ~ 62";
            case "D" -> "50 ~ 59";
            case "E" -> "40 ~ 49";
            case "F" -> "0 ~ 39";
            default -> "未知的等第";
        };

        System.out.println("等第 " + grade + " 的成績範圍為：" + range);
    }
}
