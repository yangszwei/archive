import java.util.Scanner;

enum Action {
    ATTACK, DEFEND, HEAL
}

/**
 * 1. 設計Pokemon類別
 */
class Pokemon {
    int id;
    String name;
    int attack, atkBuff, defence, blood;

    public Pokemon(int id, String name, int attack, int defence, int blood) {
        this.id = id;
        this.name = name;
        this.attack = attack;
        this.defence = defence;
        this.blood = blood;
    }

    /**
     * 執行攻擊
     *
     * @return 敵人失血值（用於建立結果）
     */
    public int attack(Pokemon enemy, Action enemyAction) {
        int damage = getAttack();
        if (enemyAction == Action.DEFEND) {
            damage = enemy.defend(damage);
        }
        atkBuff = 0;
        return enemy.damage(damage);
    }

    /**
     * 計算失血值，被攻擊時呼叫
     *
     * @return 失血值（用於建立結果）
     */
    public int defend(int damage) {
        return Math.max(damage - getDefence(damage), 0);
    }

    /**
     * 1.5 防守之後，下一次攻擊有50%機率攻擊力變成2倍
     *
     * @return 攻擊力提升值
     */
    public int defend() {
        if (Math.random() < .5) {
            return atkBuff = attack;
        }
        return 0;
    }

    /**
     * 1.4 補血: 增加血量20
     * 1.6 當血量剩下20以內，使用Overload的方式補血2倍
     *
     * @return 增加的血量
     */
    public int heal() {
        int heal = getHeal();
        blood += heal;
        return heal;
    }

    /**
     * 執行被攻擊
     *
     * @return 失血值
     */
    public int damage(int damage) {
        damage = Math.min(blood, damage);
        blood -= damage;
        return damage;
    }

    /**
     * 檢查 Pokemon 是否還活著
     *
     * @return 是否還活著
     */
    public boolean isAlive() {
        return blood > 0;
    }

    /**
     * 1.2 攻擊演算法
     *
     * @return 攻擊值
     */
    public int getAttack() {
        int attack = this.attack + atkBuff;
        double random = Math.random();
        if (random < .3) {
            return 0;
        } else if (random < .5) {
            return attack * 2;
        } else if (random < .6) {
            return attack / 2;
        }
        return attack;
    }

    /**
     * 計算防禦值
     * (1.3 防守: 提供有50%機率不被打中)
     *
     * @return 防禦值 (不被打中時與失血值相同，以抵銷失血值)
     */
    public int getDefence(int damage) {
        return Math.random() < .5 ? damage : 0;
    }

    /**
     * 計算補血值
     * (1.4 增加血量20)
     * (1.6 當血量剩下20以內，使用Overload的方式補血2倍)
     *
     * @return 補血值
     */
    public int getHeal() {
        return blood < 20 ? 40 : 20;
    }

    @Override
    public String toString() {
        return String.format("%s 攻擊 %d / 防禦 %d / 血量 %d", name, attack, defence, blood);
    }

    /**
     * 產生一個隨機的 Pokemon 實體
     */
    public static Pokemon random() {
        final String[] NAMES = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "阿柏蛇", "皮卡丘", "穿山鼠"
        };
        return new Pokemon(
                (int) (Math.random() * 100),
                NAMES[(int) (Math.random() * NAMES.length)],
                Math.round(30 + (float) Math.random() * 40),
                Math.round(30 + (float) Math.random() * 40),
                Math.round(150 + (float) Math.random() * 150)
        );
    }
}

/**
 * 一回合的戰鬥及結果
 */
class Round {
    Pokemon a, b;
    Action aAction, bAction;
    Result aResult, bResult;

    public Round(Pokemon a, Action aAction, Pokemon b, Action bAction) {
        this.a = a;
        this.aAction = aAction;
        this.b = b;
        this.bAction = bAction;
    }

