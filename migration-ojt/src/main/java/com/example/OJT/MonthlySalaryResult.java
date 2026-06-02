//2026/05/27 給与計算の追加機能
package com.example.OJT;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_salary_result") // バッチが結果を書き込むテーブルを指定します
public class MonthlySalaryResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自動連番
    private Integer id;

    private Integer employeeId;     // 従業員ID
    private String targetMonth;     // 対象年月
    private Integer baseSalary;     // 基本給
    private Integer deductionAmount;// 控除額
    private Integer totalAmount;    // 差引支給額
    private LocalDateTime calculatedAt; // 計算日時

    // ▼ 以下、データを出し入れするための「Getter」と「Setter」です ▼
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getTargetMonth() {
        return targetMonth;
    }

    public void setTargetMonth(String targetMonth) {
        this.targetMonth = targetMonth;
    }

    public Integer getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Integer baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Integer getDeductionAmount() {
        return deductionAmount;
    }

    public void setDeductionAmount(Integer deductionAmount) {
        this.deductionAmount = deductionAmount;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getCalculatedAt() {
        return calculatedAt;
    }

    public void setCalculatedAt(LocalDateTime calculatedAt) {
        this.calculatedAt = calculatedAt;
    }
}