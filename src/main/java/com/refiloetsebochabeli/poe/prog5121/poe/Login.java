/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.refiloetsebochabeli.poe.prog5121.poe;

/**
 *
 * @author LENOVO
 */
public class Login {

    // Registered user's details (stored after successful registration)
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;

    public Login() {
    }

    /**
     * Checks that the username contains an underscore and is no more
     * than five characters long.
     */
   public boolean checkUserName(String username) {
    if (username == null) {
        return false;
    }
    return username.contains("_") && username.length() <= 5;
}
   
    /**
 * Checks that the password is at least eight characters long and
 * contains a capital letter, a number, and a special character.
 */
public boolean checkPasswordComplexity(String password) {
    if (password == null) {
        return false;
    }

    boolean longEnough = password.length() >= 8;
    boolean hasCapitalLetter = false;
    boolean hasNumber = false;
    boolean hasSpecialCharacter = false;

    for (int i = 0; i < password.length(); i++) {
        char c = password.charAt(i);
        if (Character.isUpperCase(c)) {
            hasCapitalLetter = true;
        } else if (Character.isDigit(c)) {
            hasNumber = true;
        } else if (!Character.isLetterOrDigit(c)) {
            hasSpecialCharacter = true;
        }
    }

    return longEnough && hasCapitalLetter && hasNumber && hasSpecialCharacter;
}
    /**
 * Checks that the cell phone number contains the international
 * country code and is correctly formatted.
 */
public boolean checkCellPhoneNumber(String cellPhoneNumber) {
    if (cellPhoneNumber == null) {
        return false;
    }
    return cellPhoneNumber.matches("^\\+27\\d{9}$");
}

   /**
 * Registers the user if all checks pass, and returns the
 * appropriate message.
 */
public String registerUser(String username, String password, String cellPhoneNumber,
                            String firstName, String lastName) {

    if (!checkUserName(username)) {
        return "Username is not correctly formatted; please ensure that your username "
             + "contains an underscore and is no more than five characters in length.";
    }

    if (!checkPasswordComplexity(password)) {
        return "Password is not correctly formatted; please ensure that the password "
             + "contains at least eight characters, a capital letter, a number, and a "
             + "special character.";
    }

    if (!checkCellPhoneNumber(cellPhoneNumber)) {
        return "Cell phone number incorrectly formatted or does not contain international code.";
    }

    // All checks passed — store the registered user's details
    this.username = username;
    this.password = password;
    this.cellPhoneNumber = cellPhoneNumber;
    this.firstName = firstName;
    this.lastName = lastName;

    return "Username successfully captured. Password successfully captured. "
         + "Cell phone number successfully added.";
}
    /**
 * Verifies that the given login details match the stored,
 * registered details.
 */
public boolean loginUser(String username, String password) {
    if (username == null || password == null) {
        return false;
    }
    return username.equals(this.username) && password.equals(this.password);
}

   /**
 * Returns the welcome message on success, or the failure
 * message otherwise.
 */
public String returnLoginStatus(boolean loginSuccessful) {
    if (loginSuccessful) {
        return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
    } else {
        return "Username or password incorrect, please try again.";
    }
}
}