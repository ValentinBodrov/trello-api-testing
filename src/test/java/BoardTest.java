import api.BoardApi;
import beans.Board;
import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static api.BoardApi.responseSpecification;
import static constants.TrelloConstants.CONST_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest {

    @Test
    public void getBoardTest() {
        Board answers =
                BoardApi.getBoard(BoardApi.with().getBoard(CONST_ID));
        assertThat(answers.name, equalTo("TestBoard"));
    }

    @Test
    public void createBoardTest() {
        Board answers = BoardApi.getBoard(
                BoardApi.
                        with().
                        name("newBoard").
                        desc("simple desc").
                        createBoard());
        assertThat(answers.name, equalTo("newBoard"));
    }

    @Test
    public void deleteBoardTest() {
        Board answers = BoardApi.getBoard(
                BoardApi.
                        with().
                        name("newBoard").
                        desc("simple desc").
                        createBoard());
        BoardApi
                .with()
                .deleteBoard(answers.id);
        BoardApi
                .with()
                .getBoard(answers.id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }

}
