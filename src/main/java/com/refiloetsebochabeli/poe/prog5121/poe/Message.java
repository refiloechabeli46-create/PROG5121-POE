/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.refiloetsebochabeli.poe.prog5121.poe;
import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

    /**
 * Message class for QuickChat (PROG5121 POE Part 2).
 * Holds one message: its ID, number, recipient, text and hash,
 * and can check, send, store and display it.
 *
 * Attribution:
 * - JSON storage uses the Gson library by Google.
 *   Available at: https://github.com/google/gson (Accessed 18 October 2026).
 * - Parts of this code were written with help from Claude, an AI assistant by Anthropic.
 *   Available at: https://claude.ai (Accessed 18 October 2026).
 */
public class Message {
    private static int totalSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String text;
    private final String hash;

    // Makes a message with a random 10-digit ID
    public Message(String recipient, String text, int messageNumber) {
        this(generateMessageId(), recipient, text, messageNumber);
    }

    // Builds the ID one random digit at a time, using a for loop
    private static String generateMessageId() {
        StringBuilder id = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            id.append(ThreadLocalRandom.current().nextInt(10));
        }
        return id.toString();
    }

    // Makes a message with an ID we choose (for tests)
    public Message(String id, String recipient, String text, int messageNumber) {
        this.messageId = id;
        this.recipient = recipient;
        this.text = text;
        this.messageNumber = messageNumber;
        this.hash = createMessageHash();
    }

    // Is the ID 10 characters or less?
    public boolean checkMessageID() {
        return messageId != null && messageId.length() <= 10;
    }

    // Does the phone number start with + and a country code?
    public String checkRecipientCell() {
        if (recipient != null && recipient.matches("^\\+\\d{1,3}\\d{1,10}$")) {
            return "Cell phone number successfully captured.";
        }
        return "Cell phone number is incorrectly formatted or does not contain an "
             + "international code. Please correct the number and try again.";
    }

    // Is the message 250 characters or less?
    public String checkMessageLength() {
        if (text.length() <= 250) {
            return "Message ready to send.";
        }
        int over = text.length() - 250;
        return "Message exceeds 250 characters by " + over + ", please reduce the size.";
    }

    // Makes the secret code, like 00:0:HITONIGHT
    public String createMessageHash() {
        String[] words = text.trim().split("\\s+");
        String first = words[0].replaceAll("[^A-Za-z0-9]", "");
        String last = words[words.length - 1].replaceAll("[^A-Za-z0-9]", "");
        return (messageId.substring(0, 2) + ":" + messageNumber + ":" + first + last).toUpperCase();
    }

    // 1 = send, 2 = disregard, 3 = store
    public String SentMessage(int choice) {
        switch (choice) {
            case 1:
                sentMessages.add(this);
                totalSent++;
                return "Message successfully sent.";
            case 2:
                return "Press 0 to delete the message.";
            case 3:
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    // Shows all sent messages
    public static String printMessages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : sentMessages) {
            sb.append(m.details()).append("\n\n");
        }
        return sb.toString();
    }

    // How many messages were sent?
    public static int returnTotalMessages() {
        return totalSent;
    }

    // Clears everything (used by tests)
    public static void resetForTests() {
        totalSent = 0;
        sentMessages.clear();
    }

    // Shows one message in the right order
    public String details() {
        return "Message ID: " + messageId + "\nMessage Hash: " + hash
             + "\nRecipient: " + recipient + "\nMessage: " + text;
    }

    // Saves the message into a JSON file
    public void storeMessage() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path file = Paths.get("messages.json");
        try {
            JsonArray array = Files.exists(file)
                ? JsonParser.parseString(Files.readString(file)).getAsJsonArray()
                : new JsonArray();
            JsonObject obj = new JsonObject();
            obj.addProperty("messageID", messageId);
            obj.addProperty("messageHash", hash);
            obj.addProperty("recipient", recipient);
            obj.addProperty("message", text);
            array.add(obj);
            Files.writeString(file, gson.toJson(array));
        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }
}
