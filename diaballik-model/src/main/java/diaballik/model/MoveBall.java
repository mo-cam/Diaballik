package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;

/**
 * Classe representant une commande qu'un joueur peut effectuer, et plus particulierement le deplacement d'une balle
 */
public class MoveBall extends Commande {
	/**
	 * Attributs
	 */
	@JsonIdentityReference
	private Game game;

	/**
	 * Constructeur
	 *
	 * @param tx  coordonnee x de la position actuelle de la balle
	 * @param ty  coordonnee y de la position actuelle de la balle
	 * @param tx2 coordonnee x de la future position de la balle
	 * @param ty2 coordonnee y de la future position de la balle
	 */
	@JsonCreator
	public MoveBall(@JsonProperty("tx") final int tx, @JsonProperty("ty") final int ty, @JsonProperty("tx2") final int tx2, @JsonProperty("ty2") final int ty2, @JsonProperty("game") final Game game) {
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
	 * Verifie si la piece que l'on souhaite bouger possede une balle
	 * Verifie si la case est bien occupee par une piece du joueur qui souhaite faire le deplacement
	 *
	 * @return si le deplacement souhaite par le joueur est possible pour l'instant ou non
	 */
	@Override
	public boolean canDoBase(final Game game) {
		return piece.getHasBall() && ((tx2 < 7 && tx2 > -1) && (ty2 < 7 && ty2 > -1)) &&
				(game.getBoard().getValue(tx2, ty2) != null &&
						game.getBoard().getValue(tx2, ty2).getPlayer().equals(this.piece.getPlayer()) &&
						!game.getBoard().getValue(tx2, ty2).getHasBall());
	}

	/**
	 * Verifie si un deplacement est possible de (tx,ty) a (tx2,ty)
	 *
	 * @return si un deplacement vertical est possible
	 */
	private boolean deplacementVertical(final Game game) {
		final AtomicBoolean res = new AtomicBoolean(true);
		if (ty != ty2) {
			res.set(false);
		}
		if (tx2 > tx) {
			IntStream.range(1, tx2 - tx).forEach(i -> {
				if (game.getBoard().getValue(tx + i, ty) != null) {
					res.set(false);
				}
			});
		} else {
			IntStream.range(1, tx - tx2).forEach(i -> {
				if (game.getBoard().getValue(tx - i, ty) != null) {
					res.set(false);
				}
			});
		}
		return res.get();
	}

	/**
	 * Verifie si un deplacement est possible de (tx,ty) a (tx,ty2)
	 *
	 * @return si un deplacement horizontal est possible
	 */
	private boolean deplacementHorizontal(final Game game) {
		final AtomicBoolean res = new AtomicBoolean(true);
		if (tx != tx2) {
			res.set(false);
		}
		if (ty2 > ty) {
			IntStream.range(1, ty2 - ty).forEach(i -> {
				if (game.getBoard().getValue(tx, ty + i) != null) {
					res.set(false);
				}
			});
		} else {
			IntStream.range(1, ty - ty2).forEach(i -> {
				if (game.getBoard().getValue(tx, ty - i) != null) {
					res.set(false);
				}
			});
		}
		return res.get();
	}

	/**
	 * Verifie si un deplacement est possible de (tx,ty) a (tx2,ty2)
	 *
	 * @return si un deplacement en diagonal est possible
	 */
	private boolean deplacementDiagonales1(final Game game) {
		final AtomicBoolean res = new AtomicBoolean(true);
		if (tx2 == tx || ty2 == ty) {
			res.set(false);
		}
		// Haut droit
		if (tx2 > tx && ty2 > ty) {
			IntStream.range(1, tx2 - tx).forEach(i -> {
				if (game.getBoard().getValue(tx + i, ty + i) != null) {
					res.set(false);
				}
			});
		}
		// Bas gauche
		if (tx2 < tx && ty2 < ty) {
			IntStream.range(1, tx - tx2).forEach(i -> {
				if (game.getBoard().getValue(tx - i, ty - i) != null) {
					res.set(false);
				}
			});
		}
		return res.get();
	}

	/**
	 * Est-ce que la case que l'on vise est bien dans la diagonale de la case d'ou on part ?
	 *
	 * @return si c'est le cas ou non
	 */
	private boolean estDansDiagonale1(final Game game) {
		return ((tx2 > tx) && (ty2 > ty) && (tx2 - tx == ty2 - ty)) ||
				((tx2 < tx) && (ty2 < ty) && (tx - tx2 == ty - ty2));
	}

	/**
	 * Verifie si un deplacement est possible de (tx,ty) a (tx2,ty2)
	 *
	 * @return si un deplacement en diagonal est possible
	 */
	private boolean deplacementDiagonales2(final Game game) {
		final AtomicBoolean res = new AtomicBoolean(true);
		if (tx2 == tx || ty2 == ty) {
			res.set(false);
		}
		// Haut gauche
		if (tx2 > tx && ty2 < ty) {
			IntStream.range(1, ty2 - ty).forEach(i -> {
				if (game.getBoard().getValue(tx + i, ty - i) != null) {
					res.set(false);
				}
			});
		}
		// Bas droit
		if (tx2 < tx && ty2 > ty) {
			IntStream.range(1, tx - tx2).forEach(i -> {
				if (game.getBoard().getValue(tx - i, ty + i) != null) {
					res.set(false);
				}
			});
		}
		return res.get();
	}

	/**
	 * Est-ce que la case que l'on vise est bien dans la diagonale de la case d'ou on part ?
	 *
	 * @return si c'est le cas ou non
	 */
	private boolean estDansDiagonale2(final Game game) {
		return ((tx2 > tx) && (ty2 < ty) && (tx2 - tx == ty - ty2)) ||
				((tx2 < tx) && (ty2 > ty) && (tx - tx2 == ty2 - ty));
	}

	/**
	 * Verifie si les conditions de base sont verifiees et si un deplacement est possible
	 *
	 * @return si le deplacement souhaite par le joueur est possible ou non
	 */
	@Override
	public boolean canDo(final Game game) {
		return canDoBase(game) && (deplacementVertical(game) || deplacementHorizontal(game) ||
				(estDansDiagonale1(game) && deplacementDiagonales1(game)) ||
				(estDansDiagonale2(game) && deplacementDiagonales2(game)));
	}

	/**
	 * Correspond a la fonction do() du patron de conception Commande
	 */
	@Override
	public void action(final Game game) {
		if (canDo(game)) {
			// Changement des valeurs des classes du plateau
			game.getBoard().getValue(tx2, ty2).setHasBall(true);
			game.getBoard().getValue(tx, ty).setHasBall(false);
			piece.getPlayer().nbAction--;
		}
	}

	/**
	 * Annuler un deplacement : commandes de undoCommand de Game vers redoCommand
	 */
	@Override
	public void undo(final Game game) {
		// Changement des valeurs des classes du plateau
		game.getBoard().getValue(tx, ty).setHasBall(true);
		game.getBoard().getValue(tx2, ty2).setHasBall(false);
	}

	/**
	 * Refaire un deplacement qui a ete annule : commandes de redoCommand de Game vers undoCommand
	 */
	@Override
	public void redo(final Game game) {
		action(game);
	}

}
