package com.api.textsense.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum que representa as categorias de sentimento.
 */
public enum Label {
    VERY_POSITIVE("Very Positive", 4),
    POSITIVE("Positive", 3),
    NEUTRAL("Neutral", 2),
    NEGATIVE("Negative", 1),
    VERY_NEGATIVE("Very Negative", 0);

    private final String description;
    private final int score;

    Label(String description, int score) {
        this.description = description;
        this.score = score;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }

    public int getScore() {
        return score;
    }

    /**
     * Converte um score numérico para o Label correspondente.
     *
     * @param score Score de 0 a 4
     * @return Label correspondente ou NEUTRAL se inválido
     */
    public static Label fromScore(int score) {
        for (Label label : values()) {
            if (label.score == score) {
                return label;
            }
        }
        return NEUTRAL;
    }

    @Override
    public String toString() {
        return description;
    }
}