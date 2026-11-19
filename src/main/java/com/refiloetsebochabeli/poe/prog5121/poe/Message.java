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
 * Message class for QuickChat (PROG5121 POE Part 2). Holds one message: its ID,
 * number, recipient, text and hash, and can check, send, store and display it.
 *
 * Attribution: - JSON storage uses the Gson library by Google. Available at:
 * https://github.com/google/gson (Accessed 18 October 2026). - Parts of this
 * code were written with help from Claude, an AI assistant by Anthropic.
 * Available at: https://claude.ai (Accessed 18 October 2026).
 */
public class Message {

    private static int totalSent = 0;
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIds = new ArrayList<>();
    // true = save to messages.json. The tests turn this off.
    private static boolean writeToFile = true;

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
        return "Message exceeds 250 characters by " + over + "; please reduce the size.";
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
                saveIdAndHash();
                return "Message successfully sent.";
            case 2:
                disregardedMessages.add(this);
                saveIdAndHash();
                return "Press 0 to delete the message.";
            case 3:
                storedMessages.add(this);
                saveIdAndHash();
                storeMessage();
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }
// Puts this message's ID and hash into their boxes

    private void saveIdAndHash() {
        messageIds.add(messageId);
        messageHashes.add(hash);
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
        disregardedMessages.clear();
        storedMessages.clear();
        messageHashes.clear();
        messageIds.clear();
        writeToFile = false;
    }

    // Shows one message in the right order
    public String details() {
        return "Message ID: " + messageId + "\nMessage Hash: " + hash
                + "\nRecipient: " + recipient + "\nMessage: " + text;
    }

    // Gives back the message ID
    public String getMessageId() {
        return messageId;
    }

    // Says that the ID was made
    public String idGeneratedMessage() {
        return "Message ID generated: " + messageId;
    }

    // Attribution: Reading messages.json into a list uses the Gson library
// (JsonParser and JsonArray). Available at: https://github.com/google/gson
// (Accessed 19 November 2026). This method was written with help from
// Claude, an AI assistant by Anthropic. Available at: https://claude.ai
// (Accessed 19 November 2026). 
// Reads messages.json and fills the Stored box
    public static void loadStoredMessages() {
        storedMessages.clear();
        Path file = Paths.get("messages.json");
        if (!Files.exists(file)) {
            return;
        }
        try {
            JsonArray array = JsonParser.parseString(Files.readString(file)).getAsJsonArray();
            int count = 1;
            for (JsonElement item : array) {
                JsonObject obj = item.getAsJsonObject();
                int number = obj.has("messageNumber")
                        ? obj.get("messageNumber").getAsInt() : count;
                Message m = new Message(
                        obj.get("messageID").getAsString(),
                        obj.get("recipient").getAsString(),
                        obj.get("message").getAsString(),
                        number);
                storedMessages.add(m);
                messageIds.add(m.messageId);
                messageHashes.add(m.hash);
                count++;
            }
        } catch (IOException e) {
            System.out.println("Could not read stored messages: " + e.getMessage());
        }
    }

// Saves the message into a JSON file
    public void storeMessage() {
        if (!writeToFile) {
            return;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Path file = Paths.get("messages.json");
        try {
            JsonArray array = Files.exists(file)
                    ? JsonParser.parseString(Files.readString(file)).getAsJsonArray()
                    : new JsonArray();
            JsonObject obj = new JsonObject();
            obj.addProperty("messageID", messageId);
            obj.addProperty("messageNumber", messageNumber);
            obj.addProperty("messageHash", hash);
            obj.addProperty("recipient", recipient);
            obj.addProperty("message", text);
            array.add(obj);
            Files.writeString(file, gson.toJson(array));
        } catch (IOException e) {
            System.out.println("Could not store message: " + e.getMessage());
        }
    }
    // ---- Small helpers (let other classes read private things) ----

    public String getText() {
        return text;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getHash() {
        return hash;
    }

// Gives back the text of every sent message
    public static List<String> sentMessageTexts() {
        List<String> result = new ArrayList<>();
        for (Message m : sentMessages) {
            result.add(m.text);
        }
        return result;
    }

// a. Sender and recipient of all stored messages
    public static String storedSendersAndRecipients() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append("Sender: You | Recipient: ").append(m.recipient).append("\n");
        }
        return sb.toString();
    }

// b. The longest stored message
    public static String longestStoredMessage() {
        String longest = "";
        for (Message m : storedMessages) {
            if (m.text.length() > longest.length()) {
                longest = m.text;
            }
        }
        return longest;
    }

// c. Find a message by its ID (looks in sent and stored). Gives null if not found.
    public static Message findById(String id) {
        for (Message m : sentMessages) {
            if (m.messageId.equals(id)) {
                return m;
            }
        }
        for (Message m : storedMessages) {
            if (m.messageId.equals(id)) {
                return m;
            }
        }
        return null;
    }

// d. All messages (sent or stored) for one recipient
    public static List<String> messagesForRecipient(String number) {
        List<String> result = new ArrayList<>();
        for (Message m : sentMessages) {
            if (m.recipient.equals(number)) {
                result.add(m.text);
            }
        }
        for (Message m : storedMessages) {
            if (m.recipient.equals(number)) {
                result.add(m.text);
            }
        }
        return result;
    }

// e. Delete a stored message using its hash
    public static String deleteByHash(String hashToFind) {
        for (int i = 0; i < storedMessages.size(); i++) {
            Message m = storedMessages.get(i);
            if (m.hash.equals(hashToFind)) {
                storedMessages.remove(i);
                messageHashes.remove(m.hash);
                messageIds.remove(m.messageId);
                return "Message: \"" + m.text + "\" successfully deleted.";
            }
        }
        return "No stored message has that hash.";
    }

// After a delete, this saves the stored messages into messages.json again
    public static void saveAllStored() {
        if (!writeToFile) {
            return;
        }
        JsonArray array = new JsonArray();
        for (Message m : storedMessages) {
            JsonObject obj = new JsonObject();
            obj.addProperty("messageID", m.messageId);
            obj.addProperty("messageNumber", m.messageNumber);
            obj.addProperty("messageHash", m.hash);
            obj.addProperty("recipient", m.recipient);
            obj.addProperty("message", m.text);
            array.add(obj);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Files.writeString(Paths.get("messages.json"), gson.toJson(array));
        } catch (IOException e) {
            System.out.println("Could not save messages: " + e.getMessage());
        }
    }

// f. Full report of all stored messages
    public static String storedReport() {
        if (storedMessages.isEmpty()) {
            return "There are no stored messages.";
        }
        StringBuilder sb = new StringBuilder();
        for (Message m : storedMessages) {
            sb.append(m.details()).append("\n\n");
        }
        return sb.toString();
    }

// Gives back the text of every disregarded message
    public static List<String> disregardedMessageTexts() {
        List<String> result = new ArrayList<>();
        for (Message m : disregardedMessages) {
            result.add(m.text);
        }
        return result;
    }

// How many IDs are in the Message ID box
    public static int messageIdCount() {
        return messageIds.size();
    }

// How many hashes are in the Message Hash box
    public static int messageHashCount() {
        return messageHashes.size();
    }
}
