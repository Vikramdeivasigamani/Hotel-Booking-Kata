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
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id

  Scenario: Booking the same roomid twice on the same dates
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 2       | John      | Doe       | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    And I should not be able to create another booking with the same roomid and date

  Scenario: Create a booking and update it completely
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 3      | John      | Doe      | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I update the booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                  | phone        |
      | 4      | Jane      | Smith    | false       | 2024-08-01 | 2024-08-15 | jane.smith@example.com | 098-765-4321 |
    Then the booking is updated successfully
    Then the details of the booking can be found using the booking id

  Scenario: Create a booking and pay deposit
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 3      | John      | Doe      | false        | 2026-07-01 | 2026-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I partially update the booking with the following details:
      | depositpaid | firstname |lastname |
      | true        | John      | Doe   |
    Then the booking is updated successfully
