import java.util.Scanner;

class Account {
    public String id;
    public String name;
    private String password;
    private int balance;
    private int amount;

    public boolean authenticate(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }

    public int checkBalance() {
        return balance;
    }

    public boolean deposit(int amount) {
        if (amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    public int withdraw(int amount) {
        if (this.amount + amount > 30000) return 1;
        if (amount < 0 || balance - amount < 0) return 2;
        balance -= amount;
        this.amount += amount;
        return 0;
    }

    public Account(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.balance = 50000;
    }
}

class ATM {
    private Scanner scanner;
    private Account account;

    public boolean logIn() {
        for (int i = 0; i < 3; i++) {
            System.out.print("請輸入帳號：");
            String id = scanner.nextLine();
            System.out.print("請輸入密碼：");
            String password = scanner.nextLine();
            if (!account.authenticate(id, password)) {
                System.out.printf("帳號或密碼錯誤，還剩下%s次%n", 2 - i);
                continue;
            }
            return true;
        }
        System.out.println("錯誤超過三次！");
        return false;
    }

    public void checkBalance() {
        System.out.printf("目前餘額為%s%n", account.checkBalance());
    }

    public void deposit() {
        System.out.printf("目前餘額為%s%n", account.checkBalance());
        System.out.print("請輸入存款金額：");
        String amount = scanner.nextLine();
        try {
            if (account.deposit(Integer.parseInt(amount))) {
                System.out.printf("存款成功！目前餘額為%s%n", account.checkBalance());
            } else {
                System.out.println("輸入金額錯誤！");
            }
        } catch (NumberFormatException e) {
            System.out.println("輸入金額錯誤！");
        }
    }

    public void withdraw() {
        System.out.printf("目前餘額為%s%n", account.checkBalance());
        System.out.print("請輸入提款金額：");
        String amount = scanner.nextLine();
        try {
            int a = Integer.parseInt(amount);
            int result = account.withdraw(a);
            switch (result) {
                case 0 -> System.out.println("提款成功！");
                case 1 -> System.out.println("超過每日提款限額！");
                case 2 -> System.out.println("輸入金額錯誤！");
            }
        } catch (NumberFormatException e) {
            System.out.println("輸入金額錯誤！");
        }
    }

    public void main() {
        while (true) {
            System.out.print("[主畫面]\n請輸入選項 ((1) 查詢餘額 (2) 存款 (3) 提款 (e) 離開)：");
            switch (scanner.nextLine()) {
                case "1" -> checkBalance();
                case "2" -> deposit();
                case "3" -> withdraw();
                case "E", "e" -> {
                    System.out.println("Bye.");
                    return;
                }
            }
        }
    }

    public void start() {
        scanner = new Scanner(System.in);
        account = new Account("102214209", "楊斯惟", "209");

        if (!logIn()) {
            return;
        }
        main();

        scanner.close();
    }
}

public class Main {
    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.start();
    }
}
