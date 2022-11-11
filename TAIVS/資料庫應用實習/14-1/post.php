<?php

    $db = new mysqli('localhost', 'cas30', '45890802', 'cas30');
    if ($db -> connent_error) die("Connection Failed!" . $db -> conntent_errno);

    $db -> query("SET NAMES 'utf8'");
    $db -> query("SET character_set_client = utf8;");
    $db -> query("SET character_set_results = utf8;");
    echo "Action: $_POST[action]\n";

    switch ($_POST[action]) {
        case "set":
            $sql = "INSERT INTO scoreTable (seatNo, studentName, chinese, english, math, pro1, pro2)" .
                   " VALUES('$_POST[seatNo]', '$_POST[studentName]', '$_POST[chinese]', '$_POST[english]', '$_POST[math]', '$_POST[pro1]', '$_POST[pro2]')";
            $statement = $db -> prepare($sql);
            $statement -> execute();
            echo "'$_POST[seatNo]', '".$_POST[studentName]."', '$_POST[chinese]', '$_POST[english]', '$_POST[math]', '$_POST[pro1]', '$_POST[pro2]'";
        break;
        case "update":
            $sql = "UPDATE scoreTable SET ".
                   "studentName = '".$_POST[studentName]."', ".
                   "chinese = '$_POST[chinese]', ".
                   "english = '$_POST[english]', ".
                   "math = '$_POST[math]', ".
                   "pro1 = '$_POST[pro1]', ".
                   "pro2 = '$_POST[pro2]' ".
                   "WHERE seatNo = '$_POST[seatNo]';";
            $statement = $db -> prepare($sql);
            $statement -> execute();
        break;
        case "delete":
            $sql = "DELETE FROM scoreTable WHERE seatNo = '$_POST[seatNo]';";
            $statement = $db -> prepare($sql);
            $statement -> execute();
        break;
        case "random":
            $statement = $db -> prepare("TRUNCATE TABLE scoreTable;");
            $statement -> execute();

            $array = [
                "淺井惠", "春埼美空", "相麻菫", "本須和秀樹", "小唧", "絲茉茉",
                "香風智乃", "條河麻耶", "奈津惠", "音無結弦", "立華奏", "松下護驒",
                "貪吃佩可", "可可蘿", "真步", "松坂砂糖", "神戶鹽", "飛驒翔子",
                "赤松結衣", "小幸", "琴葉", "紫龍", "星矢", "瞬",
                "平澤唯", "平澤憂", "秋山澪", "各務原撫子", "志摩凜", "犬山葵",
                "東雲名乃", "水上麻衣", "長野原美緒", "神原秋人", "栗山未來", "名瀨美月",
                "可兒江西也", "拉媞琺", "千斗五十鈴", "折木奉太郎", "千反田愛瑠", "福部里志",
                "國崎往人", "霧島佳乃", "石田將也", "西宮硝子", "植野直花"
            ];
            shuffle($array);
            for ($i = 1;$i <= 30;$i++) {
                $element = array_pop($array);
                $j = ($i < 10 ? "0$i" : $i);
                $min = rand(0, 60);
                $a = rand($min, 100); $b = rand($min, 100); $c = rand($min, 100); $d = rand($min, 100); $e = rand($min, 100);
                $sql = "INSERT INTO scoreTable (seatNo, studentName, chinese, english, math, pro1, pro2)" .
                           " VALUES('$j', '$element', '$a', '$b', '$c', '$d', '$e')";
                $statement = $db -> prepare($sql);
                $statement -> execute();
            }
        break;
        default:
            echo "Fail";
        break;
    }

?>