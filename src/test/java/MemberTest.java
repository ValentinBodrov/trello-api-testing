import api.MemberApi;
import beans.Board;
import beans.Member;
import org.testng.annotations.Test;

import java.util.List;

import static constants.TrelloConstants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class MemberTest {

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
