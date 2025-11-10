package com.api.textsense.model;

import java.util.List;

public record TextSense(Statistics statistics, Sentiment sentiment, List<String> keys) {
}
