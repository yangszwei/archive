#include <stdio.h>

int main() {
    int num, pow;

    fputs("請輸入所要計算的數值：", stdout);
    scanf("%d", &num);
    fputs("請輸入1(計算平方值)或2(計算立方值):", stdout);
    scanf("%d", &pow);

    for (int i = pow, j = num; i > 0; i--) num *= j;

    printf("%s為：%d\n", (pow == 1 ? "平方值" : "立方值"), num);

    return 0;
}
