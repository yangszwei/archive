package actor.archer;

public class SilverArcher extends Archer {
    @Override
    public Archer evolve() {
        return new GoldenArcher();
    }
}
