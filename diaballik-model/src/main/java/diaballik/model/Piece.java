package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


import static diaballik.model.ChoiceColor.VANILLE;
import static diaballik.model.ChoiceColor.CARAMEL;

/**
 * Classe representant une des pieces du jeu
 */
public class Piece {
	/**
	 * Attributs
	 */
	protected int id;
	private boolean hasBall;
	private ChoiceColor color;
	private Player player;
	// Position de la piece
	private int tx;
	private int ty;

	/**
	 * Constructeur d'une piece
	 *
	 * @param id      identifiant de la piece
	 * @param hasBall booleen indiquant si une balle est sur la piece
	 */
	@JsonCreator
	public Piece(@JsonProperty("id") final int id, @JsonProperty("hasBall") final boolean hasBall) {
		this.id = id;
		this.hasBall = hasBall;
		if (this.id < 7) {
			this.color = VANILLE;
		} else {
			this.color = CARAMEL;
		}
	}

	/**
	 * Getter de l'id de la piece
	 *
	 * @return l'id de la piece
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Setter de l'attribut indiquant si la piece partage sa case avec une balle
	 *
	 * @param b Indique si la piece possede une balle
	 */
	public void setHasBall(final boolean b) {
		hasBall = b;
	}

	/**
	 * Getter du boolean hasBall
	 *
	 * @return Si la piece possede une balle ou non
	 */
	public boolean getHasBall() {
		return this.hasBall;
	}

	/**
	 * Getter pour la couleur de la piece
	 */
	public ChoiceColor getColor() {
		return this.color;
	}

	/**
	 * Associe un joueur a notre piece
	 *
	 * @param p Le joueur proprietaire de l'element
	 */
	public void setPlayer(final Player p) {
		this.player = p;
	}

	/**
	 * Getter du joueur proprietaire de la piece
	 *
	 * @return Le joueur proprietaire de l'element
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Setter de la position tx de la piece
	 *
	 * @param tx coordonnee x de la position de la piece
	 */
	public void setTx(final int tx) {
		this.tx = tx;
	}

	/**
	 * Getter de la position x de la piece
	 *
	 * @return tx
	 */
	public int getTx() {
		return tx;
	}

	/**
	 * Setter de la position ty de la piece
	 *
	 * @param ty coordonnee y de la position de la piece
	 */
	public void setTy(final int ty) {
		this.ty = ty;
	}

	/**
	 * getter de la position y de la piece
	 *
	 * @return ty
	 */
	public int getTy() {
		return ty;
	}

	/**
	 * Fonction permettant d'afficher une piece proprement
	 *
	 * @return un affichage d'une piece du jeu
	 */
	@Override
	public String toString() {
		return "Piece{" +
				"id=" + id +
				", hasBall=" + hasBall +
				", color=" + color +
				", player=" + player +
				", tx=" + tx +
				", ty=" + ty +
				'}';
	}

	/**
	 * Verifie que deux pieces sont egales
	 *
	 * @param o la piece que l'on compare a la notre
	 * @return vrai si les deux pieces sont egales, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Piece piece = (Piece) o;
		return id == piece.id &&
				hasBall == piece.hasBall &&
				color == piece.color &&
				Objects.equals(player, piece.player);
	}

	/**
	 * Hash notre piece et donc ces attributs
	 *
	 * @return le hash correspondant a notre piece
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, hasBall, color, player);
	}

}
