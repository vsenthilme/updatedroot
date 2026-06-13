import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class reverse {
    public static void main(String[] args) {
        Scanner inp = new Scanner(System.in);
        System.out.println("Enter the string to reverse: " );
        String str = inp.nextLine();
        String reversed = Stream.of(str)
                .map(n -> new StringBuilder(n).reverse())
                .collect(Collectors.joining(" "));
        System.out.println(reversed);
    }
}
