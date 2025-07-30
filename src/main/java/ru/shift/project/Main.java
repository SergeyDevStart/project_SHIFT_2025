package ru.shift.project;

public class Main {
    public static void main(String[] args) {
        CmdValidator validator = new CmdValidator();
        CmdParser cmdParser = new CmdParser(validator);
        OptionHolder optionHolder = cmdParser.parseArguments(args);
    }
}
