package com.api.textsense.service;

import com.api.textsense.model.Statistics;
import org.springframework.stereotype.Component;

import java.text.BreakIterator;
import java.util.Locale;

@Component
public class StatisticsProvider {
    private final static double TIME_PER_CHARACTER_SECONDS = 0.15;

    public Statistics of(String text) {
        String trimmedText = text.trim();
        int characters = trimmedText.replaceAll("\\s+", "").length();

        return Statistics.builder()
                .characterCount(characters)
                .readingTimeSeconds(characters * TIME_PER_CHARACTER_SECONDS)
                .wordCount(countWords(text))
                .sentenceCount(countSentences(trimmedText))
                .build();
    }

    private int countSentences(String trimmedText) {
        if (trimmedText.isEmpty()) {
            return 0;
        }

        BreakIterator boundary = BreakIterator.getSentenceInstance(Locale.of("pt", "BR"));
        boundary.setText(trimmedText);

        int count = 0;
        int start = boundary.first();

        for (int end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next()) {
            String sentence = trimmedText.substring(start, end).trim();

            if (!sentence.isEmpty()) {
                count++;
            }
        }

        return count;
    }

    private int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }

        String[] words = text.trim().split("\\s+");
        return words.length;
    }
}