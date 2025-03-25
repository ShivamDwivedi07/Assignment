# CASA Statement Parser API

This is a Spring Boot application that provides an API for parsing CASA (Current Account and Savings Account) bank statements using OpenAI's GPT model. The API extracts key information such as account holder name, email, opening balance, and closing balance from PDF statements.

## Features

- PDF statement parsing using PDFBox
- Information extraction using OpenAI GPT-4
- RESTful API endpoint for statement processing
- Support for multipart file uploads
- Configurable file size limits

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- OpenAI API key

## Setup

1. Clone the repository:
```bash
git clone <repository-url>
cd casa-statement-parser
```

2. Set up your OpenAI API key:
   - Create a `.env` file in the project root
   - Add your OpenAI API key:
     ```
     OPENAI_API_KEY=your-api-key-here
     ```

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring:boot run
```

The application will start on `http://localhost:8080`.

## API Usage

### Parse Statement

**Endpoint:** `POST /api/v1/statements/parse`

**Content-Type:** `multipart/form-data`

**Parameters:**
- `file`: PDF statement file
- `firstName`: Account holder's first name
- `dateOfBirth`: Account holder's date of birth
- `accountType`: Type of account (e.g., "SAVINGS", "CURRENT")

**Example using curl:**
```bash
curl -X POST http://localhost:8080/api/v1/statements/parse \
  -F "file=@statement.pdf" \
  -F "firstName=John" \
  -F "dateOfBirth=1990-01-01" \
  -F "accountType=SAVINGS"
```

**Example Response:**
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "openingBalance": 1000.0,
  "closingBalance": 1500.0,
  "accountNumber": "1234567890",
  "statementPeriod": "01/01/2024 - 01/31/2024"
}
```

## Project Structure

```
src/main/java/com/bank/
├── CasaStatementParserApplication.java
├── controller/
│   └── StatementController.java
├── model/
│   ├── StatementRequest.java
│   └── StatementResponse.java
└── service/
    └── StatementParserService.java
```

## Configuration

The application can be configured through `src/main/resources/application.properties`:

- `server.port`: Server port (default: 8080)
- `openai.api.key`: OpenAI API key
- `spring.servlet.multipart.max-file-size`: Maximum file size (default: 10MB)
- `spring.servlet.multipart.max-request-size`: Maximum request size (default: 10MB)

## Error Handling

The API returns appropriate HTTP status codes:
- 200: Successful parsing
- 400: Invalid request
- 500: Internal server error

## Security Considerations

- The API key should be stored securely and not committed to version control
- Consider implementing authentication and authorization
- Validate input files for security risks
- Implement rate limiting for API endpoints

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 