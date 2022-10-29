import java.util.Scanner;

public class HW01_1 {
    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入數列起始值：");
        int from = scanner.nextInt();
        System.out.print("請輸入數列終點值：");
        int to = scanner.nextInt();
        System.out.print("請輸入數列差異值：");
        int diff = scanner.nextInt();

        int sum = 0;
        for (int i = from; i <= to; i += diff) {
            sum += i;
        }

        System.out.printf("%s 到 %s 差值是 %s 的數列總和是：%s%n", from, to, diff, sum);
    }
}
