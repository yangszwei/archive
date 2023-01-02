import actor.Actor;
import console.Format;
import game.Game;
import monster.Monster;
import monster.MonsterBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

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
    PvC(new Actor(1, "玩家"), new Actor(2, "電腦", true));

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
    public void start() {
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
    protected Monster.Action chooseAction(Actor actor, Monster monster) {
        if (actor.isPc) return super.chooseAction(actor, monster);
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
                yield chooseAction(actor, monster);
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
                yield chooseAction(actor, monster);
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

        Game game = new TextBasedGame(scanner, GameMode.PvC, !false); // true: 自動產生怪獸
        game.start();

        scanner.close();
    }
}
