#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define FILENAME "result.txt"

int guess_number() {
    const int answer = rand() % 100 + 1;
    int count, number;
    for (count = 1;; ++count) {
        fputs("請輸入一整數(1~100)：", stdout);
        scanf("%d", &number);
        puts("=========================");
        if (number == answer) break;
        puts(number < answer ? "太小了喔" : "太大了喔");
    }
    printf("Bingo (您猜了%d次)\n", count);
    return count;
}

void read_result(char *name, int *min) {
    FILE *file;
    if (!(file = fopen(FILENAME, "r"))) {
        *min = -1;
        return;
    }
    if (fscanf(file, "%s %d", name, min) != 2) {
        *min = -1;
    }
    fclose(file);
}

void save_result(const char *name, int count) {
    FILE *file;
    if (!(file = fopen(FILENAME, "w+"))) {
        fprintf(stderr, "無法寫入檔案 \"%s\"。\n", FILENAME);
        return;
    }
    fprintf(file, "%s %d", name, count);
    fclose(file);
}

int main() {
    int count, min = -1;
    char *name = malloc(100);

    srand(time(NULL));

    count = guess_number();

    read_result(name, &min);

    if (count < min || min == -1) {
        fputs("您為最高分，請輸入您的姓名:", stdout);
        scanf(" %s", name);
        save_result(name, count);
    } else {
        printf("最高分: %s %d次", name, min);
    }

    free(name);

    return 0;
}
