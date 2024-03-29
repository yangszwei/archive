# 第6週

## 練習題1

[01.c](01.c)

提供一介面讓使用者不斷的輸入數值(整數)，並將所有輸入的數值相加，直到輸入負整數時停止

```
請輸入一整數：35
=========================
總和為35
請輸入一整數：66
=========================
總和為101
請輸入一整數：-1
=========================
程式結束
```

## 練習題2

[02.c](02.c)

寫一程式，讓使用者輸入一整數值N，然後計算出N!。 (N＞0)

```
請輸入一整數：3
=========================
3! = 6
```

```
請輸入一整數：6
=========================
6! = 720
```

## 回家程式練習

[02-1.c](02-1.c)

- 寫一程式，讓使用者輸入一整數值N，然後計算出N!。 (N＞0)
- 又已知0!=1，若考慮進此狀況，又應該如何完成？ (N≧0)
- 若要讓使用者不斷輸入，直到輸入-1時才結束，又該如何完成？

## 練習題3

[03.c](03.c)

寫一程式，設定一密碼(整數值M)，然後，讓使用者輸入一密碼(整數值N)，

1. 若密碼符合，顯示「密碼正確」，程式結束。
2. 若密碼錯誤，且輸入次數小於等於3次，顯示「密碼錯誤」。
3. 若密碼錯誤，且輸入次數大於3次，顯示「密碼錯誤」並延遲5秒鍾。 \[`_sleep(5000);` 或加入`#include <stdlib.h>`與`sleep(5);`\]
4. 若密碼錯誤，且輸入次數大於5次，顯示「錯誤次數過多」，程式結束。

```
請輸入一密碼：1234
=========================
密碼正確
```

```
請輸入一整數：1111
=========================
密碼錯誤
請輸入一整數：2222
=========================
密碼錯誤
請輸入一整數：3333
=========================
密碼錯誤
請輸入一整數：4444
=========================
密碼錯誤 <- 延遲5秒
請輸入一整數：5555
=========================
密碼錯誤 <- 延遲5秒
請輸入一整數：6666
=========================
錯誤次數過多
```
