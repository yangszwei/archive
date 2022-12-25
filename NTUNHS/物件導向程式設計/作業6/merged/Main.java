import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 玩家類別
 */
class Actor {
    final String name;
    final List<Monster> monsters = new ArrayList<>();
    final boolean isPc;

    Actor(String name) {
        this.name = name;
        this.isPc = false;
    }

    Actor(String name, boolean isPc) {
        this.name = name;
        this.isPc = isPc;
    }

    /**
     * 確認此玩家的所有怪獸是否都還活著。
     *
     * @return true: 所有怪獸都還活著, false: 有怪獸已經死亡
     */
    boolean isAlive() {
        return monsters.stream().allMatch(Monster::isAlive);
    }

    /**
     * 從玩家的所有怪獸中，隨機選出一隻怪獸。
     *
     * @return Monster 實體
     */
    Monster getRandomMonster() {
        if (monsters.isEmpty()) return null;
        return monsters.get((int) (Math.random() * monsters.size()));
    }

    /**
     * 取得此玩家及其所有怪獸的狀態。
     *
     * @return 狀態文字描述
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        for (Monster monster : monsters) {
            sb.append(System.lineSeparator()).append('\t').append(monster);
        }
        return sb.toString();
    }
}

/**
 * 怪獸類別，定義怪獸的基本屬性及行為。
 */
abstract class Monster {
    /**
     * 怪獸行動
     */
    enum Action {
        ATTACK, DEFEND, HEAL;

        /**
         * 取得行動中文名稱。
         *
         * @return 中文名稱
         */
        String getName() {
            return switch (this) {
                case ATTACK -> "攻擊";
                case DEFEND -> "防守";
                case HEAL -> "補血";
            };
        }
    }

    /**
     * 怪獸屬性
     */
    enum Type {
        FIRE, WATER, ELECTRIC;

        /**
         * 取得屬性中文名稱。
         *
         * @return 中文名稱
         */
        String getName() {
            return switch (this) {
                case FIRE -> "火系";
                case WATER -> "水系";
                case ELECTRIC -> "雷系";
            };
        }
    }

    final String id, name;
    final Type type;
    final int level, fullHp, attack, defense;
    int hp;

    Monster(String id, String name, Type type, int level, int hp, int fullHp, int attack, int defense) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.hp = hp;
        this.fullHp = fullHp;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * 建立進化後的怪獸。
     *
     * @return 進化後的怪獸
     */
    abstract Monster evolve();

    /**
     * 檢查怪獸是否還活著。
     *
     * @return true: 活著, false: 死亡
     */
    boolean isAlive() {
        return hp > 0;
    }

    /**
     * 攻擊力演算法
     *
     * @param enemyAction 敵人行動
     * @return 攻擊力
     */
    abstract int attack(Action enemyAction);

    /**
     * 防守力演算法
     *
     * @return 防守力
     */
    abstract int defend();

    /**
     * 補血量演算法
     *
     * @return 補血量
     */
    abstract int heal();

    /**
     * 取得怪獸狀態的文字敘述。
     *
     * @return 怪獸狀態的文字敘述
     */
    @Override
    public String toString() {
        return String.format("%s (%d) [%s] HP：%d/%d 攻擊：%d 防禦：%d", name, level, type.getName(), hp, fullHp, attack, defense);
    }
}

/**
 * 其他屬性怪獸類別的父類別，提供抽象方法的預設實作。
 */
class BaseMonster extends Monster {
    BaseMonster(String id, String name, Type type, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, type, level, hp, fullHp, attack, defense);
    }

    /**
     * （預設不進行進化）
     */
    @Override
    Monster evolve() {
        return this;
    }

    /**
     * （預設使用原始攻擊力）
     */
    @Override
    int attack(Action enemyAction) {
        return attack;
    }

    /**
     * （預設使用原始防守力）
     */
    @Override
    int defend() {
        return defense;
    }

