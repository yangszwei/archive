import java.util.Scanner;

/**
 * 怪獸行動
 */
enum MonsterAction {
    ATTACK, DEFEND, HEAL,
}

/**
 * 怪獸屬性
 */
enum MonsterType {
    NONE, FIRE, ELECTRIC,
}

abstract class MonsterBase {
    public final int id;
    public final String name;
    public final MonsterType type;
    int hp, fullHp, attack, defense;

    public MonsterBase(int id, String name, MonsterType type, int hp, int fullHp, int attack, int defense) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.fullHp = fullHp;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * 取得怪獸屬性名稱。
     */
    public static String getTypeName(MonsterType type) {
        return switch (type) {
            case NONE -> "無";
            case FIRE -> "火";
            case ELECTRIC -> "雷";
        };
    }

    /**
     * 進化怪獸。
     */
    public void evolve() {
        hp *= 2;
        fullHp *= 2;
        attack *= 2;
        defense *= 2;
    }

    /**
     * 檢查怪獸是否還活著。
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * 以文字方式顯示怪獸的狀態。
     */
    @Override
    public String toString() {
        return String.format("%s [%s] HP：%d/%d 攻擊：%d 防禦：%d", name, getTypeName(type), hp, fullHp, attack, defense);
    }

    /**
     * 攻擊演算法
     *
     * @param enemyAction 敵人的行動
     * @return 攻擊力
     */
    protected abstract int attack(MonsterAction enemyAction);

    /**
     * 防守演算法
     *
     * @return 防守力
     */
    protected abstract int defend();

    /**
     * 補血演算法
     *
     * @return 補血量
     */
    protected abstract int heal();
}

/**
 * 怪獸原型
 */
class Monster extends MonsterBase {
    /**
     * 建立一個無屬性的 Monster 實體（怪獸原型）。
     */
    public Monster(int id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, MonsterType.NONE, hp, fullHp, attack, defense);
    }

    /**
     * 建立一個其他屬性的 Monster 實體。
     */
    protected Monster(int id, String name, MonsterType type, int hp, int fullHp, int attack, int defense) {
        super(id, name, type, hp, fullHp, attack, defense);
    }

    @Override
    protected int attack(MonsterAction enemyAction) {
        return attack;
    }

    @Override
    protected int defend() {
        return defense;
    }

    @Override
    protected int heal() {
        return hp < 20 ? 40 : 20;
    }
}

/**
 * 火系怪獸
 */
class MonsterFire extends Monster {
    public MonsterFire(int id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, MonsterType.FIRE, hp, fullHp, attack, defense);
    }

    /**
     * <h3>對方沒防守</h3>
     * <ul>
     *     <li>40%會被打 100% 全力攻擊(攻擊力*1)</li>
     *     <li>40%被打50% (攻擊力*0.5)</li>
     *     <li>40%被打50% (攻擊力*0.5)</li>
     * </ul>
     * <h3>對方有防守</h3>
     * <ul>
     *     <li>40%會被打 100% 全力攻擊(攻擊力*0.5)</li>
     *     <li>40%被打50% (攻擊力*0.25)</li>
     *     <li>20% miss</li>
     * </ul>
     */
    @Override
    public int attack(MonsterAction enemyAction) {
        double random = Math.random();
        int amount = random < .4 ? attack : random < .8 ? attack / 2 : 0;
        if (enemyAction == MonsterAction.DEFEND) amount /= 2;
        return amount;
    }
}

/**
 * 雷系怪獸
 */
class MonsterElect extends Monster {
    public MonsterElect(int id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, MonsterType.ELECTRIC, hp, fullHp, attack, defense);
    }

    /**
     * 防守力加成 1.5 倍
     */
    @Override
    public int defend() {
        return (int) (defense * 1.5);
    }
}

/**
 * 一個怪獸的視角中的一回合戰鬥。
 */
class MonsterRound {
    public final MonsterBase actor, enemy;
    public final MonsterAction action, enemyAction;
    int hp, enemyHp, result;
    private boolean isFinished;

    public MonsterRound(MonsterBase actor, MonsterBase enemy, MonsterAction action, MonsterAction enemyAction) {
        this.actor = actor;
        this.enemy = enemy;
        this.action = action;
        this.enemyAction = enemyAction;
    }

    /**
     * 執行 actor 於此回合中的行動，並將剩餘血量及結果分別存入 hp, enemyHp 及 result。其中，result 於 action
     * == ATTACK 時為對敵人造成的實際傷害；action == DEFEND 時無意義；action == HEAL 時為對自己的實際補血量，
     * 此 result 值於用於建立回合結果的文字描述。
     */
    public void fight() {
        switch (action) {
            case ATTACK -> {
                int damage = actor.attack(enemyAction);
                if (enemyAction == MonsterAction.DEFEND) {
                    damage -= enemy.defend();
                }
                damage = Math.max(Math.min(damage, enemy.hp), 0);
                enemy.hp -= damage;
                result = damage;
            }
            case HEAL -> {
                int heal = Math.max(actor.heal(), 0);
                actor.hp += heal;
                result = heal;
            }
        }
        hp = actor.hp;
        enemyHp = enemy.hp;
        isFinished = true;
    }

