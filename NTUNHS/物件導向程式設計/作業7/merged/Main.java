import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * 作者類別
 */
final class Author {
    /**
     * 取得作者資訊。
     *
     * @return 作者資訊
     */
    static String getAuthor() {
        return "102214209 楊斯惟";
    }
}

/**
 * 定義顏色的 CSI 序列。
 */
class Color {
    static final String RESET = "\033[0m";
    static final String BLACK = "\033[30m";
    static final String RED = "\033[31m";
    static final String BLUE = "\033[34m";
    static final String CYAN = "\033[36m";
    static final String DARK_GREEN = "\033[38;5;28m";
    static final String BG_GREEN = "\033[42m";
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
     * 以「狀態」樣式印出一行文字至 System.out。
     */
    static void printStatus(Object x) {
        System.out.println(Color.BLUE + x + Color.RESET);
    }

    /**
     * 以「結果」樣式印出一行文字至 System.out。
     */
    static void printResult(Object x) {
        if (x != null && x.toString() != null) {
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
 * 玩家類別
 */
class Actor {
    final String id;
    final String name;
    final boolean isPc;
    final List<Monster> monsters = new ArrayList<>();

    Actor(int id, String name) {
        this(String.valueOf(id), name, false);
    }

    Actor(int id, String name, boolean isPc) {
        this(String.valueOf(id), name, isPc);
    }

    Actor(String id, String name, boolean isPc) {
        this.id = id;
        this.name = name;
        this.isPc = isPc;
    }

    /**
     * 從玩家的怪獸中選出一隻怪獸。
     */
    Monster chooseMonster() {
        return monsters.get((int) (Math.random() * monsters.size()));
    }

    /**
     * 確認此玩家的所有怪獸是否都還活著。
     */
    boolean isAlive() {
        return monsters.stream().allMatch(Monster::isAlive);
    }

    /**
     * 將玩家資訊序列化成字串。
     */
    String serialize() {
        String[] ids = monsters.stream().map(m -> m.id).toArray(String[]::new);
        return String.format("%s,%s,%s,%s", id, name, isPc, String.join(",", ids));
    }

    /**
     * 從字串及怪獸資料反序列化出玩家資訊。
     */
    static Actor deserialize(String data, Map<String, Monster> monsters) {
        String[] tokens = data.split(",");
        Actor actor = new Actor(tokens[0], tokens[1], Boolean.parseBoolean(tokens[2]));
        for (int i = 3; i < tokens.length; i++) {
            actor.monsters.add(monsters.get(tokens[i]));
        }
        return actor;
    }

    /**
     * 建立玩家資訊字串。
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", id, name);
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
        ATTACK("攻擊"), DEFEND("防禦"), HEAL("補血");

        private final String displayName;

        Action(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 取得顯示名稱。
         */
        public String displayName() {
            return displayName;
        }
    }

    /**
     * 怪獸屬性
     */
    enum Type {
        FIRE("火系"), WATER("水系"), ELECTRIC("雷系");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 取得顯示名稱。
         */
        public String displayName() {
            return displayName;
        }
    }

    final String id;
    final String name;
    final Type type;
    final int level;
    final int attack;
    final int defense;
    final int maxHp;
    int hp;

    protected Monster(String id, String name, Type type, int level, int attack, int defense, int maxHp, int hp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.maxHp = maxHp;
        this.hp = hp;
    }

    /**
     * 建立進化後的怪獸。
     */
    abstract Monster evolve();

    /**
     * 檢查怪獸是否還活著。
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
     * 將怪獸資訊序列化為字串。
     */
    String serialize() {
        return String.format(
                "%s,%s,%s,%d,%d,%d,%d,%d",
                id, name, type, level, attack, defense, maxHp, hp
        );
    }

    /**
     * 從字串反序列化出怪獸資訊。
     */
    static Monster deserialize(String data) {
        String[] parts = data.split(",");
        return MonsterBuilder.recover(
                parts[0], parts[1],
                Monster.Type.valueOf(parts[2]), Integer.parseInt(parts[3]),
                Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
                Integer.parseInt(parts[6]), Integer.parseInt(parts[7])
        );
    }

    /**
     * 取得怪獸狀態的文字敘述。
     */
    @Override
    public String toString() {
        return String.format(
                "%s. %s ［%s Lv：%d, 攻擊：%d, 防禦：%d, HP：%d/%d］",
                id, name, type.displayName(), level, attack, defense, hp, maxHp
        );
    }
}

/**
 * 其他屬性怪獸類別的父類別，提供抽象方法的預設實作。
 */
class BaseMonster extends Monster {
    protected BaseMonster(String id, String name, Type type, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, type, level, attack, defense, maxHp, hp);
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
    ElectricMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected ElectricMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.ELECTRIC, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 ElectricMonster 怪獸進化為 ElectricMonster2。
     */
    @Override
    Monster evolve() {
        return new ElectricMonster2(id, name, attack * 2, defense * 2, maxHp * 2, hp * 2);
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
    ElectricMonster2(String id, String name, int attack, int defense, int maxHp, int hp) {
        super(id, name, 2, attack, defense, maxHp, hp);
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
    FireMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected FireMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.FIRE, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 FireMonster 怪獸進化為 FireMonster2。
     */
    @Override
    Monster evolve() {
        return new FireMonster2(id, name, attack * 2, defense * 2, maxHp * 2, hp * 2);
    }

    /**
     * <h3>對方沒防守</h3>
     * <ul>
     *     <li>40%會被打 100% 全力攻擊(攻擊力*1)</li>
     *     <li>40%被打50% (攻擊力*0.5)</li>
     *     <li>20% miss</li>
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
    FireMonster2(String id, String name, int attack, int defense, int maxHp, int hp) {
        super(id, name, 2, attack, defense, maxHp, hp);
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
    WaterMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected WaterMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.WATER, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 WaterMonster 怪獸進化為 WaterMonster2。
     */
    @Override
    Monster evolve() {
        return new WaterMonster2(id, name, attack * 2, defense * 2, maxHp * 2, hp * 2);
    }
}

/**
 * 進化後的水系怪獸
 */
class WaterMonster2 extends WaterMonster {
    WaterMonster2(String id, String name, int attack, int defense, int maxHp, int hp) {
        super(id, name, 2, attack, defense, maxHp, hp);
    }

    /**
     * （此類別為水系怪獸的最終進化形態，無法再進化）
     */
    @Override
    Monster evolve() {
        return this;
    }
}

class MonsterBuilder {
    private static int autoIncrement = 1;

    /**
     * 以自動遞增方式產生一個 id，並建立一個 Monster 實體。
     */
    static Monster build(String name, Monster.Type type, int level, int attack, int defense, int maxHp, int hp) {
        return build(autoIncrement(), name, type, level, attack, defense, maxHp, hp);
    }

    /**
     * 使用參數建立一個對應屬性的 Monster 實體，並進化至對應等級。
     */
    static Monster build(String id, String name, Monster.Type type, int level, int attack, int defense, int maxHp, int hp) {
        Monster monster = switch (type) {
            case FIRE -> new FireMonster(id, name, attack, defense, maxHp, hp);
            case WATER -> new WaterMonster(id, name, attack, defense, maxHp, hp);
            case ELECTRIC -> new ElectricMonster(id, name, attack, defense, maxHp, hp);
        };
        while (level-- > 1) {
            monster = monster.evolve();
        }
        return monster;
    }

    /**
     * 使用參數建立一個對應屬性及進化等級的 Monster 實體。
     */
    static Monster recover(String id, String name, Monster.Type type, int level, int attack, int defense, int maxHp, int hp) {
        return switch (level) {
            case 1 -> switch (type) {
                case FIRE -> new FireMonster(id, name, attack, defense, maxHp, hp);
                case WATER -> new WaterMonster(id, name, attack, defense, maxHp, hp);
                case ELECTRIC -> new ElectricMonster(id, name, attack, defense, maxHp, hp);
            };
            case 2 -> switch (type) {
                case FIRE -> new FireMonster2(id, name, attack, defense, maxHp, hp);
                case WATER -> new WaterMonster2(id, name, attack, defense, maxHp, hp);
                case ELECTRIC -> new ElectricMonster2(id, name, attack, defense, maxHp, hp);
            };
            default -> null;
        };
    }

    /**
     * 建立一個完全隨機的怪獸。
     */
    static Monster random() {
        return random(null, null, 0, 0, 0, 0);
    }

    /**
     * 將未提供的參數填入隨機值，並建立一個對應的怪獸。
     */
    static Monster random(String name, Monster.Type type, int level, int attack, int defense, int maxHp) {
        int base = (int) (Math.random() * 25) + 25;
        name = name == null ? randomName() : name;
        type = type == null ? Monster.Type.values()[(int) (Math.random() * Monster.Type.values().length)] : type;
        level = level <= 0 ? (int) (Math.random() * 2) + 1 : level;
        attack = attack <= 0 ? (int) (Math.random() * 25) + base : attack;
        defense = defense <= 0 ? (int) (Math.random() * 25) + base : defense;
        maxHp = maxHp <= 0 ? (int) (Math.random() * 50) + (base * 4) : maxHp;
        return build(name, type, level, attack, defense, maxHp, maxHp);
    }

    /**
     * 以自動遞增方式產生一個 id。
     */
    private static String autoIncrement() {
        return String.valueOf(autoIncrement++);
    }

    /**
     * 隨機選擇一個怪獸名稱。
     */
    private static String randomName() {
        String[] names = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "烈雀", "阿柏蛇", "皮卡丘",
                "穿山鼠", "尼多蘭", "皮皮", "六尾", "胖丁",
        };
        return names[(int) (Math.random() * names.length)];
    }
}

/**
 * 遊戲運作的主要邏輯（抽象方法用於實作使用者介面）
 */
abstract class Game {
    record ActionContext(Monster monster, Monster enemy, Monster.Action action, Monster.Action enemyAction) {
        ActionContext enemyContext() {
            return new ActionContext(enemy, monster, enemyAction, action);
        }
    }

    record ActionResult(ActionContext ctx, int hp, int enemyHp, int effectedHp) {
        /**
         * 將行動結果序列化為字串。
         */
        String serialize() {
            return String.format(
                    "%s,%s,%s,%s,%d,%d,%d",
                    ctx.monster.id, ctx.enemy.id, ctx.action, ctx.enemyAction, hp, enemyHp, effectedHp
            );
        }

        /**
         * 從字串反序列化出行動結果。
         */
        static ActionResult deserialize(String s, Map<String, Monster> monsters) {
            String[] tokens = s.split(",");
            return new ActionResult(
                    new ActionContext(
                            monsters.get(tokens[0]), monsters.get(tokens[1]),
                            Monster.Action.valueOf(tokens[2]), Monster.Action.valueOf(tokens[3])
                    ),
                    Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6])
            );
        }

        /**
         * 建立行動結果的文字描述。
         */
        @Override
        public String toString() {
            return switch (ctx.action) {
                case ATTACK -> {
                    String s = ctx.monster.name + " 攻擊 " + ctx.enemy.name + "，";
                    if (ctx.enemyAction == Monster.Action.DEFEND) {
                        s += "對方防守" + (effectedHp == 0 ? "成功" : "失敗，受到 " + effectedHp + " 點傷害");
                        yield s + "，剩餘血量 " + enemyHp;
                    }
                    s += effectedHp == 0 ? "但對方閃避了攻擊" : "造成 " + effectedHp + " 點傷害";
                    yield s + "，" + ctx.enemy.name + " 剩餘血量 " + enemyHp;
                }
                case HEAL -> ctx.monster.name + " 回復了 " + effectedHp + " 點血量，剩餘血量 " + hp;
                default -> null;
            };
        }
    }

    protected final Actor actor;
    protected final Actor opponent;
    protected int sleepTime = 0;
    private final List<ActionResult> actions = new ArrayList<>();

    protected Game(Actor actor, Actor opponent) {
        this.actor = actor;
        this.opponent = opponent;
    }

    protected Game(Actor actor, Actor opponent, List<ActionResult> actions) {
        this.actor = actor;
        this.opponent = opponent;
        this.actions.addAll(actions);
    }

    /**
     * 設定電腦選擇行動使用的時間。
     */
    void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    /**
     * 進行遊戲（這個方法 blocks 直到遊戲結束）。
     */
    abstract void start();

    /**
     * 取得遊戲結果的文字描述。
     */
    String summary() {
        if (actor.isAlive() && opponent.isAlive()) return "遊戲尚未結束！";
        return (actor.isAlive() ? actor.name : opponent.name) + "獲勝！";
    }

    /**
     * 將遊戲資訊序列化為字串。
     */
    String serialize() {
        StringBuilder sb = new StringBuilder();
        for (Actor actor : new Actor[]{actor, opponent}) {
            actor.monsters.forEach(m -> sb.append(m.serialize()).append(System.lineSeparator()));
            sb.append("=").append(actor.serialize()).append(System.lineSeparator());
        }
        for (ActionResult action : actions) {
            sb.append(action.serialize()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * 從字串反序列化出遊戲資訊。
     */
    static Game deserialize(GameLoader<Actor, List<ActionResult>> loader, String data) {
        Scanner scanner = new Scanner(data);
        Actor[] actors = new Actor[2];
        Map<String, Monster> monsters = new HashMap<>();
        List<ActionResult> actions = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            String line;
            while (!(line = scanner.nextLine()).startsWith("=")) {
                Monster monster = Monster.deserialize(line);
                monsters.put(monster.id, monster);
            }
            actors[i] = Actor.deserialize(line.substring(1), monsters);
        }
        while (scanner.hasNextLine()) {
            actions.add(ActionResult.deserialize(scanner.nextLine(), monsters));
        }
        return loader.load(actors[0], actors[1], actions);
    }

    /**
     * 遊戲主迴圈（進行對戰直到其中一方死亡）
     */
    protected void run() {
        while (actor.isAlive() && opponent.isAlive()) {
            Monster self = actor.chooseMonster();
            Monster enemy = opponent.chooseMonster();
            GameRound round = new GameRound(self, enemy);
            beforeRound(self, enemy);
            Monster.Action action = chooseAction(actor, round);
            if (action == null) continue;
            Monster.Action enemyAction = chooseAction(opponent, round.enemyRound());
            if (enemyAction == null) continue;
            ActionContext ctx = new ActionContext(self, enemy, action, enemyAction);
            ActionResult result = runAction(ctx), enemyResult = null;
            actions.add(result);
            if (enemy.isAlive()) {
                enemyResult = runAction(ctx.enemyContext());
                actions.add(enemyResult);
            }
            afterRound(result, enemyResult);
        }
    }

    /**
     * 開始一回合前的處理（更新介面上的資訊）
     */
    protected abstract void beforeRound(Monster self, Monster enemy);

    /**
     * 選擇怪獸行動。
     */
    protected abstract Monster.Action chooseAction(Actor actor, GameRound round);

    /**
     * 結束一回合後的處理（顯示該回合的結果）
     */
    protected abstract void afterRound(ActionResult result, ActionResult enemyResult);

    /**
     * 回復遊戲資料。
     */
    protected void restore(Game game) {
        actor.monsters.clear();
        actor.monsters.addAll(game.actor.monsters);
        opponent.monsters.clear();
        opponent.monsters.addAll(game.opponent.monsters);
        actions.clear();
        actions.addAll(game.actions);
    }

    /**
     * 一段時間，讓玩家可以看到雙方行動。
     */
    protected static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 執行一個行動，並回傳結果。
     */
    private static ActionResult runAction(ActionContext ctx) {
        return switch (ctx.action) {
            case ATTACK -> {
                int effectedHp = ctx.monster.attack(ctx.enemyAction);
                if (ctx.enemyAction == Monster.Action.DEFEND) effectedHp -= ctx.enemy.defend();
                effectedHp = Math.max(Math.min(effectedHp, ctx.enemy.hp), 0); // ∈ [0, enemy.hp]
                ctx.enemy.hp -= effectedHp;
                yield new ActionResult(ctx, ctx.monster.hp, ctx.enemy.hp, effectedHp);
            }
            case HEAL -> {
                int maxHeal = ctx.monster.maxHp - ctx.monster.hp; // hp + heal <= maxHp
                int effectedHp = Math.max(Math.min(ctx.monster.heal(), maxHeal), 0); // ∈ [0, maxHeal]
                ctx.monster.hp += effectedHp;
                yield new ActionResult(ctx, ctx.monster.hp, ctx.enemy.hp, effectedHp);
            }
            default -> new ActionResult(ctx, ctx.monster.hp, ctx.enemy.hp, 0);
        };
    }
}

/**
 * 遊戲載入介面
 */
@FunctionalInterface
interface GameLoader<Actor, List> {
    Game load(Actor actor, Actor opponent, List list);
}

record GameRound(Monster self, Monster enemy) {
    GameRound enemyRound() {
        return new GameRound(enemy, self);
    }
}

class ActionDecider {
    /**
     * 依據雙方怪獸狀態為各個行動評分，並選擇一個行動。
     */
    static Monster.Action chooseAction(GameRound round) {
        double effAtk = (round.enemy().defense / (double) round.self().attack);
        double effDef = (round.enemy().attack / (double) round.self().defense);
        double hp = round.self().hp / (double) round.self().maxHp;
        double chance = round.self().attack * effAtk / (double) round.enemy().hp;
        double risk = round.enemy().attack / Math.pow(effDef, -1) / (double) round.self().hp;
        double attack = chance * (1 - risk) * (1 + (1 - hp));
        double defense = (1 - chance) * risk * (1 + (1 - hp));
        double heal = (1 - chance) * (-2 * Math.pow(risk - 0.5, 2) + 0.5) * Math.pow(1 + (1 - hp), 2.5);
        return chooseAction(attack, defense, heal);
    }

    /**
     * 依據分數及機率選擇一個行動。
     */
    private static Monster.Action chooseAction(double attack, double defense, double heal) {
        Monster.Action[] actions = Monster.Action.values();
        double[] values = {attack, defense, heal};
        double choice = Math.random();
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] < values[j]) {
                    double temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                    Monster.Action tempAction = actions[i];
                    actions[i] = actions[j];
                    actions[j] = tempAction;
                }
            }
        }
        if (choice < 0.8) {
            return actions[0];
        } else if (choice < 0.9) {
            return actions[1];
        }
        return actions[2];
    }
}

