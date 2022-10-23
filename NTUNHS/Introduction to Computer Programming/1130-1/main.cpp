#include <iostream>
#include <iomanip>

using namespace std;

int main() {
    int a = 5;
    int *pA = &a;
    cout << "a = " << a << endl;
    cout << "&a = " << pA << endl;
    cout << "a = " << *pA << endl;
}

int _main() {
    int a = 2, b = 3;
    const float PI = 3.1415926;

    cout << "Hello, World!" << endl;
    cout << "a + b = " << a + b << endl;
    cout << "PI = " << setprecision(12) << PI << endl;

    float d, e;
    cout << "Input d: ";
    fflush(stdout);
    cin >> d;
    cout << "Input e: ";
    fflush(stdout);
    cin >> e;
    cout << "d * e = " << d * e << endl;

    return 0;
}
