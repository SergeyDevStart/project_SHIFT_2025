package ru.shift.project.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class LazyDataWriter implements Writer {
    private final Path path;
    private BufferedWriter writer = null;
    private final boolean appendMode;

    public LazyDataWriter(Path path, boolean appendMode) {
        this.path = path;
        this.appendMode = appendMode;
    }

    @Override
    public void write(String line) throws IOException {
        StandardOpenOption openOption = appendMode ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING;
        if (writer == null) {
            Files.createDirectories(path.getParent());
            writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, StandardOpenOption.CREATE, openOption);
        }
        writer.write(line);
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
