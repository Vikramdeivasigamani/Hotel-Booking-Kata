@create 
Feature: Create hotel booking

  As a customer
  I want to book a hotel room
  So that my stay can be confirmed by the hotel reservation system

 Background:
 
  Given the hotel booking system is available

@positive @validation @mandatory

  Scenario Outline: Successful booking of a hotel room with valid guest and stay details
  
  When the customer provides the following booking details:  
    | firstname   | lastname   | email   | phone   | depositpaid    | checkin   | checkout   |
    | <firstname> | <lastname> | <email> | <phone> | <depositpaid>  | <checkin> | <checkout> |
  Then the response status should be 200
  And an unique booking id is generated
  
Examples:
  | firstname | lastname   | email         | phone         | depositpaid  | checkin    | checkout   |
  | Will      | Smith      | will@test.com | 9677121121907 | true         | 2026-03-15 | 2026-03-20 |
  | Sam       | Mendis     | sam@test.com  | 9677121121908 | false        | 2026-03-17 | 2026-03-20 |
  
  
   @negative @validation @error @invalidParam
     
  Scenario Outline: Unsuccessful booking of a hotel room due to invalid booking details

    Given a guest provides the following booking details
      | firstname   | lastname   | email   | phone   | depositpaid   | checkin   | checkout   |
      | <firstname> | <lastname> | <email> | <phone> | <depositpaid> | <checkin> | <checkout> |
    Then the response status should be 400
    And the response displays an error message "<errormessage>"
    And booking id should not be generated
   
    Examples:
    | firstname | lastname | email         | phone        | depositpaid | checkin    | checkout   | errormessage                                  |
    | Wi       | Smith    | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | firstname size must be between 3 and 18       |
    | Will     | Sm       | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | lastname size must be between 3 and 18        |
    | Will     | Smith    | @test.com     | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | email must be a well-formed email address     |
    | Will     | Smith    | will@test.com | 9677121907    | true        | 2026-03-15 | 2026-03-20 | phonenumber size must be between 11 and 21    |
    | Will     | Smith    | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-15 | checkin and checkout date cannot be the same  |
    | Sam      | Mendis   | sam@test.com  | 9677121121908 | true        | 2026-03-21 | 2026-03-20 | Failed to create booking                      |
  
       
