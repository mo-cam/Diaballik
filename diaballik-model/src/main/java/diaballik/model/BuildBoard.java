package diaballik.model;

import java.util.List;

/**
 * Classe abstraite représentant la construction du plateau de jeu
 */
public abstract class BuildBoard {
	/**
	 * Attributs
	 */
	private Board board;

	/**
	 * Getter du plateau que l'on construit
	 *
	 * @return le plateau que l'on construit
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Setter d'un plateau
	 *
	 * @param b le plateau du quel on va partir pour creer notre plateau
	 */
	public void setBoard(final Board b) {
		this.board = b;
	}

	/**
	 * Permet le placement des pieces sur le plateau en fonction du scenario
	 *
	 * @param elements correspond a la liste des pieces a placer
	 */
	public abstract void placerPieces(List<Piece> elements);
}
