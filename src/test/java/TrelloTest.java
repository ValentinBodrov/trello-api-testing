import api.BoardApi;
import api.MemberApi;
import beans.Board;
import beans.Member;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static api.BoardApi.notFoundSpecification;
import static api.BoardApi.successSpecification;
import static constants.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TrelloTest {

    // This is a simple test for getting test board
    @Test
    public void simpleGetBoardTest() {
        Board board =
                BoardApi.getBoard(BoardApi.with().getBoard(TEST_BOARD_SHORT_LINK));
        assertThat(board.name, equalTo("TestBoard"));
    }

    // This is a simple test for creating a board
    @Test
    public void simpleCreateBoardTest() {
        Board board = BoardApi.getBoard(
                BoardApi
                        .with()
                        .name("newBoard")
                        .desc("simple desc")
                        .createBoard());
        assertThat(board.name, equalTo("newBoard"));
        assertThat(board.desc, equalTo("simple desc"));
    }

    // This is a simple test for deleting a board
    // NB! The test passed when the created board cannot
    // be found in answer
    @Test
    public void simpleDeleteBoardTest() {
        Board board = BoardApi.getBoard(
                BoardApi.
                        with().
                        name("newBoard").
                        desc("simple desc").
                        createBoard());
        BoardApi
                .with()
                .deleteBoard(board.id)
                .then()
                .specification(successSpecification());
        BoardApi
                .with()
                .getBoard(board.id)
                .then()
                .specification(notFoundSpecification());
    }

    // This is a simple test for updating a board
    // There's a created board with name 'newBoard',
    // and the query replaced its name on 'anotherNewBoard'
    @Test
    public void simpleUpdateBoardTest() {
        Board board = BoardApi.getBoard(
                BoardApi
                        .with()
                        .name("newBoard")
                        .desc("simple desc")
                        .createBoard()
        );
        board = BoardApi.getBoard(
                BoardApi
                        .with()
                        .name("anotherNewBoard")
                        .updateBoard(board.id)
        );
        assertThat(board.name, equalTo("anotherNewBoard"));
    }

    // This is a simple test for getting a test member
    @Test
    public void simpleGetMemberTest() {
        Member member = MemberApi.getMember(
                MemberApi
                        .with()
                        .getMember(TEST_USERNAME)
        );
        assertThat(member.username, equalTo(TEST_USERNAME));
        assertThat(member.initials, equalTo(TEST_INITIALS));
    }

    // This is a simple test for getting all test member'sboard
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

    // It will be used for cleaning all unnecessary boards
    @AfterSuite
    public void tearDown() {
        List<Board> boards = MemberApi.getMemberBoards(
                MemberApi
                        .with()
                        .getMemberBoards(TEST_USERNAME)
        );
        List<String> boardsShortLinks = new ArrayList<>();
        for (Board board : boards) {
            boardsShortLinks.add(board.shortLink);
        }
        Iterator iterator = boardsShortLinks.iterator();
        for (Board board : boards) {
            String boardShortLink = iterator.next().toString();
            if (!boardShortLink.equals(TEST_BOARD_SHORT_LINK)) {
                BoardApi
                        .with()
                        .deleteBoard(board.id)
                        .then()
                        .specification(successSpecification());
                iterator.remove();
            }
        }
        assertThat(boardsShortLinks, hasSize(1));
    }
}
