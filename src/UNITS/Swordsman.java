package UNITS;

import UNITS.Unit;
import visuals.Fieldable;

public class Swordsman extends Unit  implements Walkable, Fieldable {
    public Swordsman() {
        health = 50;
        damage = 5;
        distanceOfAttack = 1;
        armor = 8;
        distanceOfWalk = 3;
        cost = 10;
        reward = 5;
        allySymbol = "\u001B[32m"+"W\t"+"\u001B[0m";
        enemySymbol = "\u001B[31m"+"W\t"+"\u001B[0m";
    }

}
