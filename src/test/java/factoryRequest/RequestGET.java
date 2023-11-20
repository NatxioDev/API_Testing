package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestGET implements IRequest {


    @Override
    public Response send(RequestInformation requestInformation) {
        return given()
                .headers(requestInformation.getHeader())
                .when()
                .get(requestInformation.getUrl())
                .then()
                .extract().response();
    }
}
