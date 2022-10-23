#include <stdio.h>

float calScore(int quiz, int midterm, int final) {
    return quiz * .15 + midterm * .35 + final * .5;
}

char calGrade(int score) {
    int upper = 100;
    while ((upper -= 10) >= 60) {
        if (score >= upper) {
            return "DCBA"[upper / 10 - 6];
        }
    }
    return 'F';
}

int main() {
    for (;;) {
        char name[10], grade, exit;
        int quiz, midterm, final;
        float score;

        fputs("輸入姓名: ", stdout);
        gets(name);
        fputs("輸入小考成績: ", stdout);
        scanf("%d", &quiz);
        fputs("輸入期中考成績: ", stdout);
        scanf("%d", &midterm);
        fputs("輸入期末考成績: ", stdout);
        scanf("%d", &final);

        score = calScore(quiz, midterm, final);
        grade = calGrade(score);

        puts("----------");

        printf("姓名: %s\n", name);
        printf("學期成績: %.1f\n", score);
        printf("成績等第: %c\n", grade);

        fputs("繼續輸入? (Y/n) ", stdout);
        scanf(" %c", &exit);

        if (exit == 'N' || exit == 'n') {
            break;
        }
    }

    return 0;
}
