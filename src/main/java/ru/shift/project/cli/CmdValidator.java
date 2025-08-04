package ru.shift.project.cli;

import org.apache.commons.cli.CommandLine;
import ru.shift.project.exception.ValidationException;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CmdValidator {
    public void validate(CommandLine cmd, String[] inputFiles) throws ValidationException {
        if (inputFiles.length == 0) {
            throw new ValidationException("Необходимо указать входные файлы");
        }
        for (String fileName : inputFiles) {
            if (!fileName.matches("^[а-яА-Яa-zA-Z0-9_]+\\.txt$")) {
                throw new ValidationException(
                        String.format("Используйте формат файла: %s", "FILE_NAME.txt")
                );
            }
        }
        if (cmd.hasOption("s") && cmd.hasOption("f")) {
            throw new ValidationException("Параметры -s и -f не могут использоваться вместе");
        }
        if (cmd.hasOption("p")) {
            String prefix = cmd.getOptionValue("p");
            if (!prefix.matches("^[a-zA-Z0-9_-]+$")) {
                throw new ValidationException("Некорректное имя префикса. Используйте латинские буквы, цифры, подчёркивание, дефис");
            }
        }
        if (cmd.hasOption("o")) {
            String pathString = cmd.getOptionValue("o");
            try {
                Path path = Paths.get(pathString);
                if (Files.exists(path) && !Files.isDirectory(path)) {
                    throw new ValidationException("Путь существует, но не является директорией: " + path);
                }
            } catch (InvalidPathException e) {
                throw new ValidationException("Невалидный путь: " + pathString);
            } catch (Exception e) {
                throw new ValidationException("Ошибка при обработке пути: " + e.getMessage());
            }
        }
    }
}
