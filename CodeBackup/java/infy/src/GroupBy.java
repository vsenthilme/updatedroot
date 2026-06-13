import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupBy {
    public static void main(String[] args) {
        String input = "interviewprep";

        Map<String, Long> charCountMap = Arrays.stream(input.split(""))
                .collect(Collectors.groupingBy(
                        ch -> ch,
                        LinkedHashMap::new, // This preserves the original order
                        Collectors.counting()
                ));
        System.out.println(charCountMap);
    }
}
