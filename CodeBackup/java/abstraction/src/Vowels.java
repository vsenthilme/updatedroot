import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Vowels {
    public static void main(String[] args) {
        System.out.println("Enter the String to find vowels : ");
        Scanner inp = new Scanner(System.in);
        String str1 = inp.nextLine();
        List<String> vowels = Arrays.asList("a","e","i","o","u");
        String res = Arrays.stream(str1.split("")).filter(vowels::contains).collect(Collectors.joining());
        System.out.println(res);
    }
}