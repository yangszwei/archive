#include <stdio.h>
#include <math.h>

double f(double x) {
    return (x >= 2) ? sqrt(5 * x - 1) : fabs(2 * x);
}

int main() {
    double n;

    fputs("input number: ", stdout);
    scanf("%lf", &n);

    printf("f(%lf) = %lf", n, f(n));

    return 0;
}
