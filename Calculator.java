import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder input;
    private double operand1, operand2;
    private String operator;
    private boolean startNewNumber;

    private final Color BACKGROUND_COLOR = new Color(51, 51, 51);
    private final Color BUTTON_COLOR = new Color(102, 102, 102);
    private final Color BUTTON_HOVER_COLOR = new Color(153, 153, 153);
    private final Color TEXT_COLOR = Color.WHITE;

    public Calculator() {
        input = new StringBuilder();
        operand1 = operand2 = 0;
        operator = "";
        startNewNumber = true;

        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);

        // Display field
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 42));
        display.setEditable(false);
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setForeground(TEXT_COLOR);
        display.setBackground(BACKGROUND_COLOR);
        display.setPreferredSize(new Dimension(300, 120)); // Increase the height to 60 pixels
        display.setBorder(null);
        add(display, BorderLayout.NORTH);

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5)); // 5 rows, 4 columns
        panel.setBackground(BACKGROUND_COLOR);

        String[] buttonLabels = {
            "", "", "AC", "/",
            "7", "8", "9", "*",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "", "="
        };

        for (String label : buttonLabels) {
            if (!label.isEmpty()) {
                JButton button = new JButton(label);
                button.setFont(new Font("Arial", Font.BOLD, 16));
                button.addActionListener(this);
                button.setForeground(TEXT_COLOR);
                button.setBackground(BUTTON_COLOR);
                button.setFocusPainted(false);
                button.setBorderPainted(false);

                button.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        button.setBackground(BUTTON_HOVER_COLOR);
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        button.setBackground(BUTTON_COLOR);
                    }
                });

                panel.add(button);
            } else {
                panel.add(new JLabel()); // Add an empty label for the empty space
            }
        }

        add(panel, BorderLayout.CENTER);

        pack(); // Resize the window to fit the preferred size of the components
        setSize(getWidth() + 50, getHeight() + 200); // Increase the height by 100 pixels

        // Add padding around the content pane
        JPanel contentPane = (JPanel) getContentPane();
        contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // top, left, bottom, right

        contentPane.setBackground(BACKGROUND_COLOR);

        // Center the window on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);

        setResizable(false); // Make the window not resizable
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("AC")) {
            clear();
        } else if (command.equals("=")) {
            calculate();
        } else if ("+-*/".contains(command)) {
            setOperator(command);
        } else {
            appendToInput(command);
        }
    }

    private void clear() {
        input.setLength(0);
        operand1 = operand2 = 0;
        operator = "";
        startNewNumber = true;
        display.setText("0");
    }

    private void calculate() {
        if (operator.isEmpty() || input.length() == 0) return;

        operand2 = Double.parseDouble(input.toString());
        input.setLength(0);

        switch (operator) {
            case "+":
                operand1 += operand2;
                break;
            case "-":
                operand1 -= operand2;
                break;
            case "*":
                operand1 *= operand2;
                break;
            case "/":
                if (operand2 != 0) {
                    operand1 /= operand2;
                } else {
                    display.setText("Error");
                    startNewNumber = true;
                    return;
                }
                break;
        }

        display.setText(String.valueOf(operand1));
        startNewNumber = true;
    }

    private void setOperator(String op) {
        if (input.length() > 0) {
            operand1 = Double.parseDouble(input.toString());
            input.setLength(0);
        }
        operator = op;
        startNewNumber = true;
    }

    private void appendToInput(String str) {
        if (startNewNumber) {
            input.setLength(0);
            startNewNumber = false;
        }
        input.append(str);
        display.setText(input.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
            calculator.clear();
        });
    }
}