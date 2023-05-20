<?php
    /*
        題目說明：
          擷取新聞各大版網頁，然後儲存至1.htm~5.htm，並結合Practice 6  (W08-2)，提供一搜尋當日新聞的功能。
    */

    // 5個新聞的連結($URLLink[0] ~ $URLLink[4])
    $URLLink = array("https://www.nownews.com/cat/news-global/", "https://www.nownews.com/cat/sport/", "https://www.nownews.com/cat/society/", "https://www.nownews.com/cat/column/", "https://www.nownews.com/cat/finance/");

    $Keyword = $_GET["Keyword"] ?? "";

    for ($i = 0; $i < 5; $i++) {
        $dest = fopen($i + 1 . ".htm", "w");
        fwrite($dest, file_get_contents($URLLink[$i]));
        fclose($dest);
    }
?>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Practice 8</title>
</head>
<body>

<form action="" method="GET">
    關鍵字：
    <input type="text" name="Keyword" value="<?php echo $Keyword; ?>" /><input type="submit" />
</form>
<?php
    if (!empty($Keyword)) {
        for ($i = 0; $i < 5; $i++) {
            $filename = $i + 1 . ".htm";
            $handle = fopen($filename, "r");
            while (!feof($handle)) {
                $line = fgets($handle);
                if (preg_match("/{$Keyword}/i", $line)) {
                    echo "<a href='{$filename}'>{$filename}</a><br/>\n";
                    echo $line . "<br/><br/>\n";
                    break;
                }
            }
            fclose($handle);
        }
    }
?>

</body>
</html>
