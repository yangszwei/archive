#include <stdio.h>

int main() {
    int WrongQ, Score = 100;
    float Question = 2.5, Result;
    char Level = 'A', Name[16];

    fputs("輸入姓名：", stdout);
    gets(Name);

    fputs("輸入錯誤題數：", stdout);
    scanf("%d", &WrongQ);

    Result = Score - WrongQ * Question;

    printf(
            "學生姓名：%s\n"
            "成績(整數)：%d\n"
            "成績(浮點數)：%f\n"
            "等級：%c\n",
            Name, (int) Result, Result, Level
    );

    return 0;
}
