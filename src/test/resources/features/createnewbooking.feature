Feature: Booking API automation
    In order to manage hotel bookings
    As a tester
    I want to perform CRUD operations via API


    @create
    Scenario: Create a booking successfully
        Given a valid booking payload
            | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email                 | phone       |
            | 24     | kevin     | rog      | true        | 2026-03-13 | 2026-04-15 | kevin.rog@example.com | 31062617436 |
        When I send a POST request to "/booking"
        Then the response should be successful
        And the response should match the booking payload

    @create
    Scenario: Create booking with empty value
        Given a booking payload with empty value
            | roomid | firstname | lastname | depositpaid | checkin    | checkout   | email                | phone       |
            | 2      | [empty]   | Doe      | true        | 2025-10-13 | 2025-10-15 | john.doe@example.com | 31062322236 |
        When I send a POST request to "/booking"
        Then the response should indicate a validation error
