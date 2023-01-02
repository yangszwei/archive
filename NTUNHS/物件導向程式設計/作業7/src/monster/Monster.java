package monster;

/**
 * 怪獸類別，定義怪獸的基本屬性及行為。
 */
public abstract class Monster {
    /**
     * 怪獸行動
     */
    public enum Action {
        ATTACK("攻擊"), DEFEND("防禦"), HEAL("補血");

        private final String displayName;

        Action(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 取得顯示名稱。
         */
        public String displayName() {
            return displayName;
        }
    }

    /**
     * 怪獸屬性
     */
    public enum Type {
        FIRE("火系"), WATER("水系"), ELECTRIC("雷系");

        private final String displayName;

        Type(String displayName) {
            this.displayName = displayName;
        }

        /**
         * 取得顯示名稱。
         */
        public String displayName() {
            return displayName;
        }
    }

    public final String id;
    public final String name;
    public final Type type;
    public final int level;
    public final int attack;
    public final int defense;
    public final int maxHp;
    public int hp;

    protected Monster(String id, String name, Type type, int level, int attack, int defense, int maxHp, int hp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.level = level;
        this.attack = attack;
        this.defense = defense;
        this.maxHp = maxHp;
        this.hp = hp;
    }

    /**
     * 建立進化後的怪獸。
     */
    public abstract Monster evolve();

    /**
     * 檢查怪獸是否還活著。
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
     * 將怪獸資訊序列化為字串。
     */
    public String serialize() {
        return String.format(
                "%s,%s,%s,%d,%d,%d,%d,%d",
                id, name, type, level, attack, defense, maxHp, hp
        );
    }

    /**
     * 從字串反序列化出怪獸資訊。
     */
    public static Monster deserialize(String data) {
        String[] parts = data.split(",");
        return MonsterBuilder.recover(
                parts[0], parts[1],
                Monster.Type.valueOf(parts[2]), Integer.parseInt(parts[3]),
                Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
                Integer.parseInt(parts[6]), Integer.parseInt(parts[7])
        );
    }

    /**
     * 取得怪獸狀態的文字敘述。
     */
    @Override
    public String toString() {
        return String.format(
                "%s. %s ［%s Lv：%d, 攻擊：%d, 防禦：%d, HP：%d/%d］",
                id, name, type.displayName(), level, attack, defense, hp, maxHp
        );
    }
}
