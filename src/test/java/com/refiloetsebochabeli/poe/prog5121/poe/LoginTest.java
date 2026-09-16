/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.refiloetsebochabeli.poe.prog5121.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author LENOVO
 */
public class LoginTest {

    private Login login;

    @BeforeEach
    public void setUp() {
        login = new Login();
    }

    // ---------- checkUserName() ----------

    @Test
    public void testUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    public void testUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    // ---------- checkPasswordComplexity() ----------

    @Test
    public void testPasswordMeetsComplexityRequirements() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testPasswordDoesNotMeetComplexityRequirements() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    // ---------- checkCellPhoneNumber() ----------

    @Test
    public void testCellPhoneNumberCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testCellPhoneNumberIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    // ---------- registerUser() ----------

    @Test
    public void testRegisterUserUsernameIncorrectlyFormatted() {
        String result = login.registerUser("kyle!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
        assertEquals(
            "Username is not correctly formatted; please ensure that your username "
          + "contains an underscore and is no more than five characters in length.",
            result
        );
    }

    @Test
    public void testRegisterUserPasswordDoesNotMeetComplexity() {
        String result = login.registerUser("kyl_1", "password", "+27838968976", "Kyle", "Naidoo");
        assertEquals(
            "Password is not correctly formatted; please ensure that the password "
          + "contains at least eight characters, a capital letter, a number, and a "
          + "special character.",
            result
        );
    }

    @Test
    public void testRegisterUserAllConditionsMet() {
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
        assertEquals(
            "Username successfully captured. Password successfully captured. "
          + "Cell phone number successfully added.",
            result
        );
    }

    // ---------- loginUser() ----------

    @Test
    public void testLoginSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailed() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
        assertFalse(login.loginUser("kyl_1", "WrongPassword1!"));
    }

    // ---------- returnLoginStatus() ----------

    @Test
    public void testReturnLoginStatusSuccessful() {
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
        String result = login.returnLoginStatus(true);
        assertEquals("Welcome Kyle, Naidoo it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatusFailed() {
        String result = login.returnLoginStatus(false);
        assertEquals("Username or password incorrect, please try again.", result);
    }
    @Test
 public void testIsRegisteredAfterSuccessfulRegistration() {
    login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Naidoo");
    assertTrue(login.isRegistered());
}
}

