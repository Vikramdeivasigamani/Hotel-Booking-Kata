QUESTIONS
1) Creating a booking does not require a token. Is this by design?

BUGS
1) When succesfully creating a booking, the expected response code is 201, but the actual response code is 200. Is this by design? Changed test to expect 200.
2) Partially updating a booking does not seem to work. It returns a 405 Method Not Allowed.
3) Lastname max length validation is incorrect:
   When I fill in a lastname with 19 characters, the booking is currently created successfully.
   When I fill in a lastname with 2 characters, the following error message is shown. Expected is the following error message: 400: size must be between 3 and 30.
   The expected result for both cases is the following error message: Expected is the following error message: 400: size must be between 3 and 18.
4) When the check-out date is before the check-in date, the following error message is shown: 409: Failed to create booking.
   Expected is the following error message: 400: Failed to create booking.
5) When the check-in and check-out dates are set in the past, a booking is created successfully.
   Expected is the following error message: 400: Failed to create booking (The expected statuscode and message are not stated in the swagger, it's an assumption I made).
6) When deleting a booking without a valid token, the following error statuscode is shown: 500. 
Expected is the following error statuscode: 403
7) When I use the booking id to get the booking details I don't receive the email or phone of the booking in the response.
    Expected is to receive the email and phone of the booking in the response. Currently commented it out so the tests don't fail because of this.