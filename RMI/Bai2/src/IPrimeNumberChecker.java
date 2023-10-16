import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrimeNumberChecker extends Remote {
    boolean isPrime(int number) throws RemoteException;
}
