import java.util.Scanner;

class Account {
    static final int OK = 0;
    static final int FAILED = -1;
    static final int QUOTA_EXCEEDED = -2;

    public String id;
    public String name;
    private String password;
    private int balance = 50000;
    private int quotaLeft = 30000;

    public boolean authorize(String password) {
        return password.equals(this.password);
    }

    public int checkBalance() {
        return this.balance;
    }

    /** 2.2.2 */
    public int deposit(String amount, int balance) {
        try {
            int a = Integer.parseInt(amount);
            this.balance = a + balance;
            return OK;
        } catch (NumberFormatException ignored) {
            return FAILED;
        }
    }

    public int withdraw(String amount, int balance) {
        try {
            int a = Integer.parseInt(amount);
            if (a > balance) {
                return FAILED;
            }
            if (a > quotaLeft) {
                return QUOTA_EXCEEDED;
            }
            this.balance = balance - a;
            this.quotaLeft -= a;
            return this.balance;
        } catch (NumberFormatException ignored) {
            return FAILED;
        }
    }

    public Account(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }
}

class ATM {
    private Scanner scanner;
    private Account account;

    private boolean logIn() {
        System.out.print("請輸入密碼：");
        String password = scanner.nextLine();
        if (!account.authorize(password)) {
            System.out.println("密碼錯誤");
            return false;
        }
        return true;
    }

    public int main() {
        System.out.println("[1.查詢餘額]、[2.存款]、[3.提款]、[4.離開]");
        switch (scanner.nextLine()) {
            case "1" -> {
                checkBalance();
            }
            case "2" -> {
                checkBalance();
                deposit();
            }
            case "3" -> {
                checkBalance();
                withdraw();
            }
            case "4" -> {
                if (exit()) {
                    return -1;
                }
            }
            default -> {
                System.out.println("輸入錯誤");
            }
        }
        return 0;
    }

    public void checkBalance() {
        System.out.printf("帳戶餘額為：%d%n", account.checkBalance());
    }

    public void deposit() {
        while (true) {
            System.out.print("請輸入金額：");
            String amount = scanner.nextLine();
            if (account.deposit(amount, account.checkBalance()) == Account.FAILED) {
                if (!confirm("輸入錯誤，是否要重新輸入 (Y/n)?", true)) {
                    return;
                }
                continue;
            }
            break;
        }
        System.out.printf("存款成功，帳戶餘額為：%d%n", account.checkBalance());
    }

    public void withdraw() {
        int result;
        while (true) {
            System.out.print("請輸入金額：");
            String amount = scanner.nextLine();
            result = account.withdraw(amount, account.checkBalance());
            if (result == Account.FAILED) {
                if (!confirm("輸入錯誤，是否要重新輸入 (Y/n)?", true)) {
                    return;
                }
                continue;
            } else if (result == Account.QUOTA_EXCEEDED) {
                System.out.println("提款金額已達每日上限！");
                continue;
            }
            break;
        }
        System.out.printf("提款成功，帳戶餘額為：%d%n", result);
    }

    public boolean exit() {
        return confirm("是否要離開程式 (y/N)?", false);
    }

    public boolean confirm(String message, Boolean defaultValue) {
        System.out.print(message);
        String result = scanner.nextLine();
        if (result.equalsIgnoreCase("Y")) return true;
        if (result.equalsIgnoreCase("N")) return false;
        return defaultValue;
    }

    public void start() {
        if (logIn()) {
            System.out.printf("歡迎光臨 %s%n", account.name);
            while (true) {
                if (main() == -1) {
                    break;
                }
            }
        }
    }

    public ATM(Scanner scanner, Account account) {
        this.scanner = scanner;
        this.account = account;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = new Account("102214209", "楊斯惟", "209");

        ATM atm = new ATM(scanner, account);
        atm.start();

        scanner.close();
    }
}
