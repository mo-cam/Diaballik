package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;

/**
 * Classe representant une commande qu'un joueur peut effectuer
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
		@JsonSubTypes.Type(value = MoveBall.class),
		@JsonSubTypes.Type(value = MovePiece.class)
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Commande {
	/**
	 * Attributs
	 */
	protected Piece piece;
	// Ancienne position
	int tx;
	int ty;
	// Nouvelle position
	int tx2;
	int ty2;

	/**
	 * Constructeur
	 */
	@JsonCreator
	public Commande(@JsonProperty("tx") final int tx, @JsonProperty("ty") final int ty, @JsonProperty("tx2") final int tx2, @JsonProperty("ty2") final int ty2) {
		super();
	}

	/**
	 * Getter de la coordonnee x de la position d'une piece
	 *
	 * @return la coordonnee x de la position d'une piece
	 */
	public int getTx() {
		return tx;
	}

	/**
	 * Setter de la coordonnee x de la position d'une piece
	 *
	 * @param newX Correspond a la coordonnee x de la nouvelle position d'une piece
	 */
	public void setTx(final int newX) {
		tx = newX;
	}

	/**
	 * Getter de la coordonnee y de la position d'une piece
	 *
	 * @return la coordonee y de la position d'une piece
	 */
	public int getTy() {
		return ty;
	}

	/**
	 * Setter de la coordonnee y de la position d'une piece
	 *
	 * @param newY Correspond a la coordonnee y de la nouvelle position d'une piece
	 */
	public void setTy(final int newY) {
		ty = newY;
	}

	/**
	 * Getter de la coordonnee x de la future position de la piece
	 *
	 * @return la future coordonnee x de la position de la piece
	 */
	public int getTx2() {
		return tx2;
	}

	/**
	 * Getter de la coordonnee y de la future position de la piece
	 *
	 * @return la future coordonnee y de la position de la piece
	 */
	public int getTy2() {
		return ty2;
	}

	/**
	 * Affecte une partie a la commande
	 *
	 * @param g la partie pendant laquelle se passe le mouvement
	 */
	public abstract void setGame(final Game g);

	/**
	 * Affecte une piece a la commande
	 *
	 * @param p la piece que l'on bouge
	 */
	public void setPiece(final Piece p) {
		this.piece = p;
	}

	/**
	 * Getter de la piece que l'on veut bouger
	 *
	 * @return la piece que l'on veut bouger
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * Verification du debut de la validite du deplacement souhaite par le joueur
	 *
	 * @return si le deplacement souhaite par le joueur est possible pour l'instant ou non
	 */
	public abstract boolean canDoBase(final Game game);

	/**
	 * Verification de la validite du deplacement souhaite par le joueur
	 *
	 * @return si le deplacement souhaite par le joueur est finalement possible
	 */
	public abstract boolean canDo(final Game game);

	/**
	 * Correspond a la fonction do() du patron de conception Commande
	 */
	public abstract void action(final Game game);

	/**
	 * Annuler un deplacement : commandes de undoCommand de Game vers redoCommand
	 */
	public abstract void undo(final Game game);

	/**
	 * Refaire un deplacement qui a ete annule : commandes de redoCommand de Game vers undoCommand
	 */
	public abstract void redo(final Game game);

	/**
	 * Verifie si deux commandes sont egales
	 *
	 * @param o commande que l'on commande compare a la notre
	 * @return vrai si la commande correspond a la notre, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Commande commande = (Commande) o;
		return tx == commande.tx &&
				ty == commande.ty &&
				tx2 == commande.tx2 &&
				ty2 == commande.ty2;
	}

	/**
	 * Hash notre commande
	 *
	 * @return le hashcode de notre commande
	 */
	@Override
	public int hashCode() {
		return Objects.hash(tx, ty, tx2, ty2);
	}

}