    /**
     * （預設補血量為 20）
     */
    @Override
    int heal() {
        return 20;
    }
}

/**
 * 雷系怪獸
 */
class ElectricMonster extends BaseMonster {
    ElectricMonster(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.ELECTRIC, 1, hp, fullHp, attack, defense);
    }

    protected ElectricMonster(String id, String name, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.ELECTRIC, level, hp, fullHp, attack, defense);
    }

    /**
     * 將此 ElectricMonster 怪獸進化為 ElectricMonster2。
     *
     * @return ElectricMonster2 實體
     */
    @Override
    Monster evolve() {
        return new ElectricMonster2(id, name, hp, fullHp, attack, defense);
    }

    /**
     * 防守力加成 1.5 倍
     */
    @Override
    int defend() {
        return (int) (defense * 1.5);
    }
}

/**
 * 進化後的雷系怪獸
 */
class ElectricMonster2 extends ElectricMonster {
    ElectricMonster2(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, 2, hp * 2, fullHp * 2, attack * 2, defense * 2);
    }

    /**
     * （此類別為雷系怪獸的最終進化形態，無法再進化）
     */
    @Override
    Monster evolve() {
        return this;
    }
}

/**
 * 火系怪獸
 */
class FireMonster extends BaseMonster {
    FireMonster(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.FIRE, 1, hp, fullHp, attack, defense);
    }

    protected FireMonster(String id, String name, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.FIRE, level, hp, fullHp, attack, defense);
    }

    /**
     * 將此 FireMonster 怪獸進化為 FireMonster2。
     *
     * @return FireMonster2 實體
     */
    @Override
    Monster evolve() {
        return new FireMonster2(id, name, hp, fullHp, attack, defense);
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
    int attack(Action enemyAction) {
        double random = Math.random();
        int amount = random < .4 ? attack : random < .8 ? attack / 2 : 0;
        if (enemyAction == Action.DEFEND) amount /= 2;
        return amount;
    }
}

/**
 * 進化後的火系怪獸
 */
class FireMonster2 extends FireMonster {
    FireMonster2(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, 2, hp * 2, fullHp * 2, attack * 2, defense * 2);
    }

    /**
     * （此類別為火系怪獸的最終進化形態，無法再進化）
     */
    @Override
    Monster evolve() {
        return this;
    }
}

/**
 * 水系怪獸
 */
class WaterMonster extends BaseMonster {
    WaterMonster(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.WATER, 1, hp, fullHp, attack, defense);
    }

    protected WaterMonster(String id, String name, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.WATER, level, hp, fullHp, attack, defense);
    }

    /**
     * 將此 WaterMonster 怪獸進化為 WaterMonster2。
     *
     * @return WaterMonster2 實體
     */
    @Override
    Monster evolve() {
        return new WaterMonster2(id, name, hp, fullHp, attack, defense);
    }
}

/**
 * 進化後的水系怪獸
 */
class WaterMonster2 extends WaterMonster {
    WaterMonster2(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, 2, hp * 2, fullHp * 2, attack * 2, defense * 2);
    }

    /**
     * （此類別為水系怪獸的最終進化形態，無法再進化）
     */
    @Override
    Monster evolve() {
        return this;
    }
}

/**
 * MonsterBuilder 類別負責建立 Monster 實體。
 */
class MonsterBuilder {
    /**
     * 提供 id 欄位自動遞增的功能。（不保證為唯一值）
     */
    private static int autoIncrement = 1;

    /**
     * 使用自動遞增功能產生一個 id，並建立一個 Monster 實體。
     *
     * @return Monster 實體
     */
    static Monster build(String name, Monster.Type type, int level, int fullHp, int attack, int defense) {
        return build(String.valueOf(autoIncrement++), name, type, level, fullHp, fullHp, attack, defense);
    }

