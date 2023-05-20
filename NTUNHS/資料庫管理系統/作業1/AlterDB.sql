use master;

-- 2.1 附加資料庫

CREATE DATABASE bank ON (
    NAME = 'bank',
    -- FILENAME = '/var/opt/mssql/data/bank.mdf'
    FILENAME = 'D:\MSSQL_DB\bank.mdf'
    ) FOR ATTACH;
GO

-- 2.2 修改資料庫中的主檔，並將其SIZE, MAXSIZE以及FILEGROWTH的數值變成原本的2倍

ALTER DATABASE bank MODIFY FILE (
    NAME = bank,
    SIZE = 20 MB,
    MAXSIZE = 200 MB,
    FILEGROWTH = 10%);
GO

---------- 2.3. ALTER TABLE ----------

use bank;

-- 2.3.1 修改在[個人資訊]的[性別]欄位中，將預設值改為'M'

ALTER TABLE profiles DROP CONSTRAINT DF_profiles_gender;
ALTER TABLE profiles ADD CONSTRAINT DF_profiles_gender DEFAULT 'M' FOR gender;

-- 2.3.2 將[帳號]以及[交易紀錄]以[帳號ID]欄位進行Foreigen Key 關聯

ALTER TABLE transactions
    ADD CONSTRAINT FK_transactions_account_id FOREIGN KEY (account_id) REFERENCES accounts (id);

-- 2.3.3 修改 [個人資訊]資料表中超過18歲才能開戶的限制，並變更為國籍為Taiwan才能開戶

ALTER TABLE profiles DROP CONSTRAINT CK_profiles_age_over_18;
ALTER TABLE profiles ADD CONSTRAINT CK_profiles_country_is_taiwan CHECK (country = 'Taiwan');

-- 2.3.4. 修改這三個資料表的[更新日期與時間]欄位，設定預設值為 "今天"

ALTER TABLE accounts ADD CONSTRAINT DF_accounts_updated_at DEFAULT GETDATE() FOR updated_at;
ALTER TABLE profiles ADD CONSTRAINT DF_profiles_updated_at DEFAULT GETDATE() FOR updated_at;
ALTER TABLE transactions ADD CONSTRAINT DF_transactions_updated_at DEFAULT GETDATE() FOR updated_at;

-- 2.3.5. 新增一資料表，為銀行[分行帳號資料表]內容包含: (1)分行帳號 (2)分行名稱

CREATE TABLE branch_accounts
(
    id   INT PRIMARY KEY,       -- 分行帳號
    name NVARCHAR(50) NOT NULL, -- 分行名稱
);

INSERT INTO branch_accounts
VALUES (1001, N'台北分行'),
       (2002, N'台中分行'),
       (2003, N'台南分行');

-- 2.3.6. 將上述分行帳號使用Alter語法使用Foreigen Key與[帳號資料表] 進行關聯

ALTER TABLE accounts
    ADD CONSTRAINT FK_accounts_branch_id FOREIGN KEY (branch_id) REFERENCES branch_accounts (id);
GO
