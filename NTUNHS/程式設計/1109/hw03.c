#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    srand(time(0));

    printf("%-10s\t%-10s\t%-10s\t%-10s\t%-10s\n", "學生id", "科目1", "科目2", "科目3", "平均");

    float table[10][4];
    for (int i = 0; i < 10; i++) {
        printf("%-6d\t", i + 1);
        float sum = 0;
        for (int j = 0; j < 3; j++) {
            int n = rand() % 41 + 60;
            table[i][j] = n;
            sum += table[i][j];
            printf("%-5d\t", n);
        }
        table[i][3] = sum / 3;
        printf("%.2f\n", table[i][3]);
    }

    return 0;
}
