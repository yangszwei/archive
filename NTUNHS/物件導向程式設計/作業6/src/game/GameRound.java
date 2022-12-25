package game;

import monster.Monster;

/**
 * 一回合對戰中的一方的行動及結果。
 */
public class GameRound {
    public final Monster monster, enemy;
    public final Monster.Action action, enemyAction;
    int hp, enemyHp, effectedHp;
    boolean isDone = false;

    public GameRound(Monster monster, Monster enemy, Monster.Action action, Monster.Action enemyAction) {
        this.monster = monster;
        this.enemy = enemy;
        this.action = action;
        this.enemyAction = enemyAction;
    }

    /**
     * 執行 actor 於此回合對戰中的行動，並剩餘血量及實際影響血量存入 hp, enemyHp, effectedHp。
     * 其中，當 action == DEFEND 時，因不會主動對雙方血量造成影響，不執行任何動作。
     */
    public void fight() {
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