    /**
     * 建立一個完全隨機的 Monster 實體。
     *
     * @return Monster 實體
     */
    static Monster random() {
        return random(null, null, 0, 0, 0, 0);
    }

    /**
     * 將未提供的參數設為隨機值，並建立一個 Monster 實體。
     *
     * @return Monster 實體
     */
    static Monster random(String name, Monster.Type type, int level, int fullHp, int attack, int defense) {
        name = name == null ? getRandomName() : name;
        type = type == null ? Monster.Type.values()[(int) (Math.random() * Monster.Type.values().length)] : type;
        level = level == 0 ? (int) (Math.random() * 2) + 1 : level;
        fullHp = fullHp == 0 ? (int) (Math.random() * 120) + 60 : fullHp;
        attack = attack == 0 ? (int) (Math.random() * 16) + 8 : attack;
        defense = defense == 0 ? (int) (Math.random() * 16) + 8 : defense;
        return build(name, type, level, fullHp, attack, defense);
    }

    /**
     * 使用參數建立一個對應屬性及進化等級的 Monster 實體。
     *
     * @return Monster 實體
     */
    static Monster build(String id, String name, Monster.Type type, int level, int hp, int fullHp, int attack, int defense) {
        return switch (level) {
            case 1 -> switch (type) {
                case FIRE -> new FireMonster(id, name, hp, fullHp, attack, defense);
                case WATER -> new WaterMonster(id, name, hp, fullHp, attack, defense);
                case ELECTRIC -> new ElectricMonster(id, name, hp, fullHp, attack, defense);
            };
            case 2 -> switch (type) {
                case FIRE -> new FireMonster2(id, name, hp, fullHp, attack, defense);
                case WATER -> new WaterMonster2(id, name, hp, fullHp, attack, defense);
                case ELECTRIC -> new ElectricMonster2(id, name, hp, fullHp, attack, defense);
            };
            default -> throw new IllegalArgumentException("無效的等級：" + level);
        };
    }

    /**
     * 產生一個隨機的怪獸名稱。
     *
     * @return 怪獸名稱
     */
    private static String getRandomName() {
        String[] names = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "烈雀", "阿柏蛇", "皮卡丘",
                "穿山鼠", "尼多蘭", "皮皮", "六尾", "胖丁",
        };
        return names[(int) (Math.random() * names.length)];
    }
}

/**
 * 此類別負責遊戲運作的邏輯，抽象方法由「遊戲介面」類別實作。
 */
abstract class Game {
    protected final Actor a, b;
    protected final List<GameRound> rounds = new ArrayList<>(); // 作業 7 用

