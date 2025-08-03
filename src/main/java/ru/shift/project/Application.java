package ru.shift.project;

public class Application {
    public void run(String[] args) {
        CmdValidator validator = new CmdValidator();
        CmdParser parser = new CmdParser(validator);
        OptionHolder optionHolder = parser.parseArguments(args);
        MainProcessor processor = new MainProcessor(optionHolder);
        processor.process();
    }
}
