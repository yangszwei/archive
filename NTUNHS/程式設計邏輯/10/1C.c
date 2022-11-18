#include <stdio.h>

int main() {
    int A[100], i;

    for (i = 0; i < 100; ++i) {
        int number;
        printf("請輸入第%d個整數：", i + 1);
        scanf("%d", &number);
        if (number == -1) break;
        A[i] = number;
    }

    for (int j = 1; j < i; ++j) {
        int k = j - 1, l = A[j];
        while (k >= 0 && A[k] > l) {
            A[k + 1] = A[k], k--;
        }
        A[k + 1] = l;
    }

    puts("======================");

    for (int j = 0; j < i; ++j) {
        printf("%d%s", A[j], j < i - 1 ? " " : "\n");
    }

    return 0;
}
