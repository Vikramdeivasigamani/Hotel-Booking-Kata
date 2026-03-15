Given the hotel booking system is available

 # Positive Scenarios
 
  Scenario Outline: Successful booking of a hotel room with valid guest and stay details
  
  Given a guest provides the following booking details:  
    | firstname   | lastname   | email   | phone   | depositpaid  | checkin   | checkout   |
    | <firstname> | <lastname> | <email> | <phone> | <depositpaid>|<checkin>  | <checkout> |
  When the guest submits the booking
  Then the booking is created successfully
  And the sytems confirm the booking
  And an unique booking reference ID is returned
  
Examples:
  | firstname | lastname   | email         | phone         | depositpaid  | checkin    | checkout   |
  | Will      | Smith      | will@test.com | 9677121121907 | true         | 2026-03-15 | 2026-03-20 |
  | Sam       | Mendis     | sam@test.com  | 9677121121908 | talse        | 2026-03-17 | 2026-03-20 |
  
