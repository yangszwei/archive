USE HW2;

-- 隨堂練習1:請將[出貨記錄].[編號]IDENTITY關閉，並手動新增一筆資料

SET IDENTITY_INSERT 出貨記錄 ON;

INSERT INTO 出貨記錄 (編號, 日期, 客戶名稱, 書名, 數量)
VALUES (9, '2016-07-23', N'天天書局', N'Windows Server MIS 實戰問答', 7);
GO

-- 隨堂練習2:請新增[員工]中，職位為 "辦事員"的資料至[圖書室借用記錄]

UPDATE 圖書室借用記錄
SET 附註 = N'辦事員'
WHERE 員工編號 IN (SELECT 編號 FROM 員工 WHERE 職位 = N'辦事員');
GO

-- 隨堂練習 3:請將SQL中定義的資料型態int, smallint, char, vchar的TYPE_NAME, PRECISION, DATA_TYPE的資訊放到自訂暫存資料表，並顯示結果

DROP TABLE IF EXISTS #result;

CREATE TABLE #result
(
    TYPE_NAME          SYSNAME,
    DATA_TYPE          SMALLINT,
    PRECISION          INT,
    LITERAL_PREFIX     VARCHAR(32),
    LITERAL_SUFFIX     VARCHAR(32),
    CREATE_PARAMS      VARCHAR(32),
    NULLABLE           SMALLINT,
    CASE_SENSITIVE     SMALLINT,
    SEARCHABLE         SMALLINT,
    UNSIGNED_ATTRIBUTE SMALLINT,
    MONEY              SMALLINT,
    AUTO_INCREMENT     SMALLINT,
    LOCAL_TYPE_NAME    SYSNAME,
    MINIMUM_SCALE      SMALLINT,
    MAXIMUM_SCALE      SMALLINT,
    SQL_DATA_TYPE      SMALLINT,
    SQL_DATETIME_SUB   SMALLINT,
    NUM_PREC_RADIX     INT,
    INTERVAL_PRECISION SMALLINT,
    USERTYPE           SMALLINT
);

INSERT INTO #result EXEC sp_datatype_info;

ALTER TABLE #result
    DROP COLUMN LITERAL_PREFIX, LITERAL_SUFFIX, CREATE_PARAMS, NULLABLE, CASE_SENSITIVE, SEARCHABLE, UNSIGNED_ATTRIBUTE, MONEY, AUTO_INCREMENT, LOCAL_TYPE_NAME, MINIMUM_SCALE, MAXIMUM_SCALE, SQL_DATA_TYPE, SQL_DATETIME_SUB, NUM_PREC_RADIX, INTERVAL_PRECISION, USERTYPE;

DELETE
FROM #result
WHERE TYPE_NAME NOT IN ('int', 'smallint', 'char', 'varchar');

SELECT *
FROM #result;

-- 隨堂練習4:顯示[出貨記錄]與[客戶]資料表中所有的資料表，顯示不能重複欄位，將聯絡人的姓氏為'陳'的聯絡人顯示出來

SELECT 編號,
       日期,
       客戶編號,
       出貨記錄.客戶名稱 AS 客戶名稱,
       書名,
       數量,
       聯絡人,
       地址,
       電話
FROM 出貨記錄
         JOIN 客戶 ON 出貨記錄.客戶名稱 = 客戶.客戶名稱
WHERE 聯絡人 LIKE N'陳%';

-- 隨堂練習5:請將隨堂練習4的結果放到新增資料表[詳細借用記錄]中

SELECT 編號,
       日期,
       客戶編號,
       出貨記錄.客戶名稱 AS 客戶名稱,
       書名,
       數量,
       聯絡人,
       地址,
       電話
INTO 詳細借用記錄
FROM 出貨記錄
         JOIN 客戶 ON 出貨記錄.客戶名稱 = 客戶.客戶名稱
WHERE 聯絡人 LIKE N'陳%';

-- 隨堂練習6 圖書館員發現之前經理借的所有書籍都少紀錄一本，請將結果更新至[圖書室借用記錄]

UPDATE 圖書室借用記錄
SET 數量 = 數量 + 1
WHERE 員工編號 IN (SELECT 編號 FROM 員工 WHERE 職位 = N'經理');

-- 隨堂練習7 請刪除[應徵者]未繳交自傳的資料

DELETE
FROM 應徵者
WHERE 自傳 IS NULL;

-- 隨堂練習8

INSERT INTO 圖書室借用記錄 (員工編號, 書名, 數量, 歸還日期, 附註)
SELECT 編號 AS 員工編號, 書籍.書籍名稱 AS 書名, 1, NULL, ''
FROM 員工, 書籍
WHERE 職位 = N'經理' AND 書籍.書籍名稱 NOT IN (SELECT 書名 FROM 圖書室借用記錄);
