package api;

import beans.Board;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import utils.ApiPropertiesSingleton;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.lessThan;

public class BoardApi {

    private BoardApi() {
    }

    private HashMap<String, String> params = new HashMap<String, String>();

    public static class ApiBuilder {
        BoardApi trelloApi;

        private ApiBuilder(BoardApi boardApi) {
            this.trelloApi = boardApi;
        }

        public Response callApi() {
            String str = String.format("%s?key=%s&token=%s",
                    ApiPropertiesSingleton.getInstance().getProperty("path"),
                    ApiPropertiesSingleton.getInstance().getProperty("key"),
                    ApiPropertiesSingleton.getInstance().getProperty("token")
            );
            return RestAssured.with()
                    .queryParams(trelloApi.params)
                    .log().all()
                    .get(str).prettyPeek();
        }
    }

    public static ApiBuilder with() {
        BoardApi api = new BoardApi();
        return new ApiBuilder(api);
    }

    public static Board getBoard(Response response) {
        return new Gson().fromJson(response.asString().trim(), new TypeToken<Board>() {
        }.getType());
    }

    public static ResponseSpecification successResponse() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectHeader("Connection", "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .expectStatusCode(HttpStatus.SC_OK)
                .build();
    }

}
