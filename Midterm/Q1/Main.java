/*
    Ahmed Elshafey
    900131045
    Question 1
 */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> primes = new ArrayList<>();
        if (args.length > 0) {
            int n = -1;
            try {
                n = Integer.parseInt(args[0]);
            } catch (NumberFormatException exception) {
                System.out.println("Input must be an integer, exiting");
                System.exit(1);
            } finally {
                if (n <= 0) {
                    System.out.println("Input must be a positive nonzero integer, exiting");
                    System.exit(1);
                }
            }
            for (int i = 1; i <= n; i++) {
                for (int j = 2; j <= i; j++) {
                    if (j == i) primes.add(i);
                    if (i % j == 0) break;
                }
            }
            System.out.println(primes);
        } else {
            System.out.println("No argument received, exiting");
        }
    }
}
