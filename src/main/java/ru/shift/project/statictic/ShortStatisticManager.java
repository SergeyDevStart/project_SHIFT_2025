package ru.shift.project.statictic;

import java.math.BigInteger;

public class ShortStatisticManager implements StatisticManager {
    private BigInteger intCount = BigInteger.ZERO;
    private BigInteger floatCount = BigInteger.ZERO;
    private BigInteger stringCount = BigInteger.ZERO;

    @Override
    public void calculateInteger(String value) {
        intCount = intCount.add(BigInteger.ONE);
    }

    @Override
    public void calculateFloat(String value) {
        floatCount = floatCount.add(BigInteger.ONE);
    }

    @Override
    public void calculateString(String value) {
        stringCount = stringCount.add(BigInteger.ONE);
    }

    @Override
    public void printStatistic() {
        System.out.println("Краткая статистика:");
        System.out.printf("Количество целых чисел: %s%n", intCount.toString());
        System.out.printf("Количество вещественных чисел: %s%n", floatCount.toString());
        System.out.printf("Количество строк: %s%n", stringCount.toString());
    }
}
