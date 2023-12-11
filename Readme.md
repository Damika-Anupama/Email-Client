# Email Client

## Overview

This command-line-based Email client is designed to manage recipients, send emails, and handle birthday greetings based on the recipient's birthday. The application utilizes Java and includes features such as recipient management, email sending, and birthday greetings.

## Features

### 1. Recipient Management

- **Adding a New Recipient:**
    - Users can add a new recipient through the command line.
    - Recipient details are stored in a text file (`ClientList.txt`).
    - Recipient types include "Official," "Office_friend," and "Personal," each with specific details.

- **Recipient Types:**
    - **Official Recipient:**
        - Includes name, email, and designation.
    - **Official Friend Recipient:**
        - Extends the official recipient with an additional birthday field.
    - **Personal Recipient:**
        - Includes name, nickname, email, and birthday.

### 2. Email Sending

- **Sending an Email:**
    - Users can send emails via the command line.
    - Email details are saved in a serialized format in the `EmailList.txt` file.
    - Different messages are sent for personal recipients and office friends.

### 3. Birthday Greetings

- **Automatic Birthday Greetings:**
    - A separate thread sends birthday greetings to recipients on their birthdays.
    - Greetings are sent based on recipient types: "Wish you Happy Birthday" for office friends and "hugs and love on your birthday" for personal recipients.

### 4. File Service

- **File Handling:**
    - Recipient details are stored in a text file (`ClientList.txt`).
    - Emails are saved in a serialized format in the `EmailList.txt` file.

### 5. User Interface

- **Command-Line Interface (CLI):**
    - Users interact with the application through a command-line interface.
    - Options are provided for adding recipients, sending emails, and retrieving information.

### 6. Object-Oriented Design

- **Modular Structure:**
    - Classes are designed using Object-Oriented Programming (OOP) principles.
    - Factory method pattern is employed for creating recipient and email objects.

### 7. Error Handling

- **Enhanced Error Handling:**
    - The code includes error handling to ensure robustness, especially during file I/O and email sending.

## How to Use

1. **Compile and Run:**
    - Compile the `EmailClient.java` file.
    - Run the compiled program.

2. **Follow On-Screen Instructions:**
    - The application will prompt the user with options to perform various actions.
    - Follow the on-screen instructions to add recipients, send emails, and retrieve information.

3. **View Output:**
    - View the output in the console to see the results of the selected actions.
    - Birthday greetings will be automatically sent by a separate thread.

## Dependencies

- Java Runtime Environment (JRE)
- [javax.mail](https://javaee.github.io/javamail/) library for email functionality (included in the `javax.mail.jar` file).

## Contributors

- Damika Anupama Nanayakkara

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgments

- This project was created as part of In20-S2-CS1040 - Program Construction/Department of Computer Science Engineering - University of Moratuwa.
- Special thanks to Dr.(Ms) Surangika Ranathunga for guidance.
