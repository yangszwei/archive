public class Main {
    public static void main(String[] args) {
        int[] numbers = {13, 25, 39, 19, 9, 47, 10, 57, 58, 283, 43, 349};

        int min = numbers[0], max = numbers[0], sum = 0;
        for (int number : numbers) {
            if (number < min) min = number;
            if (number > max) max = number;
            sum += number;
        }

        System.out.println("最小值：" + min + " / 最大值：" + max + " / 總和：" + sum);
    }
}
