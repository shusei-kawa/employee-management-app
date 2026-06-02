package com.example.OJT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MonthlySalaryResultRepository extends JpaRepository<MonthlySalaryResult, Integer> {

    // 画面で「特定の従業員（employeeId）の、過去の給与一覧」を表示するために使います！
    List<MonthlySalaryResult> findByEmployeeId(Integer employeeId);
}