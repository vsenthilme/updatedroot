import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Fibanocci {
    public static void main(String[] args) {
        System.out.println("Enter the number for fibanocci: " );
        Scanner inp = new Scanner(System.in);
        int number = inp.nextInt();
//        int number = 29; // The number to check
        Stream.iterate(new long[] { 1, 1 }, p -> new long[] { p[1], p[0] + p[1] })
                .limit(number)
                .forEach(p -> System.out.println(p[0]));
        // Step 5: Print the result
    }
}