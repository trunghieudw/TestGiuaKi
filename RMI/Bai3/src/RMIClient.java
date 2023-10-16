import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class RMIClient {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            TriangleCalculator triangleCalculator = (TriangleCalculator) registry.lookup("TriangleCalculator");

            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhập cạnh a: ");
            double a = scanner.nextDouble();

            System.out.print("Nhập cạnh b: ");
            double b = scanner.nextDouble();

            System.out.print("Nhập cạnh c: ");
            double c = scanner.nextDouble();

            scanner.close();
            double area = triangleCalculator.calculateArea(a, b, c);
            System.out.println("Diện tích của tam giác: " + area);
        } catch (Exception e) {
            System.err.println("Lỗi khi thực hiện: " + e.toString());
            e.printStackTrace();
        }
    }
}
