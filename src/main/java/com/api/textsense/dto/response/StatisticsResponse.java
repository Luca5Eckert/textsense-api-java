package com.api.textsense.dto.response;

public record StatisticsResponse(
        int characterCount,
        int wordCount,
        int sentenceCount,
        double readingTimeSeconds
) {
}
