#include <iostream>
#include <fstream>

#define MAXIMUM_INPUT 10

using namespace std;

class ScoreCalculator {
private:
    int size = 0;
    int scores[MAXIMUM_INPUT] = {};
    float average = 0;

public:
    void read(const string &Filename) {
        ifstream fin(Filename);
        if (!fin) {
            cerr << "Failed to open file \"" << Filename << "\"!" << endl;
            exit(1);
        }
        for (int &i: scores) {
            if (fin.eof()) break;
            fin >> i, size++;
        }
        if (!fin.eof()) {
            cerr << "[warning] Maximum input exceeded but haven't reached EOF!" << endl;
        }
    }

    void calScore() {
        float sum = 0;
        for (int i: scores) {
            sum += (float) i;
        }
        average = sum / (float) size;
    }

    void printReport() const {
        cout << "The average score is " << average << "." << endl;
    }
};

int main() {
    ScoreCalculator scoreCalc{};
    scoreCalc.read(R"(.\file_score.txt)");
    scoreCalc.calScore();
    scoreCalc.printReport();

    return 0;
}
