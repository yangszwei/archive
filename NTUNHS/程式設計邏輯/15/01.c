#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <time.h>

char *generate_password() {
    char *password = malloc(9 * sizeof(char));
    password[0] = '2';
    for (int i = 1; i <= 7; i += 3) {
        password[i] = rand() % 26 + 'a';
    }
    for (int i = 2; i <= 5; i += 3) {
        password[i] = rand() % 26 + 'A';
        password[i + 1] = rand() % 10 + '0';
    }
    password[8] = '\0';
    return password;
}

int main() {
    FILE *users, *template, *out;
    char username[6], password[9];
    char userIn[100], passIn[100];
    char filename[16], line[1024], *pos;
    int ok = 0;

    srand(time(NULL));

    /**
     * 1-1 產生 100 組帳號密碼並寫入 users.dat
     */
    if (!(users = fopen("users.dat", "w"))) {
        fputs("錯誤：無法開啟檔案 \"users.dat\"\n", stderr);
        return 1;
    }

    for (int i = 0; i < 100; ++i) {
        fprintf(users, "HN%03d %s\n", i + 1, generate_password());
    }

    fclose(users);

    /**
     * 1-2 判斷帳號密碼是否正確
     */
    if (!(users = fopen("users.dat", "r"))) {
        fputs("錯誤：無法開啟檔案 \"users.dat\"\n", stderr);
        return 1;
    }

    fputs("請輸入帳號：", stdout);
    scanf("%s", userIn);

    fputs("請輸入密碼：", stdout);
    scanf("%s", passIn);

    while (fscanf(users, "%s %s", username, password) != EOF) {
        if ((ok |= !strcmp(userIn, username) && !strcmp(passIn, password))) break;
    }

    puts(ok ? "登入成功" : "登入失敗");

    /**
     * 1-3 從 welcome.dat 產生 {username}.txt
     */
    if (!(template = fopen("welcome.dat", "r"))) {
        fputs("錯誤：無法開啟檔案 \"welcome.dat\"\n", stderr);
        fclose(users);
        return 1;
    }

    mkdir("users", 0777);

    ok = 1;
    rewind(users);

    while (fscanf(users, "%s %s", username, password) != EOF) {
        sprintf(filename, "users/%s.txt", username);
        if (!(ok = (out = fopen(filename, "w")) != NULL)) {
            fprintf(stderr, "錯誤：無法開啟檔案 \"%s\"\n", filename);
            break;
        }
        while (fgets(line, 1024, template) != NULL) {
            if ((pos = strstr(line, "<ID>"))) {
                sprintf(pos, "%s\n", username);
            } else if ((pos = strstr(line, "<PASSWORD>"))) {
                sprintf(pos, "%s\n", password);
            }
            fputs(line, out);
        }
        fclose(out);
        rewind(template);
    }

    fclose(users);
    fclose(template);

    return !ok;
}
