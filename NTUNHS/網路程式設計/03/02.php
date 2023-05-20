<form action="02.php" method="get">
    <label>
        <span>Weight</span>
        <input name="weight" type="number">
    </label>
    <label>
        <span>Height</span>
        <input name="height" type="number">
    </label>
    <button type="submit" style="width: 2.5rem; font-size: 1.5rem;">⤶</button>
</form>

<?php

if (isset($_GET['weight']) && isset($_GET['height'])) {
    $weight = $_GET['weight'];
    $height = $_GET['height'];
    $bmi = number_format($weight / ($height * $height) * 10000, 2);
    echo "<p>BMI = $bmi</p>";

    if ($bmi < 18.5) {
        echo "<p>體重過輕</p>";
    } else if ($bmi >= 23) {
        echo "<p>體重過重</p>";
    } else {
        echo "<p>正常體位</p>";
    }
}

?>

<style>
    body {
        display: flex;
        align-items: center;
        justify-content: center;
        flex-direction: column;
        height: 100vh;
    }

    form {
        justify-content: center;
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 1rem;
    }

    label {
        font-size: 1.25rem;
    }

    input {
        font-size: 1.25rem;
        margin-left: 0.5rem;
        line-height: 1.5em;
    }

    p {
        text-align: center;
    }
</style>
