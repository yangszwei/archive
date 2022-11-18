#include <stdio.h>

int main() {
    int num;

    fputs("請輸入一正整數：", stdout);
    scanf("%d", &num);

    if (num % 2 == 0 || num % 3 == 0) {
        puts("此數為2或3的倍數");
    } else {
        puts("此數非為2或3的倍數");
    }

    return 0;
}
