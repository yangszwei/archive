package monster;

/**
 * 進化後的雷系怪獸
 */
public class ElectricMonster2 extends ElectricMonster {
    public ElectricMonster2(String id, String name, int attack, int defense, int maxHp, int hp) {
        super(id, name, 2, attack, defense, maxHp, hp);
    }

    /**
     * （此類別為雷系怪獸的最終進化形態，無法再進化）
     */
    @Override
    public Monster evolve() {
        return this;
    }
}
