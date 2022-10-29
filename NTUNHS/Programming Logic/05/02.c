#include <stdio.h>

int main() {
    float weight, height, bmi;
    char *category;

    fputs("請輸入體重(Kg): ", stdout);
    scanf("%f", &weight);
    fputs("請輸入身高(Cm): ", stdout);
    scanf("%f", &height);

    bmi = weight / (height / 100 * height / 100);

    if (bmi < 18.5) {
        category = "體重過輕";
    } else if (bmi >= 23) {
        category = "體重過重";
    } else {
        category = "正常體位";
    }

    printf("BMI=%.2f (%s)", bmi, category);

    return 0;
}
