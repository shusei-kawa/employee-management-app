package com.example.OJT;
////2026/05/19     データの取得メソッドの変更（昇順）　削除
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
//    // これだけで、保存・検索・更新・削除（CRUD）の基本機能がすべて使えるようになります！
// ▼ 【追加】部署名に特定の文字が含まれている社員を探す命令
//    // Spring Data JPAの「命名ルール」に従って書くだけで、
//    // 裏側で自動的に「WHERE department LIKE ...」というSQLが生成されます。
//
//    List<Employee> findByDepartmentContaining(String department);
//}
////2026/05/19     データの取得メソッドの変更（昇順）　追加
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // ①部署検索用 ＋ ID昇順で並び替え
    List<Employee> findByDepartmentContainingOrderByIdAsc(String department);

    // ②ページネーション用（全件取得）
    // ※JpaRepositoryに標準で備わっているため書かなくても使えるが、分かりやすくするため書いておく。
    Page<Employee> findAll(Pageable pageable);

}