package ru.shift.project.writer;

import java.io.IOException;
import java.nio.file.FileSystems;

public class WriterManager {
    private final Writer integerWriter;
    private final Writer floatWriter;
    private final Writer stringWriter;

    public WriterManager(String outputDir, String prefix, boolean appendMode) {
        integerWriter = new LazyDataWriter(FileSystems.getDefault().getPath(outputDir, prefix + "integers.txt"), appendMode);
        floatWriter = new LazyDataWriter(FileSystems.getDefault().getPath(outputDir, prefix + "floats.txt"), appendMode);
        stringWriter = new LazyDataWriter(FileSystems.getDefault().getPath(outputDir, prefix + "strings.txt"), appendMode);
    }

    public void writeToInteger(String line) throws IOException {
        integerWriter.write(line);
    }

    public void writeToFloat(String line) throws IOException {
        floatWriter.write(line);
    }

    public void writeToString(String line) throws IOException {
        stringWriter.write(line);
    }

    public void closeAll() throws IOException {
        integerWriter.close();
        floatWriter.close();
        stringWriter.close();
    }
}
