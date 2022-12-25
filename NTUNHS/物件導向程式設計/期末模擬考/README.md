# 111 學年度第一學期國立臺北護理健康大學<br>資訊管理系物件導向程式設計期末模擬考卷

## 上機考

題目：設計一個北護大會員(User)易付卡(XDPay)提供現金儲值與電子付款系統，並有個人紅利點數累積，可提供數位付款(DigitalPay)功能，可查詢個人交易紀錄(LogList)等。根據試卷提供之範例程式，改寫範例程式(可任意修改刪除)，功能及配分說明如下：

- 撰寫類別 User 的方法。規格如下：
  - 設計 Profile.txt 內容將(1)個人基本資料（包含中使用者資訊，需至少包含：帳號、密碼、姓名、餘額、紅利點數等)(5分)與(2)交易紀錄(包含編號、交易類型、交易內容、交易金額、獲得紅利以及交易時間等)(5分)儲存至此檔案。
  - 初始化建構的 User 物件請設計一個建構子，輸入引數為檔案名稱(Profile.txt)，從檔案讀取資訊，將個人資料以及交易紀錄匯入至物件的屬性包含：(1)個人基本資料(5分)以及(2)交易紀錄。從 Profile.txt 讀取多筆交易紀錄(UserLog),並指派至myLogList(5分)。
  - 新增一個方法回傳所有交易記錄，內容組合成一個文字字串並回傳，格式可自訂，日期格式為 `yyyy/MM/dd HH:mm`。*_提示_：可在UserLog 類別中新增一個 getter 方法 (5%)
- 設計類別 XDPay 儲值 deposit（請使用 Interface 實作）以及電子付款 withdraw（請使用繼承類別 Pay 實作），並將結果儲存至 Profile.txt中。規格如下：
  - 儲值(deposit)：單筆儲值滿2000元，紅利增加100點，並回傳狀態；當儲值失敗，至少回傳2個失敗狀態，並在註解上加註失敗原因，將成功與失敗結果更新至 Profile.txt (5分)。
  - 付款(withdraw)：單筆消費滿500元，紅利增加10點，並回傳狀態；當消費失敗，至少回傳2個失敗狀態，並在註解上加註失敗原因，將成功與失敗結果更新至 Profile.txt (5分)。
- 設計設計類別 DigitalPay 數位付款功能 withdraw（使用繼承類別 Pay 實作），規格如下：
  - withdraw：單筆消費滿1000元，紅利增加10點，並回傳狀態，至少回傳2個失敗狀態，並在註解上加註失敗原因，將成功與失敗結果更新至 Profile.txt (5分)
- 撰寫 main 程式，用來驗證題目 1~3 的功能，程式執行後顯示全部結果，說明如下：
  - 測試 User 建構子：讀取個人資訊(Profile.txt)，顯示並驗證個人資訊，確定個人資料為自己的且資訊正確。例如姓名、學號與本人一致（學號跟姓名可寫在直接程式中-Hard Code）(5分)
  - 測試 User 建構子：讀取交易記錄，並將所有交易記錄列印出來，且顯示最後一筆交易日期，若無交易則顯示「尚未消費」(5分)
  - 測試XDPay與DigitalPay多型，用多型的方式實作XDPay以及DigitalPay兩種付款方式。並檢測餘額以及的紅利點數是否符合預期（含錯誤情形）。(10分)
    - *_提示_：`Pay myPay1 = new XDPay();`
    - 測試XDPay存款與付款多型：儲值功能之測試程式預設儲值金額為1,500，分別依序儲值3,000、200、450、4,000，最後再依序消費500、3,000、300、5,000、10,000(10分)
    - 測試DigitalPay付款多型：預設儲值金額為10,000，付款功能之測試程式分別依序付費金額為：500、800、2,999、5,000、3,000、2,000 (10分)
- 程式繳交：繳交兩個檔案，並上傳至iClass作業區：(1)個人資訊檔(Profile.txt)以及(2)程式碼全部存放一個檔案並命名為`Exam.java`。
- 執行 main 程式中間不得發生錯誤(5分)，並將所有題目的結果完成，最後顯示4個輸出結果，預期結果範例如下：(15分)

