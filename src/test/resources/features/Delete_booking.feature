@delete
Feature: Cancel booking

  As a customer
  I want to cancel an existing booking
  so that the room becomes available for other customers

     @delete @positive @mandatory
  
    Scenario Outline: Successful cancellation of an existing booking
    
    Given a confirmed booking already exists
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>" 
    Then the response status code should be 201
    And the system cancels the booking
    And the room becomes available for the cancelled dates
    
    Examples:
       
       | bookingid | token        |
       | 101       | abc123token  |
       | 201       | xyz456token  |
   
   
   @delete @negative @validation @error @validation
   
   Scenario Outline: Unsuccessful cancellation due to invalid booking id
   
    Given a confirmed booking already exists
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And the response displays an error message "<errormessage>"
         
    Examples: 
    
       | bookingid | token        | errormessage    |  
       | !@#       | abc123token  | unauthorized    |
       |           | vik123token  | unauthorized    |
       
     @delete @negative @validation @error 
     
    Scenario Outline: Unsuccessful cancellation due to invalid token code
    
    Given a confirmed booking already exists
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And the response displays an error message "<errormessage>"
   
       Examples: 
       
       | bookingid | token        | errormessage    |  
       | 101       | !@#123token  | unauthorized    |
       | 301       |              | unauthorized    |
       
              
    @delete @negative @validation @error @validation
        
   Scenario Outline: Unsuccessful cancellation of an already cancelled booking 
   
    Given the booking is already cancelled
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And the response displays an error message "<errormessage>"
    
        Examples: 
        
       | bookingid | token        | errormessage         |  
       | 101       | abc123token  | booking not found    |
       
    @delete @negative @validation @error @validation
    
    Scenario Outline: Unsuccessful cancellation of an booking after check-in date 
    
    Given a confirmed booking already exists
    And the check-in date has passed and check-out date is in the future
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And the response displays an error message "<errormessage>"
    And no changes made to the existing booking
    
    Examples: 
    
       | bookingid | token        | errormessage         |  
       | 101       | abc123token  | unauthorized         |
     
     
       @delete @negative @validation @error @validation
    
    Scenario Outline: Unsuccessful cancellation of an expired booking  
    
    Given a confirmed booking already exists
    And the stay dates had passed
    When the customer cancels the booking using booking id "<bookingid>" and token code "<token>"  
    Then the response status code should be 401
    And the response displays an error message "<errormessage>"
    
    Examples: 
    
       | bookingid | token        | errormessage         |  
       | 101       | abc123token  | unauthorized         |
                    
      
     
