import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StringGroup {
    public static void main(String[] args) {
        System.out.println("Enter the String : ");
        Scanner inp = new Scanner(System.in);
        String str1 = inp.nextLine();
        Map<String, List<String>> res = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(n->n));
        Map<String, Long> res2 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));

        List<String> res3 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().filter(n->n.getValue() == 1).map(Map.Entry::getKey).collect(Collectors.toList());
        List<String> res4 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).entrySet().stream().filter(n->n.getValue()>1).map(Map.Entry::getKey).toList();
        Long res5 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).values().stream().mapToLong(Long::longValue).max().orElse(0);
        String res6 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting())).entrySet().stream().filter(n-> Objects.equals(n.getValue(), res5)).map(Map.Entry::getKey).findFirst().get();

        System.out.println(res);
        System.out.println(res2);
        System.out.println(res3);
        System.out.println(res4);
        System.out.println(res5);
        System.out.println(res6);
    }
}