/**
 * 提供 TextBasedGame 類別建立怪獸的方法。
 */
class MonsterReader {
    /**
     * 讀取使用者輸入並建立怪獸。
     */
    static Monster read(Scanner scanner, Actor actor, Monster.Type type) {
        String name = readName(scanner, actor, type);
        int attack = readInt(scanner, "請輸入 " + name + " 的攻擊力：");
        int defense = readInt(scanner, "請輸入 " + name + " 的防禦力：");
        int maxHp = readInt(scanner, "請輸入 " + name + " 的血量：");
        int level = confirmEvolve(scanner, name) ? 2 : 1;
        return MonsterBuilder.build(name, type, level, attack, defense, maxHp, maxHp);
    }

    /**
     * 從玩家輸入讀取怪獸名稱。
     */
    private static String readName(Scanner scanner, Actor actor, Monster.Type type) {
        System.out.print(actor.name + " 請輸入" + type.displayName() + "怪獸名稱：");
        String name = scanner.nextLine();
        if (!name.isBlank()) return name;
        Format.printError("怪獸名稱不可為空白！");
        return readName(scanner, actor, type);
    }

    /**
     * 從玩家輸入讀取一個整數。
     */
    private static int readInt(Scanner scanner, String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            Format.printError("請輸入整數！");
        }
        return readInt(scanner, prompt);
    }

    /**
     * 從玩家輸入確認是否要將怪獸進化。
     */
    private static boolean confirmEvolve(Scanner scanner, String name) {
        System.out.print("是否要進化 " + name + "（1. 是 2. 否）？");
        String input = scanner.nextLine();
        if (input.equals("1")) return true;
        if (input.equals("2")) return false;
        Format.printError("請輸入 1 或 2！");
        return confirmEvolve(scanner, name);
    }
}

