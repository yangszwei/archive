import actor.Actor;
import console.Format;
import game.Game;
import game.GameRound;
import monster.Monster;
import monster.MonsterBuilder;

import java.util.Scanner;

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
