package ru.shulenin.farmworkerapi.dto;

/**
 * Dto выработки рабочего
 */
public class CommonProductivityReport extends ProductivityReport {
    public CommonProductivityReport(String worker, String product, Double reportAmount, Double planAmount) {
        super(worker, product, reportAmount, planAmount);
    }
}
