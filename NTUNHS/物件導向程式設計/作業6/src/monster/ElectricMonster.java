package monster;

/**
 * 雷系怪獸
 */
public class ElectricMonster extends BaseMonster {
    public ElectricMonster(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.ELECTRIC, 1, hp, fullHp, attack, defense);
    }

    protected ElectricMonster(String id, String name, int level, int hp, int fullHp, int attack, int defense) {
        super(id, name, Type.ELECTRIC, level, hp, fullHp, attack, defense);
    }

    /**
     * 將此 ElectricMonster 怪獸進化為 ElectricMonster2。
     *
     * @return ElectricMonster2 實體
     */
    @Override
    public Monster evolve() {
        return new ElectricMonster2(id, name, hp, fullHp, attack, defense);
    }

    /**
     * 防守力加成 1.5 倍
     */
    @Override
    public int defend() {
        return (int) (defense * 1.5);
    }
}
