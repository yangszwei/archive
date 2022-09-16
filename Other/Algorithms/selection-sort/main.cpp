void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
}

void selection_sort(int array[], int len) {
    for (int i = 0;i < len - 1;i++) {
        int min = i; // index of minimum
        for (int j = i + 1;j < len;j++)
            if (array[j] < array[min]) min = j;
        swap(&array[i], &array[min]);
    }
}
