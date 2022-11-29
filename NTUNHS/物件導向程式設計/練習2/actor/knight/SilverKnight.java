package actor.knight;

public class SilverKnight extends Knight {
    @Override
    public Knight evolve() {
        return new GoldenKnight();
    }
}
