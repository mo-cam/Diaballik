package model;

import diaballik.model.Board;
import diaballik.model.BuildNewGame;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * On verifie que les balles sont bien au centre sur une piece bleue (ligne 0) et une rouge (ligne 6).
 * On ne verifie pas la couleur des pieces non placees au centre d'une ligne car certaines pieces de cette ligne sont
 * des pieces de l'adversaire, on ne verifie alors que la presence d'une piece sur ces cases.
 */
public class TestBuildBoardEnemyAmongUs {
    private Board boardEnemy;

    @Before
    public void initialize() {
        BuildNewGame buildNewGame = new BuildNewGame(2, "Morgane", "Isabelle");
        boardEnemy = buildNewGame.getGame().getBoard();
    }

    @Test
    public void testCreation() {
        assertNotNull(boardEnemy);
    }

    @Test
    public void testCreationPieces() {
        assertFalse(boardEnemy.getList().isEmpty());
    }

    @Test
    public void testBallCentreBleu() {
        assertTrue(boardEnemy.getValue(0, 3).getHasBall()
                && boardEnemy.getValue(0, 3).getId() == 3);
    }

    @Test
    public void testBallCentreRouge() {
        assertTrue(boardEnemy.getValue(6, 3).getHasBall()
                && boardEnemy.getValue(6, 3).getId() == 10);
    }

    @Test
    public void testPiece0() {
        assertNotNull(boardEnemy.getValue(0, 0));
    }

    @Test
    public void testPiece1() {
        assertNotNull(boardEnemy.getValue(0, 1));
    }

    @Test
    public void testPiece2() {
        assertNotNull(boardEnemy.getValue(0, 2));
    }

    @Test
    public void testPiece3() {
        assertNotNull(boardEnemy.getValue(0, 4));
    }

    @Test
    public void testPiece4() {
        assertNotNull(boardEnemy.getValue(0, 5));
    }

    @Test
    public void testPiece5() {
        assertNotNull(boardEnemy.getValue(0, 6));
    }

    @Test
    public void testPiece6() {
        assertNotNull(boardEnemy.getValue(6, 0));
    }

    @Test
    public void testPiece7() {
        assertNotNull(boardEnemy.getValue(6, 1));
    }

    @Test
    public void testPiece8() {
        assertNotNull(boardEnemy.getValue(6, 2));
    }

    @Test
    public void testPiece9() {
        assertNotNull(boardEnemy.getValue(6, 4));
    }

    @Test
    public void testPiece10() {
        assertNotNull(boardEnemy.getValue(6, 5));
    }

    @Test
    public void testPiece11() {
        assertNotNull(boardEnemy.getValue(6, 6));
    }
}

