import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class PatientIntegrationTest {
    @BeforeAll
    static void setUp(){
        // This should be changes as per environment for testing
        // Currently set for local environment
        RestAssured.baseURI = "http://localhost:4004";
    }

    @Test
    public void shouldReturnPatientsWithValidToken() {
        // TODO: Generating of Token logic should be in helper class

        String loginPayload = """
            {
                "email":"testuser@test.com",
                "password":"password123"
            }
        """;

        String token = given()
                .contentType("application/json")
                .body(loginPayload)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .get("token");

        given()
                .header("Authorization","Bearer " + token)
                .when()
                .get("/api/patients")
                .then()
                .statusCode(200)
                .body("patients",notNullValue());

    }

}
