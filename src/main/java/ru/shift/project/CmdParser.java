package ru.shift.project;

import org.apache.commons.cli.*;

public class CmdParser {
    private final Options options = new Options();
    private final HelpFormatter helpFormatter = new HelpFormatter();
    private final CmdValidator validator;

    public CmdParser(CmdValidator validator) {
        this.validator = validator;
        init();
        printHelp();
    }

    public OptionHolder parseArguments(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        String[] inputFiles;
        try {
            cmd = parser.parse(options, args);
            inputFiles = cmd.getArgs();
            validator.validate(cmd, inputFiles);
        } catch (ParseException | IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
            System.exit(1);
        }
        return OptionHolderCreator.of(cmd);
    }

    private void init() {
        options.addOption("a", "append", false, "Вкл. режим добавления в существующие файлы");
        options.addOption(Option.builder("o")
                .longOpt("output")
                .argName("output-path")
                .hasArg()
                .desc("Задать путь для результатов")
                .build());
        options.addOption(Option.builder("p")
                .longOpt("prefix")
                .argName("prefix name")
                .hasArg()
                .desc("Задать префикс имён выходных файлов")
                .build());
        options.addOption("s", "short", false, "Вывод краткой статистики");
        options.addOption("f", "full", false, "Вывод полной статистики");
    }

    private void printHelp() {
        helpFormatter.printHelp("""
                Используйте команду для запуска:
                java -jar app.jar [OPTIONS] <files...>
                """, options);
    }
}
