package com.api.textsense.service;

import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Serviço para extração de palavras-chave de textos.
 * Remove stop words e retorna as palavras mais frequentes.
 */
@Component
public class KeyWordProvider {

    private static final Logger logger = LoggerFactory.getLogger(KeyWordProvider.class);

    /**
     * Stop words em inglês - palavras comuns que devem ser filtradas.
     */
    private static final Set<String> STOP_WORDS = Set.of(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
            "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
            "to", "was", "will", "with", "i", "you", "we", "they", "she",
            "but", "or", "can", "have", "been", "had", "were", "this", "these",
            "those", "am", "which", "who", "what", "when", "where", "why", "how",
            "could", "would", "should", "may", "might", "must", "not", "do", "does",
            "did", "their", "them", "his", "her", "my", "your", "our", "all", "more",
            "about", "into", "through", "during", "before", "after", "above", "below",
            "between", "under", "again", "further", "then", "once", "here", "there",
            "so", "than", "too", "very", "just", "now", "such", "some", "any"
    );

    private static final Pattern WORD_PATTERN = Pattern.compile("[a-zA-Z]+");
    private static final int DEFAULT_MAX_KEYWORDS = 10;
    private static final int MIN_WORD_LENGTH = 3;

    /**
     * Extrai as palavras-chave mais utilizadas do texto.
     * Método simplificado que retorna as top 10 palavras-chave.
     *
     * @param text O texto para análise
     * @return Lista com as 10 palavras-chave mais frequentes
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    public List<String> of(@NotBlank String text) {
        return extract(text, DEFAULT_MAX_KEYWORDS);
    }

    /**
     * Extrai palavras-chave do texto fornecido.
     *
     * @param text O texto para análise
     * @return Lista com as 10 palavras-chave mais frequentes
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    public List<String> extract(@NotBlank String text) {
        return extract(text, DEFAULT_MAX_KEYWORDS);
    }

    /**
     * Extrai palavras-chave do texto fornecido.
     *
     * @param text O texto para análise
     * @param maxKeywords Número máximo de palavras-chave a retornar
     * @return Lista com as palavras-chave mais frequentes
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     */
    public List<String> extract(@NotBlank String text, int maxKeywords) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto não pode ser nulo ou vazio");
        }

        if (maxKeywords < 1) {
            throw new IllegalArgumentException("O número de palavras-chave deve ser maior que zero");
        }

        logger.debug("Extraindo até {} palavras-chave do texto (comprimento: {})",
                maxKeywords, text.length());

        // Calcula frequência das palavras
        Map<String, Integer> wordFrequency = calculateWordFrequency(text);

        // Ordena por frequência e retorna as top N
        List<String> keywords = wordFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(maxKeywords)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        logger.info("Extraídas {} palavras-chave do texto", keywords.size());

        return keywords;
    }

    /**
     * Extrai palavras-chave com suas frequências.
     *
     * @param text O texto para análise
     * @param maxKeywords Número máximo de palavras-chave a retornar
     * @return Map com palavras-chave e suas frequências
     */
    public Map<String, Integer> extractWithFrequency(@NotBlank String text, int maxKeywords) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto não pode ser nulo ou vazio");
        }

        Map<String, Integer> wordFrequency = calculateWordFrequency(text);

        return wordFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(maxKeywords)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Calcula a frequência de cada palavra no texto.
     *
     * @param text O texto para análise
     * @return Map com palavras e suas frequências
     */
    private Map<String, Integer> calculateWordFrequency(String text) {
        Map<String, Integer> wordFrequency = new HashMap<>();

        // Normaliza o texto: lowercase
        String normalizedText = text.toLowerCase();

        // Extrai apenas palavras (remove pontuação e números)
        var matcher = WORD_PATTERN.matcher(normalizedText);

        while (matcher.find()) {
            String word = matcher.group();

            // Filtra palavras muito curtas e stop words
            if (word.length() >= MIN_WORD_LENGTH && !STOP_WORDS.contains(word)) {
                wordFrequency.merge(word, 1, Integer::sum);
            }
        }

        logger.debug("Processadas {} palavras únicas (após filtros)", wordFrequency.size());

        return wordFrequency;
    }

    /**
     * Adiciona stop words customizadas ao conjunto padrão.
     * Útil para domínios específicos.
     *
     * @param customStopWords Set de stop words adicionais
     * @param text O texto para análise
     * @param maxKeywords Número máximo de palavras-chave
     * @return Lista de palavras-chave
     */
    public List<String> extractWithCustomStopWords(
            Set<String> customStopWords,
            @NotBlank String text,
            int maxKeywords) {

        if (customStopWords == null || customStopWords.isEmpty()) {
            return extract(text, maxKeywords);
        }

        // Combina stop words padrão com customizadas
        Set<String> allStopWords = new HashSet<>(STOP_WORDS);
        allStopWords.addAll(customStopWords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet()));

        Map<String, Integer> wordFrequency = new HashMap<>();
        String normalizedText = text.toLowerCase();
        var matcher = WORD_PATTERN.matcher(normalizedText);

        while (matcher.find()) {
            String word = matcher.group();
            if (word.length() >= MIN_WORD_LENGTH && !allStopWords.contains(word)) {
                wordFrequency.merge(word, 1, Integer::sum);
            }
        }

        return wordFrequency.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(maxKeywords)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Retorna as stop words utilizadas.
     *
     * @return Set imutável de stop words
     */
    public Set<String> getStopWords() {
        return Collections.unmodifiableSet(STOP_WORDS);
    }
}