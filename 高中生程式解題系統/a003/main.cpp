#include <iostream>

using namespace std;

int main() {
    int m, d;
    cin >> m >> d;
    int s = (m * 2 + d) % 3;
    cout << ((s == 2) ? "大吉" : (s == 1) ? "吉" : "普通") << endl;
}