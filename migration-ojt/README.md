# 従業員管理システム（マイグレーションプロジェクト）

## 概要
旧システムのJava移行を想定した、Spring Bootベースの管理システムです。
CRUD機能に加え、業務効率化を意識したCSV一括登録機能、品質担保のための自動テストを実装しています。

## 技術スタック
- Java 17 / Spring Boot 3.x
- PostgreSQL
- Thymeleaf / Bootstrap 5
- JUnit 5 / AssertJ (テスト自動化)

## 実装のこだわり
- **バリデーション:** 不正なデータ入力をバックエンドで確実にガード。
- **テスト自動化:** JUnitを導入し、手動テストの削減とデグレ防止を実現。
- **UI/UX:** Bootstrapを採用し、業務システムとしての使いやすさを追求。
- 
- ## 📄 設計実績
本システムの基本設計書は、以下のリンクから閲覧・ダウンロードが可能です。
- [システム構成図（インフラ・シーケンス）](docs/001_employee-management-app_システム構成図.xlsx)
- [テーブル定義書](docs/001_employee-management-app_テーブル定義書.xlsx)
- [画面一覧表](docs/001_employee-management-app_画面一覧.xlsx)
- [機能一覧表](docs/001_employee-management-app_機能一覧.xlsx)