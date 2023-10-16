
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class CustomerServer{
    public static void main(String[] args) {

        try {
            CustomerDatabase customerDB = new CustomerDatabaseImpl();
            LocateRegistry.createRegistry(6789);
            Naming.bind("rmi://127.0.0.1:6789/CustomerDatabase", customerDB);
            System.out.println(">>>>>INFO: RMI Server started!!!!!!!!");

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
