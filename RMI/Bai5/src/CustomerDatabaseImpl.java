import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public Customer findCustomer(String customerID) throws RemoteException {
        // Implement logic để tìm thông tin khách hàng dựa vào customerID trong cơ sở dữ liệu
        String query = "SELECT name, address FROM customer WHERE customerID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, customerID);
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
}
