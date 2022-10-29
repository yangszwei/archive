#include <stdio.h>

int main() {
    int year, isLeapYear;

    fputs("請輸入西元年份: ", stdout);
    scanf("%d", &year);

    puts("======================");

    isLeapYear = year % 4 == 0 && year % 100 != 0 || year % 400 == 0;

    printf("%d%s閏年", year, isLeapYear ? "是" : "不是");

    return 0;
}
