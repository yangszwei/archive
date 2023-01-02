package monster;

/**
 * 其他屬性怪獸類別的父類別，提供抽象方法的預設實作。
 */
public class BaseMonster extends Monster {
    protected BaseMonster(String id, String name, Type type, int level, int attack, int defense, int maxHp, int hp) {
        super(id, name, type, level, attack, defense, maxHp, hp);
    }

    /**
     * （預設不進行進化）
     */
    @Override
    public Monster evolve() {
        return this;
    }

    /**
     * （預設使用原始攻擊力）
     */
    @Override
    public int attack(Action enemyAction) {
        return attack;
    }

    /**
     * （預設使用原始防守力）
     */
    @Override
    public int defend() {
        return defense;
    }

    /**
     * （預設補血量為 20）
     */
    @Override
    public int heal() {
        return 20;
    }
}
