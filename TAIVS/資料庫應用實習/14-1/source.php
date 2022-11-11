<?php

    $db = new mysqli('localhost', 'cas30', '45890802', 'cas30');
    if ($db -> connent_error) die("Connection Failed!" . $db -> conntent_errno);

    $db -> query("SET NAMES 'utf8'");
    $db -> query("SET character_set_client = utf8;");
    $db -> query("SET character_set_results = utf8;");
    $sort;
    if ($_POST['sort'] == "average") {
        $sort = "((chinese + english + math + pro1 + pro2) / 5)";
    }
    if ($_POST['sort'] == "seatNo") {
        $sort = "seatNo";
    }
    $statement = $db -> prepare($_POST[sort] ? "SELECT * FROM scoreTable WHERE 1 ORDER BY $sort $_POST[type]" : "SELECT * FROM scoreTable WHERE 1");
    $statement -> execute();
    $result = $statement -> get_result();

    $counter = 0;

    while ($data = $result -> fetch_assoc()) {
        if ($data["seatNo"] > $counter)
        $counter = $data["seatNo"];
        echo "<div class='row'>
                <div class='cover'>
                    <div class='identity'>
                        <span onclick='select(this)'>".$data["seatNo"]."</span>
                        <span>".$data["studentName"]."</span>
                    </div>
                    <div class='subjects'>
                        <span class='".($data["chinese"] < 60 ? "fail" : "")."'>".$data["chinese"]."</span>
                        <span class='".($data["english"] < 60 ? "fail" : "")."'>".$data["english"]."</span>
                        <span class='".($data["math"] < 60 ? "fail" : "")."'>".$data["math"]."</span>
                        <span class='".($data["pro1"] < 60 ? "fail" : "")."'>".$data["pro1"]."</span>
                        <span class='".($data["pro2"] < 60 ? "fail" : "")."'>".$data["pro2"]."</span>
                    </div>
                    <div class='other'>
                        <span>".($data["chinese"]+$data["english"]+$data["math"]+$data["pro1"]+$data["pro2"])."</span>
                        <span>".(($data["chinese"]+$data["english"]+$data["math"]+$data["pro1"]+$data["pro2"])/5)."</span>
                    </div>
                    <div class='action'>
                        <button title='編輯/修改' onclick='row(this.parentElement.parentElement)'><i class='fas fa-edit'></i></button>
                        <button title='刪除' onclick='remove(this.parentElement.parentElement.nextElementSibling)' class='delete'><i class='fas fa-trash'></i></button>
                    </div>
                </div>
                <div class='edit'>
                    <div class='identity'>
                        <span class='seatNo'>".$data["seatNo"]."</span>
                        <input class='studentName' type='text' placeholder='".$data["studentName"]."'>
                    </div>
                    <div class='subjects'>
                        <input class='chinese' type='number' placeholder='".$data["chinese"]."'>
                        <input class='english' type='number' placeholder='".$data["english"]."'>
                        <input class='math' type='number' placeholder='".$data["math"]."'>
                        <input class='pro1' type='number' placeholder='".$data["pro1"]."'>
                        <input class='pro2' type='number' placeholder='".$data["pro2"]."'>
                    </div>
                    <div class='other'>
                        <button onclick='post(this.parentElement.parentElement)'><i class='fas fa-save'></i></button>
                    </div>
                </div>
            </div>";
    }

    if ($counter == 0) {
        echo "<div class='row empty'>
                <button onclick='random(this)'>
                    <i class='fas fa-fw fa-dice'></i>
                    <span>點擊此處以建立隨機資料表</span>
                </button>
            </div>";
    }

    echo "<div class='row disabled' style='padding-bottom:50px'>
                <div class='new cover' onclick='row(this)'>
                    <span>新增項目</span>
                </div>
                <div class='edit'>
                    <div class='identity'>
                        <span class='seatNo'>".($counter < 9 ? "0".($counter+1) : $counter+1)."</span>
                        <input class='studentName' type='text' placeholder='姓名'>
                    </div>
                    <div class='subjects'>
                        <input class='chinese' type='number' placeholder='0'>
                        <input class='english' type='number' placeholder='0'>
                        <input class='math' type='number' placeholder='0'>
                        <input class='pro1' type='number' placeholder='0'>
                        <input class='pro2' type='number' placeholder='0'>
                    </div>
                    <div class='other'>
                        <button onclick='create(this.parentElement.parentElement)'><i class='fas fa-plus'></i></button>
                    </div>
                </div>
            </div>";



?>