//package com.example.OJT;
//// ▼ 1行目の package 行は消さずに残しておいてください！
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//////2026/05/19     データの取得メソッドの変更（昇順）　追加 S
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//////2026/05/19     データの取得メソッドの変更（昇順）　追加 E
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//import org.springframework.ui.Model; // ←このインポートが上部に追加されること
//
//@Controller
//public class HelloController {
//
//    // ▼データベースと接続するための窓口（Repository）を呼び出します
//    @Autowired
//    private EmployeeRepository repository;
//
//    ////2026/05/27     給与計算の機能追加　 S
//    // ▼データベースと接続するための窓口（Repository）を呼び出します
//    @Autowired
//    private EmployeeRepository repository;
//
//    @Autowired
//    private MonthlySalaryResultRepository salaryRepository; // ← これを追記！
//    ////2026/05/27     給与計算の機能追加　 E
//
//    ////2026/05/19     データの取得メソッドの変更（昇順）　削除 S
////    @GetMapping("/")
////    public String hello(@RequestParam(name = "department", required = false) String department, Model model) {
////
////        List<Employee> employeeList;
////
////        // もし検索窓（department）に文字が入っていたら
////        if (department != null && !department.isEmpty()) {
////            // その文字で絞り込み検索を行う
////            employeeList = repository.findByDepartmentContaining(department);
////        } else {
////            // 文字が入っていなければ、今まで通り全件取得する
////            employeeList = repository.findAll();
////        }
////
////        // 画面（Model）に結果を渡す
////        model.addAttribute("employees", employeeList);
////
////        return "index";
////    }
//    ////2026/05/19     データの取得メソッドの変更（昇順）　削除 E
//    ////2026/05/19     データの取得メソッドの変更（昇順）　追加 S
//    @GetMapping("/")
//    // @RequestParam を使って、URLから「page=〇」というページ番号を受け取ります（初期値は0ページ目）
//    public String index(@RequestParam(name = "keyword", required = false) String keyword,
//                        @RequestParam(defaultValue = "0") int page,
//                        Model model) {
//
//        if (keyword != null && !keyword.isEmpty()) {
//            // 検索された時：部署名で検索し、ID昇順で全件表示
//            List<Employee> employees = repository.findByDepartmentContainingOrderByIdAsc(keyword);
//            model.addAttribute("employees", employees);
//            model.addAttribute("keyword", keyword);
//        } else {
//            // 通常時（検索キーワードがない時）：ページネーションを使用
//            // 「100」件で1ページにしています。本番はここを「100」にします！
//            Pageable pageable = PageRequest.of(page, 50, Sort.by("id").ascending());
//
//            // Repositoryからページ情報を取得
//            Page<Employee> employeePage = repository.findAll(pageable);
//
//            // 画面に渡すデータ
//            model.addAttribute("employees", employeePage.getContent()); // 実際の従業員データ3件分
//            model.addAttribute("pageInfo", employeePage); // ページネーションのボタンを作るための情報
//        }
//
//        return "index"; // HTMLファイル名に合わせてください
//    }
//    ////2026/05/19     データの取得メソッドの変更（昇順）　追加 E
//    // ▼ここから追加：CSVを受け取ってDBに保存する処理
//    @PostMapping("/upload")
//    public String uploadCsv(@RequestParam("csvFile") MultipartFile file) {
//        // try-with-resourcesという構文で、ファイルを安全に読み込みます
//        try (BufferedReader br = new BufferedReader(
//                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
//
//            String line;
//            // CSVを1行ずつ読み込むループ
//            while ((line = br.readLine()) != null) {
//                // カンマ(,)でデータを分割する
//                String[] data = line.split(",");
//
//                // 分割したデータを、社員設計図（Entity）にセットする
//                Employee emp = new Employee();
//                emp.setName(data[0]);               // 1つ目: 名前
//                emp.setDepartment(data[1]);         // 2つ目: 部署
//                emp.setHireYear(Integer.parseInt(data[2])); // 3つ目: 入社年（数値に変換）
//
//                // データベースに保存！
//                repository.save(emp);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        // 処理が終わったら、トップページ("/")にリダイレクト（戻る）する
//        return "redirect:/";
//    }
//
//    // =========================================================
//// ▼【追加】指定されたIDの社員を削除する処理（必ず上の「}」の外に書く！）
//    @PostMapping("/delete/{id}")
//    public String deleteEmployee(@PathVariable("id") Integer id) {
//
//        // Repository（窓口）に「このIDのデータを消して」と命令するだけ
//        repository.deleteById(id);
//
//        // 削除が終わったらトップページに戻る
//        return "redirect:/";
//    }
//    // --- 編集機能の追加 ---
//
//    // ① 編集画面を表示する（GET）
//    @GetMapping("/edit/{id}")
//    public String showEditForm(@PathVariable("id") Integer id, Model model) {
//        // IDを元に社員を1名探し、見つからなければ例外を投げる
//        Employee employee = repository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
//
//        // 画面に「この人を編集するよ」とデータを渡す
//        model.addAttribute("employee", employee);
//        return "edit"; // edit.htmlを表示
//    }
//
///* 2026/05/11 バリデーションチェックに伴いコメントアウト
//    // ② 変更内容をDBに上書き保存する（POST）
//    @PostMapping("/update/{id}")
//    public String updateEmployee(@PathVariable("id") Integer id, Employee employee) {
//        // IDをセットしてから保存することで、新規登録ではなく「更新」になります
//        employee.setId(id);
//        repository.save(employee);
//
//        // 終わったらトップページへ
//        return "redirect:/";
//    }*/
//@PostMapping("/update/{id}")
//public String updateEmployee(@PathVariable("id") Integer id,
//                             @Validated Employee employee,   // ← @Validated を追加（ルールを適用！）
//                             BindingResult result,           // ← バリデーションの結果を受け取る箱
//                             Model model) {
//
//    // もし入力ルールに違反していたら（エラーがあったら）
//    if (result.hasErrors()) {
//        employee.setId(id); // IDをセットし直して
//        model.addAttribute("employee", employee); // 画面に入力内容を返し
//        return "edit"; // 再度、編集画面(edit.html)を表示してやり直させる
//    }
//
//    // エラーがなければ、今まで通り保存する
//    employee.setId(id);
//    repository.save(employee);
//
//    return "redirect:/";
//}
//    ////2026/05/27     給与計算の機能追加　 S
//    // =========================================================
//    // ▼【追加】指定された従業員の給与明細一覧画面を表示する処理
//    @GetMapping("/salary/{employeeId}")
//    public String showSalaryDetail(@PathVariable("employeeId") Integer employeeId, Model model) {
//        // 1. 従業員の名前などを表示するために、従業員情報を取得
//        Employee employee = repository.findById(employeeId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + employeeId));
//
//        // 2. バッチが計算してくれた、その従業員の給与計算結果をすべて取得
//        List<MonthlySalaryResult> salaryList = salaryRepository.findByEmployeeId(employeeId);
//
//        // 3. 画面（HTML）にデータを送り出す
//        model.addAttribute("employee", employee);
//        model.addAttribute("salaryList", salaryList);
//
//        return "salary"; // 後ほど作成する salary.html を表示します
//    }
//    // =========================================================
//    ////2026/05/27     給与計算の機能追加　 E
//// =========================================================
//
//} // ← これが HelloController クラス全体の最後の「}」です
//// ② 変更内容をDBに上書き保存する（POST）


