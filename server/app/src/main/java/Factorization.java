import java.util.*;

public class Factorization {

    private static List<Integer> primes = new ArrayList<>();

    public String factorize(int number) {

        long start = System.nanoTime();
        StringBuilder result = new StringBuilder();

        int divider = 2;
        while (number != 1) {
            if (isPrime(divider) && number % divider == 0) {
                number = number / divider;
                result.append(divider+"*");
            } else {
                divider++;
            }

        }

        result.setLength(result.length()-1);
        long end = System.nanoTime();
        System.out.println((end - start) / 1000);
        return result.toString();
    }

    protected boolean isPrime(int primeCandidate) {
        if(primes.contains(primeCandidate))
                return true;
        for (int prime : primes) {
            if (primeCandidate % prime == 0){
                return false;
            }
        }
        primes.add(primeCandidate);
        return true;
    }

}
