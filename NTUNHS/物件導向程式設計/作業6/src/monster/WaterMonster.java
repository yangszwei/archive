package monster;

/**
 * 水系怪獸
 */
public class WaterMonster extends BaseMonster {
    public WaterMonster(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.WATER, 1, hp, fullHp, attack, defense);
    }

    protected WaterMonster(String id, String name, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.WATER, level, hp, fullHp, attack, defense);
    }

    /**
     * 將此 WaterMonster 怪獸進化為 WaterMonster2。
     *
     * @return WaterMonster2 實體
     */
    @Override
    public Monster evolve() {
        return new WaterMonster2(id, name, hp, fullHp, attack, defense);
    }
}
