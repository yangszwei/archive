#include <iostream>
#include <cmath>

using namespace std;

int main() {
    int a, b, c;
    cin >> a >> b >> c;
    int d = pow(b, 2) - 4 * a * c;
    if (d > 0) {
        int s = sqrt(d);
        int x1 = (-b + s) / (a * 2),
            x2 = (-b - s) / (a * 2);
        cout << "Two different roots x1=" << x1 << " , x2=" << x2 << endl;
    } else if (d == 0) {
        cout << "Two same roots x=" << -b / (a * 2) << endl;
    } else {
        cout << "No real root";
    }
}