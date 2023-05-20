<?php
/*
	題目說明：
	  1. 使用PHP開啟board.txt檔，然後將其內容顯示出來。
	  2. TXT檔中換行的地方，用PHP與HTML處理後，也需要換行。
*/

?>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Practice 5</title>
</head>
<body>
    <pre><?= file_get_contents('./board.txt') ?></pre>
</body>
</html>
