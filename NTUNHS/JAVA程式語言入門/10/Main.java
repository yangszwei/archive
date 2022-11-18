import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    int n = scanner.nextInt();

    System.out.println("(a)");
    a(n);

    System.out.println("(b)");
    b(n);

    System.out.println("(c)");
    c(n);
  }

  private static void a(int n) {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j <= i; j++) System.out.print("*");
      System.out.println();
    }
  }

  private static void b(int n) {
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) System.out.print(j < i ? " " : "*");
      System.out.println();
    }
  }

  private static void c(int n) {
    for (int i = 0, k = 1; i < n; i++) {
      for (int j = 0; j <= i; j++) System.out.printf("%-2d ", k++);
      System.out.println();
    }
  }
}
