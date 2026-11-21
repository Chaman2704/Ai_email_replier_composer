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

This repository is a Java web application, not a packaged VS Code extension or browser extension. To turn it into an extension:

- A **VS Code extension** could call this API from a command or webview.
- A **browser extension** could send selected email text to this endpoint and display the result.
- The extension would need its own `package.json`, extension manifest, build process, and GitHub repository folder.

## Test

```powershell
.\mvnw.cmd test
```
