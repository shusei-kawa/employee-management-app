// 2026/06/02 給与計算実行機能追加
package com.example.OJT;

// 将来どんな計算方法（通常、ボーナス、減給規定など）になっても、このルールに従って作ります
public interface SalaryCalculator {
    /**
     * 従業員情報と勤怠データから給与明細結果を計算する共通メソッド
     */
    MonthlySalaryResult calculate(Employee employee, int absentDays, double lateHours, String targetMonth);
}