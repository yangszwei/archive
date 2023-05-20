-- 資料庫 bank_data

CREATE DATABASE bank_data;
GO

use bank_data;

-- 銀行

CREATE TABLE banks
(
    id      INT PRIMARY KEY IDENTITY (1,1), -- 銀行代號
    name    NVARCHAR(50)  NOT NULL,         -- 銀行名稱
    address NVARCHAR(100) NOT NULL          -- 地址
);

INSERT INTO banks (name, address)
VALUES (N'銀行 1', 'Taipei'),
       (N'銀行 2', 'New Taipei');

-- 個人資料

CREATE TABLE profiles
(
    id          INT PRIMARY KEY IDENTITY (1,1),                                       -- 個人帳號
    first_name  NVARCHAR(50)  NOT NULL,                                               -- 名字
    last_name   NVARCHAR(50)  NOT NULL,                                               -- 姓氏
    birthday    DATE          NOT NULL,                                               -- 生日
    gender      CHAR(1)       NOT NULL DEFAULT 'U' CHECK (gender IN ('M', 'F', 'U')), -- 性別
    address     NVARCHAR(100) NOT NULL,                                               -- 通訊地址
    city        NVARCHAR(50)  NOT NULL,                                               -- 城市
    country     NVARCHAR(50)  NOT NULL,                                               -- 國籍
    modified_at DATETIME      NOT NULL DEFAULT GETDATE(),                             -- 更新時間日期
    modified_by NVARCHAR(50)  NOT NULL                                                -- 異動人
);

INSERT INTO profiles (first_name, last_name, birthday, gender, address, city, country, modified_by)
VALUES ('AAA', 'Fu', '2001-03-01', 'M', 'NTU', N'台北市', N'台灣', 'system'),
       ('GY', 'Chen', '2001-08-01', 'F', 'FJU', N'新北市', N'台灣', 'system'),
       ('KK', 'Wang', '1995-06-30', 'F', 'NCKU', N'台南市', N'台灣', 'system');

-- 帳戶資訊

CREATE TABLE accounts
(
    id          INT PRIMARY KEY IDENTITY (1,1),                 -- 銀行帳號
    bank_id     INT          NOT NULL REFERENCES banks (id),    -- 銀行代號
    branch_id   INT          NOT NULL,                          -- 分行帳號
    profile_id  INT          NOT NULL REFERENCES profiles (id), -- 個人帳號
    type        CHAR(1)      NOT NULL,                          -- 帳戶類型
    balance     INT          NOT NULL DEFAULT 0,                -- 餘額
    modified_at DATETIME     NOT NULL DEFAULT GETDATE(),        -- 更新時間日期
    modified_by NVARCHAR(50) NOT NULL                           -- 異動人
);

INSERT INTO accounts (bank_id, branch_id, profile_id, type, balance, modified_by)
VALUES (1, 1, 1, 'S', 10000, 'system'),
       (1, 2, 2, 'S', 20000, 'system'),
       (2, 1, 3, 'S', 30000, 'system'),
       (2, 2, 1, 'S', 40000, 'system'),
       (1, 1, 2, 'S', 50000, 'system');

-- 交易資訊

CREATE TABLE transactions
(
    id          INT PRIMARY KEY IDENTITY (1,1),                                   -- 交易編號
    account_id  INT          NOT NULL REFERENCES accounts (id) ON DELETE CASCADE, -- 銀行帳號
    time        DATETIME     NOT NULL DEFAULT GETDATE(),                          -- 交易時間
    atm_id      INT          NOT NULL,                                            -- ATM編號
    type        CHAR(1)      NOT NULL,                                            -- 交易類型
    content     NVARCHAR(50) NOT NULL,                                            -- 交易內容
    modified_at DATETIME     NOT NULL DEFAULT GETDATE(),                          -- 更新時間日期
    modified_by NVARCHAR(50) NOT NULL                                             -- 異動人
);

INSERT INTO transactions (account_id, atm_id, type, content, modified_by)
VALUES (1, 1, 'c', 'aaa', 'system'),
       (1, 2, 'c', '', 'system'),
       (2, 3, 'c', 'bbb', 'system'),
       (1, 4, 'c', 'test', 'system'),
       (2, 5, 'c', 'payment', 'system'),
       (3, 1, 'c', '', 'system'),
       (1, 2, 'c', 'shop', 'system'),
       (2, 3, 'd', 'deposit', 'system'),
       (3, 4, 'd', 'test 2', 'system'),
       (4, 5, 'd', 'lorem ipsum', 'system'),
       (1, 1, 'd', 'abc', 'system'),
       (2, 2, 'd', '123', 'system'),
       (3, 3, 'd', 'test', 'system'),
       (4, 4, 'd', 'test', 'system');
GO

-- 查詢某個帳戶的所有交易紀錄

SELECT t.id, t.account_id, banks.id AS bank_id, profiles.id AS profile_id, t.time, t.type, t.content
FROM transactions AS t
         JOIN accounts ON t.account_id = accounts.id
         JOIN profiles ON accounts.profile_id = profiles.id
         JOIN banks ON accounts.bank_id = banks.id
WHERE account_id = 1;
GO
