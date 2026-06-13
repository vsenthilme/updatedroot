import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class testreverse {
    public static void main(String[] args) {
        String str1 = "i love you";
        String rev = Arrays.stream(str1.split("")).sorted(Comparator.reverseOrder()).collect(Collectors.joining());
        String reversed2 = Stream.of(str1).map(n -> new StringBuilder(n).reverse()).collect(Collectors.joining(""));
        String reverse = Stream.of(str1).map(StringBuilder::new).map(StringBuilder::reverse).collect(Collectors.joining());
        System.out.println(str1);
        System.out.println(rev);
        System.out.println(reversed2);
        System.out.println(reverse);
    }
}