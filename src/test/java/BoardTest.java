import api.BoardApi;
import beans.Board;
import org.testng.annotations.Test;

import java.util.List;

import static api.BoardApi.successResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest {

    @Test
    public void simpleTest() {
        BoardApi.with()
                .callApi()
                .then().specification(successResponse());
    }

    @Test
    public void boardsTest() {
        Board answers =
                BoardApi.getBoard(BoardApi.with().callApi());
        assertThat(answers.name, equalTo("TestBoard"));
    }
}
