import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList; // Import ArrayList from java.util
import java.util.List; // Import List from java.util

public class CustomerDatabaseImpl extends UnicastRemoteObject implements CustomerDatabase {
    private Connection connection;

    // Constructor
    public CustomerDatabaseImpl() throws RemoteException {
        super();
        // Khởi tạo kết nối đến cơ sở dữ liệu MySQL
        String dbURL = "jdbc:mysql://localhost:3306/customer_db";
        String username = "root";
        String password = "123456";
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Failed to connect to the database.");
        }
    }

    @Override
    public Customer findCustomer(int customerID) throws RemoteException {
        // Implement logic để tìm thông tin khách hàng dựa vào customerID trong cơ sở dữ liệu
        String query = "SELECT name, address FROM customer WHERE customerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                Customer customer = new Customer();
                customer.setCustomerID(customerID);
                customer.setName(name);
                customer.setAddress(address);
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while querying the database.");
        }

        return null; // Trả về null nếu không tìm thấy khách hàng
    }

    @Override
    public boolean addCustomer(Customer customer) throws RemoteException {
        String insertQuery = "INSERT INTO customer (name, address) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while adding customer.");
        }
    }

    @Override
    public boolean deleteCustomer(int customerID) throws RemoteException {
        String deleteQuery = "DELETE FROM customer WHERE customerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.setInt(1, customerID);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while deleting customer.");
        }
    }

    @Override
    public boolean updateCustomer(Customer customer) throws RemoteException {
        String updateQuery = "UPDATE customer SET name = ?, address = ? WHERE customerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setInt(3, customer.getCustomerID());
            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while updating customer.");
        }
    }
    @Override
    public List<Customer> getAllCustomers() throws RemoteException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT customerID, name, address FROM customer";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int customerID = resultSet.getInt("customerID");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");

                Customer customer = new Customer();
                customer.setCustomerID(customerID);
                customer.setName(name);
                customer.setAddress(address);

                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error while querying the database.");
        }

        return customers;
    }
}
