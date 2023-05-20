<!doctype html>
<?php
    $Student = array("Derek", "Joe", "Kevin", "Charlton", "Lee", "Martin");

    sort($Student);
?>
<html lang="zh-Hant">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Practice 2-1</title>
</head>
<body>
學生總人數：<?= count($Student) ?><br><br>
姓名排序：<br><?= implode('<br>', $Student) ?>
</body>
</html>
