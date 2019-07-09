package diaballik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Classe représentant la construction du plateau de jeu pour le scenario 1
 */
public class BuildBoardRandom extends BuildBoard {
	/**
	 * Constructeur
	 */
	BuildBoardRandom() {
		setBoard(new Board());

		// Initialisation des valeurs des cases du plateau de jeu
		IntStream.rangeClosed(0, 6).forEach(i -> {
			IntStream.rangeClosed(0, 6).forEach(j -> {
				getBoard().setValue(null, i, j);
			});
		});
	}

	/**
	 * Placer les pieces de chaque joueur sur leur ligne et les balles aleatoirement sur cette ligne
	 *
	 * @param pieces correspond a la liste des pieces a placer
	 */
	@Override
	public void placerPieces(final List<Piece> pieces) {
		// Positions aleatoires des balles
		final ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));
		Collections.shuffle(list);
		final int ballJ1 = list.get(0);
		Collections.shuffle(list);
		final int ballJ2 = list.get(0);

		// Recuperation des cases du plateau
		pieces.forEach(p -> {
			// Placement des pieces du joueur 1
			if (p.getId() < 7) {
				getBoard().setValue(p, 0, p.getId());
				if (p.getId() == ballJ1) {
					p.setHasBall(true);
				} else {
					p.setHasBall(false);
				}
				// Placement des pieces du joueur 2
			} else {
				getBoard().setValue(p, 6, p.getId() % 7);
				if ((p.getId() % 7) == ballJ2) {
					p.setHasBall(true);
				} else {
					p.setHasBall(false);
				}
			}
		});
	}
}
