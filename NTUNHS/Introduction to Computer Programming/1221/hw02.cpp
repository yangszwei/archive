#include <iostream>
#include <cmath> // required in clang toolchain

using namespace std;

class Triangle {
private:
    float sides[3] = {};

    void validate() {
        for (int i = 0; i < 3; i++) {
            if (sides[i] <= 0 || sides[i] + sides[(i + 1) % 3] <= sides[(i + 2) % 3]) {
                cerr << "Invalid triangle.";
                exit(1);
            }
        }
    }

public:
    void input() {
        cout << "Input length for the 3 sides of the triangle: ";
        for (float &side: sides) {
            cin >> side;
        }

        validate();
    }

    float calPerimeter() {
        float perimeter = 0;
        for (float side: sides) {
            perimeter += side;
        }

        return perimeter;
    }

    float calArea() {
        float s = calPerimeter() / 2;
        float area = s;
        for (float side: sides) {
            area *= (s - side);
        }
        area = sqrt(area);

        return area;
    }
};

int main() {
    Triangle triangle{};
    triangle.input();
    cout << "-> Perimeter is " << triangle.calPerimeter() << "." << endl;
    cout << "-> Area is " << triangle.calArea() << "." << endl;

    return 0;
}
