#include <stdio.h>

int main() {
    int number;

    for (;;) {
        fputs("請輸入一整數：", stdout);
        scanf("%d", &number);

        puts("===================");

        if (number == 0) {
            puts("程式結束");
            break;
        }

        int isPrime = 1;
        for (int i = 2; i < number; ++i) {
            if (number % i == 0) {
                isPrime = 0;
                break;
            }
        }

        printf(isPrime ? "%d為質數\n" : "%d非質數\n", number);
    }

    return 0;
}
