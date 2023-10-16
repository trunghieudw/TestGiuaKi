import java.rmi.Naming;
import java.util.Scanner;

public class CustomerClient{
    public static void main(String[] args) {
        try {
            String serverURL = "rmi://localhost/CustomerDatabase";
            CustomerDatabase customerDB = (CustomerDatabase) Naming.lookup(serverURL);
            
            Scanner scanner = new Scanner(System.in);
            System.out.println("Nhap ID customer : ");
            int customerIDToFind = scanner.nextInt();

            Customer customer = customerDB.findCustomer(customerIDToFind);

            if (customer != null) {
                System.out.println("Customer ID: " + customer.getCustomerID());
                System.out.println("Name: " + customer.getName());
                System.out.println("Address: " + customer.getAddress());
            } else {
                System.out.println("Customer not found.");
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
