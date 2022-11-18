#include <stdio.h>
#include <sys/unistd.h>

#define M 1234

int main() {
    int N;

    for (int i = 0; i < 6; ++i) {
        if (i > 3) sleep(5);

        fputs("請輸入密碼：", stdout);
        scanf("%d", &N);

        puts("=========================");

        if (N == M) {
            puts("密碼正確");
            return 0;
        } else if (i < 5) {
            puts("密碼錯誤");
        }
    }

    puts("錯誤次數過多");
    return 1;
}
