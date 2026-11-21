# AI Email Writer

A Spring Boot and Gemini API application that generates professional email replies from email content and an optional tone.

## Project overview

- Java 17
- Spring Boot 4
- Maven
- Google Gemini API
- Spring Web and Spring WebFlux

The current application exposes a JSON POST endpoint:

```text
POST /api/email/generate
Content-Type: application/json

{
  "emailContent": "Thanks for the meeting. Could you send the notes?",
  "tone": "friendly"
}
```

## Local setup

1. Install JDK 17 and Maven, or use the included Maven Wrapper.
2. Create a Gemini API key in Google AI Studio.
3. Set environment variables in your shell:

   ```powershell
   $env:GEMINI_URL="https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent"
   $env:GEMINI_API_KEY="your-key-here"
   ```

4. Run:

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

Never commit your real API key. Keep secrets in environment variables or a local `.env` file excluded by `.gitignore`.

## VS Code / extension note

This repository currently contains the Java/Spring Boot backend and Gemini API integration.

For a Gmail compose/reply browser extension:

- The browser extension reads the draft or selected email text from Gmail.
- It sends that text and tone to `POST /api/email/generate`.
- The generated reply is inserted into the Gmail compose window.
- The extension needs a browser manifest such as `manifest.json`, extension scripts, and Gmail permissions.

No browser manifest or Gmail content script is currently present in this repository. Those files must be added to the repository for it to be loadable as a Chrome or Edge extension.

## Test

```powershell
.\mvnw.cmd test
```
