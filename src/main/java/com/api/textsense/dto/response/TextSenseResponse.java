package com.api.textsense.dto.response;

import java.util.List;

public record TextSenseResponse(
        Statistics statistics,
        Sentiment sentiment,
        List<String> keywords
) {
}

