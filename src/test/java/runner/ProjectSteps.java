package runner;

import config.Configuration;
import factoryRequest.FactoryRequest;
import factoryRequest.RequestInformation;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;

public class ProjectSteps {

    Response response;

    RequestInformation requestInformation = new RequestInformation();

    Map<String, String> variables = new HashMap<>();


    @Given("create a new user exists with the conf data")
    public void createANewUserExistsWithTheConfData() {
        requestInformation.setUrl(Configuration.host + "/api/user.json")
                .setBody("{\"Email\":\"" + Configuration.email + "\"," +
                        "\"FullName\":\"" + Configuration.fullName + "\"," +
                        "\"Password\":\"" + Configuration.password + "\"}");

        response = FactoryRequest.make("post").send(requestInformation);

    }

    @And("the user authenticates with a valid token")
    public void theUserAuthenticatesWithAValidToken() {

        String credentials = Base64.getEncoder()
                .encodeToString((Configuration.email + ":" + Configuration.password).getBytes());

        requestInformation.setUrl(Configuration.host + "/api/authentication/token.json")
                .setHeader("Authorization", "Basic " + credentials);

        response = FactoryRequest.make("get").send(requestInformation);

        String token = response.then().extract().path("Token");
        requestInformation.setHeader("Token", token);
    }

    @When("I send a {} request to the endpoint {string} with body:")
    public void iSendRequestToTheEndpointWithBody(String method, String url, String body) {
        url = replaceValues(url);
        requestInformation.setUrl(Configuration.host + url).setBody(body);

        response = FactoryRequest.make(method).send(requestInformation);
    }

    @Then("the response code should be {int}")
    public void theResponseCodeShouldBe(int expectedStatus) {
        response.then().statusCode(expectedStatus);
    }

    @And("the attribute {string} should be {string}")
    public void theAttributeShouldBe(String attribute, String expectedValue) {
        response.then().body(attribute, equalTo(expectedValue));
    }

    @And("I save the {string} in the variable {string}")
    public void iSaveTheInTheVariable(String attribute, String nameVariable) {
        variables.put(nameVariable, response.then().extract().path(attribute) + "");
    }

    @After
    public void afterScenario() {
        requestInformation.setUrl(Configuration.host + "/api/user/" + variables.get("id") + ".json")
                .setHeader("Token", variables.get("token"));

        FactoryRequest.make("delete").send(requestInformation);
    }

    private String replaceValues(String value) {
        for (String key : variables.keySet()) {
            value = value.replace(key, variables.get(key));
        }
        return value;
    }


}
