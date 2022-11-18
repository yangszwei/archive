#include <stdio.h>

int main() {
    char op = 0, ch;
    int left = 0, right = 0;

    while (1) {
        ch = getche();
        if (ch >= '0' && ch <= '9') {
            right = right * 10 + ch - '0';
        } else {
            if (op == '+') {
                left += right;
            } else if (op == '-') {
                left -= right;
            } else if (op == '*') {
                left *= right;
            } else if (op == '/') {
                left /= right;
            } else if (op == 0) {
                left = right;
            }
            if (ch == '=') {
                break;
            } else {
                op = ch;
                right = 0;
            }
        }
    }

    printf("%d\n", left);

    return 0;
}
