import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] scores = new int[3];
        for (int i = 0; i < 3;i++) {
            System.out.printf("請輸入科目%d成績：", i+1);
            scores[i] = scanner.nextInt();
        }


        System.out.printf("總分為%d分，平均為%d分。", sum(scores), sum(scores) / scores.length);
    }

    private static int sum(int[] array) {
        int sum = 0;
        for (int number : array) sum += number;
        return sum;
    }
}
