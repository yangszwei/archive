#include <stdio.h>

int main() {
    int price, pcs;
    double sum;

    fputs("每張入場卷的價格(元): ", stdout);
    scanf("%d", &price);
    fputs("請輸入顧客欲購買的張數:", stdout);
    scanf("%d", &pcs);

    puts("==========================");

    sum = price * pcs;
    if (pcs > 10) {
        puts("購買10張以上打九折");
        sum *= .9;
    }

    printf("總價為%d元\n", (int) sum);

    return 0;
}
