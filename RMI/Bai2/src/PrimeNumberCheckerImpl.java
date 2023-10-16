import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeNumberCheckerImpl extends UnicastRemoteObject implements IPrimeNumberChecker {

    public PrimeNumberCheckerImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean isPrime(int number) throws RemoteException {
        if (number <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }
}
