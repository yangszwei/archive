#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

int main() {
    const char *suits[] = {"♠", "♥", "♦", "♣"};

    int cards[52], piles[4][13];

    for (int i = 0; i < 52; ++i) {
        cards[i] = i;
    }

    srand(time(NULL));

    // Shuffle cards
    for (int i = 0; i < 52; ++i) {
        swap(&cards[i], &cards[rand() % 52]);
    }

    for (int i = 0; i < 52; ++i) {
        piles[i % 4][i / 4] = cards[i];
    }

    for (int i = 0; i < 4; ++i) {
        for (int j = 0; j < 13; ++j) {
            if (j > 0) fputs(" ", stdout);
            printf("%s%d", suits[piles[i][j] / 13], piles[i][j] % 13 + 1);
        }
        fputs("\n", stdout);
    }

    return 0;
}
