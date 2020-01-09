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

import java.util.List;

import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class MemberApi {

    // Builder-pattern for manipulations with members
    private MemberApi() {
    }

    public static class ApiBuilder {
        MemberApi trelloApi;

        private ApiBuilder(MemberApi memberApi) {
            this.trelloApi = memberApi;
        }

        // GET-request for member
        public Response getMember(String username) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + MEMBERS_PATH + username).prettyPeek();
        }

        // GET-request for member's boards
        public Response getMemberBoards(String username) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + MEMBERS_PATH + username + BOARDS_PATH)
                    .prettyPeek();
        }
    }

    // Use it for starting building the query
    public static ApiBuilder with() {
        MemberApi api = new MemberApi();
        return new ApiBuilder(api);
    }

    // Use it for getting a member-object
    public static Member getMember(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<Member>() {
                        }.getType());
    }

    // Use it for getting a list of member's boards
    public static List<Board> getMemberBoards(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<List<Board>>() {
                        }.getType());
    }

    // A specification for successful response (OK status code)
    public static ResponseSpecification successSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(JSON)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    // A specification for setting parameters in request
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
