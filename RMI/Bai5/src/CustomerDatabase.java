import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CustomerDatabase extends Remote {
    public Customer findCustomer(String customerID) throws RemoteException;
}
