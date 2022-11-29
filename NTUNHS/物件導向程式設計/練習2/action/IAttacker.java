package action;

import monster.Monster;

public interface IAttacker {
    void attack(Monster monster);
    void magicAttack(Monster monster);
}
