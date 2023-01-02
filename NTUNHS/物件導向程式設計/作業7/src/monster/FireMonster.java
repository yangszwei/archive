package monster;

/**
 * 火系怪獸
 */
public class FireMonster extends BaseMonster {
    public FireMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected FireMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.FIRE, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 FireMonster 怪獸進化為 FireMonster2。
     */
    @Override
    public Monster evolve() {
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
    public int attack(Action enemyAction) {
        double random = Math.random();
        int amount = random < .4 ? attack : random < .8 ? attack / 2 : 0;
        if (enemyAction == Action.DEFEND) amount /= 2;
        return amount;
    }
}
