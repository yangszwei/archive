package game;

import monster.Monster;

public record GameRound(Monster self, Monster enemy) {
    public GameRound enemyRound() {
        return new GameRound(enemy, self);
    }
}
