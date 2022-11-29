package actor.knight;

import monster.Monster;

public class BronzeKnight extends Knight {
    @Override
    public Knight evolve() {
        return new SilverKnight();
    }
}
