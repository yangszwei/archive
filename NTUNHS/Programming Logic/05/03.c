#include <stdio.h>
#include <ctype.h>

int main() {
    char grade;

    fputs("請輸入分數群組: ", stdout);
    scanf("%c", &grade);

    grade = toupper(grade);

    if (grade == 'A') {
        puts("分數在90分以上");
    } else if (grade == 'E') {
        puts("你被當掉了");
    } else if (grade > 'A' && grade < 'E') {
        printf("分數在%d~%d分之間", 90 - (grade - 'A') * 10, 99 - (grade - 'A') * 10);
    } else {
        puts("沒有此分數群組");
    }

    return 0;
}
