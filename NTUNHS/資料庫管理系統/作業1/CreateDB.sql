use master;

-- 1.1. 資料庫：bank

CREATE DATABASE bank ON (
    NAME = bank,
    -- FILENAME = '/var/opt/mssql/data/bank.mdf',
    FILENAME = 'D:\MSSQL_DB\bank.mdf',
    SIZE = 10 MB,
    MAXSIZE = 100 MB,
    FILEGROWTH = 10%
    );
GO

---------- 建立資料表 ----------

use bank;

-- 資料表：銀行

CREATE TABLE banks
(
    id      INT PRIMARY KEY, -- 銀行代號
    name    NVARCHAR(20),    -- 銀行名稱
    address NVARCHAR(50),    -- 地址
);

-- 資料表：個人資料

CREATE TABLE profiles
(
    id         INT PRIMARY KEY IDENTITY (100000, 2), -- 個人帳號 (1.2., 1.6.)
    first_name NVARCHAR(20),                         -- 名字
    last_name  NVARCHAR(20),                         -- 姓氏
    birthday   DATE,                                 -- 生日
    gender     CHAR(1),                              -- 性別
    address    NVARCHAR(50),                         -- 通訊地址
    city       NVARCHAR(10),                         -- 城市
    country    NVARCHAR(10),                         -- 國籍
    updated_at DATETIME,                             -- 更新時間日期
    updated_by NVARCHAR(20),                         -- 異動人

    -- 1.4. 在 [個人資訊]資料表中加入限制開戶應超過18歲才能開戶(才能建檔)
    CONSTRAINT CK_profiles_age_over_18 CHECK (DATEDIFF(YEAR, birthday, GETDATE()) >= 18),
);

-- 1.5. 在[個人資訊]的[性別]欄位中，設定預設值為'U'
ALTER TABLE profiles ADD CONSTRAINT DF_profiles_gender DEFAULT 'U' FOR gender;

-- 資料表：帳戶資訊

CREATE TABLE accounts
(
    id         INT PRIMARY KEY, -- 銀行帳號 (1.2.)
    bank_id    INT,             -- 銀行代號
    branch_id  INT,             -- 分行帳號
    profile_id INT,             -- 個人帳號
    balance    INT,             -- 餘額
    type       INT,             -- 帳戶類型（1. 活存帳戶、2. 定存帳戶）
    updated_at DATETIME,        -- 更新時間日期
    updated_by NVARCHAR(20),    -- 異動人

    -- 1.3. 將[帳號], [個人資訊]這兩個資料表以[個人帳號ID]欄位進行Foreigen Key 關聯
    CONSTRAINT FK_accounts_profile_id FOREIGN KEY (profile_id) REFERENCES profiles (id),
);

-- 資料表：交易資訊

CREATE TABLE transactions
(
    id         INT PRIMARY KEY, -- 交易編號 (1.2.)
    account_id INT,             -- 銀行帳號
    atm_id     INT,             -- ATM編號
    type       NVARCHAR(10),    -- 交易類型
    content    NVARCHAR(50),    -- 交易內容
    created_at DATETIME,        -- 交易時間
    updated_at DATETIME,        -- 更新時間日期
    updated_by NVARCHAR(20),    -- 異動人
);
GO

---------- 插入資料 ----------

INSERT INTO banks (id, name, address)
VALUES (1, N'國泰世華銀行', N'台北市信義區忠孝東路五段 68 號'),
       (2, N'台灣銀行', N'台北市中正區忠孝西路一段 2 號');

INSERT INTO profiles (first_name, last_name, birthday, gender, address, city, country, updated_at, updated_by)
VALUES (N'小美', N'王', '1990-05-01', 'F', N'台北市信義區忠孝東路五段 68 號', N'台北市', N'Taiwan',
        '2022-03-15 10:00:00', N'John'),
       (N'小華', N'陳', '1985-11-20', 'M', N'新北市板橋區中山路 100 號', N'新北市', N'Taiwan', '2022-03-14 14:30:00',
        N'Mary'),
       (N'小明', N'林', '1992-08-10', 'M', N'高雄市前鎮區復興四路 100 號', N'高雄市', N'Taiwan',
        '2023-03-15 09:30:00', N'Emma'),
       (N'小芳', N'黃', '1995-03-08', 'F', N'桃園市中壢區中央西路 100 號', N'桃園市', N'Taiwan', '2023-03-14 14:00:00',
        N'Peter'),
       (N'小菁', N'李', '1989-10-18', 'F', N'台南市東區南門路 100 號', N'台南市', N'Taiwan', '2023-03-13 17:30:00',
        N'William');

