#include <stdio.h>
#include <string.h>

int min(int a, int b) {
    return a < b ? a : b;
}

int main() {
    char Name[5][64], Phone[5][64];

    for (int i = 0;; ++i) {
        char cont;

        if (i > 4) {
            for (int j = 0; j < 4; ++j) {
                strcpy(Name[j], Name[j + 1]);
                strcpy(Phone[j], Phone[j + 1]);
            }
        }

        fputs("請輸入姓名：", stdout);
        scanf("%s", Name[min(i, 4)]);
        fputs("請輸入電話：", stdout);
        scanf("%s", Phone[min(i, 4)]);

        for (int j = 0; j <= min(i, 4); ++j) {
            printf("\033[38;5;201m%s %s\033[0m\n", Name[j], Phone[j]);
        }

        fputs("是否繼續輸入(Y/N)？", stdout);
        scanf(" %c", &cont);
        if (cont == 'N' || cont == 'n') break;
    }
}
