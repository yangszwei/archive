package monster;

/**
 * 進化後的雷系怪獸
 */
public class ElectricMonster2 extends ElectricMonster {
    public ElectricMonster2(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, 2, hp * 2, fullHp * 2, attack * 2, defense * 2);
    }

    /**
     * （此類別為雷系怪獸的最終進化形態，無法再進化）
     */
    @Override
    public Monster evolve() {
        return this;
    }
}
