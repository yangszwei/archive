import java.util.Scanner;

public class HW01_3 {
    public static void main(String[] arg) {
        final int pwd = 70;             // 密碼數字
        int num;                        // 儲存所猜的數字
        Scanner scanner = new Scanner(System.in);

        for (int i = 1; ; i++) {        // 這是無限迴圈
            System.out.print("請猜0-99的數字： ");
            num = scanner.nextInt();    // 讀取輸入數字
            if (num == pwd) {
                System.out.println("恭喜猜對了!!");
                System.out.printf("總共猜了 %s 次%n", i);
                break;
            }
            System.out.println(num < pwd ? "請猜大一點" : "請猜小一點");
            System.out.print("是否繼續 (輸入Q/q以放棄)？");
            scanner.nextLine();         // 清除換行符號
            if (scanner.nextLine().equalsIgnoreCase("Q")) {
                System.out.println("bye!");
                break;
            }
        }
    }
}
