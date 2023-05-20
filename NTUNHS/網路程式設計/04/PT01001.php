<!doctype html>
<html lang="zh-Hant">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Practice 1</title>
</head>
<body>
比較以下兩字串<br/>
<form>
    <input type="text" name="Value1" value="<?= $_GET["Value1"] ?? "" ?>"/> ≡
    <input type="text" name="Value2" value="<?= $_GET["Value2"] ?? "" ?>"/>
    <input type="submit"/>
    <?php
    if (isset($_GET["Value1"]) && isset($_GET["Value2"])) {
        if ($_GET["Value1"] === $_GET["Value2"]) {
            echo "<p>絕對相等</p>";
        } else if (strcasecmp($_GET["Value1"], $_GET["Value2"]) === 0) {
            echo "<p>相對相等</p>";
        } else {
            echo "<p>不相等</p>";
        }
    }
    ?>
</form>
</body>
</html>
