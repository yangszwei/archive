package actor.magician;

import action.IAttacker;
import action.ITrader;
import actor.Actor;
import item.Item;
import monster.Monster;

public abstract class Magician extends Actor implements IAttacker, ITrader {
    @Override
    public void attack(Monster monster) {
    }

    @Override
    public void magicAttack(Monster monster) {
    }

    @Override
    public void trade(Item item) {
    }

    public abstract Magician evolve();
}
