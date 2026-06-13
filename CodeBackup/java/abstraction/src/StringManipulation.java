import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StringManipulation {
    public static void main(String[] args) {
        System.out.println("Enter the 1st string : ");
        Scanner inp1 = new Scanner(System.in);
        String str1 = inp1.nextLine();
        System.out.println("Enter the 2nd string : ");
        Scanner inp2 = new Scanner(System.in);
        String str2 = inp2.nextLine();
        String res1 = Arrays.stream(str1.split("")).sorted().collect(Collectors.joining(""));
        String res2 = Arrays.stream(str2.split("")).sorted().collect(Collectors.joining(""));
        if(res1.equalsIgnoreCase(res2)) {
            System.out.println(res1);
            System.out.println(res2);
            System.out.println("Same String");
        }
    }
}