import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    public static void main(String[] args) {
        try {
            TriangleCalculatorImpl triangleCalculator = new TriangleCalculatorImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("TriangleCalculator", triangleCalculator);
            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
