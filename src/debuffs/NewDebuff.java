package debuffs;

import java.io.Serializable;

public class NewDebuff extends Debuffs implements Serializable {
    public NewDebuff(double debForWalkable, double debForBowable, double debForHorsable, String symbol, String name){
        this.debForHorsable = debForHorsable;
        this.debForWalkable= debForWalkable;
        this.debForBowable=debForBowable;
        this.symbol=symbol;
        this.name = name;
    }
}
