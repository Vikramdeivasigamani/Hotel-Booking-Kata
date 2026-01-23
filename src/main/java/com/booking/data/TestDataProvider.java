package com.booking.data;

import org.testng.annotations.DataProvider;

import java.util.Arrays;

public class TestDataProvider {
    @DataProvider(name = "CreateBookingDetails")
    public Object[][] createBookingPositiveCaseData() {
        return new Object[][]{
                //roomid,FirstName,LastName,DepositPaid,email,phone
                {1, "Siva", "Kumari", true, "test1@gmail.com", "24433234324", 200, "Create Booking With all Valid Input"},
                {2, "Siva", "Kumari", false, "test1@gmail.com", "24433234324", 200, "Create Booking when Deposit Paid is False"}
        };
    }

    @DataProvider(name = "CreateBookingNegativeCaseData")
    public Object[][] createBookingNegativeCaseData() {
        return new Object[][]{
                //roomid,FirstName,LastName,DespositPaid,email,phone,StatusCode,ErrorMsg,Test Case Description
                {null, "Siva", "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("must be greater than or equal to 1"), "Room ID is null"},
                // First Name field Cases
                {2, "Si", "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("size must be between 3 and 18"), "Validate Error Msg - FirstName is less than 3"},
                {2, "SivakumariSadasivam", "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("size must be between 3 and 18"), "Validate Error Msg - FirstName is greater than 18"},
                {2, "", "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Firstname should not be blank", "size must be between 3 and 18"), "Validate Error Msg - FirstName is empty"},
                {2, " ", "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Firstname should not be blank", "size must be between 3 and 18"), "Validate Error Msg - FirstName is empty spaces"},
                {2, null, "Kumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Firstname should not be blank"), "Validate Error Msg - FirstName is null"},
                {201, "Siv", "Kumari", true, "test1@gmail.com", "24433234324", 200, null, "Validate Success Booking - FirstName is of size 3"},
                {401, "SivakumariTestTest", "Kumari", true, "test1@gmail.com", "24433234324", 200, null, "Validate Success Booking - FirstName is of size 18"},

                //Last Name Field Cases
                {2, "Siva", "Ku", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("size must be between 3 and 30"), "Validate Error Msg - LastName is less than 3"},
                {2, "Siva", "KumariKumariKumariKumariKumariKumari", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("size must be between 3 and 30"), "Validate Error Msg - LastName is greater than 30"},
                {101, "Siva", "Kum", true, "test1@gmail.com", "24433234324", 200, null, "Validate Success Booking - LastName is of size 3"},
                {301, "Siva", "KumariKumariKumariKumariKumari", true, "test1@gmail.com", "24433234324", 200, null, "Validate Success Booking - LastName is of size 30"},
                {2, "Siva", "", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Lastname should not be blank", "size must be between 3 and 30"), "Validate Error Msg - LastName is empty"},
                {2, "Siva", " ", true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Lastname should not be blank", "size must be between 3 and 30"), "Validate Error Msg - LastName is empty with spaces"},
                {2, "Siva", null, true, "test1@gmail.com", "24433234324", 400, Arrays.asList("Lastname should not be blank"), "Validate Error Msg - LastName is null"},

                //Email Field Validation
                {2, "Siva", "Kumari", true, "test@gmail", "24433234324", 400, Arrays.asList("must not be empty"), "Validate Error Msg - Email Value is Without .com"},
                {2, "Siva", "Kumari", true, "test@", "24433234324", 400, Arrays.asList("must be a well-formed email address"), "Validate Error Msg - Email Value is Without Domain(No content after@)"},
                {2, "Siva", "Kumari", true, "test", "24433234324", 400, Arrays.asList("must be a well-formed email address"), "Validate Error Msg - Email Value is Without @"},
                {2, "Siva", "Kumari", true, "", "24433234324", 400, Arrays.asList("must not be empty"), "Validate Error Msg - Email Value is Empty"},
                {2, "Siva", "Kumari", true, " ", "24433234324", 400, Arrays.asList("must be a well-formed email address"), "Validate Error Msg - Email Value is Empty with Space"},
                {2, "Siva", "Kumari", true, null, "24433234324", 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - Email Value is null"},

                //Phone Field Validation
                {2, "Siva", "Kumari", true, "test1@gmail.com", "4433234324", 400, Arrays.asList("size must be between 11 and 21"), "Validate Error Msg - Phone Value is less than 11 numbers"},
                {2, "Siva", "Kumari", true, "test1@gmail.com", "4433234324443323432422", 400, Arrays.asList("size must be between 11 and 21"), "Validate Error Msg - Phone Value is more than 21 numbers"},
                {4, "Siva", "Kumari", true, "test1@gmail.com", "44332343244", 200, null, "Validate Success Booking - Phone Value size is 11"},
                {1, "Siva", "Kumari", true, "test1@gmail.com", "443323432444343536354", 200, null, "Validate Success Booking - Phone Value size is 21"},
                {2, "Siva", "Kumari", true, "test1@gmail.com", "", 400, Arrays.asList("size must be between 11 and 21", "must not be empty"), "Validate Error Msg - Phone Value is empty"},
                {2, "Siva", "Kumari", true, "test1@gmail.com", " ", 400, Arrays.asList("size must be between 11 and 21"), "Validate Error Msg - Phone Value is empty spaces"},
                {3, "admin", "admin", true, "test1@gmail.com", null, 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - Phone Value is null"}
        };
    }

    @DataProvider(name = "CreateBookingDatesNegativeCaseData")
    public Object[][] createBookingDatesNegativeCaseData() {
        return new Object[][]{
                //CheckIn,CheckOut,StatusCode,ErrorMsg,Test Case Description
                {null, "2025-12-01", 400, Arrays.asList("must not be null"), "Validate Error Msg - Checkin Date is null"},
                {"2025-12-01", null, 400, Arrays.asList("must not be null"), "Validate Error Msg - CheckOut Date is null"},
                {null, null, 400, Arrays.asList("must not be null", "must not be null"), "Validate Error Msg - CheckOut Date is null"},
                {"2025/12/01", "2025-12-02", 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - Checkin Date format pattern is wrong"},
                {"2025-12-01", "2025/12/02", 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - CheckOut Date format pattern is wrong"},
                {"2025-12-01", "2025-12-02", 400, Arrays.asList("Both Dates are Same"), "Validate Error Msg - Both Checkin and Checkout date is Same"},
                {"2024-12-01", "2025-11-30", 400, Arrays.asList("Checkin Date is less than Current Date"), "Validate Error Msg - Checkin Date is less than Current Date"},
                {"2025-12-01", "2024-11-30", 400, Arrays.asList("Checkout Date is less than Current Date"), "Validate Error Msg - Checkout Date is less than Current Date"},
                {"2025-12-01", "2025-11-30", 400, Arrays.asList("Checkout Date is less than Checkin"), "Validate Error Msg - Checkout Date is less than Checkin"},
                {"2024-12-25", "2024-12-26", 400, Arrays.asList("Checkin Date is less than Current Date", "Checkout Date is less than Current Date"), "Validate Error Msg - Checkout and Checkin Date is less than Current Date"},
                {"test", "2025-12-01", 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - Checkin Date is not Date Value"},
                {"2025-12-01", "test", 400, Arrays.asList("Failed to create booking"), "Validate Error Msg - CheckOut Date is not Date Value"}
        };
    }

}
