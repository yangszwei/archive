package actor.archer;

public class BronzeArcher extends Archer {
    @Override
    public Archer evolve() {
        return new SilverArcher();
    }
}
