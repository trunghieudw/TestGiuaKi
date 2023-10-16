import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class ComplexCalculatorClientGUI extends JFrame {
    private JTextField real1Field, imaginary1Field, real2Field, imaginary2Field, resultField;
    private JComboBox<String> operatorComboBox;

    private ComplexCalculatorGUI complexCalculator;

    public ComplexCalculatorClientGUI() {
        setTitle("Complex Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize RMI
        // try {
        //     complexCalculator = (ComplexCalculatorGUI) Naming.lookup("rmi://localhost:8000/ComplexCalculator");
        // } catch (Exception e) {
        //     JOptionPane.showMessageDialog(this, "Could not connect to the server.", "Error", JOptionPane.ERROR_MESSAGE);
        //     System.exit(0);
        // }

        
        // Initialize components
        real1Field = new JTextField(10);
        imaginary1Field = new JTextField(10);
        real2Field = new JTextField(10);
        imaginary2Field = new JTextField(10);
        resultField = new JTextField(20);
        resultField.setEditable(false);

        operatorComboBox = new JComboBox<>(new String[]{"Add", "Subtract", "Multiply", "Divide"});

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performCalculation();
            }
        });

        // Set layout
        setLayout(new FlowLayout());

        // Add components to the frame
        add(new JLabel("Complex Number 1: "));
        add(new JLabel("Real Part"));
        add(real1Field);
        add(new JLabel("Imaginary Part"));
        add(imaginary1Field);

        add(new JLabel("Complex Number 2: "));
        add(new JLabel("Real Part"));
        add(real2Field);
        add(new JLabel("Imaginary Part"));
        add(imaginary2Field);

        add(new JLabel("Select Operator: "));
        add(operatorComboBox);
        add(calculateButton);
        add(new JLabel("Result: "));
        add(resultField);
    }

    private void performCalculation() {
        String operator = (String) operatorComboBox.getSelectedItem();

        try {
            double real1 = Double.parseDouble(real1Field.getText());
            double imaginary1 = Double.parseDouble(imaginary1Field.getText());
            double real2 = Double.parseDouble(real2Field.getText());
            double imaginary2 = Double.parseDouble(imaginary2Field.getText());

            String result = complexCalculator.performOperation(operator, real1, imaginary1, real2, imaginary2);
            resultField.setText(result);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ComplexCalculatorClientGUI gui = new ComplexCalculatorClientGUI();
                gui.setVisible(true);
            }
        });
    }
}
