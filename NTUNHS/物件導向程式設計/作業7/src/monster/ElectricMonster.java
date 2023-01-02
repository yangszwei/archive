package monster;

/**
 * 雷系怪獸
 */
public class ElectricMonster extends BaseMonster {
    public ElectricMonster(String id, String name, int attack, int defense, int maxHp, int hp) {
        this(id, name, 1, attack, defense, maxHp, hp);
    }

    protected ElectricMonster(String id, String name, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, Type.ELECTRIC, level, attack, defense, maxHp, hp);
    }

    /**
     * 將此 ElectricMonster 怪獸進化為 ElectricMonster2。
     */
    @Override
    public Monster evolve() {
        return new ElectricMonster2(id, name, attack * 2, defense * 2, maxHp * 2, hp * 2);
    }

    /**
     * 防守力加成 1.5 倍
     */
    @Override
    public int defend() {
        return (int) (defense * 1.5);
    }
}
