package game;

import actor.Actor;
import monster.Monster;

import java.util.*;

/**
 * 遊戲運作的主要邏輯（抽象方法用於實作使用者介面）
 */
public abstract class Game {
    public record ActionContext(Monster monster, Monster enemy, Monster.Action action, Monster.Action enemyAction) {
        ActionContext enemyContext() {
            return new ActionContext(enemy, monster, enemyAction, action);
        }
    }

    public record ActionResult(ActionContext ctx, int hp, int enemyHp, int effectedHp) {
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
     * 進行遊戲（這個方法 blocks 直到遊戲結束）。
     */
    public abstract void start();

    /**
     * 取得遊戲結果的文字描述。
     */
    public String summary() {
        if (actor.isAlive() && opponent.isAlive()) return "遊戲尚未結束！";
        return (actor.isAlive() ? actor.name : opponent.name) + "獲勝！";
    }

    /**
     * 將遊戲資訊序列化為字串。
     */
    public String serialize() {
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
    public static Game deserialize(GameLoader<Actor, List<ActionResult>> loader, String data) {
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
            beforeRound(self, enemy);
            Monster.Action action = chooseAction(actor, self);
            if (action == null) continue;
            Monster.Action enemyAction = chooseAction(opponent, enemy);
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
    protected Monster.Action chooseAction(Actor actor, Monster monster) {
        return Monster.Action.values()[(int) (Math.random() * 3)];
    }

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
