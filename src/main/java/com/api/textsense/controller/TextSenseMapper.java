package com.api.textsense.controller;

import com.api.textsense.dto.response.SentimentResponse;
import com.api.textsense.dto.response.StatisticsResponse;
import com.api.textsense.dto.response.TextSenseResponse;
import com.api.textsense.model.Sentiment;
import com.api.textsense.model.Statistics;
import com.api.textsense.model.TextSense;
import org.springframework.stereotype.Component;

@Component
public class TextSenseMapper {

    public TextSenseResponse toResponse(TextSense textSense) {
        return new TextSenseResponse(
                toStatisticsResponse(textSense.statistics()),
                toSentimentResponse(textSense.sentiment()),
                textSense.keys()
        );
    }

    private SentimentResponse toSentimentResponse(Sentiment sentiment) {
        return new SentimentResponse(
                sentiment.score(),
                sentiment.label().getDescription()
        );
    }

    private StatisticsResponse toStatisticsResponse(Statistics statistics) {
        return new StatisticsResponse(
                statistics.characterCount(),
                statistics.wordCount(),
                statistics.sentenceCount(),
                statistics.readingTimeSeconds()
        );

    }

}
