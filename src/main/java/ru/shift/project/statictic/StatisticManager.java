package ru.shift.project.statictic;

public interface StatisticManager {
    void calculateInteger(String value);

    void calculateFloat(String value);

    void calculateString(String value);

    void printStatistic();
}
