import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class User {
    final String id, name, password;
    final List<Transaction> transactions = new ArrayList<>();
    private final XDPay xdPay;
    private final DigitalPay digitalPay;
    private int bonus;

    public User(String id, String name, String password, int xdPayBalance, int digitalPayBalance, int bonus) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.xdPay = new XDPay(xdPayBalance);
        this.digitalPay = new DigitalPay(digitalPayBalance);
        this.bonus = bonus;
    }

    void printTransactions() {
        if (transactions.size() == 0) {
            System.out.println("尚未消費");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date lastTxDate = transactions.get(0).timestamp();
        for (Transaction tx : transactions) {
            if (tx.timestamp().after(lastTxDate)) {
                lastTxDate = tx.timestamp();
            }
        }
        System.out.printf("最後一筆交易日期: %s。全部交易紀錄%n", sdf.format(lastTxDate));
        System.out.println("編號  \t交易類型         \t交易內容       \t交易金額  \t獲得紅利  \t交易時間");
        for (Transaction tx : transactions) {
            int amount = tx.req().type() == Transaction.Type.DEPOSIT ? tx.req().amount() : -tx.req().amount();
            System.out.printf(
                    "%-6s\t%-15s\t%-12s\t%+-9d\t%-8d\t%s%n",
                    tx.id(), tx.req().typeName(), tx.req().name(), amount,
                    tx.res().bonus(), tx.prettyTimestamp()
            );
        }
    }

    void deposit(int amount) {
        Transaction.Request req = new Transaction.Request(Pay.Type.XD_PAY, Transaction.Type.DEPOSIT, "", amount);
        Transaction.Result res = xdPay.deposit(req.amount());
        saveTx("Profile.txt", req, res);
    }

    void withdraw(Transaction.Request req) {
        Pay pay = req.payment() == Pay.Type.XD_PAY ? xdPay : digitalPay;
        Transaction.Result res = xdPay.withdraw(req.amount());
        saveTx("Profile.txt", req, res);
    }

    void transaction(Transaction.Request req) {
        Transaction.Result res;
        if (req.type() == Transaction.Type.DEPOSIT) {
            res = xdPay.deposit(req.amount());
        } else {
            Pay pay = req.payment() == Pay.Type.XD_PAY ? xdPay : digitalPay;
            res = pay.withdraw(req.amount());
        }
        saveTx("Profile.txt", req, res);
    }

    void saveTx(String fileName, Transaction.Request req, Transaction.Result res) {
        Transaction tx = new Transaction(transactions.size() + 1, req, res, new Date());
        bonus += tx.res().bonus();
        transactions.add(tx);
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8, true)) {
            writer.write(tx.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void save(String fileName) {
        StringBuilder sb = new StringBuilder();
        String user = String.format("%s,%s,%s,%d,%d,%d", id, name, password, xdPay.balance, digitalPay.balance, bonus);
        sb.append(user).append(System.lineSeparator());
        for (Transaction tx : transactions) {
            sb.append(tx.serialize()).append(System.lineSeparator());
        }
        try (FileWriter writer = new FileWriter(fileName, StandardCharsets.UTF_8)) {
            writer.write(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static User load(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(fileName, StandardCharsets.UTF_8)) {
            int c;
            while ((c = reader.read()) != -1) {
                sb.append((char) c);
            }
        }
        Scanner scanner = new Scanner(sb.toString());
        String[] user = scanner.nextLine().split(",");
        User u = new User(
                user[0], user[1], user[2],
                Integer.parseInt(user[3]), Integer.parseInt(user[4]), Integer.parseInt(user[5])
        );
        while (scanner.hasNextLine()) {
            u.transactions.add(Transaction.deserialize(scanner.nextLine()));
        }
        return u;
    }
}

class Pay {
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

    int balance;

    Pay(int balance) {
        this.balance = balance;
    }

    Transaction.Result withdraw(int amount) {
        if (amount > balance) return new Transaction.Result("餘額不足");
        if (amount < 0) return new Transaction.Result("提款金額不得為負數");
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

record Transaction(int id, Request req, Result res, Date timestamp) {
    enum Type {
        DEPOSIT("儲值"), WITHDRAW("消費");

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

    record Request(Pay.Type payment, Type type, String name, int amount) {
        Request(Pay.Type payment, String name, int amount) {
            this(payment, amount >= 0 ? Type.DEPOSIT : Type.WITHDRAW, name, Math.abs(amount));
        }

        public String typeName() {
            return payment.displayName() + " " + type.displayName();
        }
    }

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
    }

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    String prettyTimestamp() {
        return DATE_FORMAT.format(timestamp);
    }

    String serialize() {
        return String.format(
                "%d,%s,%s,%s,%d,%b,%s,%d,%d",
                id, req.payment(), req.type(), req.name(), req.amount(),
                res.ok(), res.message(), res.bonus(), timestamp.getTime()
        );
    }

    static Transaction deserialize(String data) {
        String[] fields = data.split(",");
        return new Transaction(
                Integer.parseInt(fields[0]),
                new Transaction.Request(
                        Pay.Type.valueOf(fields[1]),
                        Transaction.Type.valueOf(fields[2]),
                        fields[3],
                        Integer.parseInt(fields[4])
                ),
                new Transaction.Result(
                        Boolean.parseBoolean(fields[5]),
                        fields[6],
                        Integer.parseInt(fields[7])
                ),
                new Date(Long.parseLong(fields[8]))
        );
    }
}

public class Exam {
    public static void main(String[] args) {
        // createProfile();

        // 4.1 User 建構子測試
        User user = null;

        try {
            user = User.load("Profile.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        boolean ok = user.id.equals("102214209") && user.name.equals("楊斯惟");
        System.out.printf("4.1 User 建構子測試: 讀取 Profile.txt，狀態: %s。%n", ok ? "成功" : "失敗");
        System.out.printf("姓名: %s； 學號: %s 比對%s。%n", user.name, user.id, ok ? "正確" : "錯誤");

        // 4.2 User 建構子測試：讀取交易紀錄
        ok = user.transactions.size() > 0;
        System.out.printf("%n4.2 User 建構子測試：讀取交易紀錄，狀態: %s。%n%n", ok ? "成功" : "失敗");

        user.printTransactions();

        // 4.3.1 測試 XDPay 存款與付款多型
        record Result(String type, int amount, int balance, int bonus, String message, boolean ok) {
            Result(int amount, int balance, int bonus, boolean ok) {
                this(amount, balance, bonus, "", ok);
            }

            Result(int amount, int balance, int bonus, String message, boolean ok) {
                this(amount >= 0 ? "儲值" : "消費", amount, balance, bonus, message, ok);
            }

            public boolean equals(Result res) {
                return amount == res.amount && balance == res.balance && ok == res.ok;
            }

            @Override
            public String toString() {
                return String.format("- %s %6d 餘額:%6d 紅利: %3d 點 - %s", type, amount, balance, bonus, message);
            }
        }

        List<Result> expectedResults = List.of(
                new Result(3000, 4500, 100, true),
                new Result(200, 4700, 100, true),
                new Result(450, 5150, 100, true),
                new Result(4000, 9150, 200, true),
                new Result(-500, 8650, 210, true),
                new Result(-3000, 5650, 220, true),
                new Result(-300, 5350, 220, true),
                new Result(-5000, 350, 230, true),
                new Result(-10000, 350, 230, false)
        );

        List<Result> results = new ArrayList<>();
        Pay pay = new XDPay(1500);
        boolean failed = false;
        int bonus = 0;

        Result init = new Result("預設", 1500, pay.balance, 0, "交易成功", true);

        for (Result expected : expectedResults) {
            Transaction.Result res;
            if (expected.amount() >= 0) {
                res = ((PrepaidCard) pay).deposit(expected.amount());
            } else {
                res = pay.withdraw(-expected.amount());
            }
            bonus += res.bonus();
            Result result = new Result(expected.amount(), pay.balance, bonus, res.message(), res.ok());
            if (!expected.equals(result) || bonus != expected.bonus()) failed = true;
            results.add(result);
        }

        System.out.printf("%n4.3.1 測試 XDPay 存款與付款多型，測試狀態：%s。%n", failed ? "失敗" : "成功");

        System.out.println(init);
        results.forEach(System.out::println);

        // 4.3.1 測試 DigitalPay 付款多型
        expectedResults = List.of(
                new Result(500, 9500, 0, true),
                new Result(800, 8700, 0, true),
                new Result(2999, 5701, 10, true),
                new Result(5000, 701, 20, true),
                new Result(3000, 701, 20, false),
                new Result(2000, 701, 20, false)
        );

        results = new ArrayList<>();
        pay = new DigitalPay(10000);
        failed = false;
        bonus = 0;

        init = new Result("預設", 10000, pay.balance, 0, "交易成功", true);

        for (Result expected : expectedResults) {
            Transaction.Result res = pay.withdraw(expected.amount());
            bonus += res.bonus();
            Result result = new Result(expected.amount(), pay.balance, bonus, res.message(), res.ok());
            if (!expected.equals(result) || bonus != expected.bonus()) failed = true;
            results.add(result);
        }

        System.out.printf("%n4.3.2 測試 DigitalPay 付款多型，測試狀態：%s。%n", failed ? "失敗" : "成功");

        System.out.println(init);
        results.forEach(System.out::println);
    }

    private static void createProfile() {
        User user = new User("102214209", "楊斯惟", "209", 1500, 10000, 0);
        user.transaction(new Transaction.Request(Pay.Type.XD_PAY, "儲值", 500));
        user.transaction(new Transaction.Request(Pay.Type.XD_PAY, "儲值", 2000));
        user.transaction(new Transaction.Request(Pay.Type.DIGITAL_PAY, "阿財鍋貼", -1000));
        user.transaction(new Transaction.Request(Pay.Type.DIGITAL_PAY, "小玉的店", -310));
        user.save("Profile.txt");
    }
}
