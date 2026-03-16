 @update @booking
  Feature: Update an existing booking

  As a customer
  I want to update my booking details
  So that I can modify my booking information 

  Background:
    Given a confirmed booking already exists 

  @positive @update
  
  Scenario Outline: Successfully update the customer's first name
  
    When the customer updates first name "<firstname>" using booking id "<bookingid>" and token "<token>"  
    Then the response status code should be 200
    And the booking details are updated with first name "<firstname>" 
 
    Examples:
    
       | bookingid | token        | firstname  |
       | 101       | abc123token  | Rahul      |
       | 201       | xyz456token  | Bijoy      | 
