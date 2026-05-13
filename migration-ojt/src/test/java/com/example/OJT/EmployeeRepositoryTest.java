package com.example.OJT;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// ▼ インポートを SpringBootTest から DataJpaTest に戻します
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional; // ← 追加：テスト用のお掃除部品
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

// ▼ 「SpringBootTest」を消して「DataJpaTest」にします
// これをつけるだけで、自動的にさっき入れた「H2データベース」を使ってテストしてくれます
@SpringBootTest
// これをつけると、テストで登録した「検証 太郎」が本番のデータベースに残らないよう、自動で取り消してくれます
@Transactional
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository repository;

    @Test
    public void testFindByDepartment() {

        // 1. 準備 (Arrange):
        Employee emp = new Employee();
        emp.setName("検証 太郎");
        emp.setDepartment("品質保証部");
        emp.setHireYear(2026);
        repository.save(emp);

        // 2. 実行 (Act):
        List<Employee> results = repository.findByDepartmentContaining("品質");

        // 3. 検証 (Assert):
        assertThat(results).isNotEmpty();
        assertThat(results.get(0).getDepartment()).isEqualTo("品質保証部");
    }
}
