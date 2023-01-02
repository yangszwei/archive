package intelligence;

import game.GameRound;
import monster.Monster;

public class ActionDecider {
    /**
     * 依據雙方怪獸狀態為各個行動評分，並選擇一個行動。
     */
    public static Monster.Action chooseAction(GameRound round) {
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
