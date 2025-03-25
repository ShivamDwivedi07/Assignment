package com.bank.controller;

import com.bank.model.StatementRequest;
import com.bank.model.StatementResponse;
import com.bank.service.StatementParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/statements")
@RequiredArgsConstructor
public class StatementController {

    private final StatementParserService statementParserService;

    @PostMapping("/parse")
    public ResponseEntity<StatementResponse> parseStatement(
            @RequestPart("file") MultipartFile file,
            @RequestPart("firstName") String firstName,
            @RequestPart("dateOfBirth") String dateOfBirth,
            @RequestPart("accountType") String accountType) {
        try {
            StatementResponse response = statementParserService.parseStatement(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 