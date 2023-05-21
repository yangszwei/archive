#!/usr/bin/env bash

# 1. 請做出shell script版的BMI程式碼 (bmi=(weight)/(height*height))

read -rp "Enter your height in meters: " height
read -rp "Enter your weight in kilograms: " weight

bmi=$(echo "scale=2; $weight / ($height * $height)" | bc)

echo "Your BMI is $bmi"

exit 0

# 2.請根據教材的案例二，完成以下需求

# 一. 請在tmp3資料夾底下random產生10,000個資料夾 (random是指隨機數字)

for _ in {1..10000}; do
    mkdir -p "tmp3/$(cat /dev/urandom | tr -dc '0-9' | fold -w 10 | head -n 1)"
done

# 二. 請將tmp3底下的亂數資料夾分類，1開頭的資料夾放到tmp4/1/底下，2開頭的資料夾放到tmp4/2/底下，依此類推

for dir in tmp3/*; do
    if [[ -d "$dir" ]]; then
        first_digit=$(echo "$dir" | cut -d '/' -f 2 | cut -c 1)
        mkdir -p "tmp4/$first_digit"
        mv "$dir" "tmp4/$first_digit"
    fi
done
