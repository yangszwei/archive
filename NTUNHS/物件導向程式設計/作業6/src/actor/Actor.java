package actor;

import monster.Monster;

import java.util.ArrayList;
import java.util.List;

/**
 * 玩家類別
 */
public class Actor {
    public final String name;
    public final List<Monster> monsters = new ArrayList<>();
    public final boolean isPc;

    public Actor(String name) {
        this.name = name;
        this.isPc = false;
    }

    public Actor(String name, boolean isPc) {
        this.name = name;
        this.isPc = isPc;
    }

    /**
     * 確認此玩家的所有怪獸是否都還活著。
     *
     * @return true: 所有怪獸都還活著, false: 有怪獸已經死亡
     */
    public boolean isAlive() {
        return monsters.stream().allMatch(Monster::isAlive);
    }

    /**
     * 從玩家的所有怪獸中，隨機選出一隻怪獸。
     *
     * @return Monster 實體
     */
    public Monster getRandomMonster() {
        if (monsters.isEmpty()) return null;
        return monsters.get((int) (Math.random() * monsters.size()));
    }

    /**
     * 取得此玩家及其所有怪獸的狀態。
     *
     * @return 狀態文字描述
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        for (Monster monster : monsters) {
            sb.append(System.lineSeparator()).append('\t').append(monster);
        }
        return sb.toString();
    }
}
