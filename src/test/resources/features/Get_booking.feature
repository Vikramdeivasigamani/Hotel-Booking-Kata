@retrieve
Feature: Retrieve existing booking details

  As a customer
  I want to view my existing booking details
  So that I can confirm my reservation
  
  Background:
  Given a confirmed booking exists with id "<bookingid>"   
   
  @retrieve @positive @mandatory
  Scenario Outline: Successful retrieval of booking details 
        
  When the client enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 200
  And the system displays the booking details
  
  Examples:
  
    | bookingid | token        |
    | 101       | abc123token  |
    
   @retrieve @negative @validation
    
   Scenario Outline: Unsuccessful retrieval of booking details due to invalid booking id but valid token
           
  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 401
  And the system displays an error message "<errormessage>"
  
  Examples:
  
    | bookingid | token        | errormessage  |
    | !@#       | abc123token  | unauthorized  |
    | abc       | abc123token  | unauthorized  |

   @retrieve @negative @validation

  Scenario Outline: Unsuccessful retrieval of booking details due to missing booking Id

  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 401
  And the system displays an error message "<errormessage>"
  
  Examples:
  
    | bookingid | token        | errormessage  |
    |           | abc123token  | unauthorized  |  
    
   @retrieve @negative @validation
   
  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 401
  And the system displays an error message "<errormessage>"
  
  Examples:
  
    | bookingid | token        | errormessage  |
    | !@#       | abc123token  | unauthorized  |
    | abc       | abc123token  | unauthorized  |
    
    
   @retrieve @negative @validation
   
   Scenario Outline: Unsuccessful retrieval of booking details due to valid booking id but invalid token
             
  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 401
  And the system displays an error message "<errormessage>"
  
  Examples:
  
    | bookingid | token        | errormessage  |
    | 101       | abc101       | unauthorized  |
    | 201       | abc201       | unauthorized  |
    | 301       | a$!@%6       | unauthorized  |
    
     @retrieve @negative @validation
   
   Scenario Outline: Unsuccessful retrieval of booking details due to valid booking id but missing token
               
  When the customer enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 401
  And the system displays an error message "<errormessage>"
  
  Examples:
  
    | bookingid | token        | errormessage  |
    | 101       |              | unauthorized  |   
    
    
    
