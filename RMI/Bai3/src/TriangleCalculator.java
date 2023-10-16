import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TriangleCalculator extends Remote {
    double calculateArea(double a, double b, double c) throws RemoteException;
}
