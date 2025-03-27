# 📧 Email Client

## 🧾 Overview
This is a **Java-based command-line email client** designed to manage recipients, send emails manually, and automatically deliver birthday greetings. It follows clean **object-oriented principles**, using the **Factory Method Design Pattern**, and supports data persistence via local files.



## 🚀 Features

- 👥 **Recipient Management**  
  Add and manage recipients of types: `Official`, `Office_friend`, `Personal`.  
  Recipient details are saved in `ClientList.txt`.

- ✉️ **Email Sending**  
  Send emails via command-line interface.  
  Email content and metadata are stored (serialized) in `EmailList.txt`.

- 🎂 **Birthday Greetings Automation**  
  Automatically sends birthday wishes when the app is launched on the recipient’s birthday.  
  Prevents duplicate greetings on the same day.

- 💾 **File-Based Storage**  
  - `ClientList.txt` → stores recipient information  
  - `EmailList.txt` → stores sent emails as serialized Java objects

- 🧱 **Design Patterns & Structure**  
  Uses **Factory Method Pattern** for object creation (Recipient and Email).  
  Code is modular and adheres to object-oriented best practices.

- 🦺 **Null Safety**  
  Leverages the [**Checker Framework**](https://checkerframework.org/manual) with `@NonNull`, `@Nullable`, etc., for safer code via nullness annotations.

- 🛡️ **Error Handling**  
  Handles file I/O errors, malformed inputs, email configuration issues, etc.



## ⚙️ How to Run the Project

### 1️⃣ Prerequisites

- Java JDK 8 or higher  
- [Apache Maven](https://maven.apache.org/)  
- Internet access (for email functionality) 
- (Optional) [VS Code with Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)



### 2️⃣ Build the Project

From the root directory:

```bash
mvn clean package
```

This compiles the source code and generates a JAR file in the `target/` directory.



### 3️⃣ Run the Program

#### ✅ Run via Maven (Recommended)

Make sure your `pom.xml` has `exec-maven-plugin` configured, then run:

```bash
mvn exec:java -Dexec.mainClass="com.damika.emailclient.EmailClient"
```

> 📁 Make sure `ClientList.txt` and `EmailList.txt` are available inside the `data/` directory and **writable**.



## 📦 Project Structure

```
.
├── Assignment.txt
├── data
│   ├── ClientList.txt
│   └── EmailList.txt
├── LICENSE
├── pom.xml
├── Readme.md
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── damika
    │   │           └── emailclient
    │   │               ├── EmailClient.java
    │   │               ├── factory
    │   │               │   ├── EmailController.java
    │   │               │   ├── implementations
    │   │               │   │   ├── BasicEmailController.java
    │   │               │   │   ├── EmailCreator.java
    │   │               │   │   ├── OfficialRecipientController.java
    │   │               │   │   ├── OfficialRecipientCreator.java
    │   │               │   │   ├── OfficialRecipientFriendController.java
    │   │               │   │   ├── OfficialRecipientFriendCreator.java
    │   │               │   │   ├── PersonalRecipientController.java
    │   │               │   │   └── PersonalRecipientCreator.java
    │   │               │   ├── NewEmailCreator.java
    │   │               │   ├── NewRecipientCreator.java
    │   │               │   └── RecipientController.java
    │   │               ├── handler
    │   │               │   └── GlobalExceptionHandler.java
    │   │               ├── model
    │   │               │   ├── Email.java
    │   │               │   ├── Official_Recipient_Friend.java
    │   │               │   ├── Official_Recipient.java
    │   │               │   ├── Personal_Recipient.java
    │   │               │   └── Recipient.java
    │   │               ├── service
    │   │               │   ├── EmailSendingService.java
    │   │               │   └── FileService.java
    │   │               └── util
    │   │                   └── AppendableObjectOutputStream.java
    │   └── resources
    │       └── application.properties
    └── test
        └── java
            └── com
                └── damika
                    └── emailclient
                        └── AppTest.java
```



## 📚 Dependencies

- [JavaMail (javax.mail)](https://javaee.github.io/javamail/)
- Apache Maven for dependency management and build



## 📝 Notes

- It’s recommended to **.gitignore `ClientList.txt` and `EmailList.txt`** to avoid committing runtime data.
- You can extend the system further with a GUI, scheduling services, or cloud-based email APIs (like Gmail API).



## 🪪 License

This project is licensed under the [MIT License](LICENSE).



## 🙏 Acknowledgments

Developed for **IN20-S2-CS1040 – Program Construction**,  
**Department of Computer Science & Engineering, University of Moratuwa, Sri Lanka**,  
under the guidance of **Dr. (Ms) Surangika Ranathunga**.