#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main() {
    int a, b;

    srand(time(NULL));
    a = rand() % 6 + 1;
    b = rand() % 6 + 1;

    printf("骰子點數為：%d %d\n", a, b);

    puts("=========================");

    printf("總和為%d\n", a + b);

    return 0;
}
