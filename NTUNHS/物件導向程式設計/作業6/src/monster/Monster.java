package monster;

/**
 * 怪獸類別，定義怪獸的基本屬性及行為。
 */
public abstract class Monster {
    /**
     * 怪獸行動
     */
    public enum Action {
        ATTACK, DEFEND, HEAL;

        /**
         * 取得行動中文名稱。
         *
         * @return 中文名稱
         */
        public String getName() {
            return switch (this) {
                case ATTACK -> "攻擊";
                case DEFEND -> "防守";
                case HEAL -> "補血";
            };
        }
    }

    /**
     * 怪獸屬性
     */
    public enum Type {
        FIRE, WATER, ELECTRIC;

        /**
         * 取得屬性中文名稱。
         *
         * @return 中文名稱
         */
        public String getName() {
            return switch (this) {
                case FIRE -> "火系";
                case WATER -> "水系";
                case ELECTRIC -> "雷系";
            };
        }
    }

    public final String id, name;
    public final Type type;
    public final int level, fullHp, attack, defense;
    public int hp;

    public Monster(String id, String name, Type type, int level, int hp, int fullHp, int attack, int defense) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.hp = hp;
        this.fullHp = fullHp;
        this.attack = attack;
        this.defense = defense;
    }

    /**
     * 建立進化後的怪獸。
     *
     * @return 進化後的怪獸
     */
    public abstract Monster evolve();

    /**
     * 檢查怪獸是否還活著。
     *
     * @return true: 活著, false: 死亡
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * 攻擊力演算法
     *
     * @param enemyAction 敵人行動
     * @return 攻擊力
     */
    public abstract int attack(Action enemyAction);

    /**
     * 防守力演算法
     *
     * @return 防守力
     */
    public abstract int defend();

    /**
     * 補血量演算法
     *
     * @return 補血量
     */
    public abstract int heal();

    /**
     * 取得怪獸狀態的文字敘述。
     *
     * @return 怪獸狀態的文字敘述
     */
    @Override
    public String toString() {
        return String.format("%s (%d) [%s] HP：%d/%d 攻擊：%d 防禦：%d", name, level, type.getName(), hp, fullHp, attack, defense);
    }
}
