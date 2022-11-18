#include <stdio.h>
#include <string.h>

int main() {
    char password[100], input[100];

    fputs("Input password: ", stdout);
    gets(password);

    for (int i = 0; i < 3; ++i) {
        printf("Input password (%d/3): ", i + 1);
        gets(input);

        if (strcmp(password, input) == 0) {
            puts("Success!");
            return 0;
        }
    }

    puts("Max attempts reached.");

    return 1;
}
