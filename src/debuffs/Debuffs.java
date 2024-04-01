package debuffs;

import visuals.Fieldable;

import java.io.Serializable;

public abstract class Debuffs implements Fieldable, Cloneable, Serializable {
protected String symbol;
protected double debForBowable;
protected double debForHorsable;
protected double debForWalkable;
protected String name;
protected int x, y;


    @Override
    public String getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double getDebForBowable() {
        return debForBowable;
    }

    public double getDebForHorsable() {
        return debForHorsable;
    }

    public double getDebForWalkable() {
        return debForWalkable;
    }

    public void setDebForBowable(double debForBowable) {
        this.debForBowable = debForBowable;
    }

    public void setDebForHorsable(double debForHorsable) {
        this.debForHorsable = debForHorsable;
    }

    public void setDebForWalkable(double debForWalkable) {
        this.debForWalkable = debForWalkable;
    }

    public  Debuffs clone() {
        try {
            return (Debuffs) super.clone();
        } catch (CloneNotSupportedException e) {

            throw new AssertionError();
        }
    }

}
