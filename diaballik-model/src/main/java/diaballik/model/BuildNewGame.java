package diaballik.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import static diaballik.model.ChoiceColor.CARAMEL;
import static diaballik.model.ChoiceColor.VANILLE;

/**
 * Classe representant la creation d'une nouvelle partie
 */
public class BuildNewGame {
	/**
	 * Attributs
	 */
	private Game game;
	private Map<Integer, BuildBoard> boardBuilders;

	/**
	 * Constructeur d'une nouvelle partie de type PvIA
	 *
	 * @param scenario le scenario determinant le placement initial des pieces sur le plateau
	 * @param p1       le nom du premier joueur
	 * @param level    le niveau que choisi le joueur 1 pour son adversaire, l'ia
	 */
	public BuildNewGame(final int scenario, final String p1, final int level) {
		boardBuilders = new HashMap<Integer, BuildBoard>();
		initBuilders();
		// Initialisation du plateau en fonction du scenario
		final Board board = buildScenario(scenario);
		board.setScenario(scenario);
		// Creation du joueur 1 : le joueur 1 est toujours un humain
		final Human player1 = new Human(p1, VANILLE);
		// Creation du joueur 2 : dans ce cas c'est une IA
		final Strategy strat = createStrategy(level);
		final AI player2 = new AI("AI", CARAMEL, strat);
		// Creation d'une partie avec les 2 joueurs et le plateau
		game = new Game(player1, player2, board);
		// Creation des pieces et affectation au plateau et aux joueurs
		createElements(player1, player2, board);
		// Placement des pieces
		if (!boardBuilders.containsKey(scenario)) {
			System.err.print("Le scenario demande n'est pas connu.");
		}
		boardBuilders.get(scenario).placerPieces(board.getList());

		// Initialisation des listes de commandes
		game.setUndoCommand(new ArrayList<Commande>());
		game.setRedoCommand(new ArrayList<Commande>());

		// Initialisation du premier tour de jeu
		final Turn turn = new Turn(0);
		game.setTurn(turn);
	}

	/**
	 * Construction d'une nouvelle partie de type PvP
	 *
	 * @param scenario le scenario determinant le placement initial des pieces sur le plateau
	 * @param p1       le nom du premier joueur
	 * @param p2       le nom du second joueur joueur
	 */
	public BuildNewGame(final int scenario, final String p1, final String p2) {
		boardBuilders = new HashMap<Integer, BuildBoard>();
		initBuilders();
		// Initialisation du plateau en fonction du scenario
		final Board board = buildScenario(scenario);
		board.setScenario(scenario);
		// Creation du joueur 1 : le joueur 1 est toujours un humain
		final Human player1 = new Human(p1, VANILLE);
		// Creation du joueur 2 : dans ce cas c'est aussi un humain
		final Human player2 = new Human(p2, CARAMEL);
		// Creation d'une partie avec les 2 joueurs et le plateau
		game = new Game(player1, player2, board);
		// Creation des pieces et affectation au plateau et aux joueurs
		createElements(player1, player2, board);
		// Placement des pieces
		if (!boardBuilders.containsKey(scenario)) {
			System.err.print("Le scenario demande n'est pas connu.");
		}
		boardBuilders.get(scenario).placerPieces(board.getList());

		// Initialisation des listes de commandes
		game.setUndoCommand(new ArrayList<Commande>());
		game.setRedoCommand(new ArrayList<Commande>());

		// Initialisation du premier tour de jeu
		final Turn turn = new Turn(0);
		game.setTurn(turn);
	}

	/**
	 * Getter de la partie que l'on construit
	 *
	 * @return la partie que l'on construit
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Setter d'une partie
	 *
	 * @param game la partie de laquelle on part pour construire notre partie
	 */
	public void setGame(final Game game) {
		this.game = game;
	}

	/**
	 * Cree differents plateau en fonction des scenarios possible
	 */
	public void initBuilders() {
		boardBuilders.put(0, new BuildBoardStandard());
		boardBuilders.put(1, new BuildBoardRandom());
		boardBuilders.put(2, new BuildBoardEnemyAmongUs());
	}

	/**
	 * Choix du plateau en fonction du scenario
	 *
	 * @param scenario que le j1 a choisi pour debuter la partie
	 * @return le plateau construit en fonction du scenario choisi
	 */
	public Board buildScenario(final int scenario) {
		if (!boardBuilders.containsKey(scenario)) {
			System.err.print("Le scenario demande n'est pas connu.");
		}
		return boardBuilders.get(scenario).getBoard();
	}

	/**
	 * Creation de la strategie en fonction du niveau que l'on entre en parametre
	 *
	 * @param level niveau choisi
	 * @return la strategie correspondant au niveau choisi
	 */
	private Strategy createStrategy(final int level) {
		if (level == 0) {
			return new StrategyNoob();
		}
		if (level == 1) {
			return new StrategyStarting();
		} else {
			return new StrategyProgressive();
		}
	}

	/**
	 * Creation des pieces
	 *
	 * @param player1 joueur jouant avec les pieces d'id allant de 0 a 6
	 * @param player2 joueur jouant avec les pieces d'id allant de 7 a 13
	 * @param board   plateau sur lequel seront placer les pieces
	 */
	private void createElements(final Human player1, final Player player2, final Board board) {
		/* Creation des pieces et de la balle du joueur 1 */
		board.addPiece(0, new Piece(0, false));
		board.addPiece(1, new Piece(1, false));
		board.addPiece(2, new Piece(2, false));
		board.addPiece(3, new Piece(3, true));
		board.addPiece(4, new Piece(4, false));
		board.addPiece(5, new Piece(5, false));
		board.addPiece(6, new Piece(6, false));

		/* Creation des pieces et de la balle du joueur 2 */
		board.addPiece(7, new Piece(7, false));
		board.addPiece(8, new Piece(8, false));
		board.addPiece(9, new Piece(9, false));
		board.addPiece(10, new Piece(10, true));
		board.addPiece(11, new Piece(11, false));
		board.addPiece(12, new Piece(12, false));
		board.addPiece(13, new Piece(13, false));

		board.getList().forEach(p -> {
			if (p.getId() < 7) {
				p.setPlayer(player1);
			} else {
				p.setPlayer(player2);
			}
		});
	}

}
