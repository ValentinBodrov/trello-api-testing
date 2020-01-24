import api.BoardApi;
import beans.Board;
import org.testng.annotations.Test;

import static api.BoardApi.notFoundSpecification;
import static api.ServiceSpecification.generateRandomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BoardTest {

    @Test
    public void simpleGetBoardTest() {
        String generatedString = generateRandomString();
        Board createdBoard = BoardApi.createBoard(generatedString);

        Board board = BoardApi.getBoard(createdBoard.id);
        assertThat(board.name, equalTo(generatedString));

        BoardApi.deleteBoard(board.id);
    }

    @Test
    public void simpleCreateBoardTest() {
        String generatedString = generateRandomString();
        Board board = BoardApi.createBoard(generatedString);
        assertThat(board.name, equalTo(generatedString));

        BoardApi.deleteBoard(board.id);
    }

    @Test
    public void simpleDeleteBoardTest() {
        String generatedString = generateRandomString();
        Board board = BoardApi.createBoard(generatedString);
        BoardApi.deleteBoard(board.id);

        BoardApi
                .with()
                .getBoard(board.id)
                .then()
                .specification(notFoundSpecification());
    }

    @Test
    public void simpleUpdateBoardTest() {
        String generatedString = generateRandomString();
        Board board = BoardApi.createBoard(generatedString);
        String newGeneratedString = generateRandomString();
        board = BoardApi.updateBoard(board.id, newGeneratedString);
        assertThat(board.name, equalTo(newGeneratedString));

        BoardApi.deleteBoard(board.id);
    }
}
