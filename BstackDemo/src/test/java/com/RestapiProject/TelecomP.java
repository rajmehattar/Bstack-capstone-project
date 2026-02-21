package com.RestapiProject;

import org.testng.annotations.Test;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import org.testng.Assert;

public class TelecomP {

    private static String authToken;
    private static String contactId;
    private static String userEmail;
    private static String userPassword = "myPassword";

    // 1. Register New User
    @Test(priority = 1)
    public void addNewUser() {
    	
    	System.out.println("Test add new user :");
    	
        userEmail = "testing" + System.currentTimeMillis() + "@test.com";  // Generate unique email

        Response res = given()
                .contentType("application/json")
                .body("{ \n"
                        + "\"firstName\": \"Test\", \n"
                        + "\"lastName\": \"User\", \n"
                        + "\"email\": \"" + userEmail + "\", \n"
                        + "\"password\": \"" + userPassword + "\" \n"
                        + "}")
                .when().post("https://thinking-tester-contact-list.herokuapp.com/users");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 201, "User creation failed");

        authToken = res.jsonPath().getString("token");
        Assert.assertNotNull(authToken, "Token is null after registration");
        System.out.println("Registered Email: " + userEmail);
        System.out.println("Generated Token (Registration): " + authToken);
    }

    // 2. Get User Profile
    @Test(priority = 2)
    public void getUserProfile() {
    	
    	System.out.println("Test get user profile :");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .when().get("https://thinking-tester-contact-list.herokuapp.com/users/me");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Get Profile failed");

        String email = res.jsonPath().getString("email");
        Assert.assertEquals(email, userEmail, "Email mismatch in profile");
    }

    // 3. Update User
    @Test(priority = 3)
    public void updateUser() {
    	
    	System.out.println("updateUser");

        userEmail = "updated" + System.currentTimeMillis() + "@test.com"; // Generate new email
        userPassword = "myNewPassword";  // Update password

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body("{ \n"
                        + "\"firstName\": \"Updated\", \n"
                        + "\"lastName\": \"Username\", \n"
                        + "\"email\": \"" + userEmail + "\", \n"
                        + "\"password\": \"" + userPassword + "\" \n"
                        + "}")
                .when().patch("https://thinking-tester-contact-list.herokuapp.com/users/me");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "User update failed");

        String email = res.jsonPath().getString("email");
        Assert.assertEquals(email, userEmail, "Email not updated");
    }

    // 4. Login User
    @Test(priority = 4)
    public void loginUser() {
    	System.out.println("loginUser");

    	
        Response res = given()
                .contentType("application/json")
                .body("{ \n"
                        + "\"email\":\"" + userEmail + "\", \n"
                        + "\"password\":\"" + userPassword + "\" \n"
                        + "}")
                .when().post("https://thinking-tester-contact-list.herokuapp.com/users/login");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Login failed");

        authToken = res.jsonPath().getString("token");
        Assert.assertNotNull(authToken, "Token is null after login");
        System.out.println("Logged in with Email: " + userEmail);
    }

    // (Contact tests remain same, they will use authToken)
    // 5. Add Contact
    @Test(priority = 5)
    public void addContact() {
    	System.out.println("addContact");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body("{ \n"
                        + "\"firstName\": \"John\", \n"
                        + "\"lastName\": \"Doe\", \n"
                        + "\"email\": \"jdoe" + System.currentTimeMillis() + "@fake.com\", \n"
                        + "\"phone\": \"8005555555\", \n"
                        + "\"city\": \"Anytown\", \n"
                        + "\"country\": \"USA\" \n"
                        + "}")
                .when().post("https://thinking-tester-contact-list.herokuapp.com/contacts");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 201, "Add Contact failed");

        contactId = res.jsonPath().getString("_id");
        Assert.assertNotNull(contactId, "Contact ID is null");
    }

    // 6. Get Contact List
    @Test(priority = 6)
    public void getContactList() {
    	System.out.println("getContactList");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Get Contact List failed");

        Assert.assertTrue(res.jsonPath().getList("$").size() > 0, "Contact list is empty");
    }

    // 7. Get Single Contact
    @Test(priority = 7)
    public void getContact() {
    	System.out.println("getContact");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .when().get("https://thinking-tester-contact-list.herokuapp.com/contacts/" + contactId);

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Get Contact failed");

        String id = res.jsonPath().getString("_id");
        Assert.assertEquals(id, contactId, "Contact ID mismatch");
    }

    // 8. Update Contact Fully
    @Test(priority = 8)
    public void updateContact() {
    	System.out.println("updateContact");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body("{ \n"
                        + "\"firstName\": \"Amy\", \n"
                        + "\"lastName\": \"Miller\", \n"
                        + "\"email\": \"amiller" + System.currentTimeMillis() + "@fake.com\", \n"
                        + "\"phone\": \"8005554242\", \n"
                        + "\"city\": \"Washington\", \n"
                        + "\"country\": \"Canada\" \n"
                        + "}")
                .when().put("https://thinking-tester-contact-list.herokuapp.com/contacts/" + contactId);

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Update Contact failed");

        String email = res.jsonPath().getString("email");
        Assert.assertTrue(email.contains("amiller"), "Contact email not updated");
    }

    // 9. Partial Update Contact
    @Test(priority = 9)
    public void updateContactPartial() {
    	System.out.println("updateContactPartial");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body("{ \"firstName\": \"Anna\" }")
                .when().patch("https://thinking-tester-contact-list.herokuapp.com/contacts/" + contactId);

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Partial Update failed");

        String firstName = res.jsonPath().getString("firstName");
        Assert.assertEquals(firstName, "Anna", "Partial update failed");
    }

    // 10. Logout User
    @Test(priority = 10)
    public void logoutUser() {
    	System.out.println("logoutUser");

        Response res = given()
                .header("Authorization", "Bearer " + authToken)
                .when().post("https://thinking-tester-contact-list.herokuapp.com/users/logout");

        res.then().log().body();
        Assert.assertEquals(res.statusCode(), 200, "Logout failed");
    }
}