import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List; 

public interface CustomerDatabase extends Remote {
    Customer findCustomer(int customerID) throws RemoteException;
    boolean updateCustomer(Customer customer) throws RemoteException;
    boolean addCustomer(Customer customer) throws RemoteException;
    boolean deleteCustomer(int customerID) throws RemoteException;
    List<Customer> getAllCustomers() throws RemoteException;
}