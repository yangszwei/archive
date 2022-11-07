#include <stdio.h>

int main() {
    int number, min, max;
    double average = 0;

    for (int i = 0; ; ++i) {
        fputs("請輸入一整數：", stdout);
        scanf("%d", &number);

        puts("===================");

        if (number == 0) {
            puts("程式結束");
            break;
        }

        if (i == 0) min = max = number;
        if (number < min) min = number;
        if (number > max) max = number;
        average = (average * i + number) / (i + 1);

        printf("最小值：%d\t最大值：%d\t平均值：%.2f\n", min, max, average);
    }

    return 0;
}
