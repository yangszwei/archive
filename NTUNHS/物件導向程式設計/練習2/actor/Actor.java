package actor;

public abstract class Actor {
    public String id, name;
    public int hp, type, level;

    public abstract Actor evolve();
}
