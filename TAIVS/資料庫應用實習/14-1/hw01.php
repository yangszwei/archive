<!DOCTYPE html>
<html lang="en">

<head>

    <title>訊二甲 30 楊斯惟 14-01</title>
    <meta charset="utf-8" />
    <link rel="stylesheet" href="boot.css" />
    <link defer rel="stylesheet" href="index.min.css" />
    <!-- <link defer rel="stylesheet" href="index.css" /> -->
    <script defer src="https://cdn.jsdelivr.net/npm/sortablejs@latest/Sortable.min.js"></script>
    <link href="https://fonts.googleapis.com/css?family=Noto+Sans+TC|Lato" rel="stylesheet" />
    <link href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" rel="stylesheet" />

</head>

<body>

    <div id="app-top">
        <span id="at-title">Score Table</span>
        <div id="at-action">
            <button onclick="load()" class="loader" title="重新整理"><i class="fas fa-fw fa-sync-alt"></i></button>
            <button onclick="random(this)" title="自動產生資料表"><i class="fas fa-fw fa-dice"></i></button>
            <button onclick="restore(this)" title="還原資料表"><i class="fas fa-fw fa-history"></i></button>
            <button onclick="select_all()" class="select_all hidden" title="選取全部"><i class="fas fa-fw fa-list"></i></button>
            <button onclick="removeMultiple()" class="trash hidden" title="刪除已選項目"><i style="color: red;" class="fas fa-fw fa-trash"></i></button>
            <button onclick="$('#tutorial').style.display='flex'" title="使用說明"><i class="fas fa-fw fa-question-circle"></i></button>
        </div>
    </div>

    <div id="app-main">
        <div id="am-table">
            <div id="amt-head">
                <div class="identity">
                    <span class="sort" onclick="sort('seatNo', this)" data-next="DESC">
                        <span>座號</span>
                        <button><i class="fas fa-sort-down"></i></button>
                    </span>
                    <span data-lq="title/name">姓名</span>
                </div>
                <div class="subjects">
                    <span>國文</span>
                    <span>英文</span>
                    <span>數學</span>
                    <span>專一</span>
                    <span>專二</span>
                </div>
                <div class="other">
                    <span class="sort" onclick="sort('average', this)" data-next="DESC">
                        <span>總分</span>
                        <button><i class="fas fa-sort-down"></i></button>
                    </span>
                    <span>平均</span>
                </div>
                <div class="action">
                    <span>操作區</span>
                </div>
            </div>
            <div id="amt-body"></div>
        </div>
            <div id="tutorial" class="active">
                <p>
                    1. 點擊座號 －＞ 選取<br />
                    2. 未填寫項目 －＞ 不改變/設為0<br />
                    3. 拖曳項目 －＞ 重新排序
                </p>
                <button onclick="$('#tutorial').style.display='none'">關閉</button>
            </div>
    </div>
    
    <div id="app-foot">
        <div class="progress">
            <div class="state"></div>
        </div>
    </div>

    <script src="index.min.js"></script>
    <!-- <script src="index.js"></script> -->

</body>

</html>