INSERT INTO accounts (id, bank_id, branch_id, profile_id, balance, type, updated_at, updated_by)
VALUES (1, 1, 1001, 100000, 50000, 1, '2022-03-15 09:00:00', N'John'),
       (2, 2, 2002, 100002, 100000, 2, '2022-03-14 13:30:00', N'Mary');

INSERT INTO transactions (id, account_id, atm_id, type, content, created_at, updated_at, updated_by)
VALUES (1, 1, 1, N'存款', N'存入1000元', '2023-03-15 10:00:00', '2023-03-15 10:00:00', N'管理員'),
       (2, 1, 2, N'轉帳', N'轉帳給2號帳戶100元', '2023-03-15 11:00:00', '2023-03-15 11:00:00', N'管理員'),
       (3, 1, 1, N'提款', N'提取500元', '2023-03-15 12:00:00', '2023-03-15 12:00:00', N'管理員'),
       (4, 1, 3, N'存款', N'存入500元', '2023-03-15 13:00:00', '2023-03-15 13:00:00', N'管理員'),
       (5, 1, 2, N'轉帳', N'轉帳給2號帳戶50元', '2023-03-15 14:00:00', '2023-03-15 14:00:00', N'管理員'),
       (6, 1, 3, N'提款', N'提取1000元', '2023-03-15 15:00:00', '2023-03-15 15:00:00', N'管理員'),
       (7, 1, 1, N'存款', N'存入2000元', '2023-03-15 16:00:00', '2023-03-15 16:00:00', N'管理員'),
       (8, 1, 2, N'轉帳', N'轉帳給2號帳戶200元', '2023-03-15 17:00:00', '2023-03-15 17:00:00', N'管理員'),
       (9, 1, 1, N'提款', N'提取800元', '2023-03-15 18:00:00', '2023-03-15 18:00:00', N'管理員'),
       (10, 1, 3, N'存款', N'存入100元', '2023-03-15 19:00:00', '2023-03-15 19:00:00', N'管理員'),
       (11, 2, 2, N'存款', N'存入300元', '2023-03-15 10:00:00', '2023-03-15 10:00:00', N'管理員'),
       (12, 2, 3, N'提款', N'提取50元', '2023-03-15 11:00:00', '2023-03-15 11:00:00', N'管理員'),
       (13, 2, 1, N'轉帳', N'轉帳給1號帳戶100元', '2023-03-15 12:00:00', '2023-03-15 12:00:00', N'管理員'),
       (14, 2, 2, N'轉帳', N'轉帳給好友', '2022-03-01 14:35:00', '2022-03-01 14:35:00', N'管理員'),
       (15, 2, 3, N'提款', N'提款1000元', '2022-03-03 09:20:00', '2022-03-03 09:20:00', N'管理員'),
       (16, 2, 1, N'存款', N'存款5000元', '2022-03-05 13:45:00', '2022-03-05 13:45:00', N'管理員'),
       (17, 2, 2, N'轉帳', N'轉帳至信用卡帳戶', '2022-03-06 10:10:00', '2022-03-06 10:10:00', N'管理員'),
       (18, 2, 3, N'提款', N'提款2000元', '2022-03-08 16:25:00', '2022-03-08 16:25:00', N'管理員'),
       (19, 2, 1, N'存款', N'存款8000元', '2022-03-10 11:50:00', '2022-03-10 11:50:00', N'管理員'),
       (20, 2, 2, N'存款', N'存入現金 5000 元', '2023-03-16 14:20:00', '2023-03-16 14:20:00', N'管理員');
GO

---------- 卸離資料庫 ----------

use master;

EXEC sp_detach_db 'bank';
GO
