import javax.swing.plaf.synth.SynthLookAndFeel;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class query {
    public static void main(String[] args) {
        String str1 = "I Love You";
        String str2 = "hello";
        List<String> vowels = Arrays.asList("a", "e", "i", "o", "u");
        List<Integer> num = Arrays.asList(1,4,9,7,6,2,8);
        int[] number = {1,4,9,7,6,2,8};
        String[] strLen = {"java", "sent", "senthil", "hello", "umbrella"};
        List<String> strLeng = Arrays.asList("java", "sent", "senthil kumaran", "hello", "umbrella");
        System.out.println("Enter a String:");
        Scanner inp = new Scanner(System.in);
        String str3 = inp.nextLine();
        System.out.println("Enter a number : ");
        Scanner inp2 = new Scanner(System.in);
        Integer value = inp2.nextInt();
        Map<String, List<String>> map = Arrays.stream(str2.split("")).collect(Collectors.groupingBy(s->s));
        Map<String, Long> map2 = Arrays.stream(str1.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        String reversed = Stream.of(str2).map(n->new StringBuilder(n).reverse()).collect(Collectors.joining());
        String res = Stream.of(str3).map(StringBuilder::new).map(StringBuilder::reverse).collect(Collectors.joining());
//        String firstNonRptElement = Arrays.stream(str3.split("")).collect(Collectors.groupingBy(Function.identity(),LinkedHashMap::new,Collectors.counting())).entrySet().stream().filter(n->n.getValue()>1).findFirst().get().getKey();
        List<String> result = Arrays.stream(str3.split("")).filter(vowels::contains).collect(Collectors.toList());
        Integer high = Stream.of(num).sorted().findFirst().get().get(0);
        Integer sHigh = num.stream().sorted(Comparator.reverseOrder()).skip(1).findFirst().get();
        int tHigh = Arrays.stream(number).boxed().sorted(Comparator.reverseOrder()).skip(2).findFirst().get();
        String longStr = Arrays.stream(strLen).reduce((word1,word2)->word1.length()>word2.length() ? word1 : word2).get();
        String longString = strLeng.stream().reduce((w1,w2)->w1.length()>w2.length() ? w1 : w2).get();
        boolean isPrime = IntStream.rangeClosed(2, (int) Math.sqrt(value)).noneMatch(i -> value % i == 0);
        Stream.iterate(new long[]{1,1},p -> new long[]{p[1],p[0]+p[1]}).limit(value).forEach(n->System.out.println(n[0]));
        Stream.iterate(new long[]{0,1},p -> new long[]{p[1],p[0]+p[1]}).limit(value).map(n->n[0]).forEach(System.out::println);
        System.out.println(isPrime);
        System.out.println(longString);
        System.out.println(longStr);
        System.out.println(tHigh);
        System.out.println(high);
        System.out.println(sHigh);
        System.out.println(map);
        System.out.println(map2);
        System.out.println(reversed);
        System.out.println(res);
        System.out.println(result);
//        System.out.println(firstNonRptElement);
        if(str3.equalsIgnoreCase(res)) {
            System.out.println("Palindrome");
        }
        Stream.iterate(new long[]{0,1},p -> new long[]{p[1],p[0]+p[1]}).limit(value).map(n->n[0]).forEach(System.out::println);
        Arrays.sort(number);
        List<Integer> sored = num.stream().sorted().collect(Collectors.toList());
        Integer mi = sored.get(0);
        Integer ma = sored.get(num.size()-1);
        int min = number[0];
        int max = number[number.length - 1];
        int i = IntStream.rangeClosed(min, max).sum() - Arrays.stream(number).sum();
        List<Integer> expectedNum = IntStream.rangeClosed(mi,ma).boxed().collect(Collectors.toList());
        List<Integer> missesValues = expectedNum.stream().filter(n->!num.contains(n)).collect(Collectors.toList());
        System.out.println(min);
        System.out.println(max);
        System.out.println(i);
        System.out.println(mi);
        System.out.println(ma);
        System.out.println(missesValues);

//        @Override
//                test(){
//            System.out.println("check");
//        }
    }
}