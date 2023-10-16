import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiComplexServer {
    public static void main(String[] args) {
        try {
            // Create the remote object
            ComplexOperation complexOperation = new ComplexOperationImpl();

            // Start the RMI registry on port 1099
            LocateRegistry.createRegistry(8000);

            // Bind the remote object to the RMI registry
            Naming.rebind("ComplexOperation", complexOperation);

            System.out.println("Server is running...");
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
