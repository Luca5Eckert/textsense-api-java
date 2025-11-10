package com.api.textsense.service;

import com.api.textsense.dto.request.TextSenseRequest;
import com.api.textsense.dto.response.TextSenseResponse;

public class TextSenseService {

    private final StatisticsProvider statisticsProvider;

    public TextSenseService(StatisticsProvider statisticsProvider) {
        this.statisticsProvider = statisticsProvider;
    }

    public TextSenseResponse analyse(TextSenseRequest textSenseRequest){
        return null;
    }
}
