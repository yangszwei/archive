#include <stdio.h>
#include <string.h>

struct User {
    char id[16];
    char password[16];
};

int main() {
    struct User users[100], user;

    for (int i = 0; i < 100; ++i) {
        sprintf(users[i].id, "User%d", i);
        strcpy(users[i].password, "Correct");
    }

    fputs("請輸入帳號：", stdout);
    scanf("%s", user.id);
    fputs("請輸入密碼：", stdout);
    scanf("%s", user.password);

    for (int i = 0; i < 100; ++i) {
        if (strcmp(strlwr(user.id), strlwr(users[i].id)) == 0) {
            if (strcmp(user.password, users[i].password) == 0) {
                puts("帳號密碼正確");
                return 0;
            }
            puts("密碼錯誤");
            return 1;
        }
    }

    puts("無此帳號");

    return 1;
}
