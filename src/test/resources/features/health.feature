@health

Feature: API Health Check
    Scenario: API is up
        When I check the health endpoint
        Then the API should respond with status UP