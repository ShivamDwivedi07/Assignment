package com.bank.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StatementRequest {
    private String firstName;
    private String dateOfBirth;
    private String accountType;
    private MultipartFile statementFile;
} 