    /**
     * 建立此回合執行結果的文字描述。
     */
    public String summarize() {
        if (!isFinished) return "此回合尚未結束";
        return switch (action) {
            case ATTACK -> {
                String x = actor.name + " 攻擊 " + enemy.name + "，";
                if (enemyAction == MonsterAction.DEFEND) {
                    x += "對方防守" + (result == 0 ? "成功" : "失敗，受到 " + result + " 點傷害");
                    yield x + "，剩餘血量 " + enemyHp;
                }
                x += result == 0 ? "但對方閃避了攻擊" : "造成 " + result + " 點傷害";
                yield x + "，" + enemy.name + " 剩餘血量 " + enemyHp;
            }
            case HEAL -> {
                String x = actor.name;
                if (result == 40) x += " 使用 Overload";
                yield x + " 回復了 " + result + " 點血量，剩餘血量 " + hp;
            }
            default -> "";
        };
    }
}

/**
 * MonsterBuilder 負責建立 MonsterBase 實體。
 */
class MonsterBuilder {
    private static int id;

    /**
     * 使用參數建立一個 MonsterBase 實體，並為其提供一個唯一的 id。
     *
     * @return MonsterBase 實體
     */
    public static MonsterBase build(String name, MonsterType type, int hp, int attack, int defense) {
        return switch (type) {
            case NONE -> new Monster(++id, name, hp, hp, attack, defense);
            case FIRE -> new MonsterFire(++id, name, hp, hp, attack, defense);
            case ELECTRIC -> new MonsterElect(++id, name, hp, hp, attack, defense);
        };
    }

    /**
     * 自動建立一個隨機的 MonsterBase 實體，並為其提供一個唯一的 id。
     *
     * @return MonsterBase 實體
     */
    public static MonsterBase random() {
        String name = getRandomName();
        MonsterType type = MonsterType.values()[(int) (Math.random() * MonsterType.values().length)];
        int hp = (int) (Math.random() * 150) + 150;
        int attack = (int) (Math.random() * 30) + 20;
        int defense = (int) (Math.random() * 30) + 20;
        MonsterBase monster = build(name, type, hp, attack, defense);
        if (Math.random() < 0.5) monster.evolve();
        return monster;
    }

    /**
     * 產生一個隨機的寶可夢名稱。
     *
     * @return 寶可夢名稱
     */
    private static String getRandomName() {
        final String[] NAMES = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "阿柏蛇", "皮卡丘", "穿山鼠"
        };
        return NAMES[(int) (Math.random() * NAMES.length)];
    }
}

/**
 * 定義顏色的 CSI 序列。
 */
class Color {
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String BLUE = "\033[34m";
    public static final String CYAN = "\033[36m";
    public static final String BG_GREEN = "\033[42m";
}

/**
 * 遊戲主程式及 UI。
 */
class Game {
    private final Scanner scanner;
    private final MonsterBase a, b;

    /**
     * 建立一個新的遊戲，並初始化兩個怪獸。
     *
     * @param scanner 用於讀取輸入的 Scanner 實體
     */
    public Game(Scanner scanner) {
        printHeading("寶可夢對戰遊戲");
        this.scanner = scanner;
        this.a = promptMonster(scanner, "玩家 1");
        this.b = promptMonster(scanner, "玩家 2");
        System.out.println();
    }

    /**
     * 建立一個新的遊戲。
     *
     * @param scanner 用於讀取輸入的 Scanner 實體
     * @param a       玩家一的怪獸
     * @param b       玩家二的怪獸
     */
    public Game(Scanner scanner, MonsterBase a, MonsterBase b) {
        printHeading("寶可夢對戰遊戲");
        this.scanner = scanner;
        this.a = a;
        this.b = b;
        System.out.println();
    }

    /**
     * 遊戲主程式。
     */
    public void run() {
        printHeading("遊戲開始");

        while (a.isAlive() && b.isAlive()) {
            printStatus(a.toString());
            printStatus(b.toString());
            MonsterAction aAction = runPlayerRound(a.name);
            MonsterAction bAction = runPlayerRound(b.name);
            printResult(runMonsterRound(a, b, aAction, bAction));
            if (b.isAlive()) {
                printResult(runMonsterRound(b, a, bAction, aAction));
            }
            System.out.println("--------------------");
        }

        printResult(summarize());
    }

    /**
     * 由一位玩家執行行動（對怪獸下達指令或是離開遊戲）。
     *
     * @param playerName 玩家名稱
     * @return 玩家對怪獸下達的指令（若未下達指令則為 null）
     */
    private MonsterAction runPlayerRound(String playerName) {
        System.out.printf("%s 的回合，請選擇行動（1. 攻擊 2. 防禦 3. 補血 4. 離開）：", playerName);
        return switch (scanner.nextLine()) {
            case "1" -> MonsterAction.ATTACK;
            case "2" -> MonsterAction.DEFEND;
            case "3" -> MonsterAction.HEAL;
            case "4" -> {
                printResult("遊戲結束！");
                System.exit(0);
                yield null;
            }
            default -> {
                System.out.println("輸入錯誤，請重新輸入。");
                yield runPlayerRound(playerName);
            }
        };
    }

