package diaballik.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Couleur que les joueurs, les pieces et les balles peuvent avoir
 */
public enum ChoiceColor {
	@JsonProperty("VANILLE")
	VANILLE,
	@JsonProperty("CARAMEL")
	CARAMEL;
}

