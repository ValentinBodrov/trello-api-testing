import api.BoardApi;
import api.MemberApi;
import beans.Board;
import beans.Member;
import org.testng.annotations.Test;

import static api.BoardApi.notFoundSpecification;
import static api.BoardApi.successSpecification;
import static constants.TrelloConstants.CONST_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest {

    @Test
    public void simpleGetBoardTest() {
        Board answer =
                BoardApi.getBoard(BoardApi.with().getBoard(CONST_ID));
        assertThat(answer.name, equalTo("TestBoard"));
    }

    @Test
    public void simpleCreateBoardTest() {
        Board answer = BoardApi.getBoard(
                BoardApi.
                        with().
                        name("newBoard").
                        desc("simple desc").
                        createBoard());
        assertThat(answer.name, equalTo("newBoard"));
    }

    @Test
    public void simpleDeleteBoardTest() {
        Board answer = BoardApi.getBoard(
                BoardApi.
                        with().
                        name("newBoard").
                        desc("simple desc").
                        createBoard());
        BoardApi
                .with()
                .deleteBoard(answer.id)
                .then()
                .specification(successSpecification());
        BoardApi
                .with()
                .getBoard(answer.id)
                .then()
                .specification(notFoundSpecification());
    }

    @Test
    public void simpleUpdateBoardTest() {
        Board answer = BoardApi.getBoard(
                BoardApi
                        .with()
                        .name("newBoard")
                        .desc("simple desc")
                        .createBoard()
        );
        answer = BoardApi.getBoard(
                BoardApi
                        .with()
                        .name("anotherNewBoard")
                        .updateBoard(answer.id)
        );
        assertThat(answer.name, equalTo("anotherNewBoard"));
    }

    @Test
    public void simpleGetMemberTest() {
        Member member = MemberApi.getMember(
                MemberApi
                        .with()
                        .getMember("valbod")
        );
        assertThat(member.username, equalTo("valbod"));
    }
}
