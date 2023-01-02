package monster;

/**
 * 水系怪獸
 */
public class WaterMonster extends BaseMonster {
    public WaterMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected WaterMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.WATER, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 WaterMonster 怪獸進化為 WaterMonster2。
     *
     * @return WaterMonster2 實體
     */
    @Override
    public Monster evolve() {
        return new WaterMonster2(id, name, attack * 2, defense * 2, maxHp * 2, hp * 2);
    }
}
