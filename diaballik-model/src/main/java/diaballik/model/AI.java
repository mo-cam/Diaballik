package diaballik.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe représentant un joueur, et plus particulierement une intelligence artificielle
 */
public class AI extends Player {
	/**
	 * Attributs
	 */
	protected Strategy strategy;

	/**
	 * Constructeur d'une IA
	 */
	@JsonCreator
	public AI(@JsonProperty("name") final String name, @JsonProperty("color") final ChoiceColor color, @JsonProperty("strategy") final Strategy strat) {
		super(name, color);
		this.strategy = strat;
	}

	/**
	 * Getter de la strategie de jeu de l'IA
	 *
	 * @return la strategie de jeu de l'IA
	 */
	public Strategy getStrategy() {
		return this.strategy;
	}

	/**
	 * Permet a l'IA de deplacer une de ses pieces ou sa balle
	 *
	 * @return la commande que vient d'effectuer l'IA
	 */
	public void play(final Game game) {
		final Commande commande = strategy.exec(game);
		commande.action(game);
		game.getUndoCommand().add(0, commande);
	}
}
