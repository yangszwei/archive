package monster;

public class MonsterBuilder {
    private static int autoIncrement = 1;

    /**
     * 以自動遞增方式產生一個 id，並建立一個 Monster 實體。
     */
    public static Monster build(String name, Monster.Type type, int level, int attack, int defense, int maxHp, int hp) {
        return build(autoIncrement(), name, type, level, attack, defense, maxHp, hp);
    }

    /**
     * 使用參數建立一個對應屬性及進化等級的 Monster 實體。
     */
    public static Monster build(String id, String name, Monster.Type type, int level, int attack, int defense, int maxHp, int hp) {
        Monster monster = switch (type) {
            case FIRE -> new FireMonster(id, name, attack, defense, maxHp, hp);
            case WATER -> new WaterMonster(id, name, attack, defense, maxHp, hp);
            case ELECTRIC -> new ElectricMonster(id, name, attack, defense, maxHp, hp);
        };
        while (level-- > 1) {
            monster = monster.evolve();
        }
        return monster;
    }

    /**
     * 建立一個完全隨機的怪獸。
     */
    public static Monster random() {
        return random(null, null, 0, 0, 0, 0);
    }

    /**
     * 將未提供的參數填入隨機值，並建立一個對應的怪獸。
     */
    public static Monster random(String name, Monster.Type type, int level, int attack, int defense, int maxHp) {
        int base = (int) (Math.random() * 30) + 30;
        name = name == null ? randomName() : name;
        type = type == null ? Monster.Type.values()[(int) (Math.random() * Monster.Type.values().length)] : type;
        level = level <= 0 ? (int) (Math.random() * 2) + 1 : level;
        attack = attack <= 0 ? (int) (Math.random() * 30) + base : attack;
        defense = defense <= 0 ? (int) (Math.random() * 30) + base : defense;
        maxHp = maxHp <= 0 ? (int) (Math.random() * 60) + (base * 2) : maxHp;
        return build(name, type, level, attack, defense, maxHp, maxHp);
    }

    /**
     * 以自動遞增方式產生一個 id。
     */
    private static String autoIncrement() {
        return String.valueOf(autoIncrement++);
    }

    /**
     * 隨機選擇一個怪獸名稱。
     */
    private static String randomName() {
        String[] names = {
                "妙蛙種子", "小火龍", "傑尼龜", "綠毛蟲", "獨角蟲",
                "波波", "小拉達", "烈雀", "阿柏蛇", "皮卡丘",
                "穿山鼠", "尼多蘭", "皮皮", "六尾", "胖丁",
        };
        return names[(int) (Math.random() * names.length)];
    }
}
