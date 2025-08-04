package ru.shift.project.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.project.cli.OptionHolder;
import ru.shift.project.statistic.FullStatisticManager;
import ru.shift.project.statistic.ShortStatisticManager;
import ru.shift.project.statistic.StatisticManager;
import ru.shift.project.writer.WriterManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Processor {
    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);
    private final OptionHolder optionHolder;

    public Processor(OptionHolder optionHolder) {
        this.optionHolder = optionHolder;
    }

    public void process() {
        WriterManager writerManager = createWriterManager();
        StatisticManager statisticManager = createStatisticManager();
        try {
            for (String fileName : optionHolder.getInputFiles()) {
                processFile(fileName, writerManager, statisticManager);
            }
        } finally {
            closeWriters(writerManager);
        }
        if (statisticManager != null) {
            statisticManager.printStatistic();
        }
    }

    private void processFile(String fileName, WriterManager writerManager, StatisticManager statisticManager) {
        Path filePath = Path.of(fileName);
        if (!Files.exists(filePath)) {
            LOG.warn("Файл не найден: {}", fileName);
            return;
        }
        try (var reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line, writerManager, statisticManager, fileName);
            }
        } catch (IOException e) {
            LOG.error("Ошибка при чтении файла {}: {}", fileName, e.getMessage());
        }
    }

    private void processLine(String string, WriterManager writerManager, StatisticManager statisticManager, String fileName) {
        try {
            if (!checkLine(string)) {
                return;
            }
            String line = string.trim();
            if (line.matches("^-?\\d+$")) {
                writerManager.writeToInteger(line);
                if (statisticManager != null) {
                    statisticManager.calculateInteger(line);
                }
            } else if (line.matches("^-?(?:\\d+\\.?\\d*|\\d*\\.?\\d+)([eE]-?\\d+)?$")) {
                writerManager.writeToFloat(line);
                if (statisticManager != null) {
                    statisticManager.calculateFloat(line);
                }
            } else if (line.matches("^[a-zA-Zа-яА-Я0-9\\s]+$")) {
                writerManager.writeToString(line);
                if (statisticManager != null) {
                    statisticManager.calculateString(line);
                }
            }
        } catch (IOException e) {
            LOG.error("Ошибка при записи в файл {}: {}", fileName, e.getMessage());
        }
    }

    private boolean checkLine(String string) {
        return string != null && !string.isBlank();
    }

    private StatisticManager createStatisticManager() {
        StatisticManager statisticManager = null;
        if (optionHolder.isFullStats()) {
            statisticManager = new FullStatisticManager();
        } else if (optionHolder.isShortStats()) {
            statisticManager = new ShortStatisticManager();
        }
        return statisticManager;
    }

    private WriterManager createWriterManager() {
        return new WriterManager(
                optionHolder.getOutputPath(),
                optionHolder.getPrefix(),
                optionHolder.isAppendMode()
        );
    }

    private void closeWriters(WriterManager writerManager) {
        try {
            writerManager.closeAll();
        } catch (IOException e) {
            LOG.error("Ошибка при закрытии файлов: {}", e.getMessage());
        }
    }
}
