#include <stdio.h>

int main() {
    const char *SUBJECTS[3] = {"國文", "英文", "數學"};
    int scores[3][3], N;

    for (int i = 0; i < 3; ++i) {
        for (int j = 0; j < 3; ++j) {
            printf("請輸入第%d位學生%s科成績：", i + 1, SUBJECTS[j]);
            scanf("%d", &scores[i][j]);
        }
    }

    puts("======================");

    fputs("請輸入欲判斷的同學：", stdout);
    scanf("%d", &N);

    puts("======================");

    printf("第%d位同學的成績為：", N);

    for (int i = 0; i < 3; ++i) {
        printf("%s%d分%s", SUBJECTS[i], scores[N - 1][i], i < 2 ? "，" : "\n");
    }

    return 0;
}
