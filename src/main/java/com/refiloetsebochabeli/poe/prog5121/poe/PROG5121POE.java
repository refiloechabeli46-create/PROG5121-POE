/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.refiloetsebochabeli.poe.prog5121.poe;

/**
 *
 * @author LENOVO
 */
import java.util.Scanner;

public class PROG5121POE {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();

        System.out.println("=== Registration ===");

        System.out.print("Enter a username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your South African cell phone number (e.g. +27831234567): ");
        String cellPhoneNumber = scanner.nextLine();

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        String registrationMessage = login.registerUser(username, password, cellPhoneNumber, firstName, lastName);
        System.out.println(registrationMessage);

        // Only proceed to login if registration was fully successful
        boolean registrationSuccessful = login.isRegistered();

        if (registrationSuccessful) {
            System.out.println();
            System.out.println("=== Login ===");

            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            boolean loginSuccessful = login.loginUser(loginUsername, loginPassword);
            String loginStatusMessage = login.returnLoginStatus(loginSuccessful);
            System.out.println(loginStatusMessage);
            
            if (loginSuccessful) {
                System.out.println();
                System.out.println("Welcome to QuickChat.");

                System.out.print("How many messages do you wish to enter? ");
                int limit = Integer.parseInt(scanner.nextLine());
                int entered = 0;
                boolean running = true;

                while (running) {
                    System.out.println();
                    System.out.println("1) Send Messages");
                    System.out.println("2) Show recently sent messages");
                    System.out.println("3) Quit");
                    System.out.print("Choose an option: ");
                    String option = scanner.nextLine();

                    if (option.equals("1")) {

                        if (entered >= limit) {
                            System.out.println("You have entered all your messages.");
                        } else {
                            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
                            String recipient = scanner.nextLine();
                            System.out.print("Enter your message: ");
                            String text = scanner.nextLine();

                            if (text.length() > 250) {
                                System.out.println("Please enter a message of less than 250 characters.");
                            } else {
                                Message msg = new Message(recipient, text, entered);
                                String cellResult = msg.checkRecipientCell();
                                System.out.println(cellResult);

                                if (cellResult.startsWith("Cell phone number successfully")) {
                                    System.out.println("Message sent");
                                    System.out.println("1) Send Message");
                                    System.out.println("2) Disregard Message");
                                    System.out.println("3) Store Message to send later");
                                    System.out.print("Choose: ");
                                    int choice = Integer.parseInt(scanner.nextLine());

                                    System.out.println(msg.SentMessage(choice));
                                    if (choice == 1) {
                                        System.out.println(msg.details());
                                    }

                                    entered++;
                                    if (entered >= limit) {
                                        System.out.println("Total messages sent: "
                                                + Message.returnTotalMessages());
                                    }
                                }
                            }
                        }

                    } else if (option.equals("2")) {
                        System.out.println("Coming Soon.");
                    } else if (option.equals("3")) {
                        running = false;
                    } else {
                        System.out.println("Please choose 1, 2 or 3.");
                    }
                }

                System.out.println("Goodbye! Total messages sent: "
                        + Message.returnTotalMessages());
            }
        }

        scanner.close();
    }
}
