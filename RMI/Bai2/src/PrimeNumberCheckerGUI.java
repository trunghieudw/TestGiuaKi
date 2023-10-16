import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeNumberCheckerGUI extends JFrame {
    private JTextField numberTextField;
    private JButton checkButton;
    private JLabel resultLabel;

    public PrimeNumberCheckerGUI() {
        setTitle("Prime Number Checker");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        numberTextField = new JTextField(10);
        checkButton = new JButton("Check");
        resultLabel = new JLabel("Result:");

        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkPrime();
            }
        });

        setLayout(new FlowLayout());
        add(new JLabel("Enter a number:"));
        add(numberTextField);
        add(checkButton);
        add(resultLabel);
    }

    private void checkPrime() {
        try {
            int numberToCheck = Integer.parseInt(numberTextField.getText());

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            IPrimeNumberChecker primeNumberChecker = (IPrimeNumberChecker) registry.lookup("PrimeNumberChecker");

            boolean isPrime = primeNumberChecker.isPrime(numberToCheck);

            if (isPrime) {
                resultLabel.setText("Result: " + numberToCheck + " is a prime number.");
            } else {
                resultLabel.setText("Result: " + numberToCheck + " is not a prime number.");
            }
        } catch (Exception ex) {
            resultLabel.setText("Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                PrimeNumberCheckerGUI gui = new PrimeNumberCheckerGUI();
                gui.setVisible(true);
            }
        });
    }
}
