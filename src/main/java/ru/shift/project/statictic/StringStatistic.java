package ru.shift.project.statictic;

public class StringStatistic {
    private long countValue;
    private long maxValue;
    private long minValue = Long.MAX_VALUE;

    public void add(String value) {
        countValue++;
        if (value.length() < minValue) {
            minValue = value.length();
        }
        if (value.length() > maxValue) {
            maxValue = value.length();
        }
    }

    public void print() {
        System.out.println("Статистика для строк: ");
        if (countValue != 0) {
            System.out.printf("Количество: %d%n", countValue);
            System.out.printf("Минимальное значение: %s%n", minValue);
            System.out.printf("Максимальное значение: %s%n", maxValue);
        } else {
            System.out.println("Данные для статистики отсутствуют.");
        }
    }
}
