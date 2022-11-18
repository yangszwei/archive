import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Account {
    public static final int OK = 0;
    public static final int BAD_INPUT = -1;
    public static final int QUOTA_EXCEEDED = -2;
    public static final int INCORRECT_PASSWORD = -3;
    public static final int IS_OLD_PASSWORD = -4;

    public String id;
    public String name;
    private String password;
    private int balance = 50000;
    private int quotaLeft = 30000;

    public Account(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    /**
     * This method gets the password of the account.
     *
     * @return the password of the account
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to authenticate the user.
     *
     * @return OK if the password is correct, INCORRECT_PASSWORD if the password is
     * incorrect.
     */
    public int checkPassword(String password) {
        return this.password.equals(password) ? OK : INCORRECT_PASSWORD;
    }

    /**
     * This method changes the password of the account.
     *
     * @return OK if the password is changed successfully, IS_OLD_PASSWORD if the
     * new password is the same as the old password.
     */
    public int changePassword(String password) {
        if (this.password.equals(password)) {
            return IS_OLD_PASSWORD;
        }
        this.password = password;
        return OK;
    }

    /**
     * This method is used to check the balance of the account.
     *
     * @return the balance of the account.
     */
    public int checkBalance() {
        return this.balance;
    }

    /**
     * This method is used to deposit money into the account.
     *
     * @return OK if the deposit is successful, or BAD_INPUT if the amount is
     * not a valid amount.
     */
    public int deposit(String amount, int balance) {
        int a = parseAmount(amount);
        if (a < 0) return BAD_INPUT;
        this.balance = balance + a;
        return OK;
    }

    /**
     * This method is used to withdraw the amount from the account.
     *
     * @return the remaining balance, or a negative number if there is an error.
     */
    public int withdraw(String amount, int balance) {
        int a = parseAmount(amount);
        if (a < 0 || a > this.balance) return BAD_INPUT;
        if (a > this.quotaLeft) return QUOTA_EXCEEDED;
        this.balance = balance - a;
        this.quotaLeft -= a;
        return this.balance;
    }

    /**
     * This method is used to parse the amount string to a positive integer.
     *
     * @return the amount, any negative return value is either an invalid amount
     * or an error.
     */
    private int parseAmount(String amount) {
        try {
            return Integer.parseInt(amount);
        } catch (NumberFormatException ignored) {
            return BAD_INPUT;
        }
    }
}

/**
 * ATM is the user interface to interact with the ATM account, multiple accounts is supported
 * but not implemented.
 */
class ATM {
    private static final int OK = 0;
    private static final int EXIT = -1;
    private static final int MAX_SIGN_IN_ATTEMPTS = 3;

    private final Scanner scanner;
    private Map<String, Account> accounts;
    private Account account;

    public ATM(Scanner scanner) {
        this.scanner = scanner;
        this.accounts = new HashMap<>();
    }

    public void register(Account account) {
        this.accounts.put(account.id, account);
        this.account = accounts.get(account.id);
        System.out.printf("帳號 %s 新增完成，預設密碼為 %s %n", account.id, account.getPassword());
        while (true) {
            System.out.print("請輸入新密碼（不得與預設密碼重複）：");
            String password = scanner.nextLine();
            int result = account.changePassword(password);
            if (result == Account.IS_OLD_PASSWORD) {
                System.out.println("新密碼與預設密碼重複");
                continue;
            }
            System.out.println("密碼變更成功");
            break;
        }
    }

    public void run() {
        if (signIn() == OK) {
            System.out.println("歡迎光臨 " + account.name);
            while (true) {
                if (main() == EXIT) break;
            }
        }
    }

    private int signIn() {
        System.out.print("請輸入帳號：");
        String id = scanner.nextLine();
        for (int i = 0; i < MAX_SIGN_IN_ATTEMPTS; i++) {
            System.out.print("請輸入密碼：");
            String password = scanner.nextLine();
            account = accounts.get(id);
            if (account == null) {
                System.out.println("帳號不存在");
                return EXIT;
            }
            if (account.checkPassword(password) != Account.OK) {
                if (i + 1 < MAX_SIGN_IN_ATTEMPTS) {
                    System.out.printf("密碼錯誤，還剩下%s次！%n", MAX_SIGN_IN_ATTEMPTS - i - 1);
                }
                continue;
            }
            return OK;
        }
        System.out.printf("錯誤超過%s次！%n", MAX_SIGN_IN_ATTEMPTS);
        return EXIT;
    }

    public int changePassword() {
        return OK;
    }

    /**
     * This method prints the main menu and handles the user's choice.
     *
     * @return EXIT to exit the program, or OK to continue the main loop.
     */
    private int main() {
        System.out.print("[主畫面] 請輸入功能選項 ([1] 查詢餘額 [2] 存款 [3] 提款 [4] 離開)：");
        return switch (scanner.nextLine()) {
            case "1" -> checkBalance();
            case "2" -> deposit();
            case "3" -> withdraw();
            case "4" -> exit();
            default -> {
                System.out.println("輸入錯誤，請重新輸入！");
                yield OK;
            }
        };
    }

    private int checkBalance() {
        System.out.printf("您的帳戶餘額為 %d 元。%n", account.checkBalance());
        return OK;
    }

    private int deposit() {
        checkBalance();
        System.out.print("請輸入存款金額：");
        int result = account.deposit(scanner.nextLine(), account.checkBalance());
        switch (result) {
            case Account.OK -> System.out.printf("存款成功！您的帳戶餘額為 %d 元。%n", account.checkBalance());
            case Account.BAD_INPUT -> System.out.println("輸入金額錯誤！");
        }
        return 0;
    }

    private int withdraw() {
        checkBalance();
        System.out.print("請輸入提款金額：");
        int result = account.withdraw(scanner.nextLine(), account.checkBalance());
        switch (result) {
            case Account.BAD_INPUT -> System.out.println("輸入金額錯誤！");
            case Account.QUOTA_EXCEEDED -> System.out.println("超過每日提款額度！");
            default -> System.out.printf("提款成功！您的帳戶餘額為 %d 元。%n", result);
        }
        return OK;
    }

    private int exit() {
        return confirm("確定要離開嗎？(Y/n)：", true) ? EXIT : OK;
    }

    private boolean confirm(String message, boolean defaultValue) {
        System.out.print(message);
        String input = scanner.nextLine();
        if (input.isEmpty()) return defaultValue;
        return input.equalsIgnoreCase("Y");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ATM atm = new ATM(scanner);
        atm.register(new Account("102214209", "楊斯惟", "209"));
        atm.run();

        scanner.close();
    }
}
