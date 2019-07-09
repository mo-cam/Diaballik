package diaballik.model;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Classe représentant la construction du plateau de jeu pour le scenario 0
 */
public class BuildBoardStandard extends BuildBoard {
	/**
	 * Constructeur
	 */
	public BuildBoardStandard() {
		setBoard(new Board());

		// Initialisation des valeurs des cases du plateau de jeu
		IntStream.rangeClosed(0, 6).forEach(i -> {
			IntStream.rangeClosed(0, 6).forEach(j -> {
				getBoard().setValue(null, i, j);
			});
		});
	}

	/**
	 * Placer les pieces de chaque joueur sur leur ligne et les balles au centre
	 *
	 * @param pieces correspond a la liste des pieces a placer
	 */
	@Override
	public void placerPieces(final List<Piece> pieces) {
		// Recuperation des cases du plateau
		pieces.forEach(p -> {
			// Placement des pieces du joueur 1
			if (p.getId() < 7) {
				getBoard().setValue(p, 0, p.getId());
				// Placement des pieces du joueur 2
			} else {
				getBoard().setValue(p, 6, p.getId() % 7);
			}
		});
	}
}
