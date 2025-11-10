package com.api.textsense.model;

public record Sentiment(
        Label label,
        double score
) {
}