    Game(Actor a, Actor b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 進行遊戲（這個方法 blocks 直到遊戲結束）。
     */
    abstract void start();

    /**
     * 取得遊戲狀態的文字敘述。
     *
     * @return 遊戲狀態的文字敘述
     */
    String summary() {
        if (a.isAlive() && b.isAlive()) return "遊戲尚未結束！";
        return (b.isAlive() ? b.name : a.name) + "獲勝！";
    }

    /**
     * 遊戲主迴圈（進行對戰直到其中一方死亡）
     */
    protected final void main() {
        while (a.isAlive() && b.isAlive()) {
            beforeRound();
            GameRound aRound, bRound = null;
            Monster aMonster = a.getRandomMonster();
            Monster bMonster = b.getRandomMonster();
            Monster.Action aAction = chooseAction(a, aMonster);
            Monster.Action bAction = chooseAction(b, bMonster);
            aRound = fight(aMonster, bMonster, aAction, bAction);
            if (bMonster.isAlive()) {
                bRound = fight(bMonster, aMonster, bAction, aAction);
            }
            afterRound(aRound, bRound);
        }
    }

    /**
     * 選擇怪獸行動。
     *
     * @param actor   玩家
     * @param monster 怪獸
     * @return 怪獸行動
     */
    protected Monster.Action chooseAction(Actor actor, Monster monster) {
        return actor.isPc ? randomAction(actor, monster) : promptAction(actor, monster);
    }

    /**
     * 要求玩家選擇行動。
     *
     * @param actor   玩家
     * @param monster 怪獸
     * @return 行動
     */
    protected abstract Monster.Action promptAction(Actor actor, Monster monster);

    /**
     * 隨機選擇行動。
     *
     * @param actor   玩家
     * @param monster 怪獸
     * @return 行動
     */
    protected Monster.Action randomAction(Actor actor, Monster monster) {
        return Monster.Action.values()[(int) (Math.random() * 3)];
    }

    /**
     * 使其中一隻怪獸進行一回合的對戰。
     *
     * @param monster     怪獸
     * @param enemy       敵人
     * @param action      怪獸行動
     * @param enemyAction 敵人行動
     * @return 包含結果的 GameRound 實體
     */
    protected GameRound fight(Monster monster, Monster enemy, Monster.Action action, Monster.Action enemyAction) {
        GameRound round = new GameRound(monster, enemy, action, enemyAction);
        round.fight();
        rounds.add(round);
        return round;
    }

    /**
     * 開始一回合前的處理（更新介面上的資訊）
     */
    protected abstract void beforeRound();

    /**
     * 結束一回合後的處理（顯示該回合的結果）
     *
     * @param aRound 玩家 a 的 GameRound 實體（含結果）
     * @param bRound 玩家 b 的 GameRound 實體（含結果）
     */
    protected abstract void afterRound(GameRound aRound, GameRound bRound);
}

/**
 * 一回合對戰中的一方的行動及結果。
 */
class GameRound {
    final Monster monster, enemy;
    final Monster.Action action, enemyAction;
    int hp, enemyHp, effectedHp;
    boolean isDone = false;

    GameRound(Monster monster, Monster enemy, Monster.Action action, Monster.Action enemyAction) {
        this.monster = monster;
        this.enemy = enemy;
        this.action = action;
        this.enemyAction = enemyAction;
    }

    /**
     * 執行 actor 於此回合對戰中的行動，並剩餘血量及實際影響血量存入 hp, enemyHp, effectedHp。
     * 其中，當 action == DEFEND 時，因不會主動對雙方血量造成影響，不執行任何動作。
     */
    void fight() {
        switch (action) {
            case ATTACK -> {
                int damage = monster.attack(enemyAction);
                if (enemyAction == Monster.Action.DEFEND) {
                    damage -= enemy.defend();
                }
                effectedHp = Math.max(Math.min(damage, enemy.hp), 0); // ∈ [0, enemy.hp]
                enemy.hp -= effectedHp;
            }
            case HEAL -> {
                int max = monster.fullHp - monster.hp; // hp + heal <= fullHp
                effectedHp = Math.max(Math.min(monster.heal(), max), 0);
                monster.hp += effectedHp;
            }
        }
        hp = monster.hp;
        enemyHp = enemy.hp;
        isDone = true;
    }

    /**
     * 建立此回合對戰結果的文字描述。
     *
     * @return 對戰結果的文字描述
     */
    @Override
    public String toString() {
        if (!isDone) return null; // 回合尚未結束！
        return switch (action) {
            case ATTACK -> {
                String x = monster.name + " 攻擊 " + enemy.name + "，";
                if (enemyAction == Monster.Action.DEFEND) {
                    x += "對方防守" + (effectedHp == 0 ? "成功" : "失敗，受到 " + effectedHp + " 點傷害");
                    yield x + "，剩餘血量 " + enemyHp;
                }
                x += effectedHp == 0 ? "但對方閃避了攻擊" : "造成 " + effectedHp + " 點傷害";
                yield x + "，" + enemy.name + " 剩餘血量 " + enemyHp;
            }
            case HEAL -> {
                String x = monster.name;
                if (effectedHp == 40) x += " 使用 Overload";
                yield x + " 回復了 " + effectedHp + " 點血量，剩餘血量 " + hp;
            }
            default -> "";
        };
    }
}

/**
 * 定義顏色的 CSI 序列。
 * */
class Color {
    public static final String RESET = "\033[0m";
    public static final String BLACK = "\033[30m";
    public static final String RED = "\033[31m";
    public static final String BLUE = "\033[34m";
    public static final String CYAN = "\033[36m";
    public static final String DARK_GREEN = "\033[38;5;28m";
    public static final String BG_GREEN = "\033[42m";
}

/**
 * 此類別提供附帶樣式的文字輸出功能。
 */
class Format {
    /**
     * 以「標題」樣式印出一行文字至 System.out。
     */
    static void printHeading(Object x) {
        System.out.println(Color.BG_GREEN + Color.BLACK + x + Color.RESET);
    }

