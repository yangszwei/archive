<?php

    $db = new mysqli('localhost', 'cas30', '45890802', 'cas30');
    if ($db -> connent_error) die("Connection Failed!" . $db -> conntent_errno);

    $db -> query("SET NAMES 'utf8'");
    $db -> query("SET character_set_client = utf8;");
    $db -> query("SET character_set_results = utf8;");

    mysqli_query($db, "DROP TABLE scoreTable;");
    mysqli_query($db, "CREATE TABLE scoreTable LIKE scoreTableFallback;");
    mysqli_query($db, "INSERT scoreTable SELECT * FROM scoreTableFallback;");

?>