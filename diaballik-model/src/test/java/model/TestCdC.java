package model;

import diaballik.model.AI;
import diaballik.model.BuildNewGame;
import diaballik.model.Game;
import diaballik.model.Human;
import diaballik.model.MoveBall;
import diaballik.model.MovePiece;
import diaballik.model.Piece;
import diaballik.model.Strategy;
import diaballik.model.StrategyNoob;
import diaballik.model.StrategyProgressive;
import diaballik.model.StrategyStarting;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Test;

import static diaballik.model.ChoiceColor.CARAMEL;
import static diaballik.model.ChoiceColor.VANILLE;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de tests du cahier des charges
 */
public class TestCdC {
	private Game game;
	private Game gameIA;
	private AI ia;
	private MoveBall moveBall1;
	private MoveBall moveBall2;
	private MoveBall moveBall3;
	private MoveBall moveBall4;
	private MovePiece movePiece1;
	private MovePiece movePiece2;
	private MovePiece movePiece3;
	private MovePiece movePiece4;

	@Before
	public void initalize() {
		game = new BuildNewGame(0,"Morgane","Isabelle").getGame();
		gameIA = new BuildNewGame(0,"Isabelle",0).getGame();
		Strategy strat = new StrategyNoob();
		ia = new AI("IA", CARAMEL, strat);
		moveBall1 = new MoveBall(0, 0, 6, 6, game);
		moveBall2 = new MoveBall(0, 3, 0, 4, game);
		moveBall3 = new MoveBall(0, 3, 0, 6, game);
		moveBall4 = new MoveBall(0, 3, 1, 4, game);
		Piece piece = game.getBoard().getValue(0, 4);
		movePiece1 = new MovePiece(0, 2, 0, 1, game);
		movePiece2 = new MovePiece(0, 2, 1, 2, game);
		movePiece3 = new MovePiece(0, 2, 2, 2, game);
		movePiece4 = new MovePiece(0, 3, 1, 3, game);
	}

	@Test
	public void testCreation() {
		assertNotNull(game);
	}

