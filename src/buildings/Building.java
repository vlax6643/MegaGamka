package buildings;

import visuals.Fieldable;

import java.io.Serializable;

public abstract class Building implements Fieldable, Serializable {

    protected int level = 1;
    protected String name;
    protected int woodCost;
    protected int rockCost;

    protected String symbol;
    protected int maxCount;
    protected int totalCount = 0;
    public void buff(){

    }
    public String getSymbol(){
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRockCost() {
        return rockCost;
    }

    public int getWoodCost() {
        return woodCost;
    }
    public void upgrade(){

    }
}
