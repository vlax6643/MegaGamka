package units;

import org.junit.Before;
import visuals.Game;
import visuals.SettingsMenu;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    @Before
    public void setUp() {
Game game = new Game(new SettingsMenu(10, 10));
game.unitTechArrayList = new ArrayList<Unit>();
    }

}