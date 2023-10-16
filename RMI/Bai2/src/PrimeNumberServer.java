import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeNumberServer {
    public static void main(String[] args) {
        try {
            IPrimeNumberChecker primeNumberChecker = new PrimeNumberCheckerImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("PrimeNumberChecker", primeNumberChecker);
            System.out.println("Server is ready.");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
