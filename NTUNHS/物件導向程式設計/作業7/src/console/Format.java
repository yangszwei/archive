package console;

/**
 * 此類別提供附帶樣式的文字輸出功能。
 */
public class Format {
    /**
     * 以「標題」樣式印出一行文字至 System.out。
     */
    public static void printHeading(Object x) {
        System.out.println(Color.BG_GREEN + Color.BLACK + x + Color.RESET);
    }

    /**
     * 以「狀態」樣式印出一行文字至 System.out。
     */
    public static void printStatus(Object x) {
        System.out.println(Color.BLUE + x + Color.RESET);
    }

    /**
     * 以「結果」樣式印出一行文字至 System.out。
     */
    public static void printResult(Object x) {
        if (x != null && x.toString() != null) {
            System.out.println(Color.CYAN + "| " + x + Color.RESET);
        }
    }

    /**
     * 以「錯誤訊息」樣式印出一行文字至 System.out。
     */
    public static void printError(String text) {
        System.out.println(Color.RED + text + Color.RESET);
    }
}
