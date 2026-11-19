Student Name: Refiloe Tsebo Chabeli
Student Number: ST10528733
Module Name: PROG5121
Assignment: POE Part 3

## About the Project

This project is a simple console-based chat application. It introduces basic features you would find in a real messaging app, starting with user registration and login. Before building the actual messaging
features, this first part focuses on getting the foundations right: validating user input, storing user details safely, and confirming everything works through automated unit tests. My goal was not just to
make something that runs, but to write testable, maintainable, and organised code the way a real software engineer would approach it, rather than just getting the output to match.

## Mission and Vision

The mission of this project is to build a solid understanding of the practical and theoretical side of early programming, with a focus on object-oriented principles, code testing, and version control. My
vision is for this project to be a stepping stone - both for the rest of this chat application and for how I approach programming projects going forward- aiming to build habits of writing clean, testable code
from the start rather than fixing it later.

## Project Goals

* Build the registration and login foundation for a console-based chat
  application.
* Implement validation for username, password complexity, and South
  African cell phone number formatting.
* Use classes, methods, and OOP principles to separate validation
  logic from the console interface, keeping the code organised and
  independently testable.
* Practice using Git for version control and JUnit for automated
  testing, as required by the module.

## Challenges Faced

As a beginner, the hardest part was understanding how to write code that is not only correct, but clean and easy to test. Designing the password complexity check pushed me to think through the logic
character by character rather than relying on syntax I already knew, and working with regular expressions for the cell phone validation was new to me. Setting up Git properly was also a real challenge - my
NetBeans project folder was initially separate from my cloned GitHub repository, which meant Git wasn't tracking my files at all until I diagnosed and fixed it. Getting comfortable with JUnit for testing was
another learning curve, but it taught me the value of automated checks and being able to prove my code works, rather than just assuming it does.

## Part 2: QuickChat Messaging

After a successful login, the user is welcomed to QuickChat and can choose from a menu:

1. Send Messages
2. Show recently sent messages (Coming Soon)
3. Quit
4. Stored Messages

### Features
- The user decides how many messages to enter when the program starts.
- Each message has a random 10-digit Message ID, a Message Hash, a recipient number and the message text (maximum 250 characters).
- The recipient number must start with an international code.
- The user can send a message, disregard it, or store it to send later.
- Stored messages are saved in a JSON file (messages.json).
- The total number of messages sent is shown at the end.

### Testing
- Unit tests are written with JUnit (MessageTest.java).
- GitHub Actions runs all the tests automatically every time code is pushed.

### How to run
Open the project in NetBeans and run PROG5121POE.java.


## Part 3: Store Data and Display Task

Part 3 adds arrays and a Stored Messages menu (option 4).

### Arrays
- Sent Messages: all messages that were sent.
- Disregarded Messages: all messages that were disregarded.
- Stored Messages: messages read from messages.json when the app starts.
- Message Hash: all the message hashes.
- Message ID: all the message IDs.

### Stored Messages menu
- a) Display the sender and recipient of all stored messages.
- b) Display the longest stored message.
- c) Search for a message ID and display the recipient and message.
- d) Search for all messages for a particular recipient.
- e) Delete a message using its message hash.
- f) Display a report of all stored messages.

### Testing
- MessageTest.java uses the five test messages from the brief.
- The tests check the Sent array, longest message, message ID search, recipient search, delete by hash and the report.
- GitHub Actions runs all tests automatically every time code is pushed.

### Attribution
- JSON reading and writing uses the Gson library by Google: https://github.com/google/gson (Accessed 19 November 2026).
- Parts of the code were written with help from Claude, an AI assistant by Anthropic: https://claude.ai (Accessed 19 November 2026).
