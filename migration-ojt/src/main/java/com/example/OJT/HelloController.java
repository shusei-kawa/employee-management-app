package com.example.OJT;
// ▼ 1行目の package 行は消さずに残しておいてください！

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.ui.Model; // ←このインポートが上部に追加されること

@Controller
public class HelloController {

    // ▼データベースと接続するための窓口（Repository）を呼び出します
    @Autowired
    private EmployeeRepository repository;


    @GetMapping("/")
    public String hello(@RequestParam(name = "department", required = false) String department, Model model) {

        List<Employee> employeeList;

        // もし検索窓（department）に文字が入っていたら
        if (department != null && !department.isEmpty()) {
            // その文字で絞り込み検索を行う
            employeeList = repository.findByDepartmentContaining(department);
        } else {
            // 文字が入っていなければ、今まで通り全件取得する
            employeeList = repository.findAll();
        }

        // 画面（Model）に結果を渡す
        model.addAttribute("employees", employeeList);

        return "index";
    }


    // ▼ここから追加：CSVを受け取ってDBに保存する処理
    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("csvFile") MultipartFile file) {
        // try-with-resourcesという構文で、ファイルを安全に読み込みます
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            // CSVを1行ずつ読み込むループ
            while ((line = br.readLine()) != null) {
                // カンマ(,)でデータを分割する
                String[] data = line.split(",");

                // 分割したデータを、社員設計図（Entity）にセットする
                Employee emp = new Employee();
                emp.setName(data[0]);               // 1つ目: 名前
                emp.setDepartment(data[1]);         // 2つ目: 部署
                emp.setHireYear(Integer.parseInt(data[2])); // 3つ目: 入社年（数値に変換）

                // データベースに保存！
                repository.save(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 処理が終わったら、トップページ("/")にリダイレクト（戻る）する
        return "redirect:/";
    }

    // =========================================================
// ▼【追加】指定されたIDの社員を削除する処理（必ず上の「}」の外に書く！）
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {

        // Repository（窓口）に「このIDのデータを消して」と命令するだけ
        repository.deleteById(id);

        // 削除が終わったらトップページに戻る
        return "redirect:/";
    }
    // --- 編集機能の追加 ---

    // ① 編集画面を表示する（GET）
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        // IDを元に社員を1名探し、見つからなければ例外を投げる
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));

        // 画面に「この人を編集するよ」とデータを渡す
        model.addAttribute("employee", employee);
        return "edit"; // edit.htmlを表示
    }

/* 2026/05/11 バリデーションチェックに伴いコメントアウト
    // ② 変更内容をDBに上書き保存する（POST）
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id, Employee employee) {
        // IDをセットしてから保存することで、新規登録ではなく「更新」になります
        employee.setId(id);
        repository.save(employee);

        // 終わったらトップページへ
        return "redirect:/";
    }*/
@PostMapping("/update/{id}")
public String updateEmployee(@PathVariable("id") Integer id,
                             @Validated Employee employee,   // ← @Validated を追加（ルールを適用！）
                             BindingResult result,           // ← バリデーションの結果を受け取る箱
                             Model model) {

    // もし入力ルールに違反していたら（エラーがあったら）
    if (result.hasErrors()) {
        employee.setId(id); // IDをセットし直して
        model.addAttribute("employee", employee); // 画面に入力内容を返し
        return "edit"; // 再度、編集画面(edit.html)を表示してやり直させる
    }

    // エラーがなければ、今まで通り保存する
    employee.setId(id);
    repository.save(employee);

    return "redirect:/";
}
// =========================================================

} // ← これが HelloController クラス全体の最後の「}」です
// ② 変更内容をDBに上書き保存する（POST）