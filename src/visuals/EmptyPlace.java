package visuals;

import java.io.Serializable;

public class EmptyPlace implements Fieldable, Serializable {
    String symbol ="\u25A1\t";

    @Override
    public String getSymbol() {
        return symbol;
    }
}
