package com.example.OJT;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Entity // これをつけるとDBのテーブルとして認識されます
public class Employee {

    @Id // これが主キー（一意のID）になります
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDを自動連番（1,2,3...）にする設定
    private Integer id;
    // ▼ 名前：空欄を禁止するルール
    @NotBlank(message = "氏名は必須です")
    private String name;

    // ▼ 部署名：空欄を禁止するルール
    @NotBlank(message = "部署名は必須です")
    private String department;

    // ▼ 入社年：1900以上の数字しか受け付けないルール
    @Min(value = 1900, message = "入社年は1900以上の正しい年を入力してください")
    private Integer hireYear;

    // ▼ 以下、データを出し入れするための「Getter(ゲッター)」と「Setter(セッター)」です ▼
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getHireYear() {
        return hireYear;
    }

    public void setHireYear(Integer hireYear) {
        this.hireYear = hireYear;
    }
}
