#include <stdio.h>

int main() {
    int cl, cia = 0;
    int isNormal = 0;
    float yrs;

    fputs("請輸入信用額度:", stdout);
    scanf("%d", &cl);

    fputs("繳款是否正常(1:正常,0:不正常):", stdout);
    scanf("%d", &isNormal);

    if (isNormal) {
        fputs("請輸入持卡年份:", stdout);
        scanf("%f", &yrs);

        if (yrs >= .5) {
            cia = cl / (yrs < 1 ? 20 : 10);
        }
    }

    puts("==============================");

    printf("預借現金金額為%d元", cia);

    return 0;
}
