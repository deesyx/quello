package org.dreven.quello.common.utils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;

public class QuarterUtils {
    /**
     * 获取日期所属的季度（1-4）
     */
    public static int getQuarter(LocalDate date) {
        int month = date.getMonthValue();
        return (month - 1) / 3 + 1;
    }

    /**
     * 获取季度开始日期
     */
    public static LocalDate getQuarterStartDate(LocalDate date) {
        int quarter = getQuarter(date);
        int startMonth = (quarter - 1) * 3 + 1;
        return LocalDate.of(date.getYear(), startMonth, 1);
    }

    /**
     * 获取季度结束日期
     */
    public static LocalDate getQuarterEndDate(LocalDate date) {
        int quarter = getQuarter(date);
        int endMonth = quarter * 3;
        return LocalDate.of(date.getYear(), endMonth, 1)
                .with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取上一个季度的开始日期
     */
    public static LocalDate getPreviousQuarterStartDate(LocalDate date) {
        int currentQuarter = getQuarter(date);
        int currentYear = date.getYear();

        if (currentQuarter == 1) {
            return LocalDate.of(currentYear - 1, 10, 1);
        } else {
            return LocalDate.of(currentYear, (currentQuarter - 2) * 3 + 1, 1);
        }
    }

    /**
     * 获取上一个季度的结束日期
     */
    public static LocalDate getPreviousQuarterEndDate(LocalDate date) {
        int currentQuarter = getQuarter(date);
        int currentYear = date.getYear();

        if (currentQuarter == 1) {
            return LocalDate.of(currentYear - 1, 12, 31);
        } else {
            int month = (currentQuarter - 2) * 3 + 3;
            YearMonth yearMonth = YearMonth.of(currentYear, month);
            return yearMonth.atEndOfMonth();
        }
    }
}
