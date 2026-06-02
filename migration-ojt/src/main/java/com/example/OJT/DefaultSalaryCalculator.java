// 2026/06/02 給与計算実行機能追加
package com.example.OJT;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component("defaultCalculator") // Springに「これが基本の計算機だよ」と登録します
public class DefaultSalaryCalculator implements SalaryCalculator {

    @Override
    public MonthlySalaryResult calculate(Employee employee, int absentDays, double lateHours, String targetMonth) {
        // 1. 勤続年数を計算 (現在の年 2026年 - 入社年)
        int yearsOfService = 2026 - employee.getHireYear();

        // 2. 基本給の計算（30万円 ＋ 勤続年数 × 1万円）
        int baseSalary = 300000 + (yearsOfService * 10000);

        // 3. 時給の計算（基本給 ÷ 240時間）
        int hourlyWage = baseSalary / 240;

        // 4. 控除額の計算（欠勤1日＝8時間として計算）
        double totalDeductionHours = (absentDays * 8) + lateHours;
        int deductionAmount = (int) (totalDeductionHours * hourlyWage);

        // 5. 差引支給額の計算
        int totalAmount = baseSalary - deductionAmount;

        // 6. 結果のデータにセットして返す
        MonthlySalaryResult result = new MonthlySalaryResult();
        result.setEmployeeId(employee.getId());
        result.setTargetMonth(targetMonth);
        result.setBaseSalary(baseSalary);
        result.setDeductionAmount(deductionAmount);
        result.setTotalAmount(totalAmount);
        result.setCalculatedAt(LocalDateTime.now());

        return result;
    }
}