package com.example.OJT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.ui.Model;

@Controller
public class HelloController {

    // ▼データベースと接続するための窓口（Repository）
    @Autowired
    private EmployeeRepository repository;

    // 2026/05/27 給与計算窓口を追加（重複を排除して綺麗に並べました）
    @Autowired
    private MonthlySalaryResultRepository salaryRepository;

    // ① 従業員一覧画面の表示（検索・ページネーション付き）
    @GetMapping("/")
    public String index(@RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(defaultValue = "0") int page,
                        Model model) {

        if (keyword != null && !keyword.isEmpty()) {
            List<Employee> employees = repository.findByDepartmentContainingOrderByIdAsc(keyword);
            model.addAttribute("employees", employees);
            model.addAttribute("keyword", keyword);
        } else {
            Pageable pageable = PageRequest.of(page, 50, Sort.by("id").ascending());
            Page<Employee> employeePage = repository.findAll(pageable);

            model.addAttribute("employees", employeePage.getContent());
            model.addAttribute("pageInfo", employeePage);
        }
        return "index";
    }

    // ② CSV一括登録処理
    @PostMapping("/upload")
    public String uploadCsv(@RequestParam("csvFile") MultipartFile file) {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee emp = new Employee();
                emp.setName(data[0]);
                emp.setDepartment(data[1]);
                emp.setHireYear(Integer.parseInt(data[2]));
                repository.save(emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    // ③ 従業員削除処理
    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    // ④ 編集画面の表示
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + id));
        model.addAttribute("employee", employee);
        return "edit";
    }

    // ⑤ 従業員情報の更新処理
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable("id") Integer id,
                                 @Validated Employee employee,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            employee.setId(id);
            model.addAttribute("employee", employee);
            return "edit";
        }
        employee.setId(id);
        repository.save(employee);
        return "redirect:/";
    }

    // 2026/05/27 【追加】指定された従業員の給与明細一覧画面を表示する処理
    @GetMapping("/salary/{employeeId}")
    public String showSalaryDetail(@PathVariable("employeeId") Integer employeeId, Model model) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee Id:" + employeeId));

        // バッチが計算した、その従業員の給与計算結果を取得
        List<MonthlySalaryResult> salaryList = salaryRepository.findByEmployeeId(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("salaryList", salaryList);

        return "salary"; // salary.html を呼び出します
    }
}