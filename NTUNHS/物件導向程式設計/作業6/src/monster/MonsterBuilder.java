package monster;

/**
 * MonsterBuilder 類別負責建立 Monster 實體。
 */
public class MonsterBuilder {
    /**
     * 提供 id 欄位自動遞增的功能。（不保證為唯一值）
     */
    private static int autoIncrement = 1;

    /**
     * 使用自動遞增功能產生一個 id，並建立一個 Monster 實體。
     *
     * @return Monster 實體
     */
    public static Monster build(String name, Monster.Type type, int level, int fullHp, int attack, int defense) {
        return build(String.valueOf(autoIncrement++), name, type, level, fullHp, fullHp, attack, defense);
    }

    /**
     * 建立一個完全隨機的 Monster 實體。
     *
     * @return Monster 實體
     */
    public static Monster random() {
        return random(null, null, 0, 0, 0, 0);
    }

    /**
     * 將未提供的參數設為隨機值，並建立一個 Monster 實體。
     *
     * @return Monster 實體
     */
    public static Monster random(String name, Monster.Type type, int level, int fullHp, int attack, int defense) {
        name = name == null ? getRandomName() : name;
        type = type == null ? Monster.Type.values()[(int) (Math.random() * Monster.Type.values().length)] : type;
        level = level == 0 ? (int) (Math.random() * 2) + 1 : level;
        fullHp = fullHp == 0 ? (int) (Math.random() * 120) + 60 : fullHp;
        attack = attack == 0 ? (int) (Math.random() * 16) + 8 : attack;
        defense = defense == 0 ? (int) (Math.random() * 16) + 8 : defense;
        return build(name, type, level, fullHp, attack, defense);
    }

    /**
     * 使用參數建立一個對應屬性及進化等級的 Monster 實體。
     *
     * @return Monster 實體
     */
    public static Monster build(String id, String name, Monster.Type type, int level, int hp, int fullHp, int attack, int defense) {
        return switch (level) {
            case 1 -> switch (type) {
                case FIRE -> new FireMonster(id, name, hp, fullHp, attack, defense);
                case WATER -> new WaterMonster(id, name, hp, fullHp, attack, defense);
                case ELECTRIC -> new ElectricMonster(id, name, hp, fullHp, attack, defense);
            };
            case 2 -> switch (type) {
                case FIRE -> new FireMonster2(id, name, hp, fullHp, attack, defense);
                case WATER -> new WaterMonster2(id, name, hp, fullHp, attack, defense);
                case ELECTRIC -> new ElectricMonster2(id, name, hp, fullHp, attack, defense);
            };
            default -> throw new IllegalArgumentException("無效的等級：" + level);
        };
    }

    /**
     * 產生一個隨機的怪獸名稱。
     *
     * @return 怪獸名稱
     */
    private static String getRandomName() {
        String[] names = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "烈雀", "阿柏蛇", "皮卡丘",
                "穿山鼠", "尼多蘭", "皮皮", "六尾", "胖丁",
        };
        return names[(int) (Math.random() * names.length)];
    }
}
