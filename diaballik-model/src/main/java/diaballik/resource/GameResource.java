package diaballik.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import diaballik.model.AI;
import diaballik.model.BuildNewGame;
import diaballik.model.Commande;
import diaballik.model.Game;
import diaballik.model.MoveBall;
import diaballik.model.MovePiece;
import diaballik.serialization.DiabalikJacksonProvider;
import io.swagger.annotations.Api;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;
import javax.inject.Singleton;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Classe correspondant au GameResource
 */
@Singleton
@Path("game")
@Api(value = "game", description = "Operations on Diaballik")
public class GameResource {
	/**
	 * Attributs
	 */
	static final Logger LOGGER = Logger.getAnonymousLogger();
	private Optional<Game> game;

	/**
	 * Constructeur
	 */
	public GameResource() {
		super();
		game = Optional.empty();
	}

	/**
	 * Creation d'une nouvelle partie de type PvP
	 *
	 * @param scenario initial du placement des pieces sur le plateau
	 * @param player1  premier joueur de la partie (vanille)
	 * @param player2  second joueur de la partie (caramel)
	 * @return si la creation s'est bien passee
	 */
	@POST
	@Path("newGamePvP/{scenario}/{player1}/{player2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewGamePvP(@PathParam("scenario") final int scenario, @PathParam("player1") final String player1, @PathParam("player2") final String player2) {
		try {
			final BuildNewGame buildNewGame = new BuildNewGame(scenario, player1, player2);
			game = Optional.of(buildNewGame.getGame());
			game.get().getBoard().setScenario(scenario);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Creation d'une nouvelle partie de type PvIA
	 *
	 * @param scenario initial du placement des pieces sur le plateau
	 * @param player1  premier joueur de la partie (vanille)
	 * @param level    niveau du second joueur de la partie, qui est une IA (caramel)
	 * @return si la creation s'est bien passee
	 */
	@POST
	@Path("newGamePvIA/{scenario}/{player1}/{level}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewGamePvIA(@PathParam("scenario") final int scenario, @PathParam("player1") final String player1, @PathParam("level") final int level) {
		try {
			final BuildNewGame buildNewGame = new BuildNewGame(scenario, player1, level);
			game = Optional.of(buildNewGame.getGame());
			game.get().getBoard().setScenario(scenario);
		} catch (IllegalArgumentException | IllegalStateException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Permet a un joueur humain d'effectuer son deplacement
	 *
	 * @param hasBall permet de savoir si l'objet que l'on bouge est une piece ou une balle
	 * @param tx      coordonnee x de la position initiale de la piece
	 * @param ty      coordonnee y de la position initiale de la piece
	 * @param tx2     coordonnee x de la position future de la piece
	 * @param ty2     coordonnee y de la position future de la piece
	 * @return
	 */
	@PUT
	@Path("player/move/{hasBall}/{tx}/{ty}/{tx2}/{ty2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response play(@PathParam("hasBall") final boolean hasBall, @PathParam("tx") final int tx, @PathParam("ty") final int ty, @PathParam("tx2") final int tx2, @PathParam("ty2") final int ty2) {
		final Commande commande;
		System.out.println("Ancienne position : (" + tx + " " + ty + ") nouvelle position = (" + tx2 + " " + ty2 + ")");
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("La partie n'a pas ete creee, on ne peut la sauvegarder !").build();
		}
		try {
			if (hasBall) {
				commande = new MoveBall(tx, ty, tx2, ty2, game.get());
			} else {
				commande = new MovePiece(tx, ty, tx2, ty2, game.get());
			}
			commande.setPiece(game.get().getBoard().getValue(tx, ty));
			commande.action(game.get());
			// La derniere action faite sera la premiere que l'on pourra annuler : ajout en debut de liste
			game.get().getUndoCommand().add(0, commande);
			game.get().win(commande.getPiece().getPlayer());
			System.out.println(new DiabalikJacksonProvider().getMapper().writeValueAsString(game.get()));

			if (game.get().getPlayer1().nbAction == 0 && game.get().getPlayer2().getClass().equals(AI.class)) {
				((AI) game.get().getPlayer2()).play(game.get());
				game.get().win(game.get().getPlayer2());
				((AI) game.get().getPlayer2()).play(game.get());
				game.get().win(game.get().getPlayer2());
				((AI) game.get().getPlayer2()).play(game.get());
				game.get().win(game.get().getPlayer2());
			}
			game.get().getTurn().endTurn();
		} catch (IllegalArgumentException | IllegalStateException | JsonProcessingException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Sauvegarde d'une partie dans un fichier .json
	 *
	 * @param nomfichier nom que l'on donne au fichier de sauvegarde
	 * @return si la sauvegarde s'est bien passee
	 * @throws IOException si l'ecriture dans le fichier s'est mal passee
	 */
	@POST
	@Path("save/{nomfichier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveGame(@PathParam("nomfichier") final String nomfichier) throws IOException {
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Partie non creee, impossible de la sauvegarder !").build();
		}
		// Si la sauvegarde echoue
		try {
			game.get().save(nomfichier);
			System.out.println(new DiabalikJacksonProvider().getMapper().writeValueAsString(game.get()));
		} catch (RuntimeException e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Une erreur est survenue pendant la sauvegarde !").build();
		}

		return Response.ok().build();
	}

	/**
	 * Chargement d'une partie sauvegardee dans un fichier .json
	 *
	 * @param nomFichier nom du fichier de sauvegarde dont on veut recuperer la partie
	 * @return si le chargement s'est bien passee
	 * @throws IOException si la lecture dans le fichier s'est mal passee
	 */
	@GET
	@Path("load/{nomFichier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loadGame(@PathParam("nomFichier") final String nomFichier) {
		game = Game.load(nomFichier);
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Le nom du fichier ne correspond pas à une sauvegarde !").build();
		}
		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Recuperation des parties sauvegardees
	 *
	 * @return si la recuperation s'est bien passee
	 */
	@GET
	@Path("listGameSaved")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listGameSaved() {
		return Response.status(Response.Status.OK).entity(Game.listGameSaved()).build();
	}

	/**
	 * Recuperation du plateau initial et des listes de commandes de la partie que l'on veut revisualiser
	 *
	 * @param nomfichier nom du fichier dont on recupere la partie pour la rejouer
	 * @return si la recuperation s'est bien passee
	 */
	@GET
	@Path("replay/{nomfichier}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replayGame(@PathParam("nomfichier") final String nomfichier) throws IOException {
		game = Game.load(nomfichier);
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Le nom du fichier ne correspond a aucune sauvegarde !").build();
		}

		game.get().getUndoCommand().forEach(commande -> {
			// On annule toutes les commandes de la partie recuperee
			commande.setGame(game.get());
			commande.setPiece(game.get().getBoard().getValue(commande.getTx2(), commande.getTy2()));
			commande.undo(game.get());
			// On ajoute les commandes dans la liste des commandes que l'on peut rejouer
			game.get().getRedoCommand().add(0, commande);
		});
		game.get().getUndoCommand().clear();
		game.get().setWin(false);

		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Annulation d'une commande
	 *
	 * @return si l'annulation s'est bien passee
	 */
	@PUT
	@Path("replay/undo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replayUndo() {
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Impossible de recuperer la sauvegarde, la partie n'existe pas.").build();
		}
		if (game.get().getUndoCommand().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("La partie n'a pas de commandes a annuler.").build();
		}
		try {
			game.get().undoCommande();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Une erreur est survenue pendant le chargement !").build();
		}

		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Rejeu d'une commande
	 *
	 * @return si le rejeu s'est bien passe
	 */
	@PUT
	@Path("replay/redo")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replayRedo() {
		if (!game.isPresent()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("La partie n'existe pas.").build();
		}
		if (game.get().getRedoCommand().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("La partie n'a pas de commandes a rejouer.").build();
		}
		try {
			game.get().redoCommande();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Une erreur est survenue pendant le chargement !").build();
		}

		return Response.ok().entity(game.get()).build();
	}

	/**
	 * Suppression d'une partie, et donc du fichier qui la contient
	 *
	 * @param nomfichier nom du fichier ou se trouve la partie que l'on souhaite supprimer
	 * @return si la suppression s'est bien passee
	 */
	@DELETE
	@Path("load/{nomfichier}")
	public boolean deleteGame(@PathParam("nomfichier") final String nomfichier) {
		final File file = new File("./" + nomfichier + ".json");
		final boolean delete = file.delete();
		return delete;

	}

}




