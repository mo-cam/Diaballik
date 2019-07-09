package diaballik.model;

/**
 * Classe representant la strategie de jeu que peu adopter une IA, et plus particulierement la strategy progressive (2)
 */
public class StrategyProgressive extends Strategy {
	/**
	 * Execution d'une commande en fonction de la strategie adoptee
	 *
	 * @param game la partie durant laquelle la commande est executee
	 * @return la commande executee
	 */
	@Override
	public Commande exec(final Game game) {
		Strategy strategy = new StrategyNoob();
		if (game.getTurn().getNbTurn() < 5) {
			return strategy.exec(game);
		} else {
			strategy = new StrategyStarting();
			return strategy.exec(game);
		}
	}

}
