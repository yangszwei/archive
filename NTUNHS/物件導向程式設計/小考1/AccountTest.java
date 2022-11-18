public class AccountTest {
    public static void main(String args) {
        Account account = new Account("102214209", "楊斯惟", "209");

        boolean failed = false;

        /** [2.2.2] Test Account.deposit() against numeric inputs */
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                int result = account.deposit(String.valueOf(i), j);
                if (account.checkBalance() != i + j || result == Account.FAILED) {
                    System.out.printf("Account.deposit(%d, %d) => %d%n", i, j, result);
                    failed = true;
                }
            }
        }


        /** [2.2.2] Test Account.deposit() against the alphabet */
        account = new Account("102214209", "楊斯惟", "209");
        for (char i = 'A'; i < 'z'; i++) {
            if (i == '[')
                i = 'a';
            if (account.deposit(String.valueOf(i), 50000) != Account.FAILED) {
                System.out.printf("expected Account.deposit(%d, 50000) to fail, but got OK instead.%n", i);
                failed = true;
            }
        }

        /** [2.2.3] Test Account.withdraw() against numeric inputs */
        account = new Account("102214209", "楊斯惟", "209");
        for (int i = 0; i < 30200; i++) {
            int balance = account.checkBalance();
            int result = account.withdraw("1", balance);
            if (
                ((50000-i) > balance && result != Account.FAILED) ||
                    (i > 30000 && result != Account.QUOTA_EXCEEDED) ||
                    (i < balance && i < 30000 && result != balance - 1)
                    ) {
                System.out.printf("#%d Account.withdraw(1, %d) => %d%n", balance, result);
                failed = true;
            }
        }

        /** [2.2.2] Test Account.withdraw() against the alphabet */
        account = new Account("102214209", "楊斯惟", "209");
        for (char i = 'A'; i < 'z'; i++) {
            if (i == '[') i = 'a';
            if (account.withdraw(String.valueOf(i), 50000) != Account.FAILED) {
                System.out.printf("expected Account.withdraw(%d, 50000) to fail, but got OK instead.%n", i);
                failed = true;
            }
        }

        if (failed) {
            System.out.println("測試失敗！");
            return;
        }

        System.out.println("測試成功！");
    }
}
