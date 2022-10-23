#include <stdio.h>
#include <stdlib.h>
#include <time.h>

struct score {
    int id;
    int math;
    int english;
    int computer;
    float average;
    char grade;
};

int main() {
    srand(time(0));

    struct score scores[10];
    int min = 60, max = 100;

    for (int i = 0; i < 10; ++i) {
        scores[i].id = i + 1;
        scores[i].math = rand() % (max - min + 1) + min;
        scores[i].english = rand() % (max - min + 1) + min;
        scores[i].computer = rand() % (max - min + 1) + min;
        scores[i].average = (scores[i].math + scores[i].english + scores[i].computer) / 3.0;
        if (scores[i].average >= 90) {
            scores[i].grade = 'A';
        } else if (scores[i].average >= 80) {
            scores[i].grade = 'B';
        } else if (scores[i].average >= 70) {
            scores[i].grade = 'C';
        } else if (scores[i].average >= 60) {
            scores[i].grade = 'D';
        } else {
            scores[i].grade = 'F';
        }
    }

    puts("學生id\t數學\t\t英文\t\t電腦\t\t平均\t\t成績級別");

    for (int i = 0; i < 10; ++i) {
        printf("%d\t\t%d\t\t%d\t\t%d\t\t%.2f\t%c\n",
               scores[i].id,
               scores[i].math,
               scores[i].english,
               scores[i].computer,
               scores[i].average,
               scores[i].grade);
    }

    return 0;
}
