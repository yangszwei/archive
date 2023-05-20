<!doctype html>
<?php
    $Student = array(
        array("可達鴨", 0), array("皮卡丘", 90), array("傑尼龜", 70), array("妙蛙種子", 80), array("胖丁", 50),
        array("臭臭泥", 40), array("果然翁", 30), array("捲捲耳", 60), array("帝牙盧卡", 100), array("瑪納霏", 99)
    );

    usort($Student, fn($a, $b) => $b[1] <=> $a[1]);
?>
<html lang="zh-Hant">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Practice 2-1</title>
</head>
<body>
學生總人數：<?= count($Student) ?><br><br>
成績排名：<br><?= implode('<br>', array_map(fn($s) => "$s[0] $s[1]", $Student)) ?>
</body>
</html>