    /**
     * 以「輸入」樣式印出一行文字至 System.out。
     */
    static void printInput(Object x) {
        System.out.println(Color.DARK_GREEN + x + Color.RESET);
    }

    /**
     * 以「狀態」樣式印出一行文字至 System.out。
     */
    static void printStatus(Object x) {
        System.out.println(Color.BLUE + x + Color.RESET);
    }

    /**
     * 以「結果」樣式印出一行文字至 System.out。
     */
    static void printResult(Object x) {
        if (x != null && !x.toString().isEmpty()) {
            System.out.println(Color.CYAN + "| " + x + Color.RESET);
        }
    }

    /**
     * 以「錯誤訊息」樣式印出一行文字至 System.out。
     */
    static void printError(String text) {
        System.out.println(Color.RED + text + Color.RESET);
    }
}

/**
 * 此類別負責以讀取玩家輸入方式建立怪獸。
 */
class MonsterPrompt {
    /**
     * 要求玩家自行輸入參數，並建立一個 Monster 實體。
     *
     * @param scanner 輸入來源
     * @param actor   玩家
     * @param type    怪獸類別
     * @return 建立的 Monster 實體
     */
    public static Monster prompt(Scanner scanner, Actor actor, Monster.Type type) {
        String name = promptName(scanner, actor, type);
        int hp = promptInt(scanner, String.format("請輸入 %s 的血量：", name));
        int attack = promptInt(scanner, String.format("請輸入 %s 的攻擊力：", name));
        int defense = promptInt(scanner, String.format("請輸入 %s 的防禦力：", name));
        int level = confirmEvolve(scanner, name) ? 2 : 1;
        return MonsterBuilder.build(name, type, level, hp, attack, defense);
    }

    /**
     * 要求玩家輸入怪獸名稱。
     *
     * @param scanner 輸入來源
     * @param actor   玩家
     * @return 怪獸名稱
     */
    private static String promptName(Scanner scanner, Actor actor, Monster.Type type) {
        while (true) {
            System.out.printf("%s 請輸入%s怪獸名稱：", actor.name, type.getName());
            String input = scanner.nextLine();
            if (!input.isEmpty()) return input;
            Format.printError("怪獸名稱不可為空白！");
        }
    }

    /**
     * 要求玩家輸入一個整數。
     *
     * @param scanner 輸入來源
     * @param prompt  提示訊息
     * @return 整數
     */
    private static int promptInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                Format.printError("請輸入數字！");
            }
        }
    }

    /**
     * 要求玩家確認是否要進化。
     *
     * @param scanner 輸入來源
     * @param name    怪獸名稱
     * @return true: 進化, false: 不進化
     */
    private static boolean confirmEvolve(Scanner scanner, String name) {
        while (true) {
            System.out.print("要進化 " + name + " 嗎？（1. 是 2. 否）：");
            String input = scanner.nextLine();
            if (input.equals("1")) return true;
            if (input.equals("2")) return false;
            Format.printError("請輸入 1 或 2！");
        }
    }
}

