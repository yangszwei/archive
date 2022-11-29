package actor.magician;

public class BronzeMagician extends Magician {
    public Magician evolve() {
        return new SilverMagician();
    }
}
