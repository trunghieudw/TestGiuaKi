import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ComplexOperationImpl extends UnicastRemoteObject implements ComplexOperation {
    public ComplexOperationImpl() throws RemoteException {
        super();
    }

    @Override
    public Complex add(Complex num1, Complex num2) throws RemoteException {
        double realSum = num1.getReal() + num2.getReal();
        double imaginarySum = num1.getImaginary() + num2.getImaginary();
        return new Complex(realSum, imaginarySum);
    }

    @Override
    public Complex subtract(Complex num1, Complex num2) throws RemoteException {
        double realDiff = num1.getReal() - num2.getReal();
        double imaginaryDiff = num1.getImaginary() - num2.getImaginary();
        return new Complex(realDiff, imaginaryDiff);
    }

    @Override
    public Complex multiply(Complex num1, Complex num2) throws RemoteException {
        double realProduct = num1.getReal() * num2.getReal() - num1.getImaginary() * num2.getImaginary();
        double imaginaryProduct = num1.getReal() * num2.getImaginary() + num1.getImaginary() * num2.getReal();
        return new Complex(realProduct, imaginaryProduct);
    }

    @Override
    public Complex divide(Complex num1, Complex num2) throws RemoteException {
        double denominator = num2.getReal() * num2.getReal() + num2.getImaginary() * num2.getImaginary();
        double realQuotient = (num1.getReal() * num2.getReal() + num1.getImaginary() * num2.getImaginary()) / denominator;
        double imaginaryQuotient = (num1.getImaginary() * num2.getReal() - num1.getReal() * num2.getImaginary()) / denominator;
        return new Complex(realQuotient, imaginaryQuotient);
    }
}
