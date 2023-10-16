import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class ClientGUI extends JFrame {
    private JTextField idField;
    private JButton searchButton;
    private JTextArea resultArea;
    private JTextField nameField;
    private JTextField addressField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton viewAllButton;

    // Đối tượng RMI
    private CustomerDatabase customerDatabase;

    public ClientGUI() {
        setTitle("Client GUI");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        idField = new JTextField(10);
        searchButton = new JButton("Tìm kiếm");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        nameField = new JTextField(20);
        addressField = new JTextField(20);
        addButton = new JButton("Thêm");
        updateButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        viewAllButton = new JButton("Xem tất cả");

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        inputPanel.add(new JLabel("Nhập ID khách hàng:"), c);
        c.gridx = 1;
        c.gridy = 0;
        inputPanel.add(idField, c);
        c.gridx = 2;
        c.gridy = 0;
        inputPanel.add(searchButton, c);

        c.gridx = 0;
        c.gridy = 1;
        inputPanel.add(new JLabel("Tên khách hàng:"), c);
        c.gridx = 1;
        c.gridy = 1;
        inputPanel.add(nameField, c);
        c.gridx = 0;
        c.gridy = 2;
        inputPanel.add(new JLabel("Địa chỉ khách hàng:"), c);
        c.gridx = 1;
        c.gridy = 2;
        inputPanel.add(addressField, c);

        c.gridx = 0;
        c.gridy = 3;
        inputPanel.add(addButton, c);
        c.gridx = 1;
        c.gridy = 3;
        inputPanel.add(updateButton, c);
        c.gridx = 0;
        c.gridy = 4;
        inputPanel.add(deleteButton, c);
        c.gridx = 1;
        c.gridy = 4;
        inputPanel.add(viewAllButton, c);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchCustomer();
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCustomer();
            }
        });
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewAllCustomers();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateCustomer();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCustomer();
            }
        });

        JPanel resultPanel = new JPanel();
        resultPanel.add(new JLabel("Thông tin khách hàng:"));
        resultPanel.add(new JScrollPane(resultArea));

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);

        // Khởi tạo kết nối RMI
        try {
            customerDatabase = (CustomerDatabase) Naming.lookup("rmi://localhost:6789/CustomerDatabase");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ RMI.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    private void viewAllCustomers() {
        try {
            List<Customer> customers = customerDatabase.getAllCustomers();
            if (!customers.isEmpty()) {
                StringBuilder resultText = new StringBuilder("Danh sách khách hàng:\n");
                for (Customer customer : customers) {
                    resultText.append("ID: ").append(customer.getCustomerID())
                            .append("\nTên: ").append(customer.getName())
                            .append("\nĐịa chỉ: ").append(customer.getAddress())
                            .append("\n\n");
                }
                resultArea.setText(resultText.toString());
            } else {
                resultArea.setText("Không có khách hàng nào trong cơ sở dữ liệu.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void searchCustomer() {
        try {
            int customerID = Integer.parseInt(idField.getText());
            Customer customer = customerDatabase.findCustomer(customerID);
            if (customer != null) {
                resultArea.setText("ID: " + customer.getCustomerID() + "\nTên: " + customer.getName() + "\nĐịa chỉ: " + customer.getAddress());
            } else {
                resultArea.setText("Không tìm thấy khách hàng với ID " + customerID);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void addCustomer() {
        try {
            String name = nameField.getText();
            String address = addressField.getText();
    
            if (name.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên và địa chỉ không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            Customer newCustomer = new Customer();
            newCustomer.setName(name);
            newCustomer.setAddress(address);
    
            boolean success = customerDatabase.addCustomer(newCustomer);
            if (success) {
                resultArea.setText("Thêm khách hàng thành công.");
            } else {
                resultArea.setText("Không thể thêm khách hàng.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    private void updateCustomer() {
        try {
            int customerID = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String address = addressField.getText();
    
            if (name.isEmpty() && address.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tên và địa chỉ không được để trống khi cập nhật.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            Customer updatedCustomer = new Customer();
            updatedCustomer.setCustomerID(customerID);
            updatedCustomer.setName(name);
            updatedCustomer.setAddress(address);
    
            boolean success = customerDatabase.updateCustomer(updatedCustomer);
            if (success) {
                resultArea.setText("Cập nhật thông tin khách hàng thành công.");
            } else {
                resultArea.setText("Không thể cập nhật thông tin khách hàng.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteCustomer() {
        try {
            int customerID = Integer.parseInt(idField.getText());
            boolean success = customerDatabase.deleteCustomer(customerID);
            if (success) {
                resultArea.setText("Xóa khách hàng thành công.");
            } else {
                resultArea.setText("Không thể xóa khách hàng.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập một ID hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClientGUI gui = new ClientGUI();
                gui.pack(); // Pack the components
                gui.setVisible(true); // Set the frame visible
            }
        });
    }
}
