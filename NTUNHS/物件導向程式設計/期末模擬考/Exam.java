import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class User {
    final String fileName;
    final String id, name, password;
    final Pay xdPay, digitalPay;
    final List<Transaction> transactions = new ArrayList<>();
    boolean autoSave = true;
    int bonus;

    User(String id, String name, String password, int xdBalance, int digitalBalance, int bonus) {
        this("Profile.txt", id, name, password, xdBalance, digitalBalance, bonus);
    }

    User(String fileName, String id, String name, String password, int xdBalance, int digitalBalance, int bonus) {
        this.fileName = fileName;
        this.id = id;
        this.name = name;
        this.password = password;
        this.xdPay = new XDPay(xdBalance);
        this.digitalPay = new DigitalPay(digitalBalance);
        this.bonus = bonus;
    }

    String transactions() {
        if (transactions.isEmpty()) return "尚未消費";
        StringBuilder sb = new StringBuilder();
        Date latest = transactions.stream().map(Transaction::date).max(Date::compareTo).orElse(null);
        sb.append("最後一筆交易日期: ").append(new SimpleDateFormat("yyyy/MM/dd").format(latest)).append("。全部交易紀錄");
        sb.append(System.lineSeparator()).append(Transaction.headers());
        for (Transaction txn : transactions) sb.append(System.lineSeparator()).append(txn);
        return sb.toString();
    }

    void deposit(int amount) {
        Transaction.Request request = new Transaction.Request(Pay.Type.XD_PAY, Transaction.Type.DEPOSIT, "儲值", amount);
        Transaction.Result result = ((PrepaidCard) xdPay).deposit(amount);
        save(new Transaction(transactions.size() + 1, request, result, new Date()));
    }

    void withdraw(Pay.Type payment, String name, int amount) {
        Transaction.Request request = new Transaction.Request(payment, Transaction.Type.WITHDRAW, name, amount);
        Transaction.Result result = (payment == Pay.Type.XD_PAY ? xdPay : digitalPay).withdraw(amount);
        save(new Transaction(transactions.size() + 1, request, result, new Date()));
    }

    void save() {
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8)) {
            String userStr = String.format("%s,%s,%s,%d,%d,%d", id, name, password, xdPay.balance(), digitalPay.balance(), bonus);
            writer.write(userStr + System.lineSeparator());
            for (Transaction txn : transactions) writer.write(txn.serialize() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void save(Transaction tx) {
        bonus += tx.result().bonus();
        transactions.add(tx);
        if (autoSave) try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8, true)) {
            writer.write(tx.serialize() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static User load(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            String[] userStr = scanner.nextLine().split(",");
            User user = new User(
                    fileName, userStr[0], userStr[1], userStr[2],
                    Integer.parseInt(userStr[3]), Integer.parseInt(userStr[4]), Integer.parseInt(userStr[5])
            );
            while (scanner.hasNextLine()) {
                user.transactions.add(Transaction.deserialize(scanner.nextLine()));
            }
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class Pay {
    /**
     * 支付方式
     */
    enum Type {
        XD_PAY("XDPay"), DIGITAL_PAY("DigitalPay");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 取得顯示名稱
         */
        public String displayName() {
            return displayName;
        }
    }

    protected int balance;

    Pay(int balance) {
        this.balance = balance;
    }

    int balance() {
        return balance;
    }

    Transaction.Result withdraw(int amount) {
        if (amount < 0) return new Transaction.Result("提款金額不得為負數");
        if (amount > balance) return new Transaction.Result("餘額不足");
        balance -= amount;
        return new Transaction.Result(0);
    }
}

interface PrepaidCard {
    Transaction.Result deposit(int amount);
}

class XDPay extends Pay implements PrepaidCard {
    XDPay(int balance) {
        super(balance);
    }

    @Override
    public Transaction.Result deposit(int amount) {
        if (amount < 0) return new Transaction.Result("存款金額不得為負數");
        balance += amount;
        return new Transaction.Result(amount >= 2000 ? 100 : 0);
    }

    @Override
    Transaction.Result withdraw(int amount) {
        Transaction.Result result = super.withdraw(amount);
        if (!result.ok()) return result;
        return new Transaction.Result(result.bonus() + (amount >= 500 ? 10 : 0));
    }
}

class DigitalPay extends Pay {
    DigitalPay(int balance) {
        super(balance);
    }

    @Override
    Transaction.Result withdraw(int amount) {
        Transaction.Result result = super.withdraw(amount);
        if (!result.ok()) return result;
        return new Transaction.Result(result.bonus() + (amount >= 1000 ? 10 : 0));
    }
}

record Transaction(int id, Request request, Result result, Date date) {
    /**
     * 交易類型
     */
    enum Type {
        DEPOSIT("儲值"), WITHDRAW("消費");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        static Type of(int amount) {
            return amount >= 0 ? DEPOSIT : WITHDRAW;
        }

        /**
         * 取得顯示名稱
         */
        String displayName() {
            return displayName;
        }
    }

    /**
     * 交易請求
     */
    record Request(Pay.Type payment, Type type, String name, int amount) {
        Request(Pay.Type payment, String name, int amount) {
            this(payment, Type.of(amount), name, Math.abs(amount));
        }

        public String typeName() {
            return payment.displayName() + " " + type.displayName();
        }

        String serialize() {
            return String.format("%s,%s,%s,%d", payment, type, name, amount);
        }

        static Request deserialize(String s) {
            String[] parts = s.split(",");
            return new Request(Pay.Type.valueOf(parts[0]), Type.valueOf(parts[1]), parts[2], Integer.parseInt(parts[3]));
        }
    }

    /**
     * 交易結果
     */
    record Result(boolean ok, String message, int bonus) {
        Result(int bonus) {
            this(true, "", bonus);
        }

        Result(String message) {
            this(false, message, 0);
        }

        public String message() {
            return (ok ? "交易成功" : "交易失敗") + (message.isEmpty() ? "" : " — " + message);
        }

        String serialize() {
            return String.format("%s,%s,%d", ok, message, bonus);
        }

        static Result deserialize(String s) {
            String[] parts = s.split(",");
            return new Result(Boolean.parseBoolean(parts[0]), parts[1], Integer.parseInt(parts[2]));
        }
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private static final String DISPLAY_FORMAT = "%-6s\t%-15s\t%-12s\t%+-9d\t%-8d\t%s";

    public String prettyDate() {
        return DATE_FORMAT.format(date);
    }

    public static String headers() {
        return "編號  \t交易類型         \t交易內容       \t交易金額  \t獲得紅利  \t交易時間";
    }

    @Override
    public String toString() {
        return String.format(
                DISPLAY_FORMAT, id(), request().typeName(), request().name(),
                request().type() == Type.DEPOSIT ? request().amount() : -request().amount(),
                result().bonus(), prettyDate()
        );
    }

    String serialize() {
        return String.format("%d;%s;%s;%s", id, request.serialize(), result.serialize(), date.getTime());
    }

    static Transaction deserialize(String s) {
        String[] parts = s.split(";");
        return new Transaction(
                Integer.parseInt(parts[0]), Request.deserialize(parts[1]),
                Result.deserialize(parts[2]), new Date(Long.parseLong(parts[3]))
        );
    }
}

class UserTest {
    public static void main(String[] args) {
        // createProfile();
        User user = loadProfile();
        readTransactions(user);
    }

    private static void createProfile() {
        User user = new User("102214209", "楊斯惟", "209", 1500, 10000, 0);
        user.autoSave = false;
        user.deposit(500);
        user.deposit(2000);
        user.withdraw(Pay.Type.DIGITAL_PAY, "阿財鍋貼", 1000);
        user.withdraw(Pay.Type.DIGITAL_PAY, "小玉的店", 310);
        user.save();
    }

    private static User loadProfile() {
        User user = User.load("Profile.txt");
        assert user != null;

        boolean ok = user.id.equals("102214209") && user.name.equals("楊斯惟");
        System.out.printf("4.1 User 建構子測試: 讀取 Profile.txt，狀態: %s。%n", ok ? "成功" : "失敗");
        System.out.printf("姓名: %s； 學號: %s 比對%s。%n", user.name, user.id, ok ? "正確" : "錯誤");

        return user;
    }

    private static void readTransactions(User user) {
        boolean ok = user.transactions.size() == 4;
        System.out.printf("%n4.2 User 建構子測試：讀取交易紀錄，狀態: %s。%n", ok ? "成功" : "失敗");
        System.out.println(user.transactions());
    }
}

class PayTest {
    record Case(Pay.Type payment, String name, int amount, boolean ok, String message, int balance, int bonus) {
        Case(Pay.Type payment, int amount, boolean ok, int balance, int bonus) {
            this(payment, Transaction.Type.of(amount).displayName(), amount, ok, "", balance, bonus);
        }

        Case(Transaction.Request request, Transaction.Result result, int balance, int bonus) {
            this(
                    request.payment(), request.name(),
                    request.type() == Transaction.Type.DEPOSIT ? request.amount() : -request.amount(),
                    result.ok(), result.message(), balance, bonus
            );
        }

        public boolean equals(Case other) {
            return payment() == other.payment() && amount() == other.amount() &&
                    ok() == other.ok() && balance() == other.balance() && bonus() == other.bonus();
        }

        public Transaction.Request request() {
            return new Transaction.Request(payment(), name(), amount());
        }

        @Override
        public String toString() {
            return String.format(
                    "%s %6d 餘額:%6d 紅利: %3d 點 - %s",
                    name(), Math.abs(amount()), balance(), bonus(), message()
            );
        }
    }

    record Result(String title, boolean ok, List<Case> results) {
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(System.lineSeparator()).append(title);
            sb.append("，測試狀態：").append(ok() ? "成功" : "失敗");
            for (Case c : results) {
                sb.append(System.lineSeparator()).append("- ").append(c);
            }
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        System.out.println(testXDPay());
        System.out.println(testDigitalPay());
    }

    private static Result testXDPay() {
        String title = "4.3.1 測試 XDPay 存款與付款多型";
        return test(title, new XDPay(1500), new Case[]{
                new Case(Pay.Type.XD_PAY, 3000, true, 4500, 100),
                new Case(Pay.Type.XD_PAY, 200, true, 4700, 100),
                new Case(Pay.Type.XD_PAY, 450, true, 5150, 100),
                new Case(Pay.Type.XD_PAY, 4000, true, 9150, 200),
                new Case(Pay.Type.XD_PAY, -500, true, 8650, 210),
                new Case(Pay.Type.XD_PAY, -3000, true, 5650, 220),
                new Case(Pay.Type.XD_PAY, -300, true, 5350, 220),
                new Case(Pay.Type.XD_PAY, -5000, true, 350, 230),
                new Case(Pay.Type.XD_PAY, -10000, false, 350, 230),
        });
    }

    private static Result testDigitalPay() {
        String title = "4.3.2 測試 DigitalPay 付款多型";
        return test(title, new DigitalPay(10000), new Case[]{
                new Case(Pay.Type.DIGITAL_PAY, -500, true, 9500, 0),
                new Case(Pay.Type.DIGITAL_PAY, -800, true, 8700, 0),
                new Case(Pay.Type.DIGITAL_PAY, -2999, true, 5701, 10),
                new Case(Pay.Type.DIGITAL_PAY, -5000, true, 701, 20),
                new Case(Pay.Type.DIGITAL_PAY, -3000, false, 701, 20),
                new Case(Pay.Type.DIGITAL_PAY, -2000, false, 701, 20),
        });
    }

    private static Result test(String title, Pay pay, Case[] cases) {
        List<Case> results = new ArrayList<>();
        int bonus = 0;
        boolean ok = true;
        for (Case c : cases) {
            Transaction.Request request = c.request();
            Transaction.Result result = request.type() == Transaction.Type.DEPOSIT
                    ? ((PrepaidCard) pay).deposit(request.amount())
                    : pay.withdraw(request.amount());
            bonus += result.bonus();
            Case actual = new Case(request, result, pay.balance(), bonus);
            ok &= actual.equals(c) && bonus == c.bonus();
            results.add(actual);
        }
        return new Result(title, ok, results);
    }
}

public class Exam {
    public static void main(String[] args) {
        UserTest.main(args);
        PayTest.main(args);
    }
}