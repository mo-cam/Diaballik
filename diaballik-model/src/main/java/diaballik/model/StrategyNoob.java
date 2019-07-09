package diaballik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Classe representant la strategie de jeu que peu adopter une IA, et plus particulierement la strategy noob (0)
 */
public class StrategyNoob extends Strategy {
	/**
	 * Execution d'une commande en fonction de la strategie adoptee
	 *
	 * @param game la partie durant laquelle la commande est executee
	 * @return la commande executee
	 */
	@Override
	public Commande exec(final Game game) {
		final Random random = new Random();
		final int nb = random.nextInt(2);

		// Id des pieces de l'AI, pour choisir une piece a deplacer au hasard
		final ArrayList<Integer> listId = new ArrayList<>(Arrays.asList(7, 8, 9, 10, 11, 12, 13));
		//liste de pieces de l'IA
		final ArrayList<Piece> piecesAI = new ArrayList<>();
		IntStream.rangeClosed(0, 6).forEach(i -> {
			piecesAI.add(game.getBoard().getList().get(listId.get(i)));
		});
		// Piece de l'IA qui a la balle
		final Piece pball = piecesAI.stream().filter(Piece::getHasBall).findFirst().get();

		if (nb == 0) {
			//deplacement aleatoire de la piece
			//liste de tous les deplacements valides possibles de chaque piece
			final ArrayList<MovePiece> deplacementvalide = new ArrayList<MovePiece>();
			IntStream.rangeClosed(0, 6).forEach(i -> {
				IntStream.rangeClosed(0, 6).forEach(j -> {
					piecesAI.forEach(p -> {
						if (new MovePiece(p.getTx(), p.getTy(), i, j, game).canDo(game)) {
							deplacementvalide.add(new MovePiece(p.getTx(), p.getTy(), i, j, game));
						}
					});
				});
			});
			Collections.shuffle(deplacementvalide);
			return deplacementvalide.get(0);

		} else {
			//deplacement aletoire de la balle
			//liste de tous les deplacements valides de la balle
			final ArrayList<MoveBall> deplacementvalide = new ArrayList<>();
			IntStream.rangeClosed(0, 6).forEach(i -> {
				IntStream.rangeClosed(0, 6).forEach(j -> {
					if (new MoveBall(pball.getTx(), pball.getTy(), i, j, game).canDo(game)) {
						deplacementvalide.add(new MoveBall(pball.getTx(), pball.getTy(), i, j, game));
					}
				});
			});
			Collections.shuffle(deplacementvalide);
			return deplacementvalide.get(0);
		}
	}

}
