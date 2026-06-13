import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class example {
    public static void main(String[] args) {
        System.out.println("Enter the string : ");
        Scanner inp = new Scanner(System.in);
//        int number = inp.nextInt();
        String inpString = inp.nextLine();
        long[] nm = new long[] {5,1,4,2,3};
//        Arrays.sort(nm);
//        Stream.iterate(new long[]{1,1}, p -> new long[] {p[1], p[0] + p[1]}).limit(number).forEach(p -> System.out.println(p[0]));
        Map<String, List<String>> result= Arrays.stream(inpString.split("")).collect(Collectors.groupingBy(s->s) );
        Map<String, Long> result2= Arrays.stream(inpString.split("")).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()) );
        System.out.println(result);
        System.out.println(result2);
        long res = Arrays.stream(nm).sorted().findFirst().getAsLong();
        System.out.println(res);
    }
}