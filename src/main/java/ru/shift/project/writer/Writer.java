package ru.shift.project.writer;

import java.io.IOException;

public interface Writer {
    void write(String line) throws IOException;

    void close() throws IOException;
}
