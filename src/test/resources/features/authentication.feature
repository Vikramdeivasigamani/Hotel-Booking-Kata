@authentication @security
Feature: Authentication API
  As a user of the booking API
  I want to authenticate with valid credentials
  So that I can access protected endpoints

  @happy-path @login
  Scenario: Successful login with valid credentials
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token

  @negative @login @security
  Scenario Outline: Prevent login with various invalid credential combinations
    Given I have a username "<username>" and password "<password>"
    When I send a login request
    Then I should receive a statuscode 401 with message "Invalid credentials"
    And the token should not be present in the response

    Examples:
      | username  | password      |
      |           | password      |
      | admin     |               |
      |           |               |
      | wronguser | wrongpass     |
