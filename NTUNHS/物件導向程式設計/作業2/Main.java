/**
 * Tested with "openjdk 18.0.2.1 2022-08-18".
 */

import java.util.Scanner;

class Grader {
    public static final String INVALID = "invalid";

    public static String grade(String score) {
        try {
            return grade(Integer.parseInt(score));
        } catch (NumberFormatException ignored) {
            return INVALID;
        }
    }

    public static String grade(int score) {
        if (score < 0 || score > 100) return INVALID;
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    public static String getRange(String grade) {
        return switch (grade.toUpperCase()) {
            case "A" -> "90-100";
            case "B" -> "80-89";
            case "C" -> "70-79";
            case "D" -> "60-69";
            case "F" -> "0-59";
            default -> INVALID;
        };
    }

    public static String getPerformance(String grade) {
        return switch (grade.toUpperCase()) {
            case "A" -> "極佳";
            case "B" -> "佳";
            case "C" -> "平均";
            case "D" -> "差";
            case "F" -> "不及格";
            default -> INVALID;
        };
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("請輸入功能選項 ([1] 查詢成績等第 [2] 查詢成績區間 [3] 離開)：");
            switch (scanner.nextLine()) {
                case "1" -> {
                    System.out.print("請輸入成績：");
                    String score = scanner.nextLine();
                    String grade = Grader.grade(score);
                    if (grade.equals(Grader.INVALID)) {
                        System.out.println("輸入錯誤");
                        continue;
                    }
                    System.out.printf("等第：%s 表現：%s%n", grade, Grader.getPerformance(grade));
                }
                case "2" -> {
                    System.out.print("請輸入成績等第：");
                    String grade = scanner.nextLine();
                    String range = Grader.getRange(grade);
                    if (range.equals(Grader.INVALID)) {
                        System.out.println("輸入錯誤");
                        continue;
                    }
                    System.out.println("成績區間：" + range);
                }
                case "3" -> {
                    System.out.println("Bye.");
                    return;
                }
                default -> System.out.println("輸入錯誤");
            }
        }
    }
}
