import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FibonacciServer {
    public static void main(String[] args) {
        try {
            FibonacciService fibonacciService = new FibonacciServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("FibonacciService", fibonacciService);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}