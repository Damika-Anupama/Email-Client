# Repository Audit & Recommendations

This document summarizes notable issues and improvement opportunities found across the Email Client codebase, along with recommended fixes and best practices.

## Security & Configuration
- **Hard-coded SMTP credentials and host configuration** are embedded directly in `EmailSendingService`, exposing secrets in source control and preventing environment-specific configuration. Externalize credentials (e.g., env vars or `application.properties`), use credential managers, and avoid committing secrets to the repo. 【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L26-L64】
- **No validation or sanitization of email content before sending.** Inputs are split and used directly, which can mis-handle commas and allows malformed addresses through. Introduce robust validation (RFC 5322-compatible email regex, minimum subject/content checks) before constructing messages. 【F:src/main/java/com/damika/emailclient/command/actions/SendEmailCommand.java†L23-L33】
- **Serialized email storage without integrity or schema checks** makes `EmailList.txt` vulnerable to tampering or incompatible class changes. Prefer a structured format (JSON/CSV/DB) with versioning and validation before deserialization to reduce corruption and security risks. 【F:src/main/java/com/damika/emailclient/service/FileService.java†L139-L199】

## Reliability & Error Handling
- **Null-pointer risks when recipient files are missing or empty.** `getAllRecipients` can return `null`, yet callers iterate without null checks, leading to runtime crashes in multiple commands and services. Return empty collections and guard all callers. 【F:src/main/java/com/damika/emailclient/service/FileService.java†L92-L106】【F:src/main/java/com/damika/emailclient/command/actions/AddRecipientCommand.java†L63-L69】【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L74-L122】
- **Unvalidated recipient parsing.** Several paths split input on fixed delimiters without verifying array lengths, so malformed input triggers `ArrayIndexOutOfBoundsException`. Add defensive parsing with clear user feedback. 【F:src/main/java/com/damika/emailclient/command/actions/AddRecipientCommand.java†L31-L49】【F:src/main/java/com/damika/emailclient/command/actions/SendEmailCommand.java†L23-L33】
- **File initialization swallows exceptions**, only printing to stderr, which can mask startup failures. Prefer structured logging and fail-fast behavior with actionable messages. 【F:src/main/java/com/damika/emailclient/service/FileService.java†L24-L43】

## Functional & UX Gaps
- **Input validation is minimal.** `InputValidator` checks counts but not data quality (email formats, date ranges, or duplicate values), reducing trust in stored data. Extend validators with stricter rules and reuse them before every critical operation. 【F:src/main/java/com/damika/emailclient/util/InputValidator.java†L11-L82】
- **Birthday email automation assumes all recipient records follow exact formatting** and does not handle optional fields or localization (time zones), which can result in missed or duplicate greetings. Normalize stored dates, parse with `LocalDate`, and compare using configured zones. 【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L66-L122】【F:src/main/java/com/damika/emailclient/service/FileService.java†L127-L136】

## Architecture & Maintainability
- **Cross-cutting concerns (I/O, parsing, and business logic) are intertwined** in commands and services, making testing difficult. Introduce separation of concerns: dedicated parsers, validation layers, and abstractions for storage and SMTP. 【F:src/main/java/com/damika/emailclient/command/actions/AddRecipientCommand.java†L31-L70】【F:src/main/java/com/damika/emailclient/service/FileService.java†L49-L137】
- **Lack of configuration management.** `application.properties` is empty and runtime settings are scattered. Centralize configurable values (file paths, SMTP host/port, templates) and load them via a config service to ease deployment changes. 【F:src/main/resources/application.properties†L1-L1】【F:src/main/java/com/damika/emailclient/service/FileService.java†L24-L43】【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L26-L122】
- **No automated tests or CI safeguards** are present, leaving regressions undetected. Add unit tests for parsers/validators, integration tests for file I/O, and a CI workflow (e.g., GitHub Actions) running `mvn test`. (Project lacks any test sources or workflow files.)

## Data Integrity & Observability
- **Recipient and email files can grow unbounded** and are never rotated or compacted. Introduce size limits, archival, or migration to a lightweight database to prevent performance degradation. 【F:src/main/java/com/damika/emailclient/service/FileService.java†L49-L159】
- **No structured logging or metrics.** Errors are printed or swallowed, making support difficult. Adopt a logging framework (e.g., `java.util.logging`/SLF4J), log at appropriate levels, and emit metrics for key actions (emails sent, parse failures). 【F:src/main/java/com/damika/emailclient/service/FileService.java†L40-L199】【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L26-L122】

## Developer Experience
- **User prompts and error messages are scattered and inconsistent.** Centralize messages and use enums/constants to avoid drift, improve localization, and simplify testing. 【F:src/main/java/com/damika/emailclient/command/actions/AddRecipientCommand.java†L20-L73】【F:src/main/java/com/damika/emailclient/service/EmailSendingService.java†L108-L122】
- **Repository lacks contribution hygiene** such as `.gitignore` entries for data files and build artifacts, risking accidental commits of runtime data. Add a `.gitignore` covering `data/*.txt`, `target/`, and IDE files.
