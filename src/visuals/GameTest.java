package visuals;

import static org.junit.jupiter.api.Assertions.*;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import debuffs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import units.*;

import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;
import java.util.Scanner;
import java.util.function.BooleanSupplier;


/*todo Корректность дальности атаки  Корректность совершения атаки  Корректность смерти юнита  Корректность того, как работает защита юнита
Корректность начальной покупки юнитов в магазине

 */
public class GameTest {

    private Game game;
    private SettingsMenu settingsMenu;

    @BeforeEach
    public void setUp() {

        settingsMenu = new SettingsMenu(10, 10);
        game = new Game(settingsMenu);
        game.createEmptyField();
    }

    @Nested
    class FirstTests {
        @BeforeEach
        public void setUpFirst() {

            game.unitTechArrayList.add(new HorseArcher());
            game.unitTechArrayList.add(new BabyDragon());
            game.unitTechArrayList.add(new Crossbow());
            game.enemyTechArrayList.add(new Crossbow());
        }

        @Test
        public void testInEndOfRoundVictory() {
            game.amountOfEnemies = 0;
            game.settingsMenu.setWood(0);
            game.settingsMenu.setRock(0);
            game.settingsMenu.setMoney(0);
            game.workshopcounter = 2;
            game.inEndOfRound();
            assertFalse(!game.endGame);
            assertEquals(game.settingsMenu.getMoney(), 30);
            assertEquals(game.settingsMenu.getWood(), 10);
            assertEquals(game.settingsMenu.getRock(), 10);
        }


        @Test
        public void testInEndOfRoundDefeat() {
            game.amountOfUnits = 0;
            game.settingsMenu.setWood(0);
            game.settingsMenu.setRock(0);
            game.settingsMenu.setMoney(0);
            game.inEndOfRound();
            assertTrue(game.endGame);
            assertEquals(game.settingsMenu.getMoney(), 0);
            assertEquals(game.settingsMenu.getWood(), 0);
            assertEquals(game.settingsMenu.getRock(), 0);
        }

        @Test
        public void testGenerateDebuffs() {
            game.settingsMenu.setAmountOfDebuffs(6);
            game.generateDebuffs();
            assertEquals(game.placedDebuffsBuffer.size(), 6);
        }

        @Test
        public void testPossesEnemies() {
            game.amountOfEnemies = 8;
            try {
                Method method = Game.class.getDeclaredMethod("possesEnemies", null);
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(game.enemyTechArrayList.size(), 9);
        }
    }


    @Nested
    class MovingTests {
        private Unit unit;

        @BeforeEach
        public void setUpUnit() {
            unit = new HorseArcher();
            unit.setX(5);
            unit.setY(5);
            game.generateDebuffs();
            game.placeDebuffs();
            unit.setDistanceOfWalk(5);

        }

