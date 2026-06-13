import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Vowels {
    public static void main(String[] args) {
        System.out.println("Enter the String : ");
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine();
        List<String> vowels = Arrays.asList("a","e","i","o","u");
        long count = Arrays.stream(inputString.split("")).filter(vowels::contains).count();
        Arrays.stream(inputString.split("")).filter(vowels::contains).forEach(System.out::println);
        System.out.println("vowel count : " + count);
    }
}