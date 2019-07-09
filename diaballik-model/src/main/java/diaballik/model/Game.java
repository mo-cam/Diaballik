package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.serialization.DiabalikJacksonProvider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;


import static diaballik.model.ChoiceColor.CARAMEL;
import static diaballik.model.ChoiceColor.VANILLE;

/**
 * Classe representant une partie
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Game {
	/**
	 * Attributs
	 */
	// Le plateau de jeu de la partie
	private Board board;
	// J1 sera le joueur jouant avec les pieces couleur vanille
	private Human j1;
	// J2 sera donc le joueur celui jouant avec les pieces couleur caramel
	// Si nbPlayer == 1, j2 est de type AI. Si nbPlayer == 2, j2 est de type Human.
	private Player j2;
	// Sauvegarde des commandes effectuees que l'on va pouvoir annuler
	private List<Commande> undoCommand;
	// Sauvegarde des commandes annulees que l'on va pouvoir jouer
	private List<Commande> redoCommand;
	// Le tour en cours
	@JsonProperty("turn")
	protected Turn turn;
	// Est-ce que quelqu'un a gagne ?
	@JsonProperty("win")
	private boolean win;

	/**
	 * Constructeur
	 */
	@JsonCreator
	public Game(@JsonProperty("j1") final Human j1, @JsonProperty("j2") final Player j2, @JsonProperty("board") final Board board) {
		this.board = board;
		this.j1 = j1;
		this.j2 = j2;
		this.win = false;
	}

	/**
	 * Getter du plateau de jeu de la partie
	 *
	 * @return le plateau de jeu de la partie
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Setter d'un board a notre partie
	 *
	 * @param b le plateau de jeu que l'on va utiliser pour la partie
	 */
	public void setBoard(final Board b) {
		this.board = b;
	}

	/**
	 * Getter du premier joueur de la partie
	 *
	 * @return le premier joueur
	 */
	public Human getPlayer1() {
		return j1;
	}

	/**
	 * Setter du premier joueur de la partie
	 *
	 * @param p1 le joueur auquel on va associe la place de numero 1
	 */
	public void setPlayer1(final Human p1) {
		this.j1 = p1;
	}

	/**
	 * Getter du second joueur de la partie
	 *
	 * @return le second joueur
	 */
	public Player getPlayer2() {
		return j2;
	}

	/**
	 * Setter du second joueur de la partie
	 *
	 * @param p2 le joueur auquel on va associe la place de numero 2
	 */
	public void setPlayer2(final Player p2) {
		this.j2 = p2;
	}

	/**
	 * Getter de la liste de commandes que l'on va pouvoir annuler
	 *
	 * @return la liste des commandes effectuees
	 */
	public List<Commande> getUndoCommand() {
		return undoCommand;
	}

	/**
	 * Setter de la liste de commande que l'on va pouvoir annuler
	 *
	 * @param undoCommand la liste de commande que l'on va pouvoir annuler
	 */
	public void setUndoCommand(final List<Commande> undoCommand) {
		this.undoCommand = undoCommand;
	}

	/**
	 * Getter de la liste de commandes que l'on va pouvoir rejouer
	 *
	 * @return la liste de commandes annulees
	 */
	public List<Commande> getRedoCommand() {
		return redoCommand;
	}

	/**
	 * Setter de la liste de commandes que l'on va pouvoir rejouer
	 *
	 * @param redoCommand la liste des commandes que l'on va pouvoir rejouer
	 */
	public void setRedoCommand(final List<Commande> redoCommand) {
		this.redoCommand = redoCommand;
	}

	/**
	 * Getter du tour en cours de la partie
	 *
	 * @return le tour en cours de la partie
	 */
	@JsonIgnore
	public Turn getTurn() {
		return turn;
	}

	/**
	 * Setter d'un tour : on associe un tour a notre partie
	 *
	 * @param turn le tour en cours de la partie
	 */
	public void setTurn(final Turn turn) {
		this.turn = turn;
		this.turn.setP1(getPlayer1());
		this.turn.setP2(getPlayer2());
	}

	/**
	 * Mise a jour de l'etat de la partie
	 *
	 * @param win true si un des joueurs a gagne, false sinon
	 */
	public void setWin(final boolean win) {
		this.win = win;
	}

	/**
	 * Getter de l'etat de la partie
	 *
	 * @return true si un des joueurs a gagne, false sinon
	 */
	public boolean isWin() {
		return win;
	}

	/**
	 * Verifie si la piece de j possedant la balle est sur la ligne adverse
	 *
	 * @param j Le joueur j a-t-il gagne ?
	 */
	public void win(final Player j) {
		// On essaie de savoir si le joueur 1 a gagne
		if (j1.equals(j)) {
			final Optional<Piece> pballJ1 = board.getList().stream().filter(piece -> piece.getHasBall() &&
					piece.getColor().equals(VANILLE) && piece.getTx() == 6).findFirst();
			if (pballJ1.isPresent()) {
				setWin(true);
			}
			// On essaie de savoir si le joueur 2 a gagne
		} else if (j2.equals(j)) {
			final Optional<Piece> pballJ2 = board.getList().stream().filter(piece -> piece.getHasBall() &&
					piece.getColor().equals(CARAMEL) && piece.getTx() == 0).findFirst();
			if (pballJ2.isPresent()) {
				setWin(true);
			}
		}
	}

	/**
	 * Sauvegarder la partie dans un fichier
	 *
	 * @param nomFichier nom sous lequel on souhaite sauvegarder notre partie
	 */
	public void save(final String nomFichier) throws IOException {
		new DiabalikJacksonProvider().getMapper().writeValue(new File("./savedgames/" + nomFichier + ".json"), this);
		System.out.println("Game saved : " + nomFichier);
	}

	/**
	 * Chargement d'une partie sauvegardee
	 *
	 * @return la liste des noms des parties sauvegardees
	 */
	public static List<String> listGameSaved() {
		final File file = new File("./savedgames/");
		final List<String> savedGames = new ArrayList<String>();
		Arrays.stream(file.listFiles()).filter(f -> f.isFile()).forEach(f -> {
			savedGames.add(f.getName());
		});
		return savedGames;
	}

	/**
	 * Chargement d'une partie sauvegardee au prealable
	 *
	 * @param nomFichier dont on veut recuperer la partie
	 * @return la partie que l'on veut recuperer si elle existe
	 */
	public static Optional<Game> load(final String nomFichier) {
		// Recuperation de la partie que l'on veut reprendre
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final File file = new File("./savedgames/" + nomFichier);
		Optional<Game> game = Optional.empty();
		try {
			game = Optional.of(mapper.readValue(file, Game.class));
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		// Recreer une partie avec les memes proprietes que celle que l'on recupere
		final Game newGameLoaded;
		//Partie PvP
		if (game.get().getPlayer2().getClass().equals(Human.class)) {
			newGameLoaded = new BuildNewGame(game.get().getBoard().getScenario(), game.get().getPlayer1().getName(), game.get().getPlayer2().getName()).getGame();
		} else {
			// Partie PvIA
			if (((AI) game.get().getPlayer2()).getStrategy().getClass().equals(StrategyNoob.class)) {
				newGameLoaded = new BuildNewGame(game.get().getBoard().getScenario(), game.get().getPlayer1().getName(), 0).getGame();
			} else if (((AI) game.get().getPlayer2()).getStrategy().getClass().equals(StrategyStarting.class)) {
				newGameLoaded = new BuildNewGame(game.get().getBoard().getScenario(), game.get().getPlayer1().getName(), 1).getGame();
			} else {
				newGameLoaded = new BuildNewGame(game.get().getBoard().getScenario(), game.get().getPlayer1().getName(), 2).getGame();
			}
		}
		newGameLoaded.setUndoCommand(game.get().getUndoCommand());
		newGameLoaded.setRedoCommand(new ArrayList<Commande>());
		newGameLoaded.setTurn(game.get().getTurn());
		newGameLoaded.getPlayer1().nbAction = game.get().getPlayer1().nbAction;
		newGameLoaded.getPlayer2().nbAction = game.get().getPlayer2().nbAction;
		newGameLoaded.setWin(game.get().isWin());

		final Game g = game.get();
		newGameLoaded.getBoard().getList().forEach(piece -> {
			if (piece.getId() < 7) {
				piece.setPlayer(newGameLoaded.getPlayer1());
			} else {
				piece.setPlayer(newGameLoaded.getPlayer2());
			}
			piece.setHasBall(g.getBoard().getList().get(piece.getId()).getHasBall());
			piece.setTx(g.getBoard().getList().get(piece.getId()).getTx());
			piece.setTy(g.getBoard().getList().get(piece.getId()).getTy());
			IntStream.rangeClosed(0, 6).forEach(i -> {
				IntStream.rangeClosed(0, 6).forEach(j -> {
					if (piece.getTx() == i && piece.getTy() == j) {
						newGameLoaded.getBoard().setValue(piece, i, j);
					}
				});
			});
		});

		System.out.println("Recuperation du fichier " + nomFichier);
		return Optional.of(newGameLoaded);
	}

	/**
	 * Annule la derniere action faite (presente dans la liste undoCommand)
	 */
	public void undoCommande() {
		final Commande commandeAAnnuler = undoCommand.get(0);
		commandeAAnnuler.setPiece(this.board.getValue(commandeAAnnuler.getTx2(), commandeAAnnuler.getTy2()));
		commandeAAnnuler.undo(this);
		redoCommand.add(0, commandeAAnnuler);
		undoCommand.remove(commandeAAnnuler);
	}

	/**
	 * Rejoue la derniere action annulee (presente dans la liste redoCommand)
	 */
	public void redoCommande() {
		final Commande commandeARejouer = redoCommand.get(0);
		commandeARejouer.setPiece(this.board.getValue(commandeARejouer.getTx(), commandeARejouer.getTy()));
		commandeARejouer.redo(this);
		undoCommand.add(0, commandeARejouer);
		redoCommand.remove(commandeARejouer);
	}

	/**
	 * Verifie que deux parties sont egales
	 *
	 * @param o la partie que l'on compare a la notre
	 * @return vrai si les deux parties sont egales, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Game game = (Game) o;
		return win == game.win &&
				Objects.equals(board, game.board) &&
				Objects.equals(j1, game.j1) &&
				Objects.equals(j2, game.j2) &&
				Objects.equals(undoCommand, game.undoCommand) &&
				Objects.equals(redoCommand, game.redoCommand) &&
				Objects.equals(turn, game.turn);
	}

	/**
	 * Hash notre partie et donc ces attributs
	 *
	 * @return le hash correspondant a notre partie
	 */
	@Override
	public int hashCode() {
		return Objects.hash(board, j1, j2, undoCommand, redoCommand, turn, win);
	}

}
