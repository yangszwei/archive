#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    int pt[3];

    srand(time(NULL));

    fputs("骰子點數為：", stdout);

    for (int i = 0; i < 3; ++i) {
        pt[i] = rand() % 6 + 1;
        printf("%d%s", pt[i], i == 2 ? "\n" : " ");
    }

    puts("=========================");

    for (int i = 0; i < 3 - 1; ++i) {
        for (int j = 0; j < 3 - i - 1; ++j) {
            if (pt[j] > pt[j + 1]) {
                int t = pt[j];
                pt[j] = pt[j + 1];
                pt[j + 1] = t;
            }
        }
    }

    if (pt[0] == 1 && pt[1] == 2 && pt[2] == 3) {
        puts("結果為1-2-3");
    } else if (pt[0] == 4 && pt[1] == 5 && pt[2] == 6) {
        puts("結果為4-5-6");
    } else if (pt[0] == pt[1] && pt[1] == pt[2]) {
        printf("結果為豹子(%d-%d-%d)\n", pt[0], pt[1], pt[2]);
    } else if (pt[0] == pt[1]) {
        printf("結果為%d點\n", pt[2]);
    } else if (pt[1] == pt[2]) {
        printf("結果為%d點\n", pt[0]);
    } else {
        puts("結果為不算");
    }

    return 0;
}
