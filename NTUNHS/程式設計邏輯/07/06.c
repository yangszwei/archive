#include <stdio.h>
#include <stdbool.h>

int main() {
    int number;

    fputs("請輸入一整數：", stdout);
    scanf("%d", &number);

    puts("===================");

    fputs("小於30的質數有", stdout);

    for (int i = 2; i <= number; ++i) {
        bool isPrime = true;
        for (int j = 2; j < i; ++j) {
            if (i % j == 0) {
                isPrime = false;
                break;
            }
        }
        if (isPrime) {
            printf("%d ", i);
        }
    }

    return 0;
}
