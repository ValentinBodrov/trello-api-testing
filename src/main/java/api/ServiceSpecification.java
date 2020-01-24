package api;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.http.HttpHeaders;

import static org.hamcrest.Matchers.lessThan;

public class ServiceSpecification {

    public static ResponseSpecification baseResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectHeader(HttpHeaders.CONNECTION, "keep-alive")
                .expectResponseTime(lessThan(2000L))
                .build();

    }

    public static String generateRandomString() {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
