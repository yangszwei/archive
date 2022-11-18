void merge(int array[], int left, int mid, int right) {
    int lenL = mid - left + 1,
        lenR = right - mid;
    int arrayL[lenL], arrayR[lenR];
    for (int i = 0; i < lenL; i++) arrayL[i] = array[left + i];
    for (int i = 0; i < lenR; i++) arrayR[i] = array[mid + 1 + i];
    int i = 0, j = 0, k = left;
    while (i < lenL && j < lenR)
        array[k++] = (arrayL[i] <= arrayR[j]) ? arrayL[i++] : arrayR[j++];
    while (i < lenL) array[k++] = arrayL[i++];
    while (j < lenR) array[k++] = arrayR[j++];
}

void merge_sort(int array[],int left,int right) {
    if (left >= right) return;
    int mid = left + (right - left) / 2;
    merge_sort(array, left, mid);
    merge_sort(array, mid + 1, right);
    merge(array, left, mid, right);
}
