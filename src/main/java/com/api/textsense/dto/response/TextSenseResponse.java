package com.api.textsense.dto.response;

import java.util.List;

public record TextSenseResponse(
        StatisticsResponse statisticsResponse,
        SentimentResponse sentimentResponse,
        List<String> keywords
) {
}

