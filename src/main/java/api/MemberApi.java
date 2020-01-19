package api;

import beans.Board;
import beans.Member;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import utils.ApiPropertiesSingleton;

import java.util.ArrayList;
import java.util.List;

import static api.ServiceSpecification.baseResponseSpecification;
import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.lessThan;

public class MemberApi {

    private MemberApi() {
    }

    public static List<String> getAllBoardIds(List<Board> boards) {
        List<String> boardsIds = new ArrayList<>();
        for (Board board : boards) {
            boardsIds.add(board.shortLink);
        }
        return boardsIds;
    }

    public static class ApiBuilder {
        MemberApi trelloApi;

        private ApiBuilder(MemberApi memberApi) {
            this.trelloApi = memberApi;
        }

        public Response getMember(String username) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + MEMBERS_PATH + username).prettyPeek();
        }

        public Response getMemberBoards(String username) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + MEMBERS_PATH + username + BOARDS_PATH)
                    .prettyPeek();
        }
    }

    public static Member getMember(String username) {
        MemberApi api = new MemberApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        return new Gson().
                fromJson(apiBuilder.
                        getMember(username).
                        asString().
                        trim(), new TypeToken<Member>() {}.getType());
    }

    public static List<Board> getMemberBoards(String username) {
        MemberApi api = new MemberApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        return new Gson().
                fromJson(apiBuilder.
                        getMemberBoards(username).
                        asString().
                        trim(), new TypeToken<List<Board>>() {}.getType());
    }

    public static ResponseSpecification successSpecification() {
        return new ResponseSpecBuilder()
                .addResponseSpecification(baseResponseSpecification())
                .expectContentType(JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    private static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setAccept(JSON)
                .addQueryParam(PARAM_KEY,
                        ApiPropertiesSingleton.getInstance().getProperty(PARAM_KEY))
                .addQueryParam(PARAM_TOKEN,
                        ApiPropertiesSingleton.getInstance().getProperty(PARAM_TOKEN))
                .build();
    }

}
