@authentication
Feature: Authentication API
  As a user of the booking API
  I want to authenticate with valid credentials
  So that I can access protected endpoints

  Scenario: Successful login with valid credentials
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token

  Scenario Outline: Login with various invalid credential combinations
    Given I have a username "<username>" and password "<password>"
    When I send a login request
    Then I should receive a statuscode 401 with message "Invalid credentials"
    And the token should not be present in the response

    Examples:
      | username  | password      |
      |           | password      |
      | admin     | wrongpass     |
      | wronguser | password      |
      | wronguser | wrongpass     |
