package diaballik.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe representant une commande qu'un joueur peut effectuer, et plus particulierement le deplacement d'une piece
 */
public class MovePiece extends Commande {
	/**
	 * Attributs
	 */
	@JsonIdentityReference
	private Game game;

	/**
	 * Constructeur
	 *
	 * @param tx  coordonnee x de la position actuelle de la piece
	 * @param ty  coordonnee y de la position actuelle de la piece
	 * @param tx2 coordonnee x de la future position de la piece
	 * @param ty2 coordonnee y de la future position de la piece
	 */
	@JsonCreator
	public MovePiece(@JsonProperty("tx") final int tx, @JsonProperty("ty") final int ty, @JsonProperty("tx2") final int tx2, @JsonProperty("ty2") final int ty2, @JsonProperty("game") final Game game) {
		super(tx, ty, tx2, ty2);
		this.tx = tx;
		this.ty = ty;
		this.tx2 = tx2;
		this.ty2 = ty2;
		this.game = game;
		this.piece = game.getBoard().getValue(tx, ty);
	}

	/**
	 * Affecte une partie a notre deplacement
	 *
	 * @param g la partie pendant laquelle se passe le mouvement
	 */
	@Override
	public void setGame(final Game g) {
		this.game = g;
	}

	/**
	 * Conditions de base :
	 * Verifie si l'endroit vise est bien une case du plateau : 0<=tx2<=6 et 0<=ty2<=6
	 * Verifie si la piece que l'on souhaite bouger ne possede pas de balle
	 *
	 * @return si le deplacement souhaite par le joueur est possible pour l'instant ou non
	 */
	@Override
	public boolean canDoBase(final Game game) {
		return !this.piece.getHasBall() && (tx2 < 7 && tx2 > -1) && (ty2 < 7 && ty2 > -1);
	}

	/**
	 * Verifie si les conditions de base sont validees et si la case visee est bien vide
	 *
	 * @return si le deplacement souhaite par le joueur est possible
	 */
	@Override
	public boolean canDo(final Game game) {
		return canDoBase(game) && game.getBoard().getValue(tx2, ty2) == null &&
				(((tx2 == tx) && ((ty2 == ty - 1) || (ty2 == ty + 1))) ||
						(((tx2 == tx - 1) || (tx2 == tx + 1)) && (ty2 == ty)));
	}

	/**
	 * Correspond a la fonction do() du patron de conception Commande
	 */
	@Override
	public void action(final Game game) {
		if (canDo(game)) {
			// Changement des valeurs des classes du plateau
			game.getBoard().setValue(null, tx, ty);
			game.getBoard().setValue(piece, tx2, ty2);
			piece.getPlayer().nbAction--;
		}
	}

	/**
	 * Annuler un deplacement : commandes de undoCommand de Game vers redoCommand
	 */
	@Override
	public void undo(final Game game) {
		// Changement des valeurs des classes du plateau
		game.getBoard().setValue(piece, tx, ty);
		game.getBoard().setValue(null, tx2, ty2);
	}

	/**
	 * Refaire un deplacement qui a ete annule : commandes de redoCommand de Game vers undoCommand
	 */
	@Override
	public void redo(final Game game) {
		action(game);
	}

}
