package com.api.textsense.dto.response;

public record Statistics(
        int characterCount,
        int wordCount,
        int sentenceCount,
        double readingTimeSeconds
) {
}
