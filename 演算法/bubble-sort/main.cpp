void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
}

void bubble_sort(int array[], int len) {
    for (int i = 0;i < len-1;i++) {
        for (int j = 0;j < len - i - 1;j++) {
            if (array[j] > array[j + 1]) {
                swap(&array[j], &array[j + 1]);
            }
        }
    }
}
