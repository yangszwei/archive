#!/usr/bin/env bash

# 請做出shell script版的BMI程式碼 (bmi=(weight)/(height*height))

read -rp "Enter your height in meters: " height
read -rp "Enter your weight in kilograms: " weight

bmi=$(echo "scale=2; $weight / ($height * $height)" | bc)

echo "Your BMI is $bmi"

exit 0
