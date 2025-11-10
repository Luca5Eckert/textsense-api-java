package com.api.textsense.dto.response;

public record Sentiment(
        double score,
        String label
) {
}
