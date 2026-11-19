/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.refiloetsebochabeli.poe.prog5121.poe;

/**
 *
 * @author LENOVO
 */
import java.util.Scanner;

/**
 * Main program for QuickChat: registration, login, then the messaging menu.
 */
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
                runQuickChat(scanner);
            }
        }

        scanner.close();
    }

    // Shows the welcome message and the menu until the user chooses Quit
    private static void runQuickChat(Scanner scanner) {
        System.out.println();
        System.out.println("Welcome to QuickChat.");

        int limit = askHowManyMessages(scanner);
        Message.loadStoredMessages();
        boolean messagesEntered = false;
        boolean running = true;

        // While loop: the app keeps running until the user chooses Quit
        while (running) {
            System.out.println();
            System.out.println("1) Send Messages");
            System.out.println("2) Show recently sent messages");
            System.out.println("3) Quit");
            System.out.println("4) Stored Messages");
            System.out.print("Choose an option: ");
            String option = scanner.nextLine();

            if (option.equals("1")) {
                if (messagesEntered) {
                    System.out.println("You have already entered all your messages.");
                } else {
                    sendMessages(scanner, limit);
                    messagesEntered = true;
                }
            } else if (option.equals("2")) {
                System.out.println("Coming Soon.");
            } else if (option.equals("3")) {
                running = false;
            } else if (option.equals("4")) {
                runStoredMenu(scanner);
            } else {
                System.out.println("Please choose 1, 2, 3 or 4.");
            }
        }

        System.out.println("Goodbye! Total messages sent: " + Message.returnTotalMessages());
    }

    // For loop: lets the user enter exactly the number of messages they chose
    private static void sendMessages(Scanner scanner, int limit) {
        for (int i = 0; i < limit; i++) {
            System.out.println();
            System.out.println("--- Message " + (i + 1) + " of " + limit + " ---");

            String text = askMessageText(scanner);
            Message msg = askRecipient(scanner, text, i);  // i is the message number
            System.out.println(msg.idGeneratedMessage());

            int choice = askSendChoice(scanner);
            System.out.println(msg.SentMessage(choice));
            if (choice == 1) {
                System.out.println(msg.details());
            }
        }

        System.out.println();
        System.out.println("Total messages sent: " + Message.returnTotalMessages());
    }

    // Asks how many messages the user wants to enter (must be a whole number above 0)
    private static int askHowManyMessages(Scanner scanner) {
        while (true) {
            System.out.print("How many messages do you wish to enter? ");
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                if (number > 0) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // not a number, so we ask again below
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }

    // Keeps asking until the message is 250 characters or less
    private static String askMessageText(Scanner scanner) {
        while (true) {
            System.out.print("Enter your message: ");
            String text = scanner.nextLine();
            if (text.length() <= 250) {
                System.out.println("Message sent");
                return text;
            }
            System.out.println("Please enter a message of less than 250 characters.");
        }
    }

    // Keeps asking until the phone number is written correctly
    private static Message askRecipient(Scanner scanner, String text, int number) {
        while (true) {
            System.out.print("Enter recipient cell number (e.g. +27718693002): ");
            Message msg = new Message(scanner.nextLine(), text, number);
            String result = msg.checkRecipientCell();
            System.out.println(result);
            if (result.startsWith("Cell phone number successfully")) {
                return msg;
            }
        }
    }

    // Asks: send, disregard or store (only 1, 2 or 3 is accepted)
    private static int askSendChoice(Scanner scanner) {
        while (true) {
            System.out.println("1) Send Message");
            System.out.println("2) Disregard Message");
            System.out.println("3) Store Message to send later");
            System.out.print("Choose: ");
            String input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                return Integer.parseInt(input);
            }
            System.out.println("Please choose 1, 2 or 3.");
        }
    }

    // Small menu for stored messages (a to f)
    private static void runStoredMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println();
            System.out.println("--- Stored Messages ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID");
            System.out.println("d) Search for all messages for a recipient");
            System.out.println("e) Delete a message using its hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("g) Back to main menu");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim().toLowerCase();

            switch (choice) {
                case "a":
                    System.out.println(Message.storedSendersAndRecipients());
                    break;
                case "b":
                    String longest = Message.longestStoredMessage();
                    if (longest.isEmpty()) {
                        System.out.println("There are no stored messages.");
                    } else {
                        System.out.println("Longest stored message: " + longest);
                    }
                    break;
                case "c":
                    System.out.print("Enter the message ID: ");
                    Message found = Message.findById(scanner.nextLine().trim());
                    if (found == null) {
                        System.out.println("No message with that ID.");
                    } else {
                        System.out.println("Recipient: " + found.getRecipient());
                        System.out.println("Message: " + found.getText());
                    }
                    break;
                case "d":
                    System.out.print("Enter the recipient's number: ");
                    String number = scanner.nextLine().trim();
                    boolean any = false;
                    for (String t : Message.messagesForRecipient(number)) {
                        System.out.println("- " + t);
                        any = true;
                    }
                    if (!any) {
                        System.out.println("No messages for that recipient.");
                    }
                    break;
                case "e":
                    System.out.print("Enter the message hash: ");
                    System.out.println(Message.deleteByHash(scanner.nextLine().trim()));
                    Message.saveAllStored();
                    break;
                case "f":
                    System.out.println(Message.storedReport());
                    break;
                case "g":
                    back = true;
                    break;
                default:
                    System.out.println("Please choose a, b, c, d, e, f or g.");
            }
        }
    }
}
