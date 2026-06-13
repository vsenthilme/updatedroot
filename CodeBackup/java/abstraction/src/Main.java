import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

//    Main(String customerName, Double amount, String currency) {
//        super(customerName, amount, currency);
//    }

    public static void main(String[] args) {
        System.out.println("Hello world!");

//        Pmt pmt = new Pmt("Senthil",100000D, "INR");
//        pmt.creditCard();

        System.out.println("Enter the word: ");
        Scanner inp = new Scanner(System.in);
        String str1 = inp.nextLine();
//        String str1 = "senthil";
        String res1 = Stream.of(str1).map(StringBuilder::new).map(StringBuilder::reverse).collect(Collectors.joining());
        String res2 = Stream.of(str1).map(n-> new StringBuilder(n).reverse()).collect(Collectors.joining());
        System.out.println(str1);
        System.out.println(res1);
        System.out.println(res2);
    }


}