package diaballik.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * Classe abstraite representant une strategie de jeu que peu adopter une IA
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = StrategyNoob.class),
		@JsonSubTypes.Type(value = StrategyProgressive.class),
		@JsonSubTypes.Type(value = StrategyStarting.class)
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class Strategy {
	/**
	 * Execution d'une commande en fonction de la strategie adoptee
	 *
	 * @param game la partie durant laquelle la commande est executee
	 * @return la commande executee
	 */
	public abstract Commande exec(Game game);

}
