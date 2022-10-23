#include <iostream>
#include <fstream>

using namespace std;

const string BASE_DIR = R"(D:\)";

int main() {
    ifstream fin(BASE_DIR + "in.txt");
    if (!fin) {
        cerr << R"(Cannot open file "in.txt"!)";
        return 1;
    }

    ofstream fout(BASE_DIR + "out.txt");
    if (!fout) {
        cerr << R"(Cannot open file "out.txt"!)";
        fin.close();
        return 1;
    }

    while (!fin.eof()) {
        string tmp;
        fin >> tmp;
        int num = atoi(tmp.c_str());
        fout << num * num << endl;
    }

    fin.close();
    fout.close();
    return 0;
}
