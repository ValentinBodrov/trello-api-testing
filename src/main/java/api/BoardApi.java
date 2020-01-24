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
import org.apache.http.HttpStatus;
import utils.ApiPropertiesSingleton;

import java.util.HashMap;

import static api.ServiceSpecification.baseResponseSpecification;
import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;

public class BoardApi {

    private BoardApi() {
    }

    private HashMap<String, String> params = new HashMap<>();

    public static class ApiBuilder {
        BoardApi trelloApi;

        private ApiBuilder(BoardApi boardApi) {
            this.trelloApi = boardApi;
        }

        public ApiBuilder name(String name) {
            trelloApi.params.put("name", name);
            return this;
        }

        public Response getBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .get(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

        public Response createBoard() {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .post(ROOT_PATH + BOARDS_PATH).prettyPeek();
        }

        public Response deleteBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .log().all()
                    .delete(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

        public Response updateBoard(String id) {
            return RestAssured
                    .given(requestSpecification())
                    .with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .put(ROOT_PATH + BOARDS_PATH + id).prettyPeek();
        }

    }

    public static ApiBuilder with() {
        BoardApi api = new BoardApi();
        return new ApiBuilder(api);
    }

    private static Board getGson(Response response) {
        return new Gson().
                fromJson(response.
                        asString().
                        trim(), new TypeToken<Board>() {}.getType());
    }

    public static Board getBoard(String id) {
        BoardApi api = new BoardApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        return getGson(apiBuilder.getBoard(id));
    }

    public static Board createBoard(String name) {
        BoardApi api = new BoardApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        apiBuilder.name(name);
        return getGson(apiBuilder.createBoard());
    }

    public static Board updateBoard(String id, String newName) {
        BoardApi api = new BoardApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        apiBuilder.name(newName);
        return getGson(apiBuilder.updateBoard(id));
    }

    public static void deleteBoard(String id) {
        BoardApi api = new BoardApi();
        ApiBuilder apiBuilder = new ApiBuilder(api);
        getGson(apiBuilder.deleteBoard(id));
    }

    public static ResponseSpecification successSpecification() {
        return new ResponseSpecBuilder()
                .addResponseSpecification(baseResponseSpecification())
                .expectContentType(JSON)
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static ResponseSpecification notFoundSpecification() {
        return new ResponseSpecBuilder()
                .addResponseSpecification(baseResponseSpecification())
                .expectContentType(TEXT)
                .expectStatusCode(HttpStatus.SC_NOT_FOUND)
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
