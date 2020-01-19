import api.BoardApi;
import api.MemberApi;
import beans.Board;
import beans.Member;
import org.testng.annotations.Test;
import java.util.List;

import static api.BoardApi.notFoundSpecification;
import static constants.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BoardTest {

    @Test
    public void simpleGetBoardTest() {
        Board board = BoardApi.getBoard(TEST_BOARD_SHORT_LINK);
        assertThat(board.name, equalTo("TestBoard"));
    }

    @Test
    public void simpleCreateBoardTest() {
        Board board = BoardApi.createBoard("newBoard");
        assertThat(board.name, equalTo("newBoard"));

        BoardApi.deleteBoard(board.id);
    }

    @Test
    public void simpleDeleteBoardTest() {
        Board board = BoardApi.createBoard("newBoard");
        BoardApi.deleteBoard(board.id);

        BoardApi
                .with()
                .getBoard(board.id)
                .then()
                .specification(notFoundSpecification());
    }

    @Test
    public void simpleUpdateBoardTest() {
        Board board = BoardApi.createBoard("newBoard");
        board = BoardApi.updateBoard(board.id, "anotherNewBoard");
        assertThat(board.name, equalTo("anotherNewBoard"));

        BoardApi.deleteBoard(board.id);
    }

    @Test
    public void simpleGetMemberTest() {
        Member member = MemberApi.getMember(TEST_USERNAME);
        assertThat(member.username, equalTo(TEST_USERNAME));
        assertThat(member.initials, equalTo(TEST_INITIALS));
    }

    @Test
    public void simpleGetMemberBoardsTest() {
        List<Board> boards = MemberApi.getMemberBoards(TEST_USERNAME);
        List<String> boardsIds = MemberApi.getAllBoardIds(boards);
        assertThat(boardsIds, hasItem(TEST_BOARD_SHORT_LINK));
    }
}