```
4.1User 建構子測試: 讀取 Profile.txt，狀態: 成功。
姓名: 連小月； 學號: 3345678 比對正確

4.2 User 建構子測試: 讀取交易紀錄，狀態: 成功。

最後一筆交易日期: 2022/01/05。全部交易紀錄
編號  交易類型          交易內容    交易金額    獲得紅利    交易時間
1245  XDPay 儲值       儲值        +500       0          2022/01/01 15:35
1246  XDPay 儲值       儲值        +2000      10         2022/01/02 12:30
1247  DigitalPay 消費  阿財鍋貼    -1000       10         2022/01/04 15:00
1248  DigitalPay 消費  小玉的店    -310        0          2022/01/05 13:11

4.3.1 測試 XDPay 存款與付款多型，測試狀態：成功。
- 預設 1500   餘額:1500   紅利:   0 點
- 儲值 3000   餘額:4500   紅利: 100 點 -交易成功
- 儲值  200   餘額:4700   紅利: 100 點 -交易成功
- 儲值 3000   餘額:7700   紅利: 200 點 -交易成功
- 儲值  450   餘額:8150   紅利: 200 點 -交易成功
- 儲值 4000   餘額:4150   紅利: 300 點 -交易成功
- 消費  500   餘額:3650   紅利: 300 點 -交易成功
- 消費 3000   餘額: 650   紅利: 310 點 -交易成功
- 消費  200   餘額: 450   紅利: 310 點 -交易成功
- 消費 5000   餘額: 450   紅利: 310 點 -交易失敗 - 餘額不足
- 消費 10000  餘額: 450   紅利: 320 點 -交易失敗 - 餘額不足

4.3.1 測試 DigitalPay 付款多型：，測試狀態: 成功。
- 預設 10000   餘額:10000   紅利:  0 點
- 消費   500   餘額: 9500   紅利: 10 點 -交易成功
- 消費   800   餘額: 8800   紅利: 20 點 -交易成功
- 消費  2999   餘額: 5801   紅利: 30 點 -交易成功
- 消費  5000   餘額:  801   紅利: 40 點 -交易成功
- 消費  3000   餘額:  801   紅利: 40 點 -交易失敗 - 餘額不足
- 消費  2000   餘額:  801   紅利: 40 點 -交易失敗 - 餘額不足
```

```java=
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class UserLog {
    private String LogNo, LogContent, LogTime;
    private int TranType = 0; // paid by 0: XDPay, 1: NTUNHSPay
    private int Money = 0, Bonus = 0;
    UserLog(String gLogNo, int gTranType, int gMoney, int gBonus, String gLogTime) {
        LogNo = gLogNo;
        TranType = gTranType;
        Money gMoney;
        Bonus = gBonus;
        LogTime = gLogTime;
    }
    String getLogString() { return "";  }

    public static String getCurrentlyDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }
}

class User {
    private String Name, ID, PWD;
    private int Bonus = 0, logCount = 0;
    Pay myXDPay, myDigtialPay;
    private ArrayList<UserLog> myLogList;

    //1.請設計一個建構子，並載入文字檔，將個人資料以及交易紀錄匯入至物件的屬性中
    User(String FileName) {
        // 提示: initial (gName, gID, gPWD);
        // logCount = myLogList.size();
    }
    private void initial(String gName, String gID, String gPWD) {
        Name = gName;
        ID = ID;
        PWD = gPWD;
        isLogged = false;
    }

    // 2.儲值XDPay功能
    int deposit(int gMoney) { return -1; }

    // 4.3.1 可選擇XDPay或CreditCardSpay其中一種付款方式，用多型的方式實作兩種付款方式
    int withdraw(int payType, int gMoney) { return -1; }

    public ArrayList<UserLog> getMyLogList() {
        return myLogList;
    }
    public void setMyLogList(ArrayList<UserLog> myLogList) {
        this.myLogList = myLogList;
    }

    public UserLog getLogByIndex(int index) {
        return myLogList.get(index);
    }
}

class Pay {
    int Balance;

    Pay() { Balance = 1000; }

    Pay(int gBalance) { Balance = gBalance; }

    int withdraw(int gMoney) { return -1; }
}

interface PrepaidCard {
    boolean deposit(int gMoney);
}

class XDPay {

    //儲值功能(請使用Interface PrepaidCard 實作)
    int deposit(int gMoney) { return -1; }

    // XDPay電子付款功能(請使用繼承類別Pay實作)
    int withdraw(int gMoney) { return -1;}
}

class DigitalPay {

    // XDPay電子付款功能(請使用繼承類別Pay實作)
    int withdraw(int gMoney) { return -1; }
}
 
public class Exam {
    public static void main(String[] args) {
    // 撰寫一個main程式
    }
}
```
