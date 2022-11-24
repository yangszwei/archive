#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int main() {
    const char *suits[] = {"♠", "♥", "♦", "♣"};

    int cards[52], piles[4][13];

    for (int i = 0; i < 52; ++i) {
        cards[i] = i;
    }

    srand(time(NULL));

    for (int i = 0; i < 52; ++i) {
        int j = rand() % 52;
        int temp = cards[i];
        cards[i] = cards[j];
        cards[j] = temp;
        piles[i % 4][i / 4] = cards[i];
    }

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 13; ++j) {
            printf("%s%d", suits[piles[i][j] / 13], piles[i][j] % 13 + 1);
            fputs(j == 12 ? "\n" : " ", stdout);
        }
    }

    return 0;
}
