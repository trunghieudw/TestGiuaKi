import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RMIServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(3458);
            ScoreService scoreService = new ScoreServiceImpl();
            Naming.rebind("rmi://localhost:3457/ScoreService", scoreService);
            System.out.println("Server is running");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
