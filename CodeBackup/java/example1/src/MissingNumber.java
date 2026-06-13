import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MissingNumber {
    public static void main(String[] args) {
//        Scanner number = new Scanner(System.in);
//        System.out.println("Enter the number range to print fz bz: " );
//        Long inputNumber = number.nextLong();
        int [] a = {5,3,10,8,4,2,9,6};
        Arrays.sort(a);
//        List<Integer> integerList = Arrays.stream(a).boxed().sorted().collect(Collectors.toList());
        System.out.println(a[0]);
//        System.out.println(integerList.get(0));
        int out = getMissingNumber(a, a[0], a[a.length - 1]);
        System.out.println(out);
    }
    private static int getMissingNumber(int[] a, int min, int max) {
        return IntStream.rangeClosed(min, max).sum() - Arrays.stream(a).sum();
    }
}
