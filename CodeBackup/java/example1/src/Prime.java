import java.util.Scanner;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class Prime {
    public static void main (String[] args){
        System.out.println("Enter the Number: ");
        Scanner inp = new Scanner(System.in);
        int number = inp.nextInt();
//        int sqtNumber = (int) Math.sqrt(number);
//        System.out.println("Entered Number: " + number);
//        System.out.println("Entered Number sqrt: " + sqtNumber);
//        IntStream.range(2,sqtNumber).forEach(System.out::println);
//        IntStream.rangeClosed(2,sqtNumber).forEach(System.out::println);
        boolean prime = IntStream.rangeClosed(2, (int) Math.sqrt(number)).noneMatch(i -> number % i == 0);
        boolean prime2 = !IntStream.rangeClosed(2, (int) Math.sqrt(number)).allMatch(i -> number % i == 0);
        System.out.println(prime);
        System.out.println(prime2);
    }
}