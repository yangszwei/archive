<?php
/*
	題目說明：
	  當使用者輸入「帳號」與「密碼」後，將這兩個值存入一檔案(password.dat)中，然後再顯示出來。


	注意事項：
	  1. 「帳號」與「密碼」間用「;」隔開
	  2. 輸入兩筆以上時，之前的紀錄仍須存在(一筆用一行顯示)
*/
?>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Practice 7</title>
</head>
<body>
<form action="" method="post">
   帳號 ：<input type="text" name="username" /><br />
   密碼 ：<input type="password" name="password" /><br />
   <input type="submit" value="寫入password.dat" />
</form>

<?php
	if ((isset($_POST["username"])) && (isset($_POST["password"])))	// 用isset()檢查變數是否有值(非NULL)
	{
		// 接收使用者所傳送之帳號與密碼
		$username = $_POST["username"];
		$password = $_POST["password"];

        $handle = fopen("password.dat", "a");
        fwrite($handle, $username . ";" . $password . "\n");
        fclose($handle);

        echo "<pre>" . file_get_contents("password.dat") . "</pre>";
	}
?>

</body>
</html>
