 @update @booking
  Feature: Update an existing booking

  As a customer
  I want to update my booking details
  So that I can modify my booking information 

  Background:
    Given a confirmed booking already exists 

  @positive @update
  
  Scenario Outline: Successfully update the customer's first name
  
    When the customer updates the first name "<firstname>" using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 200
    And the booking details are updated with first name "<firstname>" 
 
    Examples:
    
       | bookingid | token        | firstname  |
       | 101       | abc123token  | Rahul      |
       | 201       | xyz456token  | Bijoy      | 
       
    @positive @update     
  Scenario Outline: Successfully update the customer's Last name
  
    When the customer updates the last name "<lastname>" using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 200
    And the booking details are updated with last name "<lastname>" 
 
    Examples:
    
       | bookingid | token        | lastname   |
       | 101       | abc123token  | Bose       |
       | 201       | xyz456token  | Nambiar    | 
       
     @positive @update              
  Scenario Outline: Successfully update the customer's phone number
  
    When the customer updates the phone number "<phone>" using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 200
    And the booking details are updated with phone number "<phone>" 
 
    Examples:
    
       | bookingid | token        | phone          |
       | 101       | abc123token  | 9677121121901  |
       | 201       | xyz456token  | 9677121121902  | 
       
    @positive @update   
   Scenario Outline: Successfully update the customer's email
  
    When the customer updates the email "<email>" using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 200
    And the booking details are updated with email "<email>" 
 
    Examples:
    
       | bookingid | token        | email            |
       | 101       | abc123token  | rahul@email.com   |
       | 201       | xyz456token  | bijoy@email.com   |   
       
     @positive @update  
     Scenario Outline: Successfully update the customer's stay dates
  
    When the customer updates the check-in "<checkin>" and check-out "<checkout>" dates using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 200
    And the booking details are updated with "<checkin>" and check-out "<checkout>" dates
 
    Examples:
    
       | bookingid | token        | checkin      | checkout   |
       | 101       | abc123token  | 2026-03-15   | 2026-03-20 |
       | 201       | xyz456token  | 2026-03-20   | 2026-03-25 |  
    
    @positive @update
     Scenario Outline: Successfully update the customer's booking details
  
    When the customer updates the booking details with the following details  using booking id "<bookingid>" and token code "<token>"  
     | firstname   | lastname    | phone      | email       | checkin    | checkout   | depositpaid   |
     | <firstname> | <lastname>  | <phone>    | <email>     | <checkin>  | <checkout> | <depositpaid> | 
    Then the response status code should be 200
    And the booking details are updated with revised details
 
    Examples:
    
       | bookingid | token        | firstname  | lastname  | phone          | email            | checkin      | checkout   | depositpaid  |
       | 101       | abc123token  | Rahul      | Bose      | 9677121121901  | rahul@email.com  | 2026-03-15   | 2026-03-20 | true         |
       | 201       | xyz456token  | Bijoy      | Nambiar   | 9677121121902  | bijoy@email.com  | 2026-03-20   | 2026-03-25 | false        |
       
     @negative @update
      Scenario Outline: Unsucessful update of existing booking due to invalid booking id
  
    When the customer attempts to update booking details using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
     
    Examples: 
   
       | bookingid | token        | errormessage    |  
       | !@#       | abc123token  | unauthorized    |
       | xyz       | xyz456token  | unauthorized    |
       
        Scenario Outline: Unsucessful update of existing booking due to cancelled booking id
  
    When the customer attempts to update booking details using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
     
    Examples: 
   
       | bookingid | token        | errormessage    |  
       | 111       | abc123token  | unauthorized    |
       | 222       | xyz456token  | unauthorized    |
    
    @negative @update  
       Scenario Outline: Unsucessful update of existing booking due to invalid token code
  
    When the customer attempts to update booking details using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And no changes made to the existing booking details
     
    Examples: 
   
       | bookingid | token        | errormessage    |  
       | 101       | !@#123token  | unauthorized    |
       | 201       | 123456token  | unauthorized    |
    
    @negative @update
      Scenario Outline: Unsucessful update of existing booking due to expired token code
  
    When the customer attempts to update booking details using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And no changes made to the existing booking details
    
    Examples: 
   
       | bookingid | token        | errormessage    | 
       | 101       | !@#123token  | unauthorized    |
       | 201       | 123456token  | unauthorized    |
       
     @negative @update  
      Scenario Outline: Unsucessful update of customer's booking details due to invalid inputs
  
    When the customer updates the booking details with the following details using booking id "<bookingid>" and token code "<token>"  
     | firstname   | lastname    | phone      | email       | checkin    | checkout   |
     | <firstname> | <lastname>  | <phone>    | <email>     | <checkin>  | <checkout> |
    Then the response status code should be 401
    And no changes made to the existing booking details
 
    Examples:
    
     | bookingid | token       | firstname | lastname | email         | phone         | depositpaid | checkin    | checkout   | errormessage                                  |
     | 101       | abc123token | Wi        | Smith    | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | firstname size must be between 3 and 18       |
     | 101       | abc123token | Will      | Sm       | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | lastname size must be between 3 and 18        |
     | 101       | abc123token | Will      | Smith    | @test.com     | 9677121121907 | true        | 2026-03-15 | 2026-03-20 | email must be a well-formed email address     |
     | 101       | abc123token | Will      | Smith    | will@test.com | 9677121907    | false       | 2026-03-15 | 2026-03-20 | phonenumber size must be between 11 and 21    |
     | 101       | abc123token | Will      | Smith    | will@test.com | 9677121121907 | true        | 2026-03-15 | 2026-03-15 | checkin and checkout date cannot be the same  |
     | 101       | abc123token | Sam       | Mendis   | sam@test.com  | 9677121121908 | false       | 2026-03-21 | 2026-03-20 | Checkout date must be later than checkin      |            |
    
        
       
    

