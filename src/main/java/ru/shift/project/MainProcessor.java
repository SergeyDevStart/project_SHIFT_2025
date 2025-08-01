package ru.shift.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.shift.project.writer.WriterManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class MainProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(MainProcessor.class);
    private final OptionHolder optionHolder;

    public MainProcessor(OptionHolder optionHolder) {
        this.optionHolder = optionHolder;
    }

    public void process() {
        WriterManager writerManager = createWriterManager();
        String[] inputFilesName = optionHolder.getInputFiles();
        try {
            for (String fileName : inputFilesName) {
                Path filePath = Path.of(fileName);
                if (!Files.exists(filePath)) {
                    LOG.warn("Файл не найден: {}", fileName);
                    continue;
                }
                try (var scanner = new Scanner(new FileInputStream(fileName), StandardCharsets.UTF_8)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        try {
                            if (line.matches("^-?\\d+$")) {
                                writerManager.writeToInteger(line);
                            } else if (line.matches("^-?\\d*\\.\\d+(E-?\\d+)?$")) {
                                writerManager.writeToFloat(line);
                            } else if (line.matches("^[a-zA-Zа-яА-Я\\s]+$")) {
                                writerManager.writeToString(line);
                            }
                        } catch (IOException e) {
                            LOG.error("Ошибка при записи в файл {}: {}", fileName, e.getMessage());
                        }
                    }
                } catch (IOException e) {
                    LOG.error("Ошибка при чтении файла {}: {}", fileName, e.getMessage());
                }
            }
        } finally {
            try {
                writerManager.closeAll();
            } catch (IOException e) {
                LOG.error("Ошибка при закрытии файлов: {}", e.getMessage());
            }
        }
    }

    private WriterManager createWriterManager() {
        return new WriterManager(
                optionHolder.getOutputPath(),
                optionHolder.getPrefix(),
                optionHolder.isAppendMode()
        );
    }
}
