import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PrimeNumberClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IPrimeNumberChecker primeNumberChecker = (IPrimeNumberChecker) registry.lookup("PrimeNumberChecker");

            Scanner scanner = new Scanner(System.in);

            int numberToCheck = scanner.nextInt();
            boolean isPrime = primeNumberChecker.isPrime(numberToCheck);

            scanner.close();
            if (isPrime) {
                System.out.println(numberToCheck + " is a prime number.");
            } else {
                System.out.println(numberToCheck + " is not a prime number.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
