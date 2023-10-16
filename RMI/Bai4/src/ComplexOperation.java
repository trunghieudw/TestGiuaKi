import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ComplexOperation extends Remote {
    Complex add(Complex num1, Complex num2) throws RemoteException;
    Complex subtract(Complex num1, Complex num2) throws RemoteException;
    Complex multiply(Complex num1, Complex num2) throws RemoteException;
    Complex divide(Complex num1, Complex num2) throws RemoteException;
}
