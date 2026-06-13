import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReverseWord {
    public static void main(String[] args) {
        System.out.println("Enter the String : ");
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine();
        System.out.println("Entered String : " + inputString);
        String unReversed = Stream.of(inputString).map(StringBuilder::new).collect(Collectors.joining(""));
        String reversed2 = Stream.of(inputString).map(n -> new StringBuilder(n).reverse()).collect(Collectors.joining(""));
        String reversed = Stream.of(inputString).map(StringBuilder::new).map(StringBuilder::reverse).collect(Collectors.joining(""));
        Stream.of(inputString).map(StringBuilder::new).map(StringBuilder::reverse).forEach(System.out::println);
//                forEach(System.out::println);
        System.out.println("UnReversed String : " + unReversed);
        System.out.println("Reversed String : " + reversed);
        System.out.println("Reversed String : " + reversed2);
        if (unReversed.equalsIgnoreCase(reversed)) {
            System.out.println("Its a Palindrome !");
        }
    }
}