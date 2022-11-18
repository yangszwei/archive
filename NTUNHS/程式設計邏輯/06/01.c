#include <stdio.h>

int main() {
    int number, sum = 0;

    for (;;) {
        fputs("請輸入一整數：", stdout);
        scanf("%d", &number);

        puts("=========================");

        if (number < 0) {
            puts("程式結束");
            break;
        }

        sum += number;

        printf("總和為%d\n", sum);
    }

    return 0;
}
