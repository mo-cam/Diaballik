package diaballik.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import diaballik.model.AI;
import diaballik.model.Board;
import diaballik.model.BuildBoard;
import diaballik.model.BuildBoardStandard;
import diaballik.model.BuildNewGame;
import diaballik.model.Game;
import diaballik.model.Human;
import diaballik.model.MovePiece;
import diaballik.model.Player;
import diaballik.model.StrategyNoob;
import diaballik.serialization.DiabalikJacksonProvider;
import java.io.IOException;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;


import static diaballik.model.ChoiceColor.CARAMEL;
import static diaballik.model.ChoiceColor.VANILLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestMarshalling {
	private static Stream<Object> getInstancesToMarshall() {
		final Player p1 = new Human("Morgane", VANILLE);
		final Player p2 = new Human("Isabelle", CARAMEL);
		final BuildBoard buildBoard = new BuildBoardStandard();
		final Board board = buildBoard.getBoard();
		final AI ai = new AI("IA", CARAMEL, new StrategyNoob());
		final BuildNewGame buildGame = new BuildNewGame(0, "Morgane", 0);
		final Game game = buildGame.getGame();
		final MovePiece movePiece = new MovePiece(0, 0, 1, 0, game);
		movePiece.action(game);
		return Stream.of(p1, p2, ai, board, game);
	}

	@ParameterizedTest
	@MethodSource("getInstancesToMarshall")
	void testMarshall(final Object objectToMarshall) throws IOException {
		final ObjectMapper mapper = new DiabalikJacksonProvider().getMapper();
		final String serializedObject = mapper.writeValueAsString(objectToMarshall);
		System.out.println(serializedObject);
		final Object readValue = mapper.readValue(serializedObject, objectToMarshall.getClass());
		System.out.println(mapper.writeValueAsString(readValue));
		assertEquals(objectToMarshall, readValue);
	}
}
