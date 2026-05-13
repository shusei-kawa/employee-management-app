-- 従業員テーブルが存在しなければ作成する
CREATE TABLE IF NOT EXISTS employee (
                                        id SERIAL PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    department VARCHAR(255) NOT NULL,
    hire_year INTEGER
    );