package buildings;

public class Smithy extends Building implements Upgrateble{
    public Smithy(){
        woodCost = 7;
        rockCost = 11;
        symbol = "\uD83C\uDFED\t";
        maxCount =1;

        name = "Smithy";
    }
}
