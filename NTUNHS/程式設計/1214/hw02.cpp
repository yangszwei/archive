#include <iostream>

using namespace std;

char *reverse(char *input, size_t size) {
    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - i - 1; j++) {
            char tmp = *(input + j);
            *(input + j) = *(input + j + 1);
            *(input + j + 1) = tmp;
        }
    }
    input[size] = '\0';
    return input;
}

int main() {
    string input;
    cin >> input;
    cout << reverse(&input[0], input.size());
    return 0;
}
