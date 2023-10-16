import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class FibonacciClient {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the value of n for Fibonacci calculation: ");
            int n = scanner.nextInt();

            Registry registry = LocateRegistry.getRegistry("localhost");
            FibonacciService fibonacciService = (FibonacciService) registry.lookup("FibonacciService");

            long result = fibonacciService.calculateFibonacci(n);
            System.out.println("Fibonacci of " + n + " is: " + result);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
