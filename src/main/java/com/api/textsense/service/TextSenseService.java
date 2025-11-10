package com.api.textsense.service;

import com.api.textsense.dto.request.TextSenseRequest;
import com.api.textsense.model.Sentiment;
import com.api.textsense.model.Statistics;
import com.api.textsense.model.TextSense;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TextSenseService {

    private final StatisticsProvider statisticsProvider;
    private final SentimentProvider sentimentProvider;
    private final KeyWordProvider keyWordProvider;

    public TextSenseService(StatisticsProvider statisticsProvider, SentimentProvider sentimentProvider, KeyWordProvider keyWordProvider) {
        this.statisticsProvider = statisticsProvider;
        this.sentimentProvider = sentimentProvider;
        this.keyWordProvider = keyWordProvider;
    }

    public TextSense analyse(TextSenseRequest textSenseRequest){
        Statistics statistics = statisticsProvider.of(textSenseRequest.text());
        Sentiment sentiment = sentimentProvider.of(textSenseRequest.text());
        List<String> keywords = keyWordProvider.of(textSenseRequest.text());

        return new TextSense(statistics, sentiment, keywords);
    }
}
