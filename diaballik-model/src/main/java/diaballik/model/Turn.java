package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

/**
 * Classe representant les tours d'une partie
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Turn {
	/**
	 * Attributs
	 */
	@JsonProperty("nbTurn")
	private int nbTurn;
	@JsonIgnore
	protected Player p1;
	@JsonIgnore
	protected Player p2;

	/**
	 * Constructeur d'un tour
	 *
	 * @param nbTurn numero du tour d'une partie
	 */
	@JsonCreator
	public Turn(@JsonProperty("nbTurn") final int nbTurn) {
		this.nbTurn = nbTurn;
	}

	/**
	 * Setter du numero du tour en cours de la partie
	 *
	 * @param nbTurn le numero du tour
	 */
	public void setNbTurn(final int nbTurn) {
		this.nbTurn = nbTurn;
	}

	/**
	 * Getter du numero du tour en cours de la partie
	 *
	 * @return le numero du tour en cours de la partie
	 */
	public int getNbTurn() {
		return nbTurn;
	}

	/**
	 * Setter du premier joueur du tour
	 *
	 * @param p1 le premier joueur du tour, qui correspond au premier joueur de la partie
	 */
	public void setP1(final Player p1) {
		this.p1 = p1;
	}

	/**
	 * Getter du premier joueur du tour
	 *
	 * @return le premier joueur du tour
	 */
	public Player getP1() {
		return p1;
	}

	/**
	 * Setter du second joueur du tour
	 *
	 * @param p2 le second joueur du tour, qui correspond au second joueur de la partie
	 */
	public void setP2(final Player p2) {
		this.p2 = p2;
	}

	/**
	 * Getter du second joueur du tour
	 *
	 * @return le second joueur du tour
	 */
	public Player getP2() {
		return p2;
	}

	/**
	 * Si le tour est termine, on reinitialise le nombre d'actions restantes de chaque joueur
	 *
	 * @return si un tour est termine ou non
	 */
	public boolean endTurn() {
		if ((p1.nbAction == 0) && (p2.nbAction == 0)) {
			nbTurn++;
			p1.nbAction = 3;
			p2.nbAction = 3;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Verifie que deux tours sont egaux
	 *
	 * @param o le tour que l'on compare au notre
	 * @return vrai si les deux tours sont egaux, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Turn turn = (Turn) o;
		return nbTurn == turn.nbTurn &&
				Objects.equals(p1, turn.getP1()) &&
				Objects.equals(p2, turn.getP2());
	}

	/**
	 * Hash notre tour et donc ces attributs
	 *
	 * @return le hash correspondant a notre tour
	 */
	@Override
	public int hashCode() {
		return Objects.hash(nbTurn, p1, p2);
	}
}
