package api;

import beans.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import utils.ApiPropertiesSingleton;

import java.util.HashMap;
import java.util.List;

import static constants.TrelloConstants.*;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.http.ContentType.TEXT;
import static org.hamcrest.Matchers.lessThan;

public class BoardApi {

    private BoardApi() {
    }

    private HashMap<String, String> params = new HashMap<String, String>();
    private Method method = Method.GET;

    public static class ApiBuilder {
        BoardApi trelloApi;

        private ApiBuilder(BoardApi boardApi) {
            this.trelloApi = boardApi;
        }

        public ApiBuilder name(String name) {
            trelloApi.params.put("name", name);
            return this;
        }

        public ApiBuilder desc(String desc) {
            trelloApi.params.put("desc", desc);
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

        public Response callApi() {
            return RestAssured
                    .given(requestSpecification())
                    .log().all()
                    .request(trelloApi.method).prettyPeek();
        }
    }

    public static ApiBuilder with() {
        BoardApi api = new BoardApi();
        return new ApiBuilder(api);
    }

    public static Board getBoard(Response response) {
        return new Gson().
                fromJson(response.asString().
                                trim(),
                        new TypeToken<Board>() {
                        }.getType());
    }

    public static List<Board> getBoards(Response response) {
        return new Gson().
                fromJson(response.asString().
                        trim(), new TypeToken<List<Board>>() {
                }.getType());
    }

    public static ResponseSpecification responseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(TEXT)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

    public static RequestSpecification requestSpecification() {
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
