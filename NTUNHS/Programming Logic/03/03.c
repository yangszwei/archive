#include <stdio.h>

int main() {
    int height;

    fputs("輸入身高：", stdout);
    scanf("%d", &height);

    if (height > 180) {
        puts("高人一等");
    } else if (height > 150) {
        puts("中等身高");
    } else {
        puts("有點矮喔");
    }

    return 0;
}
