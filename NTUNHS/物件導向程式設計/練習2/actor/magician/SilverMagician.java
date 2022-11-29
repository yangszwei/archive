package actor.magician;

public class SilverMagician extends Magician {
    @Override
    public Magician evolve() {
        return new GoldenMagician();
    }
}
