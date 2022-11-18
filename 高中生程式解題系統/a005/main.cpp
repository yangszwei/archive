#include <iostream>

using namespace std;

int main() {
    int n;
    cin >> n;
    int data[n][5];
    for (int y = 0;y < n;y++) {
        for (int x = 0; x < 4;x++) {
            cin >> data[y][x];
        }
    }
    for (int y = 0;y < n;y++) {
        for (int x = 0;x < 4;x++) {
            cout << data[y][x] << " ";
        }
        if (data[y][2] - data[y][1] == data[y][1] - data[y][0]) {
            cout << data[y][3] + data[y][1] - data[y][0] << endl;
        } else {
            cout << data[y][3] * data[y][1] / data[y][0] << endl;
        }
    }
    return 0;
}