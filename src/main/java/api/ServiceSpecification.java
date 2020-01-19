package api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpHeaders;

import static org.hamcrest.Matchers.lessThan;

public class ServiceSpecification {

    public static ResponseSpecification baseResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .build();

    }
}
