package diaballik.resource;

import com.github.hanleyt.JerseyExtension;
import diaballik.model.BuildNewGame;
import diaballik.model.Game;
import diaballik.serialization.DiabalikJacksonProvider;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGameResource {
	@SuppressWarnings("unused")
	@RegisterExtension
	JerseyExtension jerseyExtension = new JerseyExtension(this::configureJersey);

	private Game gameTest0PvP;
	private Game gameTest1PvP;
	private Game gameTest2PvP;
	private Game gameTest0PvIA;

	private Application configureJersey() {
		return new ResourceConfig().
				register(GameResource.class).
				register(MyExceptionMapper.class).
				register(JacksonFeature.class).
				register(DiabalikJacksonProvider.class).
				property("jersey.config.server.tracing.type", "ALL");
	}

	@BeforeEach
	void setUp(final Client client, final URI baseUri) {
		BuildNewGame buildGamePvP0 = new BuildNewGame(0, "Isabelle", "Morgane");
		gameTest0PvP = buildGamePvP0.getGame();
		BuildNewGame buildGamePvP1 = new BuildNewGame(1, "Isabelle", "Morgane");
		gameTest1PvP = buildGamePvP1.getGame();
		BuildNewGame buildGamePvP2 = new BuildNewGame(2, "Isabelle", "Morgane");
		gameTest2PvP = buildGamePvP2.getGame();
		BuildNewGame buildNewGamePvIA0 = new BuildNewGame(0, "Isabelle", 0);
		gameTest0PvIA = buildNewGamePvIA0.getGame();
	}

	@Test
	void testTemplate(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
	}

	@Test
	void testCreationGamePvP(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response response = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testCreationGamePvIA(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response response = client.target(baseUri).path("game/newGamePvIA/0/Isabelle/0").request().post(Entity.json(gameTest0PvIA));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testSaveGame0(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		// Comme on cree une "gameTestPvP", on doit donc ensuite pouvoir la sauvegarder
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP)).readEntity(Game.class);
		Response response = client.target(baseUri).path("game/save/gameTest0PvP").request().post(Entity.json(game));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testSaveGame1(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		// Comme on cree une "gameTestPvP", on doit donc ensuite pouvoir la sauvegarder
		Game game = client.target(baseUri).path("game/newGamePvP/1/Isabelle/Morgane").request().post(Entity.json(gameTest1PvP)).readEntity(Game.class);
		Response response = client.target(baseUri).path("game/save/gameTest1PvP").request().post(Entity.json(game));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testSaveGame2(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		// Comme on cree une "gameTestPvP", on doit donc ensuite pouvoir la sauvegarder
		Game game = client.target(baseUri).path("game/newGamePvP/2/Isabelle/Morgane").request().post(Entity.json(gameTest2PvP)).readEntity(Game.class);
		Response response = client.target(baseUri).path("game/save/gameTest2PvP").request().post(Entity.json(game));
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testListSavedGames(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		File before = new File("./savedgames/");
		int expected = before.list().length;
		final Response res = client.target(baseUri).path("game/listGameSaved").request().get();
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
		Assertions.assertEquals(expected, res.readEntity(ArrayList.class).size());
	}

	@Test
	void testSaveError(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		// Comme on ne cree pas la "gameTestPvP", on doit avoir une BAD_REQUEST comme retour
		final Response res = client.target(baseUri).path("game/save/gameTest0PvP").request().post(Entity.text(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}

	@Test
	void testLoadGame1(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isa/Mo").request().post(Entity.text("")).readEntity(Game.class);
		client.target(baseUri).path("game/save/gameTest01PvP").request().post(Entity.json(game));
		Response response = client.target(baseUri).path("game/load/gameTest01PvP.json").request().get();
		assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
	}

	@Test
	void testLoadGame2(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response game = client.target(baseUri).path("game/load/gameTest0PvP.json").request().get();
		assertEquals(Response.Status.OK.getStatusCode(), game.getStatus());
	}

	@Test
	void testLoadGame3(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/load/gameTest0PvP.json").request().get().readEntity(Game.class);
		final Response res = client.target(baseUri).path("game/player/move/false/0/0/1/0").request().put(Entity.json(game));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testLoadGame4(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvIA/0/Isabelle/1").request().post(Entity.text("")).readEntity(Game.class);
		client.target(baseUri).path("game/player/move/false/0/0/1/0").request().put(Entity.json(game));
		client.target(baseUri).path("game/save/gameTestLoadPvIA").request().post(Entity.json(game));
		final Response res = client.target(baseUri).path("game/load/gameTestLoadPvIA.json").request().get();
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testReplayGame(final Client client, final URI baseUri) throws IOException {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP)).readEntity(Game.class);
		client.target(baseUri).path("game/save/gameTestPvP").request().post(Entity.json(game));
		final Response res = client.target(baseUri).path("game/replay/gameTestPvP.json").request().get();
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testUndoNotFound(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response res = client.target(baseUri).path("game/replay/undo").request().put(Entity.text(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}

	@Test
	void testPreviousReplaySuccess(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP)).readEntity(Game.class);
		client.target(baseUri).path("game/player/move/false/0/1/1/1").request().put(Entity.json(game));
		client.target(baseUri).path("game/save/gameTestPvP").request().post(Entity.json(game));
		client.target(baseUri).path("game/replay/gameTestPvP").request().get();
		final Response resNext = client.target(baseUri).path("game/replay/redo").request().put(Entity.text(""));
		final Response res = client.target(baseUri).path("game/replay/undo").request().put(Entity.text(""));
		Assertions.assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testRedoNotFound(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		final Response res = client.target(baseUri).path("game/replay/redo").request().put(Entity.text(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}

	@Test
	void testDeplacementPiece1(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP)).readEntity(Game.class);
		// On deplace la piece rouge initialement en [6][1] jusqu'en [5][1] (vertical)
		final Response res = client.target(baseUri).path("game/player/move/false/0/0/1/0").request().put(Entity.json(game));

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testDeplacementPiece2(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvIA/0/Isabelle/0").request().post(Entity.json(gameTest0PvIA)).readEntity(Game.class);
		final Response res = client.target(baseUri).path("game/player/move/false/0/0/1/0").request().put(Entity.json(game));
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	void testDeplacementBall(final Client client, final URI baseUri) {
		client.register(JacksonFeature.class).register(DiabalikJacksonProvider.class);
		Game game = client.target(baseUri).path("game/newGamePvP/0/Isabelle/Morgane").request().post(Entity.json(gameTest0PvP)).readEntity(Game.class);
		// On deplace la piece rouge initialement en [6][1] jusqu'en [4][1], on peut ainsi deplacer la balle (diagonal)
		client.target(baseUri).path("game/player/move/false/6/1/5/1").request().put(Entity.json(game));
		client.target(baseUri).path("game/player/move/false/5/1/4/1").request().put(Entity.json(game));
		final Response res = client.target(baseUri).path("game/player/move/true/6/3/4/1").request().put(Entity.json(game));

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

}
