#include <stdio.h>

#define ANSWER 77

int main() {
    int guess;

    fputs("請猜一樂透號碼: ", stdout);
    scanf("%d", &guess);

    puts("======================");

    if (guess == ANSWER) {
        puts("恭喜！您猜對了。");
    } else {
        printf("貢龜！樂透號碼為%d\n", ANSWER);
    }

    return 0;
}
