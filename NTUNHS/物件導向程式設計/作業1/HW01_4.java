public class HW01_4 {
    public static void main(String[] arg) {
        final int N = 20;
        System.out.printf("列出小於%s的質數%n", N);
        i:
        for (int i = 2; i < N; i++) {
            for (int j = 2; j < i; j++) {
                if (i % j == 0) continue i;
            }
            System.out.println(i);
        }
    }
}
