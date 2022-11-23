#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    int pt[4];

    srand(time(NULL));

    fputs("骰子點數為：", stdout);

    for (int i = 0; i < 4; ++i) {
        pt[i] = rand() % 6 + 1;
        printf("%d%s", pt[i], i == 3 ? "\n" : " ");
    }

    puts("=========================");

    for (int i = 0; i < 4 - 1; ++i) {
        for (int j = 0; j < 4 - i - 1; ++j) {
            if (pt[j] > pt[j + 1]) {
                int t = pt[j];
                pt[j] = pt[j + 1];
                pt[j + 1] = t;
            }
        }
    }

    if (pt[0] == pt[1] && pt[2] == pt[3]) {
        if (pt[0] == pt[2]) {
            printf("結果為一色(%d-%d-%d-%d)\n", pt[0], pt[1], pt[2], pt[3]);
        } else {
            puts("結果為洗拔辣");
        }
    } else if (pt[1] == pt[2]) {
        if (pt[0] == pt[1] || pt[2] == pt[3]) {
            puts("結果為不算");
        } else {
            printf("結果為%d點\n", pt[0] + pt[3]);
        }
    } else if (pt[0] == pt[1]) {
        printf("結果為%d點\n", pt[2] + pt[3]);
    } else if (pt[2] == pt[3]) {
        if (pt[0] + pt[1] == 3) {
            puts("結果為BG(3點)");
        } else {
            printf("結果為%d點\n", pt[0] + pt[1]);
        }
    } else {
        puts("結果為不算");
    }

    return 0;
}
