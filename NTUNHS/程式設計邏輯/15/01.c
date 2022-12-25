#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <time.h>

char *generate_password() {
    char *password = malloc(9 * sizeof(char));
    password[0] = '2';
    for (int i = 1; i < 8; i += 3) {
        password[i] = rand() % 26 + 'a';
    }
    for (int i = 2; i < 6; i += 3) {
        password[i] = rand() % 26 + 'A';
        password[i + 1] = rand() % 10 + '0';
    }
    password[8] = '\0';
    return password;
}

int main() {
    const int n = 100;
    char username[7], password[10];
    char user[6], pass[9];
    char name[16], line[1024], *pos;
    int isFound = 0;
    FILE *users, *welcome, *out;

    srand(time(NULL));

    // 1-1
    if (!(users = fopen("users.dat", "w"))) {
        puts("無法開啟檔案 \"users.dat\"!");
        return 1;
    }

    for (int i = 0; i < n; ++i) {
        fprintf(users, "HN%03d %s\n", i + 1, generate_password());
    }

    fclose(users);

    // 1-2
    if (!(users = fopen("users.dat", "r"))) {
        puts("Error opening file \"users.dat\"!");
        return 0;
    }

    fputs("請輸入帳號：", stdout);
    scanf("%s", user);

    fputs("請輸入密碼：", stdout);
    scanf("%s", pass);

    while (fscanf(users, "%s %s", username, password) != EOF) {
        if (strcmp(username, user) == 0 && strcmp(password, pass) == 0) {
            isFound = 1;
            break;
        }
    }

    puts(isFound ? "登入成功！" : "登入失敗！");

    // 1-3
    rewind(users);

    if (!(welcome = fopen("welcome.dat", "r"))) {
        puts("無法開啟檔案 \"welcome.txt\"!");
        return 1;
    }

    mkdir("users", 0777);

    while (fscanf(users, "%s %s", username, password) != EOF) {
        sprintf(name, "users/%s.txt", username);
        if (!(out = fopen(name, "w"))) {
            printf("無法開啟檔案 \"%s\"!", name);
            return 1;
        }
        while (fgets(line, 1024, welcome) != NULL) {
            if ((pos = strstr(line, "<ID>")) != NULL) {
                sprintf(pos, "%s\n", username);
            } else if ((pos = strstr(line, "<PASSWORD>")) != NULL) {
                sprintf(pos, "%s\n", password);
            }
            fputs(line, out);
        }
        rewind(welcome);
    }

    fclose(welcome);

    return 0;
}