/**
 * 遊戲模式
 */
enum GameMode {
    PvP(new Actor(1, "玩家1"), new Actor(2, "玩家2")),
    PvC(new Actor(1, "玩家"), new Actor(2, "電腦", true)),
    Auto(new Actor(1, "電腦1", true), new Actor(2, "電腦2", true));

    final Actor actor;
    final Actor opponent;

    GameMode(Actor actor, Actor opponent) {
        this.actor = actor;
        this.opponent = opponent;
    }
}

/**
 * 以 Scanner 實作的遊戲介面
 */
class TextBasedGame extends Game {
    private final Scanner scanner;

    TextBasedGame(Scanner scanner, GameMode mode, boolean autoGenerate) {
        super(mode.actor, mode.opponent);
        Format.printHeading("怪獸對戰遊戲");
        addMonsters(scanner, this.actor, autoGenerate);
        addMonsters(scanner, this.opponent, autoGenerate);
        this.scanner = scanner;
    }

    TextBasedGame(Scanner scanner, Actor actor, Actor opponent, List<ActionResult> actions) {
        super(actor, opponent, actions);
        Format.printHeading("怪獸對戰遊戲");
        this.scanner = scanner;
    }

    @Override
    void start() {
        System.out.println();
        Format.printHeading("遊戲開始");

        run();

        Format.printResult(summary());
    }

