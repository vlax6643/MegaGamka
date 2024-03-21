package visuals;

import javax.swing.plaf.synth.SynthOptionPaneUI;

public class Field {
    private int sizeX;
    private int sizeY;

public Fieldable[][] field;

public Field (int sizeX, int sizeY){
    this.sizeX=sizeX;
    this.sizeY=sizeY;
    field = new Fieldable[sizeY][sizeX];
}

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setFieldable(int y, int x, Fieldable img){
    field[y][x]=img;
    }

    public Fieldable getFieldable (int y, int x){
    return field[y][x];
    }



    public void showField(){
        System.out.println();
        System.out.print(" \t");
        for (int i = 0; i < sizeX; i++) {
            System.out.print("\u001B[35m"+i+"\t"+"\u001B[0m");
        }
        System.out.print ("\u001B[35m"+"X"+"\u21D2"+"\u001B[0m");

        for (int i = 0; i < sizeY; i++) {

            System.out.println();
            System.out.print ("\u001B[35m"+i+"\t"+"\u001B[0m");
            for (int j = 0; j < sizeX; j++) {

                System.out.print(field[i][j].getSymbol());
            }
        }
        System.out.println();
        System.out.println("\u001B[35m"+"Y"+"\u001B[0m");
        System.out.print("\u001B[35m"+"\u21D3"+"\u001B[0m");
        System.out.println();
    }



}
