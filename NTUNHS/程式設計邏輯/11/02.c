#include <string.h>
#include <stdio.h>
#include <stdlib.h>

char *insert_o(char *string) {
    int length = strlen(string);
    char *result = malloc(length);
    strcpy(result, string);
    for (int i = 0; i < length; ++i) {
        if (string[i] == 'o') {
            for (int j = length; j > i + 1; --j) {
                result[j] = result[j - 1];
            }
            result[i + 1] = 'o';
            ++i, ++length;
        }
    }
    result[length] = '\0';
    return result;
}

char *insert_00(char *string) {
    int length = strlen(string);
    char *result = malloc(length);
    strcpy(result, string);
    for (int i = 0; i < length; ++i) {
        if (result[i] == '1') {
            for (int j = length + 1; j > i + 2; --j) {
                result[j] = result[j - 2];
            }
            result[i + 1] = result[i + 2] = '0';
            i += 2, length += 2;
        }
    }
    result[length] = '\0';
    return result;
}

char *remove_digits(char *string) {
    int length = strlen(string);
    char *result = malloc(length);
    strcpy(result, string);
    for (int i = 0; i < length; ++i) {
        if (result[i] >= '0' && result[i] <= '9') {
            for (int j = i; j < length; ++j) {
                result[j] = result[j + 1];
            }
            --i, --length;
        }
    }
    result[length] = '\0';
    return result;
}

char *remove_ch(char *string) {
    int length = strlen(string);
    char *result = malloc(length);
    strcpy(result, string);
    for (int i = 0; i < length; ++i) {
        if (result[i] == 'c' && result[i + 1] == 'h') {
            for (int j = i; j < length; ++j) {
                result[j] = result[j + 2];
            }
            --i, length -= 2;
        }
    }
    result[length] = '\0';
    return result;
}

char *replace_digits(char *string) {
    int length = strlen(string);
    char *result = malloc(length);
    strcpy(result, string);
    for (int i = 0; i < length; ++i) {
        if (result[i] >= '0' && result[i] <= '9') {
            result[i] = '?';
        }
    }
    result[length] = '\0';
    return result;
}

int main() {
    puts(insert_o("god"));
    puts(insert_00("12315"));
    puts(remove_digits("1a2b3c4d"));
    puts(remove_ch("churches"));
    puts(replace_digits("abc123def"));
    return 0;
}
