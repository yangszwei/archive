#include <stdio.h>

int main() {
    double topline, baseline, height, area;

    fputs("請輸入梯形的上底: ", stdout);
    scanf("%lf", &topline);
    fputs("請輸入梯形的下底: ", stdout);
    scanf("%lf", &baseline);
    fputs("請輸入梯形的高: ", stdout);
    scanf("%lf", &height);

    area = (topline + baseline) * height / 2;

    printf("梯形面積=%.2lf\n", area);

    return 0;
}
