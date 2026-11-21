package com.email.writer;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;


@Service   // this class is to help us make a api call to gemini
public class EmailGeneratorService {
	
	private final WebClient webClient;
	private final ObjectMapper objectMapper;
	
	public EmailGeneratorService(WebClient webClient, ObjectMapper objectMapper) {
		this.webClient = webClient;
		this.objectMapper = objectMapper;
	}

	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	
	public String generateEmailReply(EmailRequest emailRequest) {
		String prompt=buildPrompt(emailRequest);

		Map<String,Object> requestBody=Map.of(
				"contents", new Object[] {
						Map.of("parts",new Object[] {
								Map.of("text",prompt)
						})
				}
			);

		if (geminiApiKey == null || geminiApiKey.isBlank()) {
			throw new IllegalStateException("GEMINI_API_KEY is not configured.");
		}

		String response =webClient.post()
				.uri(geminiApiUrl + "?key=" + geminiApiKey)
				.header("Content-Type","application/json")
				.bodyValue(requestBody)
				.retrieve()
				.bodyToMono(String.class)
				.block();
		return extractResponseContent(response);
	}

	private String extractResponseContent(String response) {
		try {
			JsonNode root = objectMapper.readTree(response);
			JsonNode text = root.path("candidates").path(0).path("content").path("parts").path(0).path("text");
			if (text.isMissingNode() || text.isNull()) {
				throw new IllegalStateException("Gemini returned no usable email text.");
			}
			return text.asText();
		} catch (Exception exception) {
			throw new IllegalStateException("Could not read Gemini's response.", exception);
		}
	}

	private String buildPrompt(EmailRequest emailRequest) {
		StringBuilder prompt= new StringBuilder();
		prompt.append("Genrate a professional email reply for the following email content.Please dont generate a subject line");
		if(emailRequest.getTone() !=null && !emailRequest.getTone().isEmpty()) {
			prompt.append("Use a").append(emailRequest.getTone()).append(" tone.");
		}
		
		prompt.append("\n original email: \n").append(emailRequest.getEmailContent());
		return prompt.toString();
		
	}

	
}
