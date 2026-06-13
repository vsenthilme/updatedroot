<<<<<<< HEAD
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Enter the Number to find prime or not:");
        System.out.println("Enter the Number to identify weather its prime or not:");
        Scanner inputNum = new Scanner(System.in);
        Double inputNumber = inputNum.nextDouble();
=======
public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");

        Double inputNumber = 5D;
        System.out.println("Enter the Number to identify weather its prime or not:\n");
>>>>>>> 81dc64bb940937a238631ec1ef8d3af347b8ec9e

        for(int i=1;i<= inputNumber;i++){
            Double result1 = (inputNumber/i);

            Double result2 = (inputNumber % i);

            if(result1 == 1 && result2 != 0){
                System.out.println("the Number is prime\n");
            } else {
                System.out.println("the Number is Not prime\n");

            }
        }


    }
}