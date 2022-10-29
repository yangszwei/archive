#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    srand(time(NULL));

    const int answer = rand() % 100 + 1;
    int number;

    for (;;) {
        fputs("請輸入一整數(1~100)：", stdout);
        scanf("%d", &number);

        puts("=========================");

        if (number == answer) {
            puts("Bingo!");
            break;
        }

        puts(number < answer ? "太小了喔" : "太大了喔");
    }

    return 0;
}
