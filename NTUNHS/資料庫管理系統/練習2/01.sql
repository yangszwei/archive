use master;

CREATE DATABASE WhoPanda;
GO

use WhoPanda;

CREATE TABLE members
(
    id         INT IDENTITY (1, 1) PRIMARY KEY, -- ID
    name       NVARCHAR(50) NOT NULL,           -- 會員姓名
    department NVARCHAR(50) NOT NULL,           -- 系所
    type       NVARCHAR(50) NOT NULL,           -- 類型
)

CREATE TABLE payments
(
    id          INT IDENTITY (1, 1) PRIMARY KEY, -- ID
    name        NVARCHAR(50) NOT NULL,           -- 付款名稱
    member_id   INT          NOT NULL,           -- 會員ID
    card_number NVARCHAR(50),                    -- 卡號

    FOREIGN KEY (member_id) REFERENCES members (id)
);

CREATE TABLE stores
(
    id   INT PRIMARY KEY,      -- ID
    name NVARCHAR(50) NOT NULL -- 店家名稱
);

CREATE TABLE products
(
    id       INT IDENTITY (1, 1) PRIMARY KEY, -- ID
    store_id INT          NOT NULL,           -- 店家ID
    name     NVARCHAR(50) NOT NULL,           -- 產品名稱
    price    INT          NOT NULL,           -- 售價

    FOREIGN KEY (store_id) REFERENCES stores (id),
)

CREATE TABLE orders
(
    id          INT IDENTITY (1, 1) PRIMARY KEY,         -- ID
    payment_id  INT          NOT NULL,                   -- 付款ID
    date        DATE         NOT NULL,                   -- 訂單日期
    status      NVARCHAR(50) NOT NULL,                   -- 訂單狀態
    amount      INT          NOT NULL,                   -- 金額
    update_date DATE         NOT NULL DEFAULT GETDATE(), -- 更新日期

    FOREIGN KEY (payment_id) REFERENCES payments (id),
)

CREATE TABLE transactions
(
    id       INT IDENTITY (1, 1) PRIMARY KEY, -- ID
    type     NVARCHAR(50) NOT NULL,           -- 交易類型
    date     DATE         NOT NULL,           -- 交易日期
    status   NVARCHAR(50) NOT NULL,           -- 交易狀態
    order_id INT          NOT NULL,           -- 訂單ID

    FOREIGN KEY (order_id) REFERENCES orders (id),
)

CREATE TABLE order_items
(
    id         INT IDENTITY (1, 1) PRIMARY KEY, -- ID
    order_id   INT  NOT NULL,                   -- 訂單ID
    add_date   DATE NOT NULL,                   -- 加入日期
    product_id INT  NOT NULL,                   -- 產品ID
    quantity   INT  NOT NULL,                   -- 數量

    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id),
)

INSERT INTO members (name, department, type)
VALUES (N'林小美', N'資訊工程系', N'學生'),
       (N'張大同', N'數學系', N'教師');

INSERT INTO payments (name, member_id, card_number)
VALUES (N'信用卡', 1, N'1234-5678-9012-3456'),
       (N'轉帳', 2, N'2345-6789-0123-4567'),
       (N'貨到付款', 1, NULL),
       (N'現金', 2, NULL);

INSERT INTO stores (id, name)
VALUES (1, N'大三元'),
       (2, N'阿財鍋貼');

INSERT INTO products (store_id, name, price)
VALUES (1, N'招牌飯', 200),
       (1, N'滷肉飯', 70),
       (1, N'魯肉飯', 65),
       (1, N'排骨飯', 90),
       (1, N'炸雞排飯', 95),
       (1, N'炒泡麵', 50),
       (2, N'水餃', 35),
       (2, N'鍋貼', 40),
       (2, N'蔥油餅', 25),
       (2, N'豆花', 20),
       (2, N'烤餅', 30),
       (2, N'煎餃', 45);

