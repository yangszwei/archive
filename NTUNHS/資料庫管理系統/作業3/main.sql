-- USE master
-- GO
--
-- CREATE DATABASE [練習10] ON
--     ( FILENAME = N'/var/opt/mssql/data/hw03.mdf')
--     FOR ATTACH;

USE [練習10]
GO

-- 1. 使用JOIN語法改寫10-7範例 (25)

SELECT o.訂單編號, o.下單日期, SUM(i.數量) AS 總數量
FROM 訂單 o
         LEFT OUTER JOIN 訂購項目 i ON o.訂單編號 = i.訂單編號
GROUP BY o.訂單編號, o.下單日期

-- 2. 查詢欄位中不確定字元語法(25)

SELECT * FROM 標標公司 WHERE 產品名稱 LIKE N'%__手冊%'

-- 3. 查詢欄位中不確定字元包含在某個範圍語法(25)

SELECT * FROM 標標公司 WHERE 產品名稱 LIKE '%[STUVWXYZ]indows%'

SELECT
    問卷編號,
    CASE WHEN 性別 = 1 THEN N'男' ELSE N'女' END AS 性別,
    CASE 滿意度 WHEN 3 THEN N'滿意' WHEN 2 THEN N'尚可' WHEN 1 THEN N'差勁' END AS 評價
FROM 問卷
ORDER BY 滿意度 DESC, 問卷編號
