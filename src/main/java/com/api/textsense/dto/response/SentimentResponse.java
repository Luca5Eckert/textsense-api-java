package com.api.textsense.dto.response;

public record SentimentResponse(
        double score,
        String label
) {
}
