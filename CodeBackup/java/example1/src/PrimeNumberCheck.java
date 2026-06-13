import java.util.Scanner;
import java.util.stream.IntStream;

public class PrimeNumberCheck {
    public static void main(String[] args) {
        System.out.println("Enter the number to check prime or not: " );
        Scanner inp = new Scanner(System.in);
        int number = inp.nextInt();
//        int number = 29; // The number to check
        boolean isPrime = isPrime(number); // Check if the number is prime

        // Step 5: Print the result
        if (isPrime) {
            System.out.println(number + " is a prime number.");
        } else {
            System.out.println(number + " is not a prime number.");
        }
    }

    // Step 1: Define the method to check for a prime number
    public static boolean isPrime(int number) {
        // Edge case for number 1
        if (number <= 1) {
            return false;
        }
        // Step 2 & 3: Generate a stream and check for divisors
        return !IntStream.rangeClosed(2, (int) Math.sqrt(number))
                .anyMatch(i -> number % i == 0); // Step 4: Return true if no divisors found
    }
}