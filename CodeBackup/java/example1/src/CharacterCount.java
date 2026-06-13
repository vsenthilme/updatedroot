import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharacterCount {
    public static void main(String[] args){
        System.out.println("Enter the String : ");
        Scanner input = new Scanner(System.in);
        String inpString = input.nextLine();
        Arrays.stream(inpString.split("")).forEach(System.out::println);
        Map<String, List<String>> map1 = Arrays.stream(inpString.split("")).collect(Collectors.groupingBy(n->n));
        Map<String, Long> map = Arrays.stream(inpString.split("")).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println(map1);
        System.out.println(map);
    }
}