    @Override
    protected void beforeRound(Monster self, Monster enemy) {
        for (Actor actor : new Actor[]{actor, opponent}) {
            Format.printStatus(actor);
            actor.monsters.forEach(m -> Format.printStatus("\t" + m));
        }
    }

    @Override
    protected Monster.Action chooseAction(Actor actor, GameRound round) {
        Monster monster = round.self();
        if (actor.isPc) {
            Monster.Action action = ActionDecider.chooseAction(round);
            System.out.print(actor.name + " 的回合，選擇 " + monster.name + " 進行");
            sleep(sleepTime);
            System.out.println(action.displayName());
            return action;
        }
        System.out.printf("%s 的回合，請選擇 %s 的行動（1. 攻擊 2. 防守 3. 補血 4. 離開 5. 存檔 6. 讀檔）：", actor.name, monster.name);
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
            case "5" -> {
                System.out.println("--------------------");
                String fileName = readFileName(scanner);
                try (FileWriter fw = new FileWriter(fileName)) {
                    Base64.Encoder encoder = Base64.getEncoder();
                    fw.write(encoder.encodeToString(serialize().getBytes()));
                    Format.printResult("遊戲已儲存至 \"" + fileName + "\"！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                yield chooseAction(actor, round);
            }
            case "6" -> {
                System.out.println("--------------------");
                String fileName = readFileName(scanner);
                try (FileReader reader = new FileReader(fileName)) {
                    StringBuilder sb = new StringBuilder();
                    int c;
                    while ((c = reader.read()) != -1) {
                        sb.append((char) c);
                    }
                    Base64.Decoder decoder = Base64.getDecoder();
                    Game game = deserialize(scanner, new String(decoder.decode(sb.toString())));
                    restore(game);
                    Format.printResult("已從 \"" + fileName + "\" 載入遊戲！");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                yield null;
            }
            default -> {
                Format.printError("輸入錯誤，請重新輸入！");
                yield chooseAction(actor, round);
            }
        };
    }

    @Override
    protected void afterRound(ActionResult result, ActionResult enemyResult) {
        Format.printResult(result);
        Format.printResult(enemyResult);
        System.out.println("--------------------");
    }

    /**
     * 從字串反序列化出 TextBasedGame 物件。
     */
    TextBasedGame deserialize(Scanner scanner, String data) {
        return (TextBasedGame) deserialize(
                (actor, opponent, actions) -> new TextBasedGame(scanner, actor, opponent, actions),
                data
        );
    }

    /**
     * 為玩家建立怪獸。
     */
    private static void addMonsters(Scanner scanner, Actor actor, boolean autoGenerate) {
        if (actor.isPc) {
            actor.monsters.add(MonsterBuilder.random());
        } else {
            for (Monster.Type type : new Monster.Type[]{Monster.Type.WATER, Monster.Type.FIRE, Monster.Type.ELECTRIC}) {
                actor.monsters.add(autoGenerate ? MonsterBuilder.random() : MonsterReader.read(scanner, actor, type));
            }
        }
    }

    private static String readFileName(Scanner scanner) {
        System.out.print("請輸入檔案名稱（預設為 game.dat）：");
        String fileName = scanner.nextLine();
        if (fileName.isEmpty()) return "game.dat";
        return fileName;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new TextBasedGame(scanner, GameMode.PvC, false);// PvP|PvC|Auto, true: 自動產生怪獸
        // game.setSleepTime(3000);
        game.start();

        scanner.close();
    }
}
