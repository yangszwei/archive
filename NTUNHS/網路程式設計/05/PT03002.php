<?php
    /*
        題目說明：
          搜尋$Score，當輸入關鍵字(部分)符合$Score[?]的第1個欄位或第2個欄位時，則顯示出$Score[?]該筆資料。
    */

    // 一個陣列所宣告產生的資料庫
    //	欄位依序代表「學生姓名」、「學生學號」、「國文成績」、「英文成績」與「數學成績」
    $Score = array(array("王小明", "440000001", 95, 93, 92),
        array("王小乖", "440000002", 85, 95, 77),
        array("王小笨", "440000003", 87, 76, 50),
        array("王大毛", "412345678", 88, 95, 85),
        array("林大毛", "440000005", 79, 82, 82));

    $Result = array();

    if (!empty($_GET['Keyword'])) {
        $Result = array_filter($Score, function ($Student) {
            return preg_match('/' . $_GET['Keyword'] . '/i', $Student[0]) ||
                preg_match('/' . $_GET['Keyword'] . '/i', $Student[1]);
        });
    }
?>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Practice 3-2</title>

    <style>
        th, td {
            border: 1px #ffac55 solid;
            padding: 5px;
            text-align: center;
        }
    </style>
</head>
<body>

<b>NTUNHS成績查詢系統</b><br/>
<form action="" method="GET">
    請輸入學生姓名或學號：
    <input type="text" name="Keyword" value="<?= $_GET['Keyword'] ?? "" ?>"/>
    <input type="submit"/>
</form>
<br/>

<?php if (isset($_GET['Keyword'])) { ?>
    <?php if (empty($Result)) { ?>
        <p>查無資料</p>
    <?php } else { ?>
        <table style="border: 3px #ffac55 double; border-collapse: collapse; padding: 5px;">
            <thead>
            <tr style="background-color: #E0E0E0;">
                <th>學生姓名</th>
                <th>學生學號</th>
                <th>國文成績</th>
                <th>英文成績</th>
                <th>數學成績</th>
            </tr>
            </thead>
            <tbody>
            <?php foreach ($Result as $item) { ?>
                <tr>
                    <?php foreach ($item as $value) { ?>
                        <td><?= $value ?></td>
                    <?php } ?>
                </tr>
            <?php } ?>
            </tbody>
        </table>
    <?php } ?>
<?php } ?>

</body>
</html>
