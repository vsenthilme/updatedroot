import java.util.*;
import java.util.stream.Collectors;

public class Sum {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1,2,3,4,5,6);
        List<Long> integers2 = Arrays.asList(1L,2L,3L,4L,5L,6L);
        List<Integer> integers1 = Arrays.asList(1,2,3,4,5,6,1,2,3,4,5,6);
//        integers2.stream().sorted(Comparator.reverseOrder());
        Integer result = integers.stream().mapToInt(n->n).sum();
        Integer result1 = integers.stream().mapToInt(Integer::intValue).sum();
        long res = integers.stream().mapToLong(Integer::longValue).sum();
        double res2 = integers.stream().mapToDouble(Integer::doubleValue).sum();
        System.out.println(result);
        System.out.println("res " + res);
        System.out.println("res2 " + res2);
        integers.stream().filter(n -> n % 2 == 0).forEach(System.out::println);
        integers.stream().filter(n -> n % 2 != 0).forEach(System.out::println);
        Integer max = integers.stream().mapToInt(n -> n).max().orElseThrow();
        Double avg = integers.stream().mapToDouble(Integer::doubleValue).average().orElseThrow();
        Integer secondMax = integers.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
        Set<Integer> integerSet = new HashSet<>(integers);
        List<String> index = integers1.stream().map(n->n+"").filter(p->p.startsWith("4")).toList();
        System.out.println(index);
        System.out.println(integerSet);
        System.out.println(max);
        System.out.println(avg);
        System.out.println(secondMax);

    }
}