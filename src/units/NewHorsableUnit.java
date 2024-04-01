package units;

public class NewHorsableUnit extends Unit implements Horsable{
    public NewHorsableUnit (int health, int damage, int distanceOfAttack, int armor, double distanceOfWalk, int cost, String name, String allySymb, String enemySymb){

        this.health =health;
        this.damage = damage;
        this.distanceOfAttack = distanceOfAttack;
        this.distanceOfWalk = distanceOfWalk;
        this.armor = armor;
        this.cost = cost;
        this.name = name;
        this.allySymbol = allySymb;
        this.reward = 0;
        this.enemySymbol= enemySymb;
    }

}
