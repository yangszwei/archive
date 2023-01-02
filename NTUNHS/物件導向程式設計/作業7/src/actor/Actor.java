package actor;

import monster.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 玩家類別
 */
public class Actor {
    public final String id;
    public final String name;
    public final boolean isPc;
    public final List<Monster> monsters = new ArrayList<>();

    public Actor(int id, String name) {
        this(String.valueOf(id), name, false);
    }

    public Actor(int id, String name, boolean isPc) {
        this(String.valueOf(id), name, isPc);
    }

    public Actor(String id, String name, boolean isPc) {
        this.id = id;
        this.name = name;
        this.isPc = isPc;
    }

    /**
     * 從玩家的怪獸中選出一隻怪獸。
     */
    public Monster chooseMonster() {
        return monsters.get((int) (Math.random() * monsters.size()));
    }

    /**
     * 確認此玩家的所有怪獸是否都還活著。
     */
    public boolean isAlive() {
        return monsters.stream().allMatch(Monster::isAlive);
    }

    /**
     * 將玩家資訊序列化成字串。
     */
    public String serialize() {
        String[] ids = monsters.stream().map(m -> m.id).toArray(String[]::new);
        return String.format("%s,%s,%s,%s", id, name, isPc, String.join(",", ids));
    }

    /**
     * 從字串及怪獸資料反序列化出玩家資訊。
     */
    public static Actor deserialize(String data, Map<String, Monster> monsters) {
        String[] tokens = data.split(",");
        Actor actor = new Actor(tokens[0], tokens[1], Boolean.parseBoolean(tokens[2]));
        for (int i = 3; i < tokens.length; i++) {
            actor.monsters.add(monsters.get(tokens[i]));
        }
        return actor;
    }

    /**
     * 建立玩家資訊字串。
     */
    @Override
    public String toString() {
        return String.format("[%s] %s", id, name);
    }
}
