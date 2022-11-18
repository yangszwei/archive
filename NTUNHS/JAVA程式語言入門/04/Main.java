import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int a = scanner.nextInt();
        int b = scanner.nextInt();

        System.out.printf("GCD(%d, %d) = %d\n", a, b, GCD(a, b));
    }

    public static int GCD(int a, int b) {
        int max = Math.max(a, b), min = Math.min(a, b);
        return max % min == 0 ? min : GCD(min, max % min);
    }
}
