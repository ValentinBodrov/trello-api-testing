import api.BoardApi;
import api.MemberApi;
import beans.Board;
import beans.Member;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static api.BoardApi.notFoundSpecification;
import static api.BoardApi.successSpecification;
import static constants.TrelloConstants.TEST_BOARD_SHORT_LINK;
import static constants.TrelloConstants.TEST_USERNAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BoardTest {

    @Test
    public void simpleGetBoardTest() {
        Board answer =
                BoardApi.getBoard(BoardApi.with().getBoard(TEST_BOARD_SHORT_LINK));
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
                        .getMember(TEST_USERNAME)
        );
        assertThat(member.username, equalTo(TEST_USERNAME));
    }

    @Test
    public void simpleGetMemberBoardsTest() {
        List<Board> boards = MemberApi.getMemberBoards(
                MemberApi
                        .with()
                        .getMemberBoards(TEST_USERNAME)
        );
        List<String> boardsIds = new ArrayList<>();
        for (Board board : boards) {
            boardsIds.add(board.shortLink);
        }
        assertThat(boardsIds, hasItem(TEST_BOARD_SHORT_LINK));
    }

    @Test
    public void simpleDeleteAllBoardsExceptTestBoard() {
        List<Board> boards = MemberApi.getMemberBoards(
                MemberApi
                        .with()
                        .getMemberBoards(TEST_USERNAME)
        );
        List<String> boardsShortLinks = new ArrayList<>();
        for (Board board : boards) {
            boardsShortLinks.add(board.shortLink);
        }
        for (int i = 0; i < boards.size(); i++) {
            if (!boardsShortLinks.get(i).equals(TEST_BOARD_SHORT_LINK)) {
                BoardApi
                        .with()
                        .deleteBoard(boards.get(i).id)
                        .then()
                        .specification(successSpecification());
                boardsShortLinks.remove(i);
            }
        }
        assertThat(boardsShortLinks, hasSize(1));
    }

}
