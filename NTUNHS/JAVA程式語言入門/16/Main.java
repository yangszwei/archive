import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Scanner;

class Scores {
    static public void printTitles() {
        System.out.println("姓名\t國文成績 英文成績 數學成績 總分   平均");
    }

    public String name;
    public int[] scores;

    public Scores(String[] tokens) {
        this.name = tokens[0];
        this.scores = Arrays.stream(Arrays.copyOfRange(tokens, 1, 4))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public int sum() {
        return Arrays.stream(scores).sum();
    }

    public int average() {
        return sum() / scores.length;
    }

    /** This returns the name & scores in CSV format. */
    public String toCSV() {
        return name + "," + sum() + "," + average() + "\n";
    }

    /** This prints the name & scores for program output. */
    public void prettyPrint() {
        System.out.printf("%-8s%-9d%-9d%-9d%-7d%d\n", name, scores[0], scores[1], scores[2], sum(), average());
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        Scores.printTitles();

        Scanner sc = new Scanner(new File("student.csv"));
        FileWriter fw = new FileWriter("result.txt");

        while (sc.hasNextLine()) {
            String[] tokens = sc.nextLine().split(",");
            Scores scores = new Scores(tokens);
            scores.prettyPrint();
            fw.write(scores.toCSV());
        }

        sc.close();
        fw.close();
    }
}
