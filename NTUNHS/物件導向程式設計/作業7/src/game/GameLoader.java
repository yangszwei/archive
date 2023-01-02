package game;

/**
 * 遊戲載入介面
 */
@FunctionalInterface
public interface GameLoader<Actor, List> {
    Game load(Actor actor, Actor opponent, List list);
}
