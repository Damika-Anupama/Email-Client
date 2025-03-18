# **Email Client**

## **Overview**
This is a command-line-based Email client that allows users to manage recipients, send emails, and automatically send birthday greetings. The application follows an **object-oriented design** and utilizes **Java** with the `javax.mail` library for email functionality.

## **Features**
- **Recipient Management**: Users can add recipients (`Official`, `Office_friend`, `Personal`), stored in `ClientList.txt`.
- **Email Sending**: Emails can be sent via CLI, with details stored in `EmailList.txt` (serialized format).
- **Birthday Greetings**: Automatically sends personalized greetings to recipients on their birthday.
- **File Handling**: Uses `ClientList.txt` for recipient data and `EmailList.txt` for email storage.
- **Object-Oriented Design**: Implements **Factory Method Pattern** for recipient and email creation.
- **Error Handling**: Includes robust error handling for file I/O and email operations.

## **How to Run in VS Code**
### **1. Install Java and VS Code Extensions**
Ensure you have:
- Java installed (`JDK 8` or later).
- VS Code with the **"Extension Pack for Java"**.

### **2. Compile and Run the Project**
#### **Compile Java Files into `out/` Folder**
```sh
javac -cp "Lib/javax.mail.jar" -d out $(find src -name "*.java")
```
*(For Windows, use `dir /s /b src\*.java` instead of `find`.)*

#### **Run the Program**
```sh
java -cp "Lib/javax.mail.jar:out" EmailClient
```
*(For Windows, use `;` instead of `:` in classpath.)*

## **Dependencies**
- **Java Development Kit (JDK)**
- **[javax.mail](https://javaee.github.io/javamail/) library** (`javax.mail.jar` included)

## **License**
This project is licensed under the **[MIT License](LICENSE)**.

## **Acknowledgments**
Created as part of **IN20-S2-CS1040 - Program Construction, University of Moratuwa**, under the guidance of **Dr. (Ms) Surangika Ranathunga**.
