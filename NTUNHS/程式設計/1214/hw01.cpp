#include <iostream>

using namespace std;

void swap(int *x, int *y);

void bubble_sort(int *p, size_t size);

void prettyPrint(int *p, size_t size);

int main() {
    int test_array[] = {3, 6, 23, -1, 54};
    size_t size = sizeof(test_array) / sizeof(int);

    bubble_sort(test_array, size);
    prettyPrint(test_array, size);

    return 0;
}

void swap(int *x, int *y) {
    int tmp = *x;
    *x = *y;
    *y = tmp;
}

void bubble_sort(int *p, size_t size) {
    bool done = true;
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            if (*(p + j) < *(p + j + 1)) {
                swap(p + j, p + j + 1);
                done = false;
            }
        }
        if (done) break;
    }
}

void prettyPrint(int *p, size_t size) {
    cout << "{";
    for (int i = 0; i < size - 1; i++) {
        cout << *(p + i) << ", ";
    }
    cout << *(p + size - 1) << "}" << endl;
}
