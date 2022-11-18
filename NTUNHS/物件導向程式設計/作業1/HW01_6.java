import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HW01_6 {
    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> scores = new ArrayList<>();

        System.out.println("如果輸入負分數則輸入結束");
        int score;
        while (true) {
            System.out.print("請輸入分數：");
            score = scanner.nextInt();
            if (score < 0) break;
            scores.add(score);
        }

        final double average = (double) scores.stream().mapToInt(i -> i).sum() / scores.size();
        int higher = 0, lower = 0;
        for (int s : scores) {
            if (s > average) higher++;
            if (s < average) lower++;
        }

        System.out.println("分數筆數 = " + scores.size());
        System.out.printf("平均分數 = %.2f%n", average);
        System.out.println("高於平均分數人數 = " + higher);
        System.out.println("低於平均分數人數 = " + lower);
    }
}
