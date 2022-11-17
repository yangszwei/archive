#include <stdio.h>
#include <stdlib.h>

int main() {
    const char *SUBJECTS[3] = {"國文", "英文", "數學"};
    int *scores[3] = {
            malloc(sizeof(int) * 5),
            malloc(sizeof(int) * 5),
            malloc(sizeof(int) * 5)
    };

    for (int i = 0; i < 3; ++i) {
        scores[i][0] = i + 1;
        for (int j = 1; j < 4; ++j) {
            printf("請輸入第%d位學生%s科成績：", i + 1, SUBJECTS[j - 1]);
            scanf("%d", &scores[i][j]);
        }
        scores[i][4] = scores[i][1] + scores[i][2] + scores[i][3];
    }

    for (int i = 1; i < 3; ++i) {
        int j = i - 1, *k = scores[i];
        while (j >= 0 && scores[j][4] < *(k + 4)) {
            scores[j + 1] = scores[j], j--;
        }
        scores[j + 1] = k;
    }

    puts("======================");

    for (int i = 0; i < 3; ++i) {
        printf("%d. 第%d位同學：", i + 1, scores[i][0]);
        for (int j = 1; j < 4; ++j) {
            printf("%s%d分，", SUBJECTS[i], scores[i][j]);
        }
        printf("總分%d分，平均%.2lf分\n", scores[i][4], scores[i][4] / 3.0);
    }

    return 0;
}
