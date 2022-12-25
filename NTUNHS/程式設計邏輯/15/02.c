#include <stdio.h>
#include <string.h>

int main() {
    FILE *fp;
    char query[1024], line[1024], tmp[1024];
    int count = 0;

    fputs("請輸入關鍵字：", stdout);
    scanf("%s", query);

    if (!(fp = fopen("search.dat", "r"))) {
        printf("無法開啟檔案 \"search.dat\"！");
        return 1;
    }

    puts("================================");

    while (count < 3 && fgets(line, 1024, fp) != NULL) {
        strcpy(tmp, line);
        if (strstr(strlwr(tmp), strlwr(query)) != NULL) {
            fputs(line, stdout);
            ++count;
        }
    }

    if (count == 0) {
        puts("找不到資料!!");
    }

    fclose(fp);

    return 0;
}
