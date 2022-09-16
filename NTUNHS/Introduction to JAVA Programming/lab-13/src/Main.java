import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] scores = {50, 65, 80, 48, 35, 93, 57, 90, 86, 77};

        Arrays.sort(scores);

        int fail = 0;
        for (int score : scores) {
            fail += score < 60 ? 1 : 0;
        }

        for (int i = scores.length - 1; i >= 0; i--) {
            scores[i] = Math.max(scores[i], 60);
            System.out.print(scores[i] + (i > 0 ? " " : ""));
        }

        System.out.println("\n" + (scores.length - fail) + " 及格，" + fail + " 不及格。");
    }
}