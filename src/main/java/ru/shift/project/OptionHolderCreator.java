package ru.shift.project;

import org.apache.commons.cli.CommandLine;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OptionHolderCreator {
    public static OptionHolder of(CommandLine cmd) {
        OptionHolder optionHolder = new OptionHolder();
        optionHolder.setAppendMode(cmd.hasOption("a"));
        optionHolder.setPrefix(cmd.getOptionValue("p", ""));
        optionHolder.setFullStats(cmd.hasOption("f"));
        optionHolder.setShortStats(cmd.hasOption("s"));
        optionHolder.setInputFiles(cmd.getArgs());
        Path outputDir;
        if (cmd.hasOption("o")) {
            Path path = Paths.get(cmd.getOptionValue("o"));
            outputDir = path.isAbsolute()
                    ? path : Paths.get(System.getProperty("user.dir")).resolve(path);
            optionHolder.setOutputPath(outputDir.toString());
        } else {
            optionHolder.setOutputPath(System.getProperty("user.dir"));
        }
        return optionHolder;
    }
}
