package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Objects;

/**
 * Classe représentant un joueur
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = Human.class),
		@JsonSubTypes.Type(value = AI.class),
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Player {
	/**
	 * Attributs
	 */
	private String name;
	private ChoiceColor color;
	// Il reste 3 deplacements a effectuer au joueur au debut d'un tour
	public int nbAction;

	/**
	 * Constructeur d'un joueur
	 */
	@JsonCreator
	public Player(@JsonProperty("name") final String name, @JsonProperty("color") final ChoiceColor color) {
		this.name = name;
		this.color = color;
		this.nbAction = 3;
	}

	/**
	 * Getter du nom du joueur
	 *
	 * @return le nom du joueur
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter pour le nom du joueur
	 *
	 * @param name Le nom que l'on donne au joueur
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Getter de la couleur du joueur
	 *
	 * @return la couleur du joueur
	 */
	public ChoiceColor getColor() {
		return this.color;
	}

	/**
	 * Setter de la couleur d'un joueur
	 *
	 * @param color couleur que l'on affecte au joueur
	 */
	public void setColor(final ChoiceColor color) {
		this.color = color;
	}

	/**
	 * Verifie que deux joueurs sont egaux
	 *
	 * @param o le joueur que l'on compare au notre
	 * @return vrai si les deux joueurs sont egaux, faux sinon
	 */
	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final Player player = (Player) o;
		return nbAction == player.nbAction &&
				Objects.equals(name, player.name) &&
				Objects.equals(color, player.color);
	}

	/**
	 * Hash notre joueur et donc ces attributs
	 *
	 * @return le hash correspondant a notre joueur
	 */
	@Override
	public int hashCode() {
		return Objects.hash(name, color, nbAction);
	}
}
