# 第15週

## 練習題1

[01.c](01.c)

### 1-1

- 設計一程式，產生一文字檔(users.dat)，第一欄為帳號(HN001~HN100)，第2欄為密碼(共8個字元)，密碼規則如下：
  - 第1個字元為「2」
  - 第2,5,8個字元為小寫英文字母 (亂數產生; a~z)
  - 第3,6個字元為大寫英文字母 (亂數產生; A~Z)
  - 第4,7個字元為數值 (亂數產生; 0~9)
- 共100列

```
HN001 2xA5fS8s
HN002 2gG6aH3v
HN003 2lK7sG6n
…
…
HN299 2sT0xC2v
HN100 2qZ1nM5c
```

### 1-2

接續上一題，設計一程式，提供使用者輸入一帳號與密碼，然後與上一題中的文字檔(users.dat)進行驗證，判斷帳號密碼是否正確

### 1-3

- 接續上上題，設計一程式，由文字檔(users.dat)中抓出帳號與密碼，然後將檔案(welcome.dat)中的`<ID>`欄位改成抓出的帳號名，`<PASSWORD>`欄位改成抓出的密碼名，最後，將此結果匯入一新檔案中
- 此新檔案檔名為抓出的帳號名後面加上.txt(例如HN001.txt)，共將產生100個檔案

## 練習題2

[02.c](02.c)

### 2-1

- 設計一簡易之搜尋引擎, 判斷關鍵字是否有在檔案(search.dat)中出現
  - 若有, 則顯示出該行內容，最多顯示3列字串
  - 若無, 則顯示「找不到資料!!」

```
請輸入關鍵字: is
================================
Music by Mark Isham
Distributed by Columbia Pictures(USA)
Language English 
```

```
請輸入關鍵字: favor
================================
找不到資料!!
```

### 2-2

設計一程式，將檔案(search.dat)中內容，重新排列，變成不要有換行，並將結果儲存於檔案(result1.txt)中 (以下3種方式均要會)

1. 使用`fscanf()`完成
2. 使用`fgets()`，配合`strlen()`完成
3. 使用`fgetc()`完成

```
A River Runs Through It (film)From
Wikipedia, the free encyclopedia
For information on the 1976 novella by
Norman Maclean, see A River Runs Through It
(novel).
A River Runs Through It
original movie poster
Directed by Robert Redford
……
```

```
A River Runs Through It (film)From
Wikipedia, the free encyclopedia For
information on the 1976 novella by Norman
Maclean, see A River Runs Through It
(novel). A River Runs Through It original
movie poster Directed by Robert Redford
……
```

### 2.3

設計一程式，將檔案(search.dat)中內容，顯示在
螢幕上，並將其中有「the」的地方，變更為「a」

```
A River Runs Through It (film)From
Wikipedia, the free encyclopedia
For information on the 1976 novella by
Norman Maclean, see A River Runs Through It
(novel).
A River Runs Through It
original movie poster
Directed by Robert Redford
……
```

```
A River Runs Through It (film)From
Wikipedia, a free encyclopedia
For information on a 1976 novella by Norman
Maclean, see A River Runs Through It
(novel).
A River Runs Through It
original movie poster
Directed by Robert Redford
……
```
