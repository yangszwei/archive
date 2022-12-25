package monster;

/**
 * 進化後的火系怪獸
 */
public class FireMonster2 extends FireMonster {
    public FireMonster2(String id, String name, int hp, int fullHp, int attack, int defense) {
        super(id, name, 2, hp * 2, fullHp * 2, attack * 2, defense * 2);
    }

    /**
     * （此類別為火系怪獸的最終進化形態，無法再進化）
     */
    @Override
    public Monster evolve() {
        return this;
    }
}
