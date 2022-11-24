#include <stdio.h>
#include <string.h>

int main() {
    char A[5][256], B[64];

    for (int i = 0; i < 5; ++i) {
        fputs("請輸入長字串：", stdout);
        scanf("%s", A[i]);
    }

    fputs("請輸入短字串：", stdout);
    scanf("%s", B);

    for (int i = 0; i < 5; ++i) {
        if (strstr(strlwr(A[i]), strlwr(B)) != NULL) {
            puts(A[i]);
            return 0;
        }
    }

    puts("沒有找到");

    return 1;
}
