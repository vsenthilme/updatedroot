import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringManip {
    public static void main(String[] args) {
//        List<String> list1 = Arrays.asList("apple", "banana", "kiwi", "orange", "pear","oai");
        List<String> list1 = Arrays.asList("apple", "banana", "kiwi", "banana", "apple");
        List<String> list3 = Arrays.asList("apple", "banana", "kiwi", "banana", "apple");
        List<String> list2 = Arrays.asList("banana", "orange", "grape", "watermelon");
        List<String> list4 = Arrays.asList("ban23ana", "ora1ng5e", "6g8rape", "w9a0te2rmelon");
        Collections.reverse(list1);
        boolean isPalindrome = list1.equals(list3);
        System.out.println(isPalindrome);
        List<String> res = list1.stream().filter(list2::contains).toList();
        List<String> res2 = list1.stream().filter(n->n.matches("[aeiouAEIOU]+")).collect(Collectors.toList());
        List<String> res3 = list1.stream().filter(n->n.matches(".*[AEIOUaeiou].*")).toList();
        Set<String> res4 = Stream.concat(list1.stream(),list2.stream()).collect(Collectors.toSet());
        List<String> res5 = list4.stream().map(n->n.replaceAll("\\d+","")).toList();
        List<String> res6 = list4.stream().map(n->n.replaceAll("[^\\d.]","")).toList();
        System.out.println("withoutNumber : " + res5);
        System.out.println("Number : " + res6);
        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);
    }
}