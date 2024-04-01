package buildings;

public class Club extends Building implements Upgrateble{
    public Club(){
        woodCost = 9;
        rockCost = 8;
        symbol = "\uD83C\uDFDB\t";
        maxCount = 1;

        name = "Club";
    }
}
