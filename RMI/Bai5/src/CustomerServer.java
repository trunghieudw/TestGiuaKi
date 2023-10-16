import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class CustomerServer{
    public static void main(String[] args) {

        try {
            CustomerDatabase customerDB = new CustomerDatabaseImpl();
            Naming.rebind("CustomerDatabase", customerDB);
            System.out.println("Server is running...");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
