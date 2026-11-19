/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.refiloetsebochabeli.poe.prog5121.poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class MessageTest {

    // Test data 1 from the brief (we choose the ID so the hash starts with 00)
    Message m1 = new Message("0012345678", "+27718693002",
            "Hi Mike, can you join us for dinner tonight?", 0);

    // Clears old messages before every test
    @BeforeEach
    void clean() {
        Message.resetForTests();
    }

    // ---- Message length: success and failure ----
    @Test
    void messageLengthSuccess() {
        assertEquals("Message ready to send.", m1.checkMessageLength());
    }

    @Test
    void messageLengthFailure() {
        String tooLong = "a".repeat(260);
        Message big = new Message("0012345678", "+27718693002", tooLong, 0);
        assertEquals("Message exceeds 250 characters by 10; please reduce the size.",
                big.checkMessageLength());
    }

    // ---- Recipient number: success and failure ----
    @Test
    void recipientSuccess() {
        assertEquals("Cell phone number successfully captured.", m1.checkRecipientCell());
    }

    @Test
    void recipientFailure() {
        Message bad = new Message("0012345678", "08575975889",
                "Hi Keegan, did you receive the payment?", 1);
        assertEquals("Cell phone number is incorrectly formatted or does not contain an "
                + "international code. Please correct the number and try again.",
                bad.checkRecipientCell());
    }

    // ---- Hash ----
    @Test
    void hashTest1() {
        assertEquals("00:0:HITONIGHT", m1.createMessageHash());
    }

    @Test
    void hashLoop() {
        String[] messages = {
            "Hi Mike, can you join us for dinner tonight?",
            "Hi Keegan, did you receive the payment?"
        };
        String[] expected = {"00:0:HITONIGHT", "00:1:HIPAYMENT"};

        for (int i = 0; i < messages.length; i++) {
            Message m = new Message("0012345678", "+27718693002", messages[i], i);
            assertEquals(expected[i], m.createMessageHash());
        }
    }

    // ---- Message ID ----
    @Test
    void messageIdCreated() {
        Message m = new Message("+27718693002", "Hello there", 0);
        assertTrue(m.checkMessageID());
        assertTrue(m.details().contains("Message ID: "));
        assertEquals("Message ID generated: " + m.getMessageId(), m.idGeneratedMessage());
    }

    // ---- Send / Disregard / Store ----
    @Test
    void sentMessageOptions() {
        assertEquals("Message successfully sent.", m1.SentMessage(1));
        assertEquals("Press 0 to delete the message.", m1.SentMessage(2));
        assertEquals("Message successfully stored.", m1.SentMessage(3));
        assertEquals(1, Message.returnTotalMessages());
    }

    // Nothing has been sent yet, so the total is 0
    @Test
    void totalStartsAtZero() {
        assertEquals(0, Message.returnTotalMessages());
    }

    // A message that is disregarded must not be counted
    @Test
    void disregardedMessageIsNotCounted() {
        m1.SentMessage(2);
        assertEquals(0, Message.returnTotalMessages());
    }

    // A sent message must show up in the printed list
    @Test
    void sentMessageIsPrinted() {
        m1.SentMessage(1);
        assertTrue(Message.printMessages().contains("Message ID: 0012345678"));
    }

    // An ID with more than 10 characters is not allowed
    @Test
    void messageIdTooLong() {
        Message longId = new Message("12345678901", "+27718693002", "Hi there", 0);
        assertFalse(longId.checkMessageID());
    }
    
    // ---- Part 3: the 5 test messages from the brief ----
private final Message t1 = new Message("0000000001", "+27834557896",
        "Did you get the cake?", 1);
private final Message t2 = new Message("0000000002", "+27838884567",
        "Where are you? You are late! I have asked you to be on time.", 2);
private final Message t3 = new Message("0000000003", "+27834484567",
        "Yohoooo, I am at your gate.", 3);
private final Message t4 = new Message("0000000004", "0838884567",
        "It is dinner time!", 4);
private final Message t5 = new Message("0000000005", "+27838884567",
        "Ok, I am leaving without you.", 5);

// Puts each message in the right box: 1 = Sent, 2 = Disregard, 3 = Stored
private void loadTestData() {
    t1.SentMessage(1);
    t2.SentMessage(3);
    t3.SentMessage(2);
    t4.SentMessage(1);
    t5.SentMessage(3);
} 

@Test
void sentArrayCorrectlyPopulated() {
    loadTestData();
    assertEquals(List.of("Did you get the cake?", "It is dinner time!"),
            Message.sentMessageTexts());
}

@Test
void displayLongestMessage() {
    loadTestData();
    assertEquals("Where are you? You are late! I have asked you to be on time.",
            Message.longestStoredMessage());
}

@Test
void searchForMessageId() {
    loadTestData();
    assertEquals("It is dinner time!", Message.findById("0000000004").getText());
}

@Test
void searchByRecipient() {
    loadTestData();
    assertEquals(List.of(
            "Where are you? You are late! I have asked you to be on time.",
            "Ok, I am leaving without you."),
            Message.messagesForRecipient("+27838884567"));
}

@Test
void deleteByHash() {
    loadTestData();
    assertEquals("Message: \"Where are you? You are late! I have asked you to be on time.\" successfully deleted.",
            Message.deleteByHash(t2.getHash()));
}

@Test
void displayReport() {
    loadTestData();
    String report = Message.storedReport();
    assertTrue(report.contains(t2.getHash()));
    assertTrue(report.contains("+27838884567"));
    assertTrue(report.contains("Ok, I am leaving without you."));
}
}
