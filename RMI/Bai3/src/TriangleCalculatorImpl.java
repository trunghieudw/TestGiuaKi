import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TriangleCalculatorImpl extends UnicastRemoteObject implements TriangleCalculator {

    public TriangleCalculatorImpl() throws RemoteException {
        // Constructor
    }

    @Override
    public double calculateArea(double a, double b, double c) throws RemoteException {
        // Sử dụng công thức Heron để tính diện tích tam giác
        double s = (a + b + c) / 2;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }
}