/**
 * 基於文字介面的遊戲實作。
 */
class TextBasedGame extends Game {
    private final Scanner scanner;

    TextBasedGame(Scanner scanner, Actor a, Actor b) {
        super(a, b);
        this.scanner = scanner;
    }

    /**
     * 由一位玩家與電腦對戰的遊戲模式。（玩家持有水系、火系與雷系的怪獸各一隻）
     */
    static class PvC extends TextBasedGame {
        PvC(Scanner scanner) {
            this(scanner, false);
        }

        /**
         * @param scanner      輸入來源
         * @param autoGenerate 是否自動產生怪獸
         */
        PvC(Scanner scanner, boolean autoGenerate) {
            super(scanner, new Actor("玩家"), new Actor("電腦", true));
            Format.printHeading("怪獸對戰遊戲");
            for (Monster.Type type : new Monster.Type[]{Monster.Type.WATER, Monster.Type.FIRE, Monster.Type.ELECTRIC}) {
                a.monsters.add(autoGenerate ?
                        MonsterBuilder.random(null, type, 0, 0, 0, 0) :
                        MonsterPrompt.prompt(scanner, a, type)
                );
            }
            b.monsters.add(MonsterBuilder.random());
        }
    }

    /**
     * 由兩位玩家對戰的遊戲模式。（玩家持有水系、火系與雷系的怪獸各一隻）
     */
    static class PvP extends TextBasedGame {
        PvP(Scanner scanner) {
            this(scanner, false);
        }

        /**
         * @param scanner      輸入來源
         * @param autoGenerate 是否自動產生怪獸
         */
        PvP(Scanner scanner, boolean autoGenerate) {
            super(scanner, new Actor("玩家1"), new Actor("玩家2"));
            Format.printHeading("怪獸對戰遊戲");
            for (Actor actor : new Actor[]{a, b}) {
                for (Monster.Type type : new Monster.Type[]{Monster.Type.WATER, Monster.Type.FIRE, Monster.Type.ELECTRIC}) {
                    actor.monsters.add(autoGenerate ?
                            MonsterBuilder.random(null, type, 0, 0, 0, 0) :
                            MonsterPrompt.prompt(scanner, actor, type)
                    );
                }
            }
        }
    }

    @Override
    public void start() {
        System.out.println();
        Format.printHeading("遊戲開始");

        main();

        Format.printResult(summary());
    }

    @Override
    protected Monster.Action promptAction(Actor actor, Monster monster) {
        System.out.printf("%s 的回合，請選擇怪獸 %s 的行動（1. 攻擊 2. 防守 3. 補血 4. 離開）：", actor.name, monster.name);
        return switch (scanner.nextLine()) {
            case "1" -> Monster.Action.ATTACK;
            case "2" -> Monster.Action.DEFEND;
            case "3" -> Monster.Action.HEAL;
            case "4" -> {
                System.out.println("--------------------");
                Format.printResult("遊戲結束");
                System.exit(0);
                yield null;
            }
            default -> {
                Format.printError("輸入錯誤，請重新輸入！");
                yield promptAction(actor, monster);
            }
        };
    }

    @Override
    protected Monster.Action randomAction(Actor actor, Monster monster) {
        Monster.Action action = super.randomAction(actor, monster);
        System.out.printf("%s 的回合，已決定怪獸 %s 的行動：%s%n", actor.name, monster.name, action.getName());
        return action;
    }

    @Override
    protected void beforeRound() {
        Format.printStatus(a);
        Format.printStatus(b);
    }

    @Override
    protected void afterRound(GameRound aRound, GameRound bRound) {
        Format.printResult(aRound);
        Format.printResult(bRound);
        System.out.println("--------------------");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new TextBasedGame.PvC(scanner); // true: 自動產生怪獸
        // Game game = new TextBasedGame.PvP(scanner);
        game.start();

        scanner.close();
    }
}
