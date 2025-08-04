package ru.shift.project.statistic;

public class FullStatisticManager implements StatisticManager {
    private final IntegerStatistic integerStatistic = new IntegerStatistic();
    private final FloatStatistic floatStatistic = new FloatStatistic();
    private final StringStatistic stringStatistic = new StringStatistic();

    @Override
    public void calculateInteger(String value) {
        integerStatistic.add(value);
    }

    @Override
    public void calculateFloat(String value) {
        floatStatistic.add(value);
    }

    @Override
    public void calculateString(String value) {
        stringStatistic.add(value);
    }

    @Override
    public void printStatistic() {
        integerStatistic.print();
        floatStatistic.print();
        stringStatistic.print();
    }
}