	/**
	 * R21_1_GAME_PLAYERS et R21_5_GAME_COLOUR
	 */

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
		game.setPlayer2(ia);
		assertTrue(game.getPlayer2().getClass().equals(AI.class) && game.getPlayer2().getName().equals("IA")
				&& game.getPlayer2().getColor().equals(CARAMEL));
	}

	/**
	 * R21_2_GAME_BOARD
	 */
	@Test
	public void testBoard1() {
		assertNotNull(game.getBoard());
	}

	@Test
	public void testBoard2() {
		assertEquals(7,game.getBoard().boardTiles.length);
	}

	@Test
	public void testBoard3() {
		final int bool[] = new int[1];
		bool[0] = 0;
		IntStream.range(0, 7).forEach(i -> {
			IntStream.range(0, 7).forEach(j -> {
				bool[0] = bool[0] + 1;
			});
		});
		assertEquals(49,bool[0]);
	}

	/**
	 * R21_3_GAME_PIECES
	 */
	@Test
	public void testPieces1() {
		final int cptPiecesJ1[] = new int[1];
		cptPiecesJ1[0] = 0 ;
		game.getBoard().getList().forEach(piece -> {
			if(piece.getPlayer().equals(game.getPlayer1())){
				cptPiecesJ1[0] = cptPiecesJ1[0]+1;
			}
		});
		assertEquals(7,cptPiecesJ1[0]);
	}

	@Test
	public void testPieces2() {
		final int cptPiecesJ2[] = new int[1];
		cptPiecesJ2[0] = 0 ;
		game.getBoard().getList().forEach(piece -> {
			if(piece.getPlayer().equals(game.getPlayer2())){
				cptPiecesJ2[0] = cptPiecesJ2[0]+1;
			}
		});
		assertEquals(7,cptPiecesJ2[0]);
	}

	/**
	 * R21_4_GAME_BALL
	 */
	@Test
	public void testBall1() {
		final int cptPiecesJ1[] = new int[1];
		cptPiecesJ1[0] = 0 ;
		game.getBoard().getList().forEach(piece -> {
			if(piece.getPlayer().equals(game.getPlayer1()) && piece.getHasBall()){
				cptPiecesJ1[0] = cptPiecesJ1[0]+1;
			}
		});
		assertEquals(1,cptPiecesJ1[0]);
	}

	@Test
	public void testBall2() {
		final int cptPiecesJ2[] = new int[1];
		cptPiecesJ2[0] = 0 ;
		game.getBoard().getList().forEach(piece -> {
			if(piece.getPlayer().equals(game.getPlayer2()) && piece.getHasBall()){
				cptPiecesJ2[0] = cptPiecesJ2[0]+1;
			}
		});
		assertEquals(1,cptPiecesJ2[0]);
	}

	/**
	 * R21_6_GAMEPLAY_TURN
	 */
	@Test
	public void testTurn1() {
		assertEquals(0,game.getTurn().getNbTurn());
	}

	@Test
	public void testTurn2() {
		assertEquals(game.getPlayer1(),game.getTurn().getP1());
	}

	@Test
	public void testTurn3() {
		assertEquals(game.getPlayer2(),game.getTurn().getP2());
	}

	/**
	 * R21_8_GAMEPLAY_ACTIONS et R21_12_GAMEPLAY_HOW_START
	 */
	@Test
	public void testAction1() {
		assertEquals(3,game.getPlayer1().nbAction);
	}
	@Test
	public void testAction2() {
		assertEquals(3,game.getPlayer2().nbAction);
	}

	/**
	 *R21_9_GAMEPLAY_MOVE_BALL
	 */
	@Test
	public void testCreationMb() {
		assertNotNull(moveBall1);
	}

	/**
	 * R25_2_SELECTION et R25_3_NOT_SELECTABLE
	 */
	@Test
	public void testCanDoBaseMb1() {
		// La piece que l'on veut bouger ne possede pas de balle
		assertFalse(moveBall1.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMb2() {
		// La case que l'on vise ne contient pas de piece
		assertFalse(moveBall4.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMb3() {
		assertTrue(moveBall3.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMb4() {
		assertTrue(moveBall2.canDoBase(game));
	}

	@Test
	public void testCanDoMB1() {
		// Il y a des cases occupees entre celle ou la piece se trouve et celle que l'on vise
		assertFalse(moveBall3.canDo(game));
	}

	@Test
	public void testCanDoMb2() {
		// Deplacement horizontal possible
		assertTrue(moveBall2.canDo(game));
	}

	@Test
	public void testActionMb1() {
		moveBall2.action(game);
		assertTrue(game.getBoard().getValue(0, 4).getHasBall());
	}

	@Test
	public void testActionMb2() {
		moveBall2.action(game);
		assertFalse(game.getBoard().getValue(0, 3).getHasBall());
	}

	@Test
	public void testActionMb3() {
		MovePiece movePiece = new MovePiece(0,4,1,4,game);
		movePiece.action(game);
		// Deplacement diagonal possible
		moveBall4.action(game);
		assertTrue(game.getBoard().getValue(1,4).getHasBall());
	}

	@Test
	public void testUndoMb1() {
		moveBall2.action(game);
		moveBall2.undo(game);
		assertTrue(game.getBoard().getValue(0, 3).getHasBall());
	}

	@Test
	public void testUndoMb2() {
		moveBall2.action(game);
		moveBall2.undo(game);
		assertFalse(game.getBoard().getValue(0, 4).getHasBall());

	}

	@Test
	public void testRedoMb1() {
		moveBall2.action(game);
		moveBall2.undo(game);
		moveBall2.redo(game);
		assertTrue(game.getBoard().getValue(0, 4).getHasBall());
	}

	@Test
	public void testRedoMb2() {
		moveBall2.action(game);
		moveBall2.undo(game);
		moveBall2.redo(game);
		assertFalse(game.getBoard().getValue(0, 3).getHasBall());
	}

	/**
	 * R21_10_GAMEPLAY_MOVE_PIECE
	 */
	@Test
	public void testCreationMp() {
		assertNotNull(movePiece1);
	}

	@Test
	public void testCanDoBaseMp1() {
		// La piece que l'on veut bouger possede une balle
		assertFalse(movePiece4.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMp2() {
		assertTrue(movePiece1.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMp3() {
		assertTrue(movePiece3.canDoBase(game));
	}

	@Test
	public void testCanDoBaseMp4() {
		assertTrue(movePiece2.canDoBase(game));
	}

	/**
	 * R21_11_GAMEPLAY_MOVE_PIECE_WITH_BALL et R25_2_SELECTION et R25_3_NOT_SELECTABLE
	 */
	@Test
	public void testCanDoMp1() {
		// La piece que l'on vise contient une piece
		assertFalse(movePiece1.canDo(game));
	}

	@Test
	public void testCanDoMp2() {
		// Deplacement en diagonale non autorise
		assertFalse(movePiece3.canDo(game));
	}

	@Test
	public void testCanDoMp3() {
		assertTrue(movePiece2.canDo(game));
	}

	@Test
	public void testActionMp1() {
		movePiece2.action(game);
		assertNotNull(game.getBoard().getValue(1, 2));
	}

	@Test
	public void testActionMp2() {
		movePiece2.action(game);
		assertNull(game.getBoard().getValue(0, 2));
	}

	@Test
	public void testUndoMp1() {
		movePiece2.action(game);
		movePiece2.undo(game);
		assertNotNull(game.getBoard().getValue(0, 2));
	}

	@Test
	public void testUndoMp2() {
		movePiece2.action(game);
		movePiece2.undo(game);
		assertNull(game.getBoard().getValue(1, 2));

	}

	@Test
	public void testRedoMp1() {
		movePiece2.action(game);
		movePiece2.undo(game);
		movePiece2.redo(game);
		assertNotNull(game.getBoard().getValue(1, 2));
	}

	@Test
	public void testRedoMp2() {
		movePiece2.action(game);
		movePiece2.undo(game);
		movePiece2.redo(game);
		assertNull(game.getBoard().getValue(0, 2));
	}

	/**
	 * R22_1_SCENARIO_STANDARD
	 */
	// Voir classe TestBuildBoardStandard

	/**
	 * R22_2_SCENARIO_BALL_RANDOM
	 */
	// Voir classe TestBuildBoardRandom

	/**
	 * R22_2_SCENARIO_ENEMY_AMONG_US
	 */
	// Voir classe TestBuildBoardEnemyAmongUs

	/**
	 * R23_1_PLAYER_KINDS
	 */
	@Test
	public void testPlayerKinds1(){
		assertTrue(game.getPlayer1().getClass().equals(Human.class) &&
				game.getPlayer2().getClass().equals(Human.class));
	}

	@Test
	public void testPlayerKinds2(){
		assertTrue(gameIA.getPlayer1().getClass().equals(Human.class) &&
				gameIA.getPlayer2().getClass().equals(AI.class));
	}

	/**
	 * R23_2_IA_LEVELS
	 */
	@Test
	public void testLevel1(){
		assertEquals(StrategyNoob.class,((AI)gameIA.getPlayer2()).getStrategy().getClass());
	}

	@Test
	public void testLevel2(){
		gameIA = new BuildNewGame(0,"Isabelle",1).getGame();
		assertEquals(StrategyStarting.class,((AI)gameIA.getPlayer2()).getStrategy().getClass());
	}

	@Test
	public void testLevel3(){
		gameIA = new BuildNewGame(0,"Isabelle",2).getGame();
		assertEquals(StrategyProgressive.class,((AI)gameIA.getPlayer2()).getStrategy().getClass());
	}

	/**
	 * R23_3_AI_LEVEL_NOOB et R23_4_AI_LEVEL_STARTING et R23_5_AI_LEVEL_PROGRESSIVE
	 */
	@Test
	public void testNoob(){
		AI ia = (AI) gameIA.getPlayer2();
		ia.play(gameIA);
		assertEquals(2,gameIA.getPlayer2().nbAction);
	}

	@Test
	public void testStarting(){
		gameIA = new BuildNewGame(0,"Isabelle",1).getGame();
		AI ia = (AI) gameIA.getPlayer2();
		ia.play(gameIA);
		assertEquals(2,gameIA.getPlayer2().nbAction);
	}

	@Test
	public void testProgressive(){
		gameIA = new BuildNewGame(0,"Isabelle",2).getGame();
		AI ia = (AI) gameIA.getPlayer2();
		ia.play(gameIA);
		assertEquals(2,gameIA.getPlayer2().nbAction);
	}

	/**
	 * R24_1_VICTORY
	 */



	/**
	 * R25_4_GAME_CREATION et R25_7_GAME_INITIALISATION
	 */
	@Test
	public void testBuildGamePvP() {
		assertNotNull(game);
	}

	@Test
	public void testBuildGamePvIA() {
		assertNotNull(gameIA);
	}

	@Test
	public void testGamePvP(){
		assertEquals(0,game.getTurn().getNbTurn());
	}

	@Test
	public void testGamePvIA(){
		assertEquals(0,gameIA.getTurn().getNbTurn());
	}

	/**
	 * R25_5_SAVE_LOAD_GAME et R25_6_REPLAY
	 */
	// Voir classe TestGameResource

}
