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

## **How to Run the Project**

### **1. Prerequisites**
- Java JDK 8 or higher
- [Apache Maven](https://maven.apache.org/)
- (Optional) [VS Code with Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

---

### **2. Build the Project**
From the project root directory, run:

```bash
mvn clean package
```

This compiles the code and creates a JAR file in the `target/` directory.

---

### **3. Run the Program**

#### **Option A: Using Maven (Recommended)**
Make sure you’ve added the `exec-maven-plugin` in `pom.xml`, then run:
```bash
mvn exec:java
```

#### **Option B: Run the JAR Manually**
```bash
java -cp target/email-client-1.0-SNAPSHOT.jar com.damika.emailclient.EmailClient
```

> ⚠️ Ensure that `ClientList.txt` and `EmailList.txt` are in a writable location such as a `data/` folder if you're modifying them at runtime.

---

## **Dependencies**
- **Java Development Kit (JDK)**
- **[javax.mail](https://javaee.github.io/javamail/)** for email functionality
- **Maven** for build automation

---

## **License**
This project is licensed under the **[MIT License](LICENSE)**.

---

## **Acknowledgments**
Created as part of **IN20-S2-CS1040 - Program Construction, University of Moratuwa**, under the guidance of **Dr. (Ms) Surangika Ranathunga**.
