package org.example;

public class Main {
    public static class Stats {
        public long lines;
        public long words;
        public long characters;

        @Override
        public String toString() {
            return String.format("Lines: %d%nWords: %d%nCharacters: %d%n", lines, words, characters);
        }
    }

    public static Stats analyze(String content) {
        Stats s = new Stats();

        String[] lines = content.split("\\R", -1);
        s.lines = lines.length;

        String trimmed = content.trim();
        if (trimmed.isEmpty()) {
            s.words = 0;
        } else {
            String[] words = trimmed.split("\\s+");
            s.words = words.length;
        }

        s.characters = content.length();

        return s;
    }
    public static void main(String[] args) {

    }
}