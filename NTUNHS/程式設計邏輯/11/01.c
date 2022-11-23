void insert(int array[], int size, int index, int value) {
    for (int i = size; i > index; --i) {
        array[i] = array[i - 1];
    }
    array[index] = value;
}

void remove(int array[], int size, int index) {
    for (int i = index; i < size; ++i) {
        array[i] = array[i + 1];
    }
}

void replace(int array[], int size, int index, int value) {
    array[index] = value;
}
