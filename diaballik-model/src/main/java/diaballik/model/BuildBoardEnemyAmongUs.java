package diaballik.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Classe représentant la construction du plateau de jeu pour le scenario 2
 */
public class BuildBoardEnemyAmongUs extends BuildBoard {
	/**
	 * Constructeur
	 */
	BuildBoardEnemyAmongUs() {
		setBoard(new Board());

		// Initialisation des valeurs des cases du plateau de jeu
		IntStream.rangeClosed(0, 6).forEach(i -> {
			IntStream.rangeClosed(0, 6).forEach(j -> {
				getBoard().setValue(null, i, j);
			});
		});
	}

	/**
	 * Placer 2 pieces d'un joueur sur la ligne adverse et 5 pieces sur sa ligne
	 * Placer la balle d'un joueur au centre de sa ligne
	 *
	 * @param pieces correspond a la liste des pieces a placer
	 */
	@Override
	public void placerPieces(final List<Piece> pieces) {
		// Separation de la liste des elements en piecesJ1 et piecesJ2
		final List<Piece> piecesJ1APlacer = new ArrayList<Piece>();
		IntStream.rangeClosed(0, 6).forEach(i -> {
			if (!pieces.get(i).getHasBall()) {
				piecesJ1APlacer.add(pieces.get(i));
			}
		});
		final List<Piece> piecesJ2APlacer = new ArrayList<Piece>();
		IntStream.range(0, pieces.size()).forEach(i -> {
			if (pieces.get(i).getId() > 6) {
				if (!pieces.get(i).getHasBall()) {
					piecesJ2APlacer.add(pieces.get(i));
				}
			}
		});

		// Positions aleatoires des pieces
		final ArrayList<Integer> listJ1 = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6));
		Collections.shuffle(listJ1);
		final int piece1J1 = listJ1.get(0);
		System.out.println(piece1J1);
		final int piece2J1 = listJ1.get(1);
		System.out.println(piece2J1);
		final ArrayList<Integer> listJ2 = new ArrayList<>(Arrays.asList(0, 1, 2, 4, 5, 6));
		Collections.shuffle(listJ2);
		final int piece1J2 = listJ2.get(0);
		System.out.println(piece1J2);
		final int piece2J2 = listJ2.get(1);
		System.out.println(piece2J2);

		// Placement des pieces qui ont la balle
		getBoard().setValue(pieces.get(3), 0, 3);
		getBoard().setValue(pieces.get(10), 6, 3);

		/* Placement des pieces qui vont sur la ligne de leur adversaire */

		final List<Piece> piecesPlaceesJ1 = new ArrayList<Piece>();
		// Placement des pieces couleur vanille n'ayant pas la balle
		piecesJ1APlacer.forEach(p -> {
			if (p.getId() == piece1J1 || p.getId() == piece2J1) {
				getBoard().setValue(p, 6, p.getId());
				piecesPlaceesJ1.add(p);
			}
		});
		piecesPlaceesJ1.forEach(piecesJ1APlacer::remove);
		//Placement des pieces couleur caramel n'ayant pas la balle
		final List<Piece> piecesPlaceesJ2 = new ArrayList<Piece>();
		piecesJ2APlacer.forEach(p -> {
			if ((p.getId() % 7) == piece1J2 || (p.getId() % 7) == piece2J2) {
				getBoard().setValue(p, 0, p.getId() % 7);
				piecesPlaceesJ2.add(p);
			}
		});
		piecesPlaceesJ2.forEach(piecesJ2APlacer::remove);

		/* Placement des autres pieces */

		//Placement des autres pieces couleur vanille
		placerPiecesJ1(piecesJ1APlacer, piece1J1, piece2J1, piece1J2, piece2J2);
		// Placement des autres pieces couleur caramel
		placerPiecesJ2(piecesJ2APlacer, piece1J1, piece2J1, piece1J2, piece2J2);

	}

	/**
	 * Placement des pieces de couleur vanille n'ayant pas la balle
	 *
	 * @param pieces   liste des pieces vanille n'ayant pas la balle dans l'ordre croissant des id
	 * @param piece1J1 nombre aleatoire de la position d'une piece vanille positionnee sur la ligne 6
	 * @param piece2J1 nombre aleatoire de la position d'une piece vanille positionnee sur la ligne 6
	 * @param piece1J2 nombre aleatoire de la position d'une piece caramel a positionneer sur la ligne 0
	 * @param piece2J2 nombre aleatoire de la position d'une piece caramel a positionneer sur la ligne 0
	 */
	private void placerPiecesJ1(final List<Piece> pieces, final int piece1J1, final int piece2J1, final int piece1J2, final int piece2J2) {
		final AtomicInteger c = new AtomicInteger();
		pieces.forEach(p -> {
			c.set(0);
			IntStream.rangeClosed(0, 6).forEach(i -> {
				if ((getBoard().getValue(0, i) == null) && (c.get() != 1)) {
					getBoard().setValue(p, 0, i);
					c.set(1);
				}
			});
		});
	}

	/**
	 * Placement des pieces de couleur caramel n'ayant pas la balle
	 *
	 * @param pieces   liste des pieces caramel n'ayant pas la balle dans l'ordre croissant des id
	 * @param piece1J1 nombre aleatoire de la position d'une piece vanille positionnee sur la ligne 6
	 * @param piece2J1 nombre aleatoire de la position d'une piece vanille positionnee sur la ligne 6
	 * @param piece1J2 nombre aleatoire de la position d'une piece caramel a positionneer sur la ligne 0
	 * @param piece2J2 nombre aleatoire de la position d'une piece caramel a positionneer sur la ligne 0
	 */
	private void placerPiecesJ2(final List<Piece> pieces, final int piece1J1, final int piece2J1, final int piece1J2, final int piece2J2) {
		final AtomicInteger c = new AtomicInteger();
		pieces.forEach(p -> {
			c.set(0);
			IntStream.rangeClosed(0, 6).forEach(i -> {
				if ((getBoard().getValue(6, i) == null) && (c.get() != 1)) {
					getBoard().setValue(p, 6, i);
					c.set(1);
				}
			});
		});
	}

}
