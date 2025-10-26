package org.example;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

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

    private static void printUsage() {
        System.out.println("Для запуска в качестве аргумента необходимо указать путь к исходному файлу.");
        System.out.println("Чтобы вывести результат работы программы в файл необходимо после исходного файла указать флаг --out, затем путь, по которому нужно сохранить файл.");
        System.out.println("Если не указать флаг --out, результат программы выведется в консоль.");
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            System.exit(1);
        }

        Path input = null;
        Path out = null;

        try {
            input = Paths.get(args[0]);

            if (!Files.exists(input) || !Files.isRegularFile(input)) {
                System.err.println("Такого файла нет или это не стандартный файл");
                System.exit(2);
            }

            if (args.length > 1) {
                if ("--out".equals(args[1])) {
                    out = Paths.get(args[2]);
                }
                else {
                    System.err.println("Неизвестный флаг: " + args[1]);
                    printUsage();
                    System.exit(3);
                }
            }

            byte[] bytes = Files.readAllBytes(input);
            String content = new String(bytes, StandardCharsets.UTF_8);

            Stats stats = analyze(content);

            System.out.println("Статистика файла: " + input.toString());
            System.out.println(stats.toString());

            if (out != null) {
                String outText = "Статистика файла: " + input.toString() + System.lineSeparator() + stats.toString();
                Files.writeString(out, outText, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("Статистика сохранена в файл: " + out.toString());
            }
        } catch (IOException e) {
            System.err.println("Ошибка ввода/вывода: " + e.getMessage());
        }
    }
}