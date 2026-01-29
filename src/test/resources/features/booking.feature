@booking
Feature: Booking management

  Background:
    Given I have a username "admin" and password "password"
    When I send a login request
    Then I should receive an authentication token

@BUG1 @BUG7 @happy-path @create @read
  Scenario: Create a booking and retrieve it by booking ID
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 9       | John      | Doe       | true        | 2026-09-01 | 2026-09-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id

    @happy-path @create @read @boundary
  Scenario Outline: Allow booking with minimum and maximum valid field lengths for firstname, lastname, and phone
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn | checkOut | email | phone |
      | 2 | <firstname> | <lastname> | true | 2026-07-01 | 2026-07-10 | john@example.com | <phone> |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id

    Examples:
      |  firstname | lastname | phone |
      | Ali | Kon | 01234567890 |
      |  AFirstNameOf18Char | ALastNameOf18Chars | 012345678901234567890 |

  @BUG3 @negative @create @boundary
  Scenario Outline: Reject booking when <field> is outside allowed range
    When I create a new booking with the following details:
      | roomid | firstname   | lastname   | depositpaid | checkIn    | checkOut   | email            | phone   |
      | 3      | <firstname> | <lastname> | true        | 2026-07-01 | 2026-07-10 | john@example.com | <phone> |
    Then I should receive a statuscode 400
    And the response contains the following errors:
      | <error message> |

    Examples:
      | field     | firstname              | lastname               | phone                  | error message                |
      | firstname | Al                     | Doe                    | 01234567890            | size must be between 3 and 18|
      | firstname | AFirstNameOf19Chars    | Doe                    | 01234567890            | size must be between 3 and 18|
      | lastname  | John                   | Ko                     | 01234567890            | size must be between 3 and 18|
      | lastname  | John                   | ALastNameOf19Charss    | 01234567890            | size must be between 3 and 18|
      | phone     | John                   | Doe                    | 0123456789             | size must be between 11 and 21|
      | phone     | John                   | Doe                    | 0123456789012345678901 | size must be between 11 and 21|

  @negative @email-validation
  Scenario Outline: Prevent booking for invalid email formats
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn | checkOut | email | phone |
      | 4 | John | Doe | true | 2026-07-01 | 2026-07-10 | <email> | 123-456-7890 |
    Then I should receive a statuscode 400
    And the response contains the following errors:
      | must be a well-formed email address |

    Examples:
      | email |
      | john.doe.example.com |
      | john.doe@ |
      | @example.com |
      | john..doe@example.com |
      | john doe@example.com |
      | B                     |

  @BUG4 @negative @date-validation
  Scenario: Prevent booking when check-out date is before check-in date
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 5       | John      | Doe       | true        | 2026-08-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then I should receive a statuscode 400
    Then the response contains the following errors:
      | Failed to create booking            |

@BUG5 @negative @date-validation
  Scenario: Prevent booking creation for past check-in dates
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 6       | John      | Doe       | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com    | 123-456-7890 |
    Then I should receive a statuscode 409 with message "Failed to create booking"

  @negative @double-booking
  Scenario: Prevent double-booking of the same room on identical dates
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 7       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 7       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then I should receive a statuscode 409 with message "Failed to create booking"

  @negative @double-booking
  Scenario: Prevent double-booking of the same room on overlapping dates
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 8       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 8       | John      | Doe       | true        | 2026-07-09 | 2026-07-14 | john.doe@example.com    | 123-456-7890 |
    Then I should receive a statuscode 409 with message "Failed to create booking"

    @happy-path @update
  Scenario: Update an existing booking with new details
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 10      | John      | Doe      | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I update the booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                  | phone        |
      | 11      | Jane      | Smith    | false       | 2024-08-01 | 2024-08-15 | jane.smith@example.com | 098-765-4321 |
    Then the booking is updated successfully
    Then the details of the booking can be found using the booking id

    @BUG2 @put @update @happy-path
  Scenario: Partially update booking to mark deposit as paid
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 12      | John      | Doe      | false        | 2026-07-01 | 2026-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I partially update the booking with the following details:
      | depositpaid | firstname |lastname |
      | true        | John      | Doe   |
    Then the booking is updated successfully

      @happy-path @delete
  Scenario: Delete an existing booking
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 13       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I delete the booking
    Then the booking is deleted successfully
    When I try to find the booking using the booking id
    Then I should receive a statuscode 403

    @negative @security @authorization
  Scenario: Reject access to get booking without authentication
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 14       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I try to fetch the booking without authentication
    Then I should receive a statuscode 401 with message "Authentication required"

  @negative @security @authorization
  Scenario: Reject access to get booking without valid token
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 15       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I try to fetch the booking with an invalid token
    Then I should receive a statuscode 403

  @negative @security @authorization
  Scenario: Reject access to update booking without authentication
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 16      | John      | Doe      | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I update the booking without authentication with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                  | phone        |
      | 17      | Jane      | Smith    | false       | 2024-08-01 | 2024-08-15 | jane.smith@example.com | 098-765-4321 |
    Then I should receive a statuscode 401 with message "Authentication required"

  @negative @security @authorization
  Scenario: Reject access to update booking without valid token
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 18      | John      | Doe      | true        | 2024-07-01 | 2024-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I update the booking with an invaid token with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                  | phone        |
      | 19      | Jane      | Smith    | false       | 2024-08-01 | 2024-08-15 | jane.smith@example.com | 098-765-4321 |
    Then I should receive a statuscode 403

  @negative @security @authorization
  Scenario: Reject access to delete booking without authentication
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 20       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I delete the booking without authentication
    Then I should receive a statuscode 401 with message "Authentication required"

  @BUG6 @negative @security @authorization
  Scenario: Reject access to delete booking without valid token
    When I create a new booking with the following details:
      | roomid  | firstname | lastname  | depositpaid | checkIn    | checkOut   | email                   | phone        |
      | 21       | John      | Doe       | true        | 2026-07-01 | 2026-07-10 | john.doe@example.com    | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    Then the details of the booking can be found using the booking id
    When I delete the booking with an invalid token
    Then I should receive a statuscode 403

    @BUG2 @negative @security @authorization
  Scenario: Reject access to partially update booking without authentication
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 22      | John      | Doe      | false        | 2026-07-01 | 2026-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I partially update the booking without authentication with the following details:
      | depositpaid | firstname |lastname |
      | true        | John      | Doe   |
    Then I should receive a statuscode 401 with message "Authentication required"

    @BUG2 @negative @security @authorization
  Scenario: Reject access to partially update booking without valid token
    When I create a new booking with the following details:
      | roomid | firstname | lastname | depositpaid | checkIn    | checkOut   | email                | phone        |
      | 23      | John      | Doe      | false        | 2026-07-01 | 2026-07-10 | john.doe@example.com | 123-456-7890 |
    Then the booking is created successfully and returns a booking id
    When I partially update the booking without a valid token with the following details:
      | depositpaid | firstname |lastname |
      | true        | John      | Doe   |
    Then I should receive a statuscode 403