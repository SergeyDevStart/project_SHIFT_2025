package ru.shift.project.statistic;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatStatistic {
    private long countValue;
    private BigDecimal sumValue = BigDecimal.ZERO;
    private BigDecimal maxValue;
    private BigDecimal minValue;

    public void add(String value) {
        BigDecimal item = new BigDecimal(value);
        countValue++;
        sumValue = sumValue.add(item);
        if (minValue == null || item.compareTo(minValue) < 0) {
            minValue = item;
        }
        if (maxValue == null || item.compareTo(maxValue) > 0) {
            maxValue = item;
        }
    }

    public BigDecimal calculateAverage() {
        if (countValue == 0) {
            return BigDecimal.ZERO;
        }
        return sumValue.divide(new BigDecimal(countValue), 2, RoundingMode.HALF_UP);
    }

    public void print() {
        System.out.println("Статистика для вещественных чисел: ");
        if (countValue != 0) {
            System.out.printf("Количество: %d%n", countValue);
            System.out.printf("Сумма: %s%n", sumValue);
            System.out.printf("Минимальное значение: %s%n", minValue);
            System.out.printf("Максимальное значение: %s%n", maxValue);
            System.out.printf("Среднее значение: %s%n", calculateAverage().toString());
        } else {
            System.out.println("Данные для статистики отсутствуют.");
        }
    }
}
