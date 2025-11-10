package com.api.textsense.service;

import com.api.textsense.model.Label;
import com.api.textsense.model.Sentiment;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

@Component
public class SentimentProvider {

    private static final Logger logger = LoggerFactory.getLogger(SentimentProvider.class);

    private StanfordCoreNLP pipeline;

    @PostConstruct
    public void initModel() {
        try {
            logger.info("Inicializando pipeline de análise de sentimento...");
            Properties props = new Properties();
            props.setProperty("annotators", "tokenize,ssplit,parse,sentiment");
            props.setProperty("parse.model", "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
            props.setProperty("sentiment.model", "edu/stanford/nlp/models/sentiment/sentiment.ser.gz");

            this.pipeline = new StanfordCoreNLP(props);
            logger.info("Pipeline de sentimento carregado com sucesso!");
        } catch (Exception e) {
            logger.error("Erro ao inicializar o pipeline de sentimento", e);
            throw new RuntimeException("Falha ao carregar modelo de sentimento", e);
        }
    }

    @PreDestroy
    public void cleanup() {
        if (this.pipeline != null) {
            logger.info("Liberando recursos do pipeline...");
        }
    }

    /**
     * Analisa o sentimento de todas as sentenças do texto e retorna uma média ponderada.
     *
     * @param text O texto em inglês para analisar
     * @return Um objeto Sentiment com o score médio (0-4) e Label correspondente
     * @throws IllegalArgumentException se o texto for nulo ou vazio
     * @throws IllegalStateException se o pipeline não estiver inicializado
     */
    public Sentiment of(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("O texto não pode ser nulo ou vazio");
        }

        if (this.pipeline == null) {
            throw new IllegalStateException("Pipeline de sentimento não inicializado");
        }

        try {
            CoreDocument document = new CoreDocument(text);
            this.pipeline.annotate(document);

            List<CoreSentence> sentences = document.sentences();

            if (sentences.isEmpty()) {
                logger.warn("Nenhuma sentença detectada no texto");
                return new Sentiment(Label.NEUTRAL, 2);
            }

            double totalScore = 0.0;
            int sentenceCount = sentences.size();

            // Analisa cada sentença e acumula os scores
            for (CoreSentence sentence : sentences) {
                String sentiment = sentence.sentiment();
                int sentenceScore = convertSentimentToScore(sentiment);
                totalScore += sentenceScore;

                logger.debug("Sentença: '{}' | Sentiment: {} | Score: {}",
                        sentence.text().substring(0, Math.min(50, sentence.text().length())),
                        sentiment,
                        sentenceScore);
            }

            // Calcula a média
            double averageScore = totalScore / sentenceCount;
            int finalScore = (int) Math.round(averageScore);

            // Garante que o score esteja no intervalo válido [0-4]
            finalScore = Math.max(0, Math.min(4, finalScore));

            Label finalLabel = convertScoreToLabel(finalScore);

            logger.info("Análise concluída: {} sentenças | Score médio: {} | Label: {}",
                    sentenceCount, finalScore, finalLabel);

            return new Sentiment(finalLabel, finalScore);

        } catch (Exception e) {
            logger.error("Erro ao analisar sentimento do texto", e);
            throw new RuntimeException("Falha na análise de sentimento", e);
        }
    }

    /**
     * Converte a string de sentimento retornada pelo Stanford NLP para score numérico.
     *
     * @param sentiment String retornada pela anotação (Very negative, Negative, etc.)
     * @return Score numérico de 0 a 4
     */
    private int convertSentimentToScore(String sentiment) {
        if (sentiment == null) {
            return 2; // Neutral como padrão
        }

        return switch (sentiment.toLowerCase()) {
            case "very negative" -> 0;
            case "negative" -> 1;
            case "neutral" -> 2;
            case "positive" -> 3;
            case "very positive" -> 4;
            default -> {
                logger.warn("Sentimento desconhecido: {}. Usando NEUTRAL.", sentiment);
                yield 2;
            }
        };
    }

    /**
     * Converte o score numérico (0-4) para o Enum Label.
     *
     * @param score Score numérico de 0 a 4
     * @return Label correspondente
     */
    private Label convertScoreToLabel(int score) {
        return switch (score) {
            case 0 -> Label.VERY_NEGATIVE;
            case 1 -> Label.NEGATIVE;
            case 2 -> Label.NEUTRAL;
            case 3 -> Label.POSITIVE;
            case 4 -> Label.VERY_POSITIVE;
            default -> {
                logger.warn("Score fora do intervalo esperado: {}. Usando NEUTRAL.", score);
                yield Label.NEUTRAL;
            }
        };
    }
}