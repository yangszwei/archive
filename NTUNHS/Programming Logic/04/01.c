#include <stdio.h>

int main() {
    int hours, fee = 0;

    puts("停車超過一小時，每小時收費40元");
    fputs("請輸入停車幾小時: ", stdout);
    scanf("%d", &hours);

    if (hours > 1) fee = 40 * hours;

    printf("停車%d小時，總費用為:%d元\n", hours, fee);

    return 0;
}
