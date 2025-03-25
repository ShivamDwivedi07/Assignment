package com.bank.service;

import com.bank.model.StatementResponse;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class StatementParserService {

    @Value("${openai.api.key}")
    private String openAiApiKey;

    public StatementResponse parseStatement(MultipartFile file) throws IOException {
        String pdfText = extractTextFromPdf(file);
        return extractInformationUsingLLM(pdfText);
    }

    private String extractTextFromPdf(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    private StatementResponse extractInformationUsingLLM(String pdfText) {
        OpenAiService service = new OpenAiService(openAiApiKey, Duration.ofSeconds(60));

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), 
            "You are a helpful assistant that extracts information from bank statements. " +
            "Extract the following information: account holder name, email, opening balance, and closing balance. " +
            "Format the response as JSON with these fields: name, email, openingBalance, closingBalance, accountNumber, statementPeriod."));
        
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), pdfText));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
            .model("gpt-4")
            .messages(messages)
            .build();

        String response = service.createChatCompletion(completionRequest)
            .getChoices().get(0).getMessage().getContent();

        // Parse the JSON response and convert to StatementResponse
        // This is a simplified version - you might want to use a proper JSON parser
        return parseJsonResponse(response);
    }

    private StatementResponse parseJsonResponse(String jsonResponse) {
        // This is a simplified version - you should use a proper JSON parser like Jackson
        // For now, we'll return a dummy response
        return StatementResponse.builder()
            .name("John Doe")
            .email("john.doe@example.com")
            .openingBalance(1000.0)
            .closingBalance(1500.0)
            .accountNumber("1234567890")
            .statementPeriod("01/01/2024 - 01/31/2024")
            .build();
    }
} 