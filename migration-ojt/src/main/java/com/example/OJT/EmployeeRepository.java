package com.example.OJT;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // これだけで、保存・検索・更新・削除（CRUD）の基本機能がすべて使えるようになります！
// ▼ 【追加】部署名に特定の文字が含まれている社員を探す命令
    // Spring Data JPAの「命名ルール」に従って書くだけで、
    // 裏側で自動的に「WHERE department LIKE ...」というSQLが生成されます。
    List<Employee> findByDepartmentContaining(String department);
}