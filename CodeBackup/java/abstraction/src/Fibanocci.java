import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Fibanocci {
    public static void main(String[] args) {
        System.out.println("Enter the number :");
        Scanner inp = new Scanner(System.in);
        int value = inp.nextInt();
//        Stream.iterate(new long[]{1,1},p -> new long[]{p[1],p[0]+p[1]}).limit(value).map(n->n[0]).forEach(System.out::println);
//        Stream.iterate(new long[]{1,1},p -> new long[]{p[1],p[0]+p[1]}).limit(value).forEach(n->System.out.println(n[0]));

        boolean isPrime = IntStream.rangeClosed(2, (int) Math.sqrt(value)).noneMatch(i -> value % i == 0);
        if(value < 2) {
            isPrime = false;
        }
        System.out.println(isPrime);
        Long fact = LongStream.rangeClosed(1,value).reduce(1,(a, b)->a*b);
        System.out.println(fact);
    }
}