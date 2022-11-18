import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入一個數字：");
        int num = scanner.nextInt();

        if (num >= 0 && num <= 100) {
            if (num >= 90) System.out.println("A+");
            else if (num >= 85) System.out.println("A");
            else if (num >= 80) System.out.println("A-");
            else if (num >= 77) System.out.println("B+");
            else if (num >= 73) System.out.println("B");
            else if (num >= 70) System.out.println("B-");
            else if (num >= 67) System.out.println("C+");
            else if (num >= 63) System.out.println("C");
            else if (num >= 60) System.out.println("C-");
            else if (num >= 50) System.out.println("D");
            else if (num >= 40) System.out.println("E");
            else System.out.println("F");
        } else {
            System.out.println("輸入錯誤");
        }
    }
}
