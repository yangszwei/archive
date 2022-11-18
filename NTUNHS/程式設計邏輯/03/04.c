#include <stdio.h>

int main() {
    int i, n, sum = 0, sum1 = 0;

    fputs("輸入一個整數：", stdout);
    scanf("%d", &n);

    if (n > 100) return -1;

    /** For-loop implementation */
    for (i = 1; i <= n; ++i) {
        sum += i * i;
    }

    /** While-loop implementation */
    i = 0;
    while (++i <= n) {
        sum1 += i * i;
    }

    printf("f(%d) = %d\n", n, sum);
    printf("w(%d) = %d\n", n, sum1);

    return 0;
}
