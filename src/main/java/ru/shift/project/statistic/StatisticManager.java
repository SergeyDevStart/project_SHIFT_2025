package ru.shift.project.statistic;

public interface StatisticManager {
    void calculateInteger(String value);

    void calculateFloat(String value);

    void calculateString(String value);

    void printStatistic();
}
