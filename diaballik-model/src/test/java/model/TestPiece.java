package model;

import diaballik.model.*;
import org.junit.Before;
import org.junit.Test;

import static diaballik.model.ChoiceColor.CARAMEL;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPiece {
    private Piece p;
    private Player player;

    @Before
    public void initalize() {
        player = new Human("Isabelle", CARAMEL);
        p = new Piece(8, false);
    }

    @Test
    public void testCreationPiece() {
        assertNotNull(p);
    }


    @Test
    public void testGetId() {
        assertEquals(8, p.getId());
    }

    @Test
    public void testGetHasBall() {
        assertFalse(p.getHasBall());
    }

    @Test
    public void testSetHasBall() {
        p.setHasBall(true);
        assertTrue(p.getHasBall());
    }

    @Test
    public void testColor(){
        assertEquals(CARAMEL,p.getColor());
    }

    @Test
    public void testGetPlayer() {
        assertNull(p.getPlayer());
    }

    @Test
    public void testSetPlayer() {
        p.setPlayer(player);
        assertNotNull(p.getPlayer());
    }

    @Test
    public void testTx1(){
        p.setTx(6);
        assertEquals(6,p.getTx());
    }

    @Test
    public void testTx2(){
        Piece piece = new BuildNewGame(0,"Isabelle","Morgane").getGame().getBoard().getList().get(9);
        assertEquals(6,piece.getTx());
    }

    @Test
    public void testTy1(){
        p.setTy(2);
        assertEquals(2,p.getTy());
    }

    @Test
    public void testTy2(){
        Piece piece = new BuildNewGame(0,"Isabelle","Morgane").getGame().getBoard().getList().get(9);
        assertEquals(2,piece.getTy());
    }

}
