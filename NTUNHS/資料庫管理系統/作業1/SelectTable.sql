use bank;

-- 3.1. 跨資料表查詢

SELECT accounts.id,                                     -- (4) 帳號
       p.last_name + ' ' + p.first_name AS full_name,   -- (1) "姓氏 + 名字"
       CAST(p.birthday AS NVARCHAR) + ', ' + p.gender,  -- (2) 生日與性別
       p.city + ', ' + p.country        AS address,     -- (3) 城市, 國籍
       ba.name                          AS branch_name, -- (5) 分行名稱
       balance                                          -- (6) 餘額
FROM accounts
         JOIN profiles p ON p.id = accounts.profile_id
         JOIN branch_accounts ba ON ba.id = accounts.branch_id;

-- 3.2. 所有使用者帳號一共交易的次數

SELECT p.last_name + ' ' + p.first_name AS full_name,        -- (1) "姓氏 + 名字"
       COUNT(transactions.id)           AS transaction_count -- (2) 交易次數
FROM transactions
         JOIN accounts a ON a.id = transactions.account_id
         JOIN profiles p ON p.id = a.profile_id
GROUP BY a.id, p.last_name, p.first_name;
