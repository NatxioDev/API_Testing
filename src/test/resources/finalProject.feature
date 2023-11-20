Feature: Final Project

  Background:
    Given create a new user exists with the conf data
    And the user authenticates with a valid token

  Scenario: Create a new project, update the project, delete the project using authentication
    When I send a POST request to the endpoint "/api/projects.json" with body:
      """
      {
          "Content": "Project 1",
          "Icon": 4
      }
      """
    Then the response code should be 200
    And the attribute "Content" should be "Project 1"
    And I save the "Id" in the variable "$ID_PROJECT"

    When I send a PUT request to the endpoint "/api/projects/$ID_PROJECT.json" with body:
      """
      {
          "Content": "Project 1 Updated"
      }
      """
    Then the response code should be 200
    And the attribute "Content" should be "Project 1 Updated"

    When I send a DELETE request to the endpoint "/api/projects/$ID_PROJECT.json" with body:
    """
       {
         "Content": "Project 1 Updated"
       }
    """

    Then the response code should be 200
    And the attribute "Content" should be "Project 1 Updated"


