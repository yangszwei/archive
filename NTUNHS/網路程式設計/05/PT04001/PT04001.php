<?php
/*
	題目說明：
	  1. 使用PHP顯示伺服器的系統時間
	  2. 用亂數抓取10張圖中的其中一張圖出來顯示
	  3. 以上HTML均需置中
*/

    date_default_timezone_set('Asia/Taipei');

?>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Practice 4</title>

    <style>
        body {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
    </style>
</head>
<body>

<p><?= date('Y/m/d H:i:s'); ?></p>

<img src="./<?= rand(1, 10) ?>.jpg" alt="" />

</body>
</html>
