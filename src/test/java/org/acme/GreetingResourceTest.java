package org.acme;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class GreetingResourceTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(9999);

    @BeforeAll
    static void beforeAll() {
        WIRE_MOCK_SERVER.start();
    }

    @AfterAll
    static void afterAll() {
        WIRE_MOCK_SERVER.stop();
    }

    @Test
    public void testHelloEndpoint() {
        String greeting = "Hello Quarkus";
        WIRE_MOCK_SERVER.stubFor(WireMock.get(WireMock.urlPathEqualTo("/hello")).willReturn(WireMock.aResponse().withStatus(200).withBody(greeting)));

        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is(greeting));

        WIRE_MOCK_SERVER.verify(1, WireMock.getRequestedFor(WireMock.urlEqualTo("/hello")));
    }
}