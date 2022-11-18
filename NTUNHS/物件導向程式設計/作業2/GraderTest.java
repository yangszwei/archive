public class GraderTest {
    public static void main(String[] args) {

        boolean failed = false;

        // (1) Test Grader.grade() against -200-200.
        for (int i = -200; i <= 200; i++) {
            String score = String.valueOf(i);
            String grade = Grader.grade(score);
            if (
                    ((i < 0 || i > 100) && !grade.equals(Grader.INVALID)) ||
                            (i >= 90 && i <= 100 && !grade.equals("A")) ||
                            (i >= 80 && i <= 89 && !grade.equals("B")) ||
                            (i >= 70 && i <= 79 && !grade.equals("C")) ||
                            (i >= 60 && i <= 69 && !grade.equals("D")) ||
                            (i >= 0 && i <= 59 && !grade.equals("F"))
            ) {
                System.err.printf("Grader.grade(\"%s\") -> \"%s\"%n", score, grade);
                failed = true;
            }
        }

        // (2) Test Grader.grade() against the alphabet (A-Z, a-z).
        for (char i = 'A'; i <= 'z'; i++) {
            if (i > 'Z' && i < 'a') continue;
            String score = String.valueOf(i);
            String grade = Grader.grade(score);
            if (!grade.equals(Grader.INVALID)) {
                System.err.printf("Grader.grade(\"%s\") -> \"%s\"%n", score, grade);
                failed = true;
            }
        }

        if (failed) {
            System.err.println("測試失敗");
            return;
        }

        System.out.println("測試成功");
    }
}
