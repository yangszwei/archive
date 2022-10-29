#include <stdio.h>

int main() {
    for (int i = 0; i < 5; ++i) {
        for (int j = 0; j <= i; ++j) {
            fputs("*", stdout);
        }
        putchar('\n');
    }

    puts("");

    for (int i = 5; i > 0; --i) {
        for (int j = 0; j < i; ++j) {
            fputs("*", stdout);
        }
        putchar('\n');
    }

    puts("");

    for (int i = 0; i < 5; ++i) {
        for (int j = 4; j > i; --j) {
            fputs(" ", stdout);
        }
        for (int j = 0; j <= i * 2; ++j) {
            fputs("*", stdout);
        }
        putchar('\n');
    }
    return 0;
}
