#include <stdio.h>
#include <math.h>

int squareSum(int array[], int arraySize) {
    int sum = 0;
    for (int i = 0; i < arraySize; ++i) {
        sum += array[i] * array[i];
    }
    return sum;
}

int main() {
    int arraySize;

    fputs("input array size: ", stdout);
    scanf("%d", &arraySize);
    
    int array[arraySize];

    for (int i = 0; i < arraySize; ++i) {
        printf("input array[%d]: ", i);
        scanf("%d", &array[i]);
    }

    printf("sum of square of array: %d", squareSum(array, arraySize));

    return 0;
}