    /**
     * 執行一回合的戰鬥，並建立 Result 實體
     */
    public void fight() {
        aResult = new Result(a, aAction, b, bAction);
        bResult = new Result(b, bAction, a, aAction);
        switch (aAction) {
            case ATTACK -> aResult.damage = a.attack(b, bAction);
            case DEFEND -> aResult.effect = a.defend();
            case HEAL -> aResult.heal = a.heal();
        }
        aResult.snapshot();
        switch (bAction) {
            case ATTACK -> bResult.damage = b.attack(a, aAction);
            case DEFEND -> bResult.effect = b.defend();
            case HEAL -> bResult.heal = b.heal();
        }
        bResult.snapshot();
    }
}

/**
 * 一次動作的結果 (攻擊、防守、補血)
 */
class Result {
    Pokemon player, enemy;
    Action playerAction, enemyAction;
    int damage, heal, effect;
    int playerBlood, enemyBlood;

    public Result(Pokemon player, Action playerAction, Pokemon enemy, Action enemyAction) {
        this.player = player;
        this.playerAction = playerAction;
        this.enemy = enemy;
        this.enemyAction = enemyAction;
        this.effect = player.atkBuff;
    }

    /**
     * 於執行動作後．儲存當下的狀態 (血量、其他效果)
     */
    public void snapshot() {
        playerBlood = player.blood;
        enemyBlood = enemy.blood;
        effect = player.atkBuff - effect;
    }

    /**
     * 將結果轉換成字串
     * (2.2 將結果與血量以文字方式顯示)
     *
     * @return 要顯示的文字
     */
    @Override
    public String toString() {
        return switch (playerAction) {
            case ATTACK -> {
                String result = String.format("| %s 攻擊 %s，", player.name, enemy.name);
                if (enemyAction == Action.DEFEND) {
                    if (damage == 0) {
                        result += String.format("但 %s 防禦成功，", enemy.name);
                    } else {
                        result += String.format("%s 防禦失敗，受到 %d 點傷害，", enemy.name, damage);
                    }
                } else {
                    if (damage == 0) {
                        result += String.format("但被 %s 閃避，", enemy.name);
                    } else {
                        result += String.format("造成 %d 點傷害，", damage);
                    }
                    result += String.format("%s ", enemy.name);
                }
                result += String.format("血量剩下 %d%n", enemyBlood);
                yield result;
            }
            case DEFEND -> {
                if (effect > 0) {
                    yield String.format("| %s 因防禦效果，攻擊力提升至兩倍%n", player.name);
                }
                yield "";
            }
            case HEAL -> {
                String result = String.format("| %s ", player.name);
                if (heal == 40) result += "使用 Overload ";
                result += String.format("回復了 %d 點血量，血量剩下 %d%n", heal, playerBlood);
                yield result;
            }
        };
    }
}

class Battle {
    private final Scanner scanner;
    private final Pokemon a, b;

    public Battle(Scanner scanner, Pokemon a, Pokemon b) {
        this.scanner = scanner;
        this.a = a;
        this.b = b;
    }

    /**
     * 開始戰鬥
     */
    public void start() {
        System.out.printf("[寶可夢對戰遊戲] %n%s%n%s%n%n對戰開始：%n", a, b);

        /* 2.3 遊戲規則直到其中一方的寶可夢死掉為止 */
        while (a.isAlive() && b.isAlive()) {
            Round round = new Round(a, getAction(a), b, getAction(b));
            /* 2.2 雙方輸入選項完畢後執行，並將結果與血量以文字方式顯示 */
            round.fight();
            System.out.print(round.aResult);
            System.out.print(round.bResult);
        }

        System.out.printf("戰鬥結束，%s獲勝！%n", a.isAlive() ? a.name : b.name);
    }

    /**
     * 提供玩家輸入選項
     * (2.1 提供兩位玩家分別輸入選項: (1)攻擊 (2)防守 (3)補血)
     *
     * @return 玩家選擇的選項
     */
    private Action getAction(Pokemon pokemon) {
        while (true) {
            System.out.printf("%s的回合，請輸入選項 ([1]攻擊 [2]防守 [3]補血)：", pokemon.name);
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    return Action.ATTACK;
                case "2":
                    return Action.DEFEND;
                case "3":
                    return Action.HEAL;
            }
            System.out.println("輸入錯誤，請重新輸入");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Battle battle = new Battle(scanner, Pokemon.random(), Pokemon.random());
        battle.start();

        scanner.close();
    }
}
