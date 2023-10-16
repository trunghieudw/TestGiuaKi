import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FibonacciServiceImpl extends UnicastRemoteObject implements FibonacciService {
    public FibonacciServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public long calculateFibonacci(int n) throws RemoteException {
        if (n <= 1) {
            return n;
        } else {
            return calculateFibonacci(n - 1) + calculateFibonacci(n - 2);
        }
    }
}