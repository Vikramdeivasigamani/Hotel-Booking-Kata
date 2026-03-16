@booking @e2e

Feature: Hotel booking End to End flow 

  As a customer 
  I want to create, retrieve, update and delete an hotel booking
  So that I can manage reservations successfully


  @create @positive @mandatory @validation
  
   Scenario Outline: Successfully create a hotel booking 
   
  Given the hotel booking system is available
  When the customer enter the valid booking details:  
    | firstname   | lastname   | email   | phone   | depositpaid    | checkin   | checkout   |
    | <firstname> | <lastname> | <email> | <phone> | <depositpaid>  | <checkin> | <checkout> |
  Then the response status should be 200
  And booking details should be displayed with unique booking id
    
  Examples:

  | firstname | lastname   | email         | phone         | depositpaid  | checkin    | checkout   |
  | Will      | Smith      | will@test.com | 9677121121907 | true         | 2026-03-15 | 2026-03-20 |
  | Sam       | Mendis     | sam@test.com  | 9677121121908 | false        | 2026-03-17 | 2026-03-20 |
 
 
  @retrieve @positive @validation
  
  Scenario Outline: Successfully retrieve an existing booking  

  Given a confirmed booking already exists      
  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 200
  And the booking details should be displayed
  
  Examples:
  
    | bookingid | token        |
    | 101       | abc123token  |
    
    
    @update  @positive @validation
    
     Scenario Outline: Successfully update an existing booking 
     
    Given a confirmed booking already exists    
    When the customer updates the booking details using booking id "<bookingid>" and token code "<token>"
     | firstname   | lastname    | phone      | email       | checkin    | checkout   | depositpaid   |
     | <firstname> | <lastname>  | <phone>    | <email>     | <checkin>  | <checkout> | <depositpaid> | 
    Then the response status code should be 200
    And the booking is updated with revised details
 
    Examples:
    
       | bookingid | token        | firstname  | lastname  | phone          | email            | checkin      | checkout   | depositpaid  |
       | 101       | abc123token  | Will       | smith     | 9677121121909  | will @email.com  | 2026-03-16   | 2026-03-20 | true         |
       | 201       | xyz456token  | Sam        | Mendis    | 9677121121910  | smith@email.com  | 2026-03-18   | 2026-03-25 | false        |
       
     
    @delete @positive @validation
  
    Scenario Outline: Successfully cancel an existing booking 
    
    Given a confirmed booking already exists
    When the customer cancels booking using booking id "<bookingid>" and token code "<token>" 
    Then the response status code should be 201
    And the system cancels the booking
    And the room type becomes available during the cancelled dates
    
    Examples:
       
       | bookingid | token        |
       | 101       | abc123token  |
       | 201       | xyz456token  |
       
       
        @delete @negative @validation @error
  
    Scenario Outline: Unsuccessful retreival of cancelled booking           
       
    Given the booking is already cancelled
    When the customer attempts to retrieve the booking using booking id "<bookingid>" and token code "<token>" 
    Then the response status code should be 401
    And the booking details should be displayed
    And the response displays an error message "<errormessage>"
    
 Examples: 
        
       | bookingid | token        | errormessage         |  
       | 101       | abc123token  | booking not found    |
 
 
 
