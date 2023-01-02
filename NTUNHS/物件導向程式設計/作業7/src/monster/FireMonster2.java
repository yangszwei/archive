package monster;

/**
 * 進化後的火系怪獸
 */
public class FireMonster2 extends FireMonster {
    public FireMonster2(String id, String name, int attack, int defense, int maxHp, int hp) {
        super(id, name, 2, attack, defense, maxHp, hp);
    }

    /**
     * （此類別為火系怪獸的最終進化形態，無法再進化）
     */
    @Override
    public Monster evolve() {
        return this;
    }
}
