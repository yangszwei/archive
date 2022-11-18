import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println(sum(promptScore("國文"), promptScore("英文"), promptScore("數學")));
    }

    private static int promptScore(String subject) {
        System.out.printf("請輸入%s成績：", subject);
        return scanner.nextInt();
    }

    private static int sum(int ...scores) {
        int total = 0;
        for (int score : scores) total += score;
        return total;
    }
}
