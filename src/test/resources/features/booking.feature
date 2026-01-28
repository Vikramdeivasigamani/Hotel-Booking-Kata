@authentication
@booking
Feature: Booking management


  Scenario: Create a booking successfully
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 1       | John      | Doe       | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully with a booking id
    Given a booking exists
    When I delete the booking
    Then the booking is removed



