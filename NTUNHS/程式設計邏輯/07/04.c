#include <stdio.h>

int main() {
    int number, result = 1;

    fputs("請輸入一整數：", stdout);
    scanf("%d", &number);

    puts("===================");

    for (int i = 1; i <= number; ++i, result = 1) {
        for (int j = i; j > 0; --j) {
            result *= j;
        }
        printf("%d!=%d\n", i, result);
    }

    return 0;
}
