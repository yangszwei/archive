#include <stdio.h>

void read_file(const char *name) {
    FILE *file;
    char c;
    if ((file = fopen(name, "r")) == NULL) {
        printf("無法開啟檔案 \"%s\"！\n", name);
        return;
    }
    while ((c = fgetc(file)) != EOF) {
        putchar(c);
    }
    fclose(file);
}

void write_file(const char *name, const char *content) {
    FILE *file;
    if ((file = fopen(name, "w")) == NULL) {
        printf("無法開啟檔案 \"%s\"！\n", name);
        return;
    }
    fputs(content, file);
    fclose(file);
}

int main() {
    read_file("01.txt");

    write_file("01.out", "Hello, world!\n");

    return 0;
}
