package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe représentant un joueur, et plus particulierement un humain
 */
public class Human extends Player {
	/**
	 * Constructeur d'un joueur humain
	 *
	 * @param name le nom que le joueur se choisira au debut d'une partie
	 */
	@JsonCreator
	public Human(@JsonProperty("name") final String name, @JsonProperty("color") final ChoiceColor color) {
		super(name, color);
	}

}
