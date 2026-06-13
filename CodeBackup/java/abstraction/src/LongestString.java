import javax.swing.*;
import java.util.*;
import java.util.stream.Collectors;

public class LongestString {
    public static void main(String[] args) {
        List<String> stringList = Arrays.asList("Senthil", "kumaran", "divya", "karthic", "adidev", "harsikavardini");
        String character = "t";
        List<String> upcs = stringList.stream().map(String::toUpperCase).toList();
        String longestString = stringList.stream().reduce((w1,w2) -> w1.length() > w2.length() ? w1 : w2).get().toUpperCase();
        String shortestString = stringList.stream().reduce((s1,s2) -> s1.length() < s2.length() ? s1 :s2).get().toLowerCase();
        Collections.sort(stringList);
        Collections.reverse(stringList);
        System.out.println(stringList);
        String lString = stringList.stream().max(Comparator.comparingInt(String::length)).orElse(null);
        List<String> duplicateCharacters = stringList.stream().filter(n->n.length() != n.chars().distinct().count()).toList();
        String sString = stringList.stream().min(Comparator.comparingInt(String::length)).orElse(null).toUpperCase();
        Long count = stringList.stream().filter(n -> n.contains(character)).count();
        String concat = String.join("_", stringList);
        System.out.println(duplicateCharacters);
        System.out.println(longestString);
        System.out.println(shortestString);
        System.out.println(lString);
        System.out.println(sString);
        System.out.println(upcs);
        System.out.println(count);
        System.out.println(concat);
    }
}