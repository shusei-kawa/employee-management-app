package com.example.OJT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaryService {

    @Autowired
    private MonthlySalaryResultRepository salaryRepository;

    @Autowired
    @Qualifier("defaultCalculator") // ★ここで使う計算機を指定！規定が変わったらここを差し替えるだけ
    private SalaryCalculator salaryCalculator;

    /**
     * 画面から送られてきた勤怠情報を元に、リアルタイムで給与を計算してDBに保存する
     */
    @Transactional
    public void executeCalculation(Employee employee, String targetMonth, int absentDays, double lateHours) {

        // 1. 指定されたアルゴリズムで給与を計算
        MonthlySalaryResult result = salaryCalculator.calculate(employee, absentDays, lateHours, targetMonth);

        // 2. データベースにリアルタイム保存（支払い確定）
        salaryRepository.save(result);
    }
}