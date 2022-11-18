#include <stdio.h>

void hello() {
    puts("Hello, world!");
}

int main() {
    for (int i = 0; i < 3; ++i) {
        hello();
    }
    return 0;
}
