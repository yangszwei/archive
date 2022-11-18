#include <stdio.h>

int main() {
    for (int i = 1; i <= 9; ++i) {
        for (int j = 2; j <= 9; j += 2) {
            printf("%d * %d = %d\t", j, i, j * i);
        }
        putchar('\n');
    }
    return 0;
}
