#include <iostream>

using namespace std;

int main() {
    int y;
    while (cin >> y) {
        cout << (((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) ? "閏年" : "平年") << endl;
    }
}