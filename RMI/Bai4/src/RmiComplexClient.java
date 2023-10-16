import java.rmi.Naming;

public class RmiComplexClient {
    public static void main(String[] args) {
        try {
            // Look up the remote object
            ComplexOperation complexOperation = (ComplexOperation) Naming.lookup("rmi://localhost:8000/ComplexOperation");

            // Create two complex numbers
            Complex num1 = new Complex(2, 3);
            Complex num2 = new Complex(1, 2);

            // Perform operations
            Complex sum = complexOperation.add(num1, num2);
            Complex difference = complexOperation.subtract(num1, num2);
            Complex product = complexOperation.multiply(num1, num2);
            Complex quotient = complexOperation.divide(num1, num2);

            // Print the results
            System.out.println("Sum: " + sum);
            System.out.println("Difference: " + difference);
            System.out.println("Product: " + product);
            System.out.println("Quotient: " + quotient);
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
