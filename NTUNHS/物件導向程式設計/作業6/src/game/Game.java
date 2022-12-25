package game;

import actor.Actor;
import monster.Monster;

import java.util.ArrayList;
import java.util.List;

/**
 * 此類別負責遊戲運作的邏輯，抽象方法由「遊戲介面」類別實作。
 */
public abstract class Game {
    protected final Actor a, b;
    protected final List<GameRound> rounds = new ArrayList<>(); // 作業 7 用

    public Game(Actor a, Actor b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 進行遊戲（這個方法 blocks 直到遊戲結束）。
     */
    public abstract void start();

    /**
     * 取得遊戲狀態的文字敘述。
     *
     * @return 遊戲狀態的文字敘述
     */
    public String summary() {
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
