#include <stdio.h>

int main() {
    int amount, discount = 0;

    fputs("客戶購物金額(元): ", stdout);
    scanf("%d", &amount);

    puts("==========================");

    if (amount >= 100000) {
        discount = 20;
    } else if (amount >= 30000) {
        discount = 15;
    } else if (amount >= 5000) {
        discount = 10;
    } else if (amount >= 1000) {
        discount = 5;
    }

    amount -= (double) amount * discount / 100;

    printf("折扣為%d%%\n", discount);
    printf("實付為%d元\n", amount);

    return 0;
}
