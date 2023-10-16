import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FibonacciClientGUI {
    private JFrame frame;
    private JTextField textField;
    private JLabel resultLabel;

    public FibonacciClientGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Fibonacci Calculator");
        frame.setBounds(100, 100, 300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel label = new JLabel("Enter the value of n for Fibonacci calculation:");
        panel.add(label);

        textField = new JTextField();
        panel.add(textField);

        JButton calculateButton = new JButton("Calculate Fibonacci");
        panel.add(calculateButton);

        resultLabel = new JLabel();
        frame.getContentPane().add(resultLabel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateFibonacci();
            }
        });
    }

    private void calculateFibonacci() {
        try {
            int n = Integer.parseInt(textField.getText());

            Registry registry = LocateRegistry.getRegistry("localhost");
            FibonacciService fibonacciService = (FibonacciService) registry.lookup("FibonacciService");

            long result = fibonacciService.calculateFibonacci(n);
            resultLabel.setText("Fibonacci of " + n + " is: " + result);
        } catch (Exception e) {
            resultLabel.setText("Error: " + e.toString());
            e.printStackTrace();
        }
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        FibonacciClientGUI clientGUI = new FibonacciClientGUI();
        clientGUI.show();
    }
}
