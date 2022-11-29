package actor.knight;

import action.IAttacker;
import action.IMover;
import actor.Actor;
import monster.Monster;

public abstract class Knight extends Actor implements IAttacker, IMover {
    @Override
    public void attack(Monster monster) {
    }

    @Override
    public void magicAttack(Monster monster) {
    }

    @Override
    public void move(int coord) {
    }

    public abstract Knight evolve();
}
