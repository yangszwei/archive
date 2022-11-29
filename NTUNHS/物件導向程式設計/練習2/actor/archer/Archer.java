package actor.archer;

import action.IAttacker;
import action.IDefender;
import actor.Actor;
import monster.Monster;

public abstract class Archer extends Actor implements IAttacker, IDefender {
    @Override
    public void attack(Monster monster) {
    }

    @Override
    public void magicAttack(Monster monster) {
    }

    @Override
    public void defend(Monster monster) {
    }

    @Override
    public void magicDefend(Monster monster) {
    }

    @Override
    public abstract Archer evolve();
}
