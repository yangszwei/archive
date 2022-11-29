#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void swap(int *a, int *b) {
    int temp = *a;
    *a = *b;
    *b = temp;
}

void sort(int *arr, int n) {
    for (int i = 0; i < n; ++i) {
        for (int j = i + 1; j < n; ++j) {
            if (arr[i] > arr[j]) {
                swap(&arr[i], &arr[j]);
            }
        }
    }
}

int main() {
    const char *suits[] = {"♠", "♥", "♦", "♣"};
    const char *ranks[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

    int cards[52], piles[4][13];
    char *card = malloc(5);

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
        sort(piles[i], 13);

        for (int j = 0; j < 13;++j) {
            if (j > 0) fputs(" ", stdout);
            strcpy(card, suits[piles[i][j] / 13]);
            strcat(card, ranks[piles[i][j] % 13]);
            fputs(card, stdout);
        }

        fputs("\n", stdout);
    }

    free(card);

    return 0;
}
