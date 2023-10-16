import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ComplexCalculatorGUIImpl extends UnicastRemoteObject implements ComplexCalculatorGUI {
    public ComplexCalculatorGUIImpl() throws RemoteException {
        super();
    }

    @Override
    public String performOperation(String operator, double real1, double imaginary1, double real2, double imaginary2) throws RemoteException {
        Complex num1 = new Complex(real1, imaginary1);
        Complex num2 = new Complex(real2, imaginary2);
        Complex result;

        switch (operator) {
            case "Add":
                result = add(num1, num2);
                break;
            case "Subtract":
                result = subtract(num1, num2);
                break;
            case "Multiply":
                result = multiply(num1, num2);
                break;
            case "Divide":
                result = divide(num1, num2);
                break;
            default:
                return "Invalid operator";
        }

        return "Result: " + result.toString();
    }

    private Complex add(Complex num1, Complex num2) {
        double realSum = num1.getReal() + num2.getReal();
        double imaginarySum = num1.getImaginary() + num2.getImaginary();
        return new Complex(realSum, imaginarySum);
    }

    private Complex subtract(Complex num1, Complex num2) {
        double realDiff = num1.getReal() - num2.getReal();
        double imaginaryDiff = num1.getImaginary() - num2.getImaginary();
        return new Complex(realDiff, imaginaryDiff);
    }

    private Complex multiply(Complex num1, Complex num2) {
        double realProduct = num1.getReal() * num2.getReal() - num1.getImaginary() * num2.getImaginary();
        double imaginaryProduct = num1.getReal() * num2.getImaginary() + num1.getImaginary() * num2.getReal();
        return new Complex(realProduct, imaginaryProduct);
    }

    private Complex divide(Complex num1, Complex num2) {
        double denominator = num2.getReal() * num2.getReal() + num2.getImaginary() * num2.getImaginary();
        double realQuotient = (num1.getReal() * num2.getReal() + num1.getImaginary() * num2.getImaginary()) / denominator;
        double imaginaryQuotient = (num1.getImaginary() * num2.getReal() - num1.getReal() * num2.getImaginary()) / denominator;
        return new Complex(realQuotient, imaginaryQuotient);
    }
}
