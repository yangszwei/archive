public class Main {
  public static void main(String[] args) {
    if (args.length != 3) throw new IllegalArgumentException("args.length != 3");
    System.out.format("你的年齡是：%s\n你住在%s的%s\n", args[0], args[1], args[2]);
  }
}
