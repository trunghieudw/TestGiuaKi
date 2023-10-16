import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComplexCalculatorGUI extends Remote {
    String performOperation(String operator, double real1, double imaginary1, double real2, double imaginary2) throws RemoteException;
}
