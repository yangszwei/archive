#include <stdio.h>

int main() {
    int number;

    fputs("請輸入一整數：", stdout);
    scanf("%d", &number);

    puts("===================");

    for (int i = 1; i <= number; ++i) {
        if (number % i == 0) printf("%d ", i);
    }

    return 0;
}