INSERT INTO orders (payment_id, date, status, amount)
VALUES (1, '2022-01-01', N'已付款', 200),
       (2, '2022-01-02', N'已付款', 150),
       (3, '2022-01-03', N'已取消', 100),
       (4, '2022-01-04', N'已付款', 300),
       (1, '2022-01-05', N'已出貨', 250),
       (2, '2022-01-06', N'已付款', 120),
       (3, '2022-01-07', N'已付款', 180),
       (4, '2022-01-08', N'已出貨', 400),
       (1, '2022-01-09', N'已付款', 150),
       (2, '2022-01-10', N'已付款', 200),
       (3, '2022-01-11', N'已付款', 80),
       (4, '2022-01-12', N'已取消', 90),
       (1, '2022-01-13', N'已出貨', 150),
       (2, '2022-01-14', N'已付款', 280),
       (3, '2022-01-15', N'已付款', 210),
       (4, '2022-01-16', N'已出貨', 350),
       (1, '2022-01-17', N'已付款', 120),
       (2, '2022-01-18', N'已付款', 170),
       (3, '2022-01-19', N'已付款', 220),
       (4, '2022-01-20', N'已取消', 90);

INSERT INTO transactions (type, date, status, order_id)
VALUES (N'信用卡', '2022-01-01', N'成功', 1),
       (N'LINE Pay', '2022-01-02', N'成功', 2),
       (N'銀行轉帳', '2022-01-03', N'失敗', 3),
       (N'PayPal', '2022-01-04', N'成功', 4),
       (N'信用卡', '2022-01-05', N'成功', 5),
       (N'LINE Pay', '2022-01-06', N'成功', 6),
       (N'銀行轉帳', '2022-01-07', N'成功', 7),
       (N'PayPal', '2022-01-08', N'失敗', 8),
       (N'信用卡', '2022-01-09', N'成功', 9),
       (N'LINE Pay', '2022-01-10', N'成功', 10),
       (N'銀行轉帳', '2022-01-11', N'成功', 11),
       (N'PayPal', '2022-01-12', N'失敗', 12),
       (N'信用卡', '2022-01-13', N'成功', 13),
       (N'LINE Pay', '2022-01-14', N'成功', 14),
       (N'銀行轉帳', '2022-01-15', N'成功', 15),
       (N'PayPal', '2022-01-16', N'失敗', 16),
       (N'信用卡', '2022-01-17', N'成功', 17),
       (N'LINE Pay', '2022-01-18', N'成功', 18),
       (N'銀行轉帳', '2022-01-19', N'失敗', 19),
       (N'PayPal', '2022-01-20', N'成功', 20);

INSERT INTO order_items (order_id, add_date, product_id, quantity)
VALUES (1, '2022-02-01', 1, 2),
       (1, '2022-02-01', 2, 1),
       (2, '2022-02-02', 3, 3),
       (2, '2022-02-02', 4, 1),
       (3, '2022-02-03', 5, 1),
       (3, '2022-02-03', 6, 2),
       (4, '2022-02-04', 7, 1),
       (4, '2022-02-04', 8, 1),
       (5, '2022-02-05', 9, 2),
       (5, '2022-02-05', 10, 1),
       (6, '2022-02-06', 11, 3),
       (6, '2022-02-06', 12, 1),
       (7, '2022-02-07', 1, 1),
       (7, '2022-02-07', 2, 2),
       (8, '2022-02-08', 3, 1),
       (8, '2022-02-08', 4, 1),
       (9, '2022-02-09', 5, 2),
       (9, '2022-02-09', 6, 1),
       (10, '2022-02-10', 7, 3),
       (10, '2022-02-10', 8, 1),
       (11, '2022-02-11', 9, 1),
       (11, '2022-02-11', 10, 2),
       (12, '2022-02-12', 11, 1),
       (12, '2022-02-12', 12, 1),
       (13, '2022-02-13', 1, 2),
       (13, '2022-02-13', 2, 1),
       (14, '2022-02-14', 3, 1),
       (14, '2022-02-14', 4, 2),
       (15, '2022-02-15', 5, 1),
       (15, '2022-02-15', 6, 1);
GO

DECLARE @order_id INT = 1;
SELECT orders.id            as id,
       orders.date          as 訂單日期,
       amount               as 訂單金額,
       member_id            as "會員編號",
       m.name               as 會員姓名,
       order_items.quantity as 數量,
       order_items.quantity *
       (SELECT price FROM products WHERE id = order_items.product_id)
                            as 金額,
       products.name        as 產品名稱,
       stores.name          as 店家名稱,
       payments.name        as 付款名稱
FROM orders
         JOIN payments ON payments.id = orders.payment_id
         JOIN members m on m.id = payments.member_id
         JOIN order_items ON order_items.order_id = orders.id
         JOIN products on products.id = order_items.product_id
         JOIN stores ON stores.id = products.store_id
WHERE orders.id = @order_id;
