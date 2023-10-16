
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FibonacciService extends Remote {
    long calculateFibonacci(int n) throws RemoteException;
}