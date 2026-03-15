@retrieve
Feature: Retrieve existing booking details

  As a customer
  I want to view my existing booking details
  So that I can confirm my reservation
  
  Background:
  Given Given a confirmed booking exists with id "<bookingid>"   
   
  @retrieve @positive
  Scenario Outline: Successful retrieval of booking details 
        
  When the client enter the booking reference "<bookingid>" "<token>"
  Then the response status should be 200
  And the system displays the booking details
  
  Examples:
  
    | bookingid | token        |
    | 101       | abc123token  |