    /**
     * 以一個怪獸的視角執行一回合的對戰。從雙方視角皆執行一次 runMonsterRound() 即為完成一回合的對戰。
     *
     * @param actor       怪獸
     * @param enemy       敵人
     * @param action      怪獸於此回合的行動
     * @param enemyAction 敵人於此回合的行動
     * @return 此回合對戰結果的文字描述
     */
    private String runMonsterRound(MonsterBase actor, MonsterBase enemy, MonsterAction action, MonsterAction enemyAction) {
        MonsterRound round = new MonsterRound(actor, enemy, action, enemyAction);
        round.fight();
        return round.summarize();
    }

    /**
     * 建立遊戲結果的文字描述。
     */
    private String summarize() {
        if (a.isAlive() && b.isAlive()) return "雙方平手！";
        return (b.isAlive() ? b.name : a.name) + " 獲勝！";
    }

    /**
     * 以玩家自行輸入的方式建立一個 MonsterBase 實體。
     *
     * @param scanner    用於讀取輸入的 Scanner 實體
     * @param playerName 玩家名稱
     * @return MonsterBase 實體
     */
    private static MonsterBase promptMonster(Scanner scanner, String playerName) {
        String name = promptName(scanner, playerName);
        int hp = promptInt(scanner, playerName + " 請輸入怪獸血量：");
        int attack = promptInt(scanner, playerName + " 請輸入怪獸攻擊力：");
        int defense = promptInt(scanner, playerName + " 請輸入怪獸防守力：");
        MonsterType type = promptMonsterType(scanner, playerName);
        MonsterBase monster = MonsterBuilder.build(name, type, hp, attack, defense);
        if (confirmEvolve(scanner, playerName)) monster.evolve();
        return monster;
    }

    /**
     * 要求玩家輸入怪獸名稱。
     *
     * @param scanner    用於讀取輸入的 Scanner 實體
     * @param playerName 玩家名稱
     * @return 怪獸名稱
     */
    private static String promptName(Scanner scanner, String playerName) {
        while (true) {
            System.out.print(playerName + " 請輸入怪獸名稱：");
            String name = scanner.nextLine();
            if (name.length() > 0) return name;
            printError("怪獸名稱不可為空");
        }
    }

    /**
     * 要求玩家輸入一個整數。
     *
     * @param scanner 用於讀取輸入的 Scanner 實體
     * @param prompt  輸入提示
     * @return 整數
     */
    private static int promptInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                printError("請輸入數字");
            }
        }
    }

    /**
     * 要求玩家選擇怪獸屬性。
     *
     * @param scanner    用於讀取輸入的 Scanner 實體
     * @param playerName 玩家名稱
     * @return 怪獸屬性
     */
    private static MonsterType promptMonsterType(Scanner scanner, String playerName) {
        MonsterType type = null;
        while (type == null) {
            System.out.print(playerName + " 請選擇怪獸屬性（1. 怪獸原型 2. 火系怪獸 3. 雷系怪獸）：");
            switch (scanner.nextLine()) {
                case "1" -> type = MonsterType.NONE;
                case "2" -> type = MonsterType.FIRE;
                case "3" -> type = MonsterType.ELECTRIC;
                default -> printError("輸入錯誤，請重新輸入。");
            }
        }
        return type;
    }

    /**
     * 要求玩家選擇怪獸是否進化。
     *
     * @param scanner    用於讀取輸入的 Scanner 實體
     * @param playerName 玩家名稱
     * @return 玩家是否選擇進化
     */
    private static boolean confirmEvolve(Scanner scanner, String playerName) {
        while (true) {
            System.out.print(playerName + " 請輸入是否進化？(1: 是, 2: 否) ");
            String choice = scanner.nextLine();
            if (choice.equals("1")) return true;
            if (choice.equals("2")) return false;
            printError("輸入錯誤，請重新輸入。");
        }
    }

    /**
     * 以「標題」樣式印出一行文字。
     *
     * @param text 文字
     */
    private static void printHeading(String text) {
        System.out.println(Color.BG_GREEN + Color.BLACK + text + Color.RESET);
    }

    /**
     * 以「狀態」樣式印出一行文字。
     *
     * @param text 文字
     */
    private static void printStatus(String text) {
        System.out.println(Color.BLUE + text + Color.RESET);
    }

    /**
     * 以「結果」樣式列印一行文字。
     *
     * @param text 文字
     */
    private static void printResult(String text) {
        if (text.length() > 0) {
            System.out.println(Color.CYAN + "| " + text + Color.RESET);
        }
    }

    /**
     * 以「錯誤訊息」樣式列印一行文字至 stdout。
     *
     * @param text 文字
     */
    private static void printError(String text) {
        System.out.println(Color.RED + text + Color.RESET);
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game(scanner);
        // Game game = new Game(scanner, MonsterBuilder.random(), MonsterBuilder.random());
        game.run();

        scanner.close();
    }
}
