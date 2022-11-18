#include <stdio.h>

int indexOf(const int array[], int length, int value) {
    for (int i = 0; i < length; ++i) {
        if (array[i] == value) return i;
    }
    return -1;
}

int main() {
    int A[100], B, i, pos;

    for (i = 0; i < 100; ++i) {
        int number;
        printf("請輸入第%d個整數：", i + 1);
        scanf("%d", &number);
        if (number == -1) break;
        A[i] = number;
    }

    puts("======================");

    fputs("請輸入欲判斷的值：", stdout);
    scanf("%d", &B);

    puts("======================");

    pos = indexOf(A, i, B);

    if (pos > 0) {
        printf("%d為輸入的第%d個整數\n", B, pos + 1);
    } else {
        printf("%d不在剛剛輸入的值中\n", B);
    }

    return 0;
}
