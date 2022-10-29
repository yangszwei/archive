#include <stdio.h>

int main() {
    int number, result = 1;

    fputs("請輸入一整數：", stdout);
    scanf("%d", &number);

    puts("=========================");

    for (int i = number; i > 0; --i) {
        result *= i;
    }

    printf("%d! = %d\n", number, result);

    return 0;
}
