import java.util.Scanner;

class Game {
    private final String answer;
    public Boolean finished;

    public Game() {
        answer = randomString();
        finished = false;
    }

    public void guess(String guess) {
        if (guess.length() != 4) return;
        int A = 0, B = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (answer.charAt(i) == guess.charAt(j)) {
                    if (i == j) {
                        A++;
                    } else {
                        B++;
                    }
                }
            }
        }
        System.out.println(A + "A" + B + "B");
        finished = finished || A == 4;
    }

    private static String randomString() {
        String digits = "0123456789";
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 4; i++) {
            int index = (int) (digits.length() * Math.random());
            sb.append(digits.charAt(index));
            digits = new StringBuilder(digits).deleteCharAt(index).toString();
        }
        return sb.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner sc = new Scanner(System.in);

        while (!game.finished) {
            System.out.print("輸入數字：");
            game.guess(sc.nextLine());
        }
    }
}
