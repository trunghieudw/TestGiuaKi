import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TriangleCalculatorClientGUI {
    private JFrame frame;
    private JTextField sideAField;
    private JTextField sideBField;
    private JTextField sideCField;
    private JButton calculateButton;
    private JLabel resultLabel;

    private TriangleCalculator triangleCalculator;

    public TriangleCalculatorClientGUI() {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            triangleCalculator = (TriangleCalculator) registry.lookup("TriangleCalculator");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Không thể kết nối tới server RMI.");
        }

        frame = new JFrame("Tính diện tích tam giác");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        frame.add(new JLabel("Cạnh a:"));
        sideAField = new JTextField();
        frame.add(sideAField);

        frame.add(new JLabel("Cạnh b:"));
        sideBField = new JTextField();
        frame.add(sideBField);

        frame.add(new JLabel("Cạnh c:"));
        sideCField = new JTextField();
        frame.add(sideCField);

        calculateButton = new JButton("Tính diện tích");
        frame.add(calculateButton);

        resultLabel = new JLabel("Diện tích tam giác: ");
        frame.add(resultLabel);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateArea();
            }
        });

        frame.setVisible(true);
    }

    private void calculateArea() {
        try {
            double a = Double.parseDouble(sideAField.getText());
            double b = Double.parseDouble(sideBField.getText());
            double c = Double.parseDouble(sideCField.getText());

            double area = triangleCalculator.calculateArea(a, b, c);
            resultLabel.setText("Diện tích tam giác: " + area);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số hợp lệ cho các cạnh tam giác.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi tính toán diện tích: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TriangleCalculatorClientGUI();
            }
        });
    }
}
