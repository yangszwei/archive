import java.util.Scanner;

class WageCalculator {
    public static final double HOURLY_RATE = 150;

    public static double calculate(int hours) {
        if (hours > 50) return HOURLY_RATE * hours * 1.6;
        if (hours > 40) return HOURLY_RATE * hours * 1.2;
        if (hours < 40) return HOURLY_RATE * hours * .8;
        return HOURLY_RATE * hours;
    }
}

public class HW01_2 {
    public static void main(String[] arg) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入本週工作時數：");
        int hours = scanner.nextInt();

        double wage = WageCalculator.calculate(hours);

        System.out.println("本週薪資：" + wage);
    }
}
