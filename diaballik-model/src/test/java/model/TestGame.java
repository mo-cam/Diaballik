package model;

import diaballik.model.AI;
import diaballik.model.Board;
import diaballik.model.Commande;
import diaballik.model.Game;
import diaballik.model.Human;
import diaballik.model.Strategy;
import diaballik.model.StrategyNoob;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import static diaballik.model.ChoiceColor.CARAMEL;
import static diaballik.model.ChoiceColor.VANILLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGame {
    private Game game;
    private Human player1;
    private Human player2;
    AI ia;

    @Before
    public void initalize() {
        player1 = new Human("Morgane", VANILLE);
        player2 = new Human("Isabelle", CARAMEL);
        Strategy strat = new StrategyNoob();
        ia = new AI("IA",CARAMEL,strat);
        game = new Game(player1, player2, new Board());
    }

    @Test
    public void testCreation() {
        assertNotNull(game);
    }

    @Test
    public void testBoard() {
        assertNotNull(game.getBoard());
    }

    @Test
    public void testPlayer1_1() {
        assertEquals(Human.class, game.getPlayer1().getClass());
    }

    @Test
    public void testPlayer1_2() {
        assertEquals("Morgane", game.getPlayer1().getName());
    }

    @Test
    public void testPlayer1_3() {
        assertEquals(VANILLE, game.getPlayer1().getColor());
    }

    @Test
    public void testPlayer1_4() {
        assertEquals(player1, game.getPlayer1());
    }

    @Test
    public void testPlayer2_1() {
        assertEquals(Human.class, game.getPlayer2().getClass());
    }

    @Test
    public void testPlayer2_2() {
        assertEquals("Isabelle", game.getPlayer2().getName());
    }

    @Test
    public void testPlayer2_3() {
        assertEquals(CARAMEL, game.getPlayer2().getColor());
    }

    @Test
    public void testPlayer2_4() {
        assertEquals(player2, game.getPlayer2());
    }

    @Test
    public void testPlayer2_5(){
        game.setPlayer2(ia);
        assertTrue(game.getPlayer2().getClass().equals(AI.class) && game.getPlayer2().getName().equals("IA")
                && game.getPlayer2().getColor().equals(CARAMEL));
    }

    @Test
    public void testUndoCommand1() {
        assertNull(game.getUndoCommand());
    }

    @Test
    public void testUndoCommand2() {
        game.setUndoCommand(new ArrayList<Commande>());
        assertNotNull(game.getUndoCommand());
    }

    @Test
    public void testRedoCommand1() {
        assertNull(game.getRedoCommand());
    }

    @Test
    public void testRedoCommand2() {
        game.setRedoCommand(new ArrayList<Commande>());
        assertNotNull(game.getRedoCommand());
    }

    @Test
    public void testTurn1() {
        assertNull(game.getTurn());
    }

}
