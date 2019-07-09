package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Classe représentant le plateau de jeu d'une partie
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Board {
	/**
	 * Attributs
	 */
	// Scenario initial du plateau
	private int scenario;
	// Le plateau a une taille fixe de 7x7 cases
	@JsonProperty("boardTiles")
	public Piece[][] boardTiles = new Piece[7][7];
	// Les pieces sont placees sur un plateau
	@JsonProperty("list")
	private List<Piece> list = new ArrayList<Piece>();

	/**
	 * Constructeur d'un plateau de jeu
	 */
	@JsonCreator
	public Board() {
	}

	/**
	 * Getter du scenario de construction de la partie
	 *
	 * @return le numero du scenario
	 */
	public int getScenario() {
		return scenario;
	}

	/**
	 * Setter du numero du scenario du plateau de la partie
	 *
	 * @param scenario le scenario de la partie
	 */
	public void setScenario(final int scenario) {
		this.scenario = scenario;
	}

	/**
	 * Getter du tableau representant les cases du plateau de jeu
	 *
	 * @return le tableau de pieces
	 */
	private Piece[][] getBoardTiles() {
		return this.boardTiles;
	}

	/**
	 * Setter de nos pieces
	 *
	 * @param pieces la liste de pieces que l'on ajoute a notre board
	 */
	public void setList(final List<Piece> pieces) {
		IntStream.range(0, pieces.size()).forEach(i -> {
			this.list.add(pieces.get(i));
		});
	}

	/**
	 * Getter de nos pieces
	 *
	 * @return
	 */
	public List<Piece> getList() {
		return this.list;
	}

	/**
	 * Perrmet d'ajouter une piece a la liste de pieces du plateau
	 *
	 * @param index indice auquel on ajoute la piece dans la liste
	 * @param piece piece que l'on ajoute a la liste de pieces
	 */
	public void addPiece(final int index, final Piece piece) {
		list.add(index, piece);
	}

	/**
	 * Getter de la piece se trouvant dans la case a la ligne x et colonne y
	 *
	 * @param x coordonnee x
	 * @param y coordonnee y
	 * @return la piece dans la case x, y
	 */
	public Piece getValue(final int x, final int y) {
		return this.boardTiles[x][y];
	}

	/**
	 * Permet de changer la valeur de la case du plateau une fois la piece bougee
	 *
	 * @param p piece que l'on a place sur une certaine case du plateau
	 * @param x coordonnee x de la position ou l'on a place la piece
	 * @param y coordonnee y de la position ou l'on a place la piece
	 */
	public void setValue(final Piece p, final int x, final int y) {
		if (p != null) {
			p.setTx(x);
			p.setTy(y);
		}
		this.boardTiles[x][y] = p;
	}

	/**
	 * Fonction permettant d'afficher le contenu du plateau proprement
	 *
	 * @return un affichage du plateau de jeu
	 */
	@Override
	public String toString() {
		return "Board{" +
				"boardTiles=" + Arrays.toString(boardTiles) +
				", list=" + list +
				'}';
	}

	/**
	 * Verifie que deux plateaux sont egaux
	 *
	 * @param o le plateau que l'on compare au notre
	 * @return vrai si les deux plateaux sont egaux, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Board board = (Board) o;
		final int bool[] = new int[1];
		bool[0] = 0;
		IntStream.range(0, 7).forEach(i -> {
			IntStream.range(0, 7).forEach(j -> {
				if (this.boardTiles[i][j] == null && board.getBoardTiles()[i][j] == null) {
					bool[0] = bool[0] + 1;
				} else if ((this.boardTiles[i][j].equals(board.getBoardTiles()[i][j]))) {
					bool[0] = bool[0] + 1;
				}
			});
		});
		return bool[0] == 49;
	}

	/**
	 * Hash notre plateau et donc ces attributs
	 *
	 * @return le hash correspondant a notre plateau
	 */
	@Override
	public int hashCode() {
		int result = Objects.hash(list);
		result = 31 * result + Arrays.hashCode(boardTiles);
		return result;
	}
}
