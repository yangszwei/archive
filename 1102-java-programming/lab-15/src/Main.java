import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        StringBuilder sb = new StringBuilder(scanner.nextLine());
        sb.reverse();

        for (char c : sb.toString().toCharArray())
            System.out.print((char)(c + 1));
    }
}
