import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGame extends JFrame implements ActionListener {
    private JTextField textField;
    private JButton guessButton;
    private JLabel resultLabel;
    private int secretNum;
    private int attemptsLeft;

    public NumberGame() {
        setTitle("Number Guessing Game");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter your guess (1-100):");
        add(label);

        textField = new JTextField(10);
        add(textField);

        guessButton = new JButton("Guess");
        guessButton.addActionListener(this);
        add(guessButton);

        resultLabel = new JLabel("");
        add(resultLabel);

        initializeGame();
    }

    private void initializeGame() {
        Random random = new Random();
        secretNum = random.nextInt(100) + 1;
        attemptsLeft = 5;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == guessButton) {
            int guess;
            try {
                guess = Integer.parseInt(textField.getText());
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input. Please enter a number.");
                return;
            }

            attemptsLeft--;

            if (guess == secretNum) {
                resultLabel.setText("Congratulations! Your guess was right");
                displayScore();
                initializeGame();
            } else if (attemptsLeft == 0) {
                resultLabel.setText("You've run out of attempts. The number was " + secretNum);
                initializeGame();
            } else {
                if (guess > secretNum) {
                    resultLabel.setText("Your guess is high! Attempts left: " + attemptsLeft);
                } else {
                    resultLabel.setText("Your guess is low! Attempts left: " + attemptsLeft);
                }
            }
            textField.setText("");
        }
    }

    private void displayScore() {
        int score = 0;
        switch (attemptsLeft) {
            case 4:
                score = 25;
                break;
            case 3:
                score = 50;
                break;
            case 2:
                score = 75;
                break;
            case 1:
                score = 100;
                break;
        }
        JOptionPane.showMessageDialog(this, "Your score: " + score);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NumberGame().setVisible(true);
        });
    }
}
