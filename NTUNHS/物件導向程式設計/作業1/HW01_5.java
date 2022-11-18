public class HW01_5 {
    public static void main(String[] arg) {
        final int[] array = {23, 33, 43, 53, 63, 73};

        System.out.print("相反順序輸出：");
        int sum = 0;
        for (int i = array.length - 1; i >= 0; i--) {
            System.out.printf(array[i] + (i > 0 ? " " : "%n"));
            sum += array[i];
        }

        System.out.println("總和 = " + sum);
        System.out.println("平均 =   " + (double) sum / array.length);
    }
}