        @Test
        public void testMovingW() {
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "w");
            assertEquals(4, unit.getY());
            assertEquals(4, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingA() {
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "a");
            assertEquals(4, unit.getX());
            assertEquals(4, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingS() {
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "s");
            assertEquals(6, unit.getY());
            assertEquals(4, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingD() {
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "d");
            assertEquals(6, unit.getX());
            assertEquals(4, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingWWhenCellIsOccupied() {
            game.field.setFieldable(unit.getY() - 1, unit.getX(), new HorseArcher());
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "w");
            assertEquals(5, unit.getY());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingAWhenCellIsOccupied() {
            game.field.setFieldable(unit.getY(), unit.getX() - 1, new HorseArcher());
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "a");
            assertEquals(5, unit.getX());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingSWhenCellIsOccupied() {
            game.field.setFieldable(unit.getY() + 1, unit.getX(), new HorseArcher());
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "s");
            assertEquals(5, unit.getY());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingDWhenCellIsOccupied() {
            game.field.setFieldable(unit.getY(), unit.getX() + 1, new HorseArcher());
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "d");
            assertEquals(5, unit.getY());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingWWhenFieldEnd() {
            unit.setY(0);
            unit.setX(0);
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "w");
            assertEquals(0, unit.getY());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingAWhenFieldEnd() {
            unit.setY(0);
            unit.setX(0);
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "a");
            assertEquals(0, unit.getX());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingSWhenFieldEnd() {
            unit.setY(9);
            unit.setX(9);
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "s");
            assertEquals(9, unit.getY());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }

        @Test
        public void testMovingDWhenFieldEnd() {
            unit.setY(9);
            unit.setX(9);
            unit.movingWASD(game.field, game.debaffField, game.settingsMenu.placedDebuffs, "d");
            assertEquals(9, unit.getX());
            assertEquals(5, (int) unit.getDistanceOfWalk());
        }


    }

    @Nested
    class TestDebuffs {
        private Unit unit;
        private Debuffs debuffs;


        @Test
        public void testHillOnWalkable() {
            unit = new Axeman();
            debuffs = new Hill();
            unit.setX(5);
            unit.setY(5);
            unit.setDistanceOfWalk(5);
            game.field.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            game.debaffField.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            debuffs.setY(unit.getY() - 1);
            debuffs.setX(unit.getX());
            game.placedDebuffsBuffer.add(debuffs);
            unit.movingWASD(game.field, game.debaffField, game.placedDebuffsBuffer, "w");
            assertEquals(3, (int) unit.getDistanceOfWalk());

        }

        @Test
        public void testSwampOnHorsable() {
            unit = new HorseArcher();
            debuffs = new Swamp();
            unit.setX(5);
            unit.setY(5);
            unit.setDistanceOfWalk(5);
            game.field.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            game.debaffField.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            debuffs.setY(unit.getY() - 1);
            debuffs.setX(unit.getX());
            game.placedDebuffsBuffer.add(debuffs);
            unit.movingWASD(game.field, game.debaffField, game.placedDebuffsBuffer, "w");
            assertEquals(2, (int) unit.getDistanceOfWalk());

        }

        @Test
        public void testTreeOnBowable() {
            unit = new Axeman();
            debuffs = new Tree();
            unit.setX(5);
            unit.setY(5);
            unit.setDistanceOfWalk(5);
            game.field.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            game.debaffField.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            debuffs.setY(unit.getY() - 1);
            debuffs.setX(unit.getX());
            game.placedDebuffsBuffer.add(debuffs);
            unit.movingWASD(game.field, game.debaffField, game.placedDebuffsBuffer, "w");
            assertEquals(3, (int) unit.getDistanceOfWalk());

        }

        @Test
        public void testFireDebuff() {
            unit = new HorseArcher();
            debuffs = new Fire();
            unit.setX(5);
            unit.setY(5);
            unit.setDistanceOfWalk(5);
            unit.setOnFire(0);
            unit.setHealth(10);
            unit.setArmor(0);
            game.field.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            game.debaffField.setFieldable(unit.getY() - 1, unit.getX(), debuffs);
            debuffs.setY(unit.getY() - 1);
            debuffs.setX(unit.getX());
            game.placedDebuffsBuffer.add(debuffs);
            unit.movingWASD(game.field, game.debaffField, game.placedDebuffsBuffer, "w");
            assertEquals(4, (int) unit.getDistanceOfWalk());
            assertEquals(3, unit.getOnFire());
            assertEquals(8, unit.getHealth());
        }

    }

    @Nested
    class TestDragon {
        private Unit dragon;
        private Debuffs debuffs;
        private final InputStream systemInBackup = System.in;
        private ByteArrayInputStream testIn;

        private ArrayList<Debuffs> debuffsArrayList = new ArrayList<>();


        private void provideInput(String data) {
            testIn = new ByteArrayInputStream(data.getBytes());
            System.setIn(testIn);
        }

        @BeforeEach
        public void setUpDragon() {
            dragon = new BabyDragon();
            dragon.setX(5);
            dragon.setY(5);
            dragon.setDistanceOfWalk(3);

        }

        @After
        public void restoreSystemInputOutput() {
            System.setIn(systemInBackup);
        }


        @Test
        public void testDragonMoving() {
            String input = "4\n3\n";
            provideInput(input);
            dragon.moving(game.field, game.debaffField, debuffsArrayList);
            assertEquals(4, dragon.getX());
            assertEquals(3, dragon.getY());
        }

        @Test
        public void testDragonMovingToo() {
            String input = "3\n3\n";
            provideInput(input);
            dragon.moving(game.field, game.debaffField, debuffsArrayList);
            assertEquals(5, dragon.getX());
            assertEquals(5, dragon.getY());
        }

        @Test
        public void testDragonMovingOnTree() {
            debuffs = new Tree();
            game.field.setFieldable(3, 4, debuffs);
            game.debaffField.setFieldable(3, 4, debuffs);
            String input = "4\n3\n";
            provideInput(input);
            dragon.moving(game.field, game.debaffField, debuffsArrayList);
            assertTrue(game.debaffField.getFieldable(3, 4) instanceof Swamp);
        }

        @Test
        public void testDragonMovingOnSwamp() {
            debuffs = new Swamp();
            game.field.setFieldable(3, 4, debuffs);
            game.debaffField.setFieldable(3, 4, debuffs);
            String input = "4\n3\n";
            provideInput(input);
            dragon.moving(game.field, game.debaffField, debuffsArrayList);
            assertTrue(game.debaffField.getFieldable(3, 4) instanceof Fire);
        }

    }


    @Nested
    class TakeDamageTest {

        private Unit unit;

        @BeforeEach
        public void setUpTakeDamage() {
            unit = new HorseArcher();
        }

        @Test
        public void testTakeDamageWithoutArmor() {
            unit.setArmor(0);
            unit.setHealth(100);
            unit.takeDamage(30);
            assertEquals(70, unit.getHealth());
        }

        @Test
        public void testTakeDamageWithArmor() {
            unit.setArmor(15);
            unit.setHealth(100);
            unit.takeDamage(30);
            assertEquals(85, unit.getHealth());
            assertEquals(0, unit.getArmor());
        }

    }

    @Nested
    class AttackTest {
        private Unit attacking;
        private Unit accepting;

        private final InputStream systemInBackup = System.in;
        private ByteArrayInputStream testIn;

        private void provideInput(String data) {
            testIn = new ByteArrayInputStream(data.getBytes());
            System.setIn(testIn);
        }

        @After
        public void restoreSystemInputOutput() {
            System.setIn(systemInBackup);
        }

        @BeforeEach
        public void setUpAttack() {
            attacking = new Axeman();
            accepting = new HorseArcher();
            accepting.setArmor(0);
            accepting.setHealth(10);
            attacking.setDamage(5);
            attacking.setDistanceOfAttack(3);
            attacking.setX(5);
            attacking.setY(5);
            game.field.setFieldable(attacking.getY(), attacking.getX(), attacking);
            game.unitsArrayList.add(attacking);

        }

        @Test
        public void testAttack() {
            attacking.setDamage(5);
            accepting.setY(7);
            accepting.setX(6);
            game.field.setFieldable(accepting.getY(), accepting.getX(), accepting);
            game.enemyTechArrayList.add(accepting);
            String input = "6\n7\n";
            provideInput(input);
            game.attack(attacking);
            assertEquals(5, accepting.getHealth());
        }


        @Test
        public void testAttackToo() {
            attacking.setDamage(5);
            accepting.setY(7);
            accepting.setX(7);
            game.field.setFieldable(accepting.getY(), accepting.getX(), accepting);
            game.enemyTechArrayList.add(accepting);
            String input = "7\n7\n";
            provideInput(input);
            game.attack(attacking);
            assertEquals(10, accepting.getHealth());
        }

        @Test
        public void testDeadEnemy() {
            attacking.setDamage(10);
            accepting.setY(7);
            accepting.setX(6);
            game.field.setFieldable(accepting.getY(), accepting.getX(), accepting);
            game.enemyTechArrayList.add(accepting);
            String input = "6\n7\n";
            provideInput(input);
            game.attack(attacking);
            assertEquals(0, accepting.getHealth());
            assertTrue(game.enemyTechArrayList.isEmpty());
        }


    }


    @Nested
    class ShoppingTest {
        private final InputStream systemInBackup = System.in;
        private ByteArrayInputStream testIn;

        private void provideInput(String data) {
            testIn = new ByteArrayInputStream(data.getBytes());
            System.setIn(testIn);
        }

        @After
        public void restoreSystemInputOutput() {
            System.setIn(systemInBackup);
        }

        @BeforeEach
        public void setUpShopping() {
            game.settingsMenu.setMoney(100);

        }

        @Test
        public void testBuy() {
            String input = "9\n0\n10\n";
            provideInput(input);
            try {
                Method method = Game.class.getDeclaredMethod("BuyUnit");
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(2, game.unitTechArrayList.size());
        }

        @Test
        public void testBuyToo() {
            String input = "9\n0\n9\n0\n9\n10\n";
            provideInput(input);
            try {
                Method method = Game.class.getDeclaredMethod("BuyUnit");
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(4, game.unitTechArrayList.size());
        }

    }


    @Nested
    class BotTest {
        Unit enemy;
        Unit ally;

        @BeforeEach
        public void setUpBot() {
            enemy = new HorseArcher();
            enemy.setY(5);
            enemy.setX(5);
            enemy.setDistanceOfAttack(3);
            enemy.setDamage(5);
            enemy.setTeam("enemy");
            game.enemyTechArrayList.add(enemy);
            game.field.setFieldable(enemy.getY(), enemy.getX(), enemy);
            ally = new HorseArcher();
            ally.setArmor(0);
            ally.setHealth(10);
        }


        @Test
        public void testAttackBot() {
            ally.setX(6);
            ally.setY(6);
            game.unitTechArrayList.add(ally);
            game.field.setFieldable(ally.getY(), ally.getX(), ally);
            try {
                Method method = Game.class.getDeclaredMethod("enemyTurn");
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(5, ally.getHealth());
        }

        @Test
        public void testAttackBotKill() {
            enemy.setDamage(11);
            ally.setX(6);
            ally.setY(6);
            game.unitTechArrayList.add(ally);
            game.field.setFieldable(ally.getY(), ally.getX(), ally);
            try {
                Method method = Game.class.getDeclaredMethod("enemyTurn");
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertEquals(0, ally.getHealth());
            assertTrue(game.unitTechArrayList.isEmpty());
        }

        @Test
        public void testMovingBot() {
            int prevX = 7;
            int prevY = 7;
            ally.setX(prevX);
            ally.setY(prevY);
            game.unitTechArrayList.add(ally);
            game.field.setFieldable(ally.getY(), ally.getX(), ally);
            try {
                Method method = Game.class.getDeclaredMethod("enemyTurn");
                method.setAccessible(true);
                method.invoke(game);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assertTrue(prevX != enemy.getX() || prevY != enemy.getY());
        }
    }
}