#include <stdio.h>

int main() {
    int number;

    fputs("請輸入一整數：", stdout);
    scanf("%d", &number);

    puts("===================");

    while (number != 0) {
        putchar(number % 10 + '0');
        number /= 10;
    }

    return 0;
}
