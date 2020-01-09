package api;

import beans.Board;
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

import java.util.HashMap;

import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {

    // Builder-pattern for manipulations with boards
    private BoardApi() {
    }

    private HashMap<String, String> params = new HashMap<>();

    public static class ApiBuilder {
        BoardApi trelloApi;

        private ApiBuilder(BoardApi boardApi) {
            this.trelloApi = boardApi;
        }

        // Use it for adding name in request
        public ApiBuilder name(String name) {
            trelloApi.params.put("name", name);
            return this;
        }

        // Use it for adding description in request
        public ApiBuilder desc(String desc) {
            trelloApi.params.put("desc", desc);
            return this;
        }

        // GET-request for board
        public Response getBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

        // POST-request for board
        public Response createBoard() {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .post(ROOT_PATH + BOARDS_PATH).prettyPeek();
        }

        // DELETE-request for board
        public Response deleteBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .delete(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

        // PUT-request for board
        public Response updateBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .put(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

    }

    // Use it for starting building the query
    public static ApiBuilder with() {
        BoardApi api = new BoardApi();
        return new ApiBuilder(api);
    }

    // Use it for getting a board-object
    public static Board getBoard(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<Board>() {
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

    // A specification for response which has NOT_FOUND status code
    public static ResponseSpecification notFoundSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
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
