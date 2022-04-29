void insertion_sort(int array[], int len) {
    for (int i = 1;i < len;i++) {
        int tmp = array[i], j = i;
        while (j > 0 && array[j - 1] > tmp)
            array[j] = array[--j];
        array[j] = tmp;
    }
}
