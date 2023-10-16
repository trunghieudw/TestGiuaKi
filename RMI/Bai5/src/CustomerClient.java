import java.rmi.Naming;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;


public class CustomerClient {
    public static void main(String[] args) {
        try {
            String serverURL = "rmi://127.0.0.1:6789/CustomerDatabase";
            CustomerDatabase customerDB = (CustomerDatabase) Naming.lookup(serverURL);

            Scanner scanner = new Scanner(System.in);
            int choice;

            while (true) {
                System.out.println("Choose:");
                System.out.println("1. Search by ID");
                System.out.println("2. Add");
                System.out.println("3. Edit");
                System.out.println("4. Delete");
                System.out.println("5. List customer");
                System.out.println("0. Exit");
                System.out.print("Your choose: ");
                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        findCustomer(customerDB);
                        break;
                    case 2:
                        addCustomer(customerDB);
                        break;
                    case 3:
                        updateCustomer(customerDB);
                        break;
                    case 4:
                        deleteCustomer(customerDB);
                        break;
                    case 5:
                        viewAllCustomers(customerDB);
                        break;
                    case 0:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("None.");
                        break;
                }
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void findCustomer(CustomerDatabase customerDB) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter ID: ");
            int customerIDToFind = scanner.nextInt();
            Customer customer = customerDB.findCustomer(customerIDToFind);

            if (customer != null) {
                System.out.println("ID: " + customer.getCustomerID());
                System.out.println("name: " + customer.getName());
                System.out.println("address: " + customer.getAddress());
            } else {
                System.out.println("ID not found " + customerIDToFind);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void addCustomer(CustomerDatabase customerDB) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("enter Name: ");
            String name = scanner.nextLine();
            System.out.print("enter Address: ");
            String address = scanner.nextLine();
    
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setAddress(address);
    
            boolean success = customerDB.addCustomer(newCustomer);
            if (success) {
                System.out.println("Created");
            } else {
                System.out.println("Can not create");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void updateCustomer(CustomerDatabase customerDB) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID to edit: ");
            int customerIDToUpdate = scanner.nextInt();
            System.out.print("New Name: ");
            String newName = scanner.next();
            System.out.print("New Address: ");
            String newAddress = scanner.next();

            Customer updatedCustomer = new Customer();
            updatedCustomer.setCustomerID(customerIDToUpdate);
            updatedCustomer.setName(newName);
            updatedCustomer.setAddress(newAddress);

            boolean success = customerDB.updateCustomer(updatedCustomer);
            if (success) {
                System.out.println("Updated");
            } else {
                System.out.println("can not update");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void deleteCustomer(CustomerDatabase customerDB) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("ID to remove: ");
            int customerIDToDelete = scanner.nextInt();

            boolean success = customerDB.deleteCustomer(customerIDToDelete);
            if (success) {
                System.out.println("Deleted");
            } else {
                System.out.println("can not delete");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    private static void viewAllCustomers(CustomerDatabase customerDB) {
        try {
            List<Customer> customers = customerDB.getAllCustomers();
            if (!customers.isEmpty()) {
                System.out.println("All Customers:");
                for (Customer customer : customers) {
                    System.out.println("ID: " + customer.getCustomerID());
                    System.out.println("Name: " + customer.getName());
                    System.out.println("Address: " + customer.getAddress());
                    System.out.println("-------------");
                }
            } else {
                System.out.println("No customers found.");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
