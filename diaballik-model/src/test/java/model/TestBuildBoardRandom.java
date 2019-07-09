package model;

import diaballik.model.Board;
import diaballik.model.BuildNewGame;
import org.junit.Before;
import org.junit.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * On verifie que les pieces sont bien placees aux endroits ou on les attend
 * On ne verifie pas ou se trouve les balles car elles sont placees aleatoirement
 */
public class TestBuildBoardRandom {
    private Board boardRandom;

    @Before
    public void initialize() {
        BuildNewGame buildNewGame = new BuildNewGame(1, "Morgane", "Isabelle");
        boardRandom = buildNewGame.getGame().getBoard();
    }

    @Test
    public void testCreation() {
        assertNotNull(boardRandom);
    }

    @Test
    public void testCreationPieces() {
        assertFalse(boardRandom.getList().isEmpty());
    }

    /*
    @Test
    public void testBallBleue1() {
        assertTrue(boardRandom.getValue(0, 0).getColor().equals(BLEU)
                && boardRandom.getValue(0, 0).getId() == 0);
    }

    @Test
    public void testBallBleue2() {
        assertTrue(boardRandom.getValue(0, 1).getColor().equals(BLEU)
                && boardRandom.getValue(0, 1).getId() == 1);
    }

    @Test
    public void testBallBleue3() {
        assertTrue(boardRandom.getValue(0, 2).getColor().equals(BLEU)
                && boardRandom.getValue(0, 2).getId() == 2);
    }

    @Test
    public void testBallBleue4() {
        assertTrue(boardRandom.getValue(0, 3).getColor().equals(BLEU)
                && boardRandom.getValue(0, 3).getId() == 3);
    }

    @Test
    public void testBallBleue5() {
        assertTrue(boardRandom.getValue(0, 4).getColor().equals(BLEU)
                && boardRandom.getValue(0, 4).getId() == 4);
    }

    @Test
    public void testBallBleue6() {
        assertTrue(boardRandom.getValue(0, 5).getColor().equals(BLEU)
                && boardRandom.getValue(0, 5).getId() == 5);
    }

    @Test
    public void testBallBleue7() {
        assertTrue(boardRandom.getValue(0, 6).getColor().equals(BLEU)
                && boardRandom.getValue(0, 6).getId() == 6);
    }

    @Test
    public void testBallRouge1() {
        assertTrue(boardRandom.getValue(6, 0).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 0).getId() == 7);
    }

    @Test
    public void testBallRouge2() {
        assertTrue(boardRandom.getValue(6, 1).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 1).getId() == 8);
    }

    @Test
    public void testBallRouge3() {
        assertTrue(boardRandom.getValue(6, 2).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 2).getId() == 9);
    }

    @Test
    public void testBallRouge4() {
        assertTrue(boardRandom.getValue(6, 3).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 3).getId() == 10);
    }

    @Test
    public void testBallRouge5() {
        assertTrue(boardRandom.getValue(6, 4).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 4).getId() == 11);
    }

    @Test
    public void testBallRouge6() {
        assertTrue(boardRandom.getValue(6, 5).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 5).getId() == 12);
    }

    @Test
    public void testBallRouge7() {
        assertTrue(boardRandom.getValue(6, 6).getColor().equals(ROUGE)
                && boardRandom.getValue(6, 6).getId() == 13);
    }
    */
}