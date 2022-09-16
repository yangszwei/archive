import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("輸入你的姓名：");
        String name = scanner.nextLine();

        int chinese = inputScore(scanner, "國文"),
                english = inputScore(scanner, "英文"),
                math = inputScore(scanner, "數學");

        int total = chinese + english + math;

        System.out.printf("”%s”同學 你的總分為%d 平均為%d\n", name, total, total / 3);
    }

    private static int inputScore(java.util.Scanner scanner, String subject) {
        System.out.printf("請輸入%s成績：", subject);
        return scanner.nextInt();
    }
}
