# TextSense API

![Java](https://img.shields.io/badge/Java-21-blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green)

TextSense API is a lightweight, stateless (no database) API built with Java and Spring Boot to enable real-time text analysis. This service focuses on providing on-demand processing with high performance and simplicity.

---

## Features

- **Text Statistics**: Calculates word count, character count, sentence count, and estimated reading time.
- **Sentiment Analysis**: Analyzes the overall sentiment of the provided text, returning a sentiment label (e.g., POSITIVE, NEGATIVE) and a score.

---

## Tech Stack

- **Java**: Version 21
- **Spring Boot**: Version 3
- **Apache Maven**: Build tool
- **Stanford CoreNLP**: For sentiment analysis logic

---

## API Contract

### Endpoint: `POST /analyze`

#### Description:
Analyzes a block of text and returns comprehensive statistics and sentiment analysis.

#### Example Request Body

```json
{
  "text": "This new framework is absolutely brilliant. I am overjoyed with how easy it is to use, and the performance is simply stunning."
}
```

#### Example Response Body

```json
{
  "statistics": {
    "characterCount": 160,
    "wordCount": 29,
    "sentenceCount": 3,
    "readingTimeSeconds": 8
  },
  "sentiment": {
    "score": 3,
    "label": "POSITIVE"
  }
}
```

- **Score**: Integer from `0` to `4`, where `0` represents `VERY_NEGATIVE` and `4` represents `VERY_POSITIVE`.

---

## How to Run Locally

### Prerequisites
- **JDK**: Version 21 (or 17+)
- **Apache Maven**

### Steps
1. Clone the repository:
   ```bash
   git clone <URL_TO_YOUR_REPO>
   ```
2. Navigate to the project directory:
   ```bash
   cd textsense-api
   ```
3. Run the project:
   ```bash
   mvn spring-boot:run
   ```
4. Access the API:
   ```
   http://localhost:8080
   ```

---

## Usage Example

You can test the API using `curl` as shown below:

```bash
curl -X POST http://localhost:8080/analyze \
-H "Content-Type: application/json" \
-d '{
      "text": "This is a great test, but the second part was awful."
    }'
```

Sample Response:
```json
{
  "statistics": {
    "characterCount": 55,
    "wordCount": 12,
    "sentenceCount": 2,
    "readingTimeSeconds": 3
  },
  "sentiment": {
    "score": 2,
    "label": "NEUTRAL"
  }
}
```

---

Enjoy leveraging the **TextSense API** for all your real-time text analysis needs!
