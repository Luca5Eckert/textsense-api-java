package com.api.textsense.model;

public record Statistics(
        int characterCount,
        int wordCount,
        int sentenceCount,
        double readingTimeSeconds
) {

    /**
     * Retorna uma nova instância do Builder para construir um objeto Statistics.
     * @return O Builder.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * O padrão Builder para a classe Statistics.
     */
    public static class Builder {
        private int characterCount = 0;
        private int wordCount = 0;
        private int sentenceCount = 0;
        private double readingTimeSeconds = 0.0;

        // Construtor privado para forçar o uso do método estático Statistics.builder()
        private Builder() {}

        public Builder characterCount(int characterCount) {
            this.characterCount = characterCount;
            return this;
        }

        public Builder wordCount(int wordCount) {
            this.wordCount = wordCount;
            return this;
        }

        public Builder sentenceCount(int sentenceCount) {
            this.sentenceCount = sentenceCount;
            return this;

        }

        public Builder readingTimeSeconds(double readingTimeSeconds) {
            this.readingTimeSeconds = readingTimeSeconds;
            return this;
        }

        /**
         * Constrói e retorna a instância final de Statistics.
         * @return O objeto Statistics construído.
         */
        public Statistics build() {
            return new Statistics(
                    characterCount,
                    wordCount,
                    sentenceCount,
                    readingTimeSeconds
            );
        }
    }
}