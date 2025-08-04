package ru.shift.project.app;

import ru.shift.project.cli.CmdParser;
import ru.shift.project.cli.CmdValidator;
import ru.shift.project.cli.OptionHolder;

public class AppRunner {
    public void run(String[] args) {
        CmdValidator validator = new CmdValidator();
        CmdParser parser = new CmdParser(validator);
        OptionHolder optionHolder = parser.parseArguments(args);
        Processor processor = new Processor(optionHolder);
        processor.process();
    }
}
