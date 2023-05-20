<?php
/*
    題目說明：
      搜尋$SearchEngineDB，當輸入關鍵字符合$SearchEngineDB[]，則將$SearchEngineDB[]顯示出來。
*/

// 一個陣列所宣告產生的資料庫
$SearchEngineDB = array("Apple", "Orange", "Pineapple", "Berry", "Strawberry", "Blueberry", "Blackberry",
    "An array in PHP is actually an ordered map. A map is a type that associates values to keys. This type is optimized for several different uses; it can be treated as an array, list (vector), hash table (an implementation of a map), dictionary, collection, stack, queue, and probably more. As array values can be other arrays, trees and multidimensional arrays are also possible.",
    "Explanation of those data structures is beyond the scope of this manual, but at least one example is provided for each of them. For more information, look towards the considerable literature that exists about this broad topic.",
    "As mentioned above, if no key is specified, the maximum of the existing integer indices is taken, and the new key will be that maximum value plus 1. If no integer indices exist yet, the key will be 0 (zero).",
    "PHP原本的簡稱為Personal Home Page，是Rasmus Lerdorf為了要維護個人網頁，而用c語言開發的一些CGI工具程式集，來取代原先使用的Perl程式。最初這些工具程式用來顯示Rasmus Lerdorf的個人履歷，以及統計網頁流量。他將這些程式和一些表單直譯器整合起來，稱為PHP/FI。PHP/FI可以和資料庫連接，產生簡單的動態網頁程式。",
    "PHP是一個應用範圍很廣的語言，特別是在網路程式開發方面。一般來說PHP大多在伺服器端執行，透過執行PHP的程式碼來產生網頁提供瀏覽器讀取，此外也可以用來開發命令列腳本程式和使用者端的GUI應用程式。PHP可以在許多的不同種的伺服器、作業系統、平台上執行，也可以和許多資料庫系統結合。使用PHP不需要任何費用，官方組織PHP Group提供了完整的程式原始碼，允許使用者修改、編譯、擴充來使用。");
?>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Practice 3</title>
</head>
<body>

<form action="" method="GET">
    關鍵字：
    <input type="text" name="Keyword" value="<?= $_GET['Keyword'] ?? "" ?>"/><input type="submit"/>
</form>

<?php
if (!empty($_GET['Keyword'])) {
    $Result = preg_grep('/' . $_GET['Keyword'] . '/i', $SearchEngineDB);
    echo implode('<br/><br/>', $Result);
}
?>

</body>
</html>
