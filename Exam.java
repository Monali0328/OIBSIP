import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class Exam extends JFrame {
    private int questionsAnswered;
    private String createdUsername;
    private String createdPass;
    private Timer timer;
    private int timeLeft;

    public Exam() {
        setTitle("Exam System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton exitButton = new JButton("Exit");

        add(loginButton);
        add(registerButton);
        add(exitButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);
    }

    private void login(String updatedUsername, String updatedPassword) {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setLayout(new FlowLayout());
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginFrame.add(new JLabel("Username:"));
        loginFrame.add(usernameField);
        loginFrame.add(new JLabel("Password:"));
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (username.equals(createdUsername) && password.equals(createdPass)) {
                    JOptionPane.showMessageDialog(null, "Login successful");
                    loginFrame.dispose(); // Close login window
                    showOptions(); // Show options after successful login
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong information");
                }
            }
        });

        loginFrame.setVisible(true);
    }

    private void showOptions() {
        JFrame optionsFrame = new JFrame("Options");
        optionsFrame.setSize(300, 200);
        optionsFrame.setLayout(new FlowLayout());
        optionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton updateProfileButton = new JButton("Update Profile");
        JButton giveTestButton = new JButton("Give Test");

        optionsFrame.add(updateProfileButton);
        optionsFrame.add(giveTestButton);

        updateProfileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add logic to update profile
                optionsFrame.dispose();
                showProfileOptions();
            }
        });

        giveTestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionsFrame.dispose(); // Close options window
                startTest(); // Start the test
            }
        });

        optionsFrame.setVisible(true);
    }

    private void showProfileOptions() {
        JFrame profileFrame = new JFrame("Update Profile");
        profileFrame.setSize(300, 200);
        profileFrame.setLayout(new FlowLayout());
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField nameField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton submitButton = new JButton("Submit");
        profileFrame.add(new JLabel("Name:"));
        profileFrame.add(nameField);
        profileFrame.add(new JLabel("Username:"));
        profileFrame.add(usernameField);
        profileFrame.add(new JLabel("Password:"));
        profileFrame.add(passwordField);
        profileFrame.add(submitButton);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createdUsername = usernameField.getText();
                createdPass = new String(passwordField.getPassword());
                JOptionPane.showMessageDialog(null, "Profile updated successfully");
                profileFrame.dispose();
                dispose();
                login(createdUsername, createdPass);
            }
        });
        // Add your profile update components here, such as text fields, buttons, etc.

        profileFrame.setVisible(true);
    }

    private int[] userSelection;

    private void startTest() {
        JFrame testFrame = new JFrame("Test");
        testFrame.setSize(600, 400);
        testFrame.setLayout(new BoxLayout(testFrame.getContentPane(), BoxLayout.Y_AXIS));
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel timerLabel = new JLabel("Time Left: 20 seconds");
        testFrame.add(timerLabel);
        String[] questions = {
                "1.What is the capital of India?",
                "2.Is 43 divisible by 5?",
                "3.Which is the closest planet to the sun?"
        };

        String[][] options = {
                { "New Delhi", "Mumbai", "Ahmedabad", "Indore" },
                { "Yes", "No", "Cannot determine" },
                { "Mars", "Neptune", "Jupiter", "Mercury" }
        };

        int[] answers = { 1, 2, 4 };
        userSelection = new int[questions.length];
        questionsAnswered = 0;
        for (int i = 0; i < questions.length; i++) {
            JLabel questionLabel = new JLabel(questions[i]);
            JPanel optionsPanel = new JPanel();

            for (int j = 0; j < options[i].length; j++) {
                JRadioButton optionButton = new JRadioButton(options[i][j]);
                final int optionIndex = j + 1;
                final int questionIndex = i;
                optionButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Handle selection of the option
                        userSelection[questionIndex] = optionIndex;
                    }
                });
                optionsPanel.add(optionButton);
            }

            testFrame.add(questionLabel);
            testFrame.add(optionsPanel);
        }
        startTimer(testFrame, timerLabel);

        JButton submitButton = new JButton("Submit");
        testFrame.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Handle submission of the test
                // Calculate score, etc.
                int correct = 0;
                int wrong = 0;
                for (int i = 0; i < questions.length; i++) {
                    if (userSelection[i] == answers[i]) {
                        correct++;
                    } else {
                        wrong++;
                    }
                }
                JOptionPane.showMessageDialog(null, "Test submitted" + "\nCorrect:" + correct + "\nWrong:" + wrong);
                JButton logoutButton = new JButton("Logout");
                JButton giveTestAgainButton = new JButton("Give Test Again");
                testFrame.add(logoutButton);
                testFrame.add(giveTestAgainButton);
                logoutButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        displayLoginPage();
                        // code for logout functionality
                    }
                });
                giveTestAgainButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        testFrame.dispose();
                        startTest(); // Start the test again
                    }
                });
                testFrame.getContentPane().revalidate();
                testFrame.getContentPane().repaint();

                timer.cancel();
            }
        });

        testFrame.setVisible(true);
    }

    private void displayLoginPage() {
        // Dispose the current frame
        dispose();
        // Create a new instance of Exam to display the login page
        new Exam();
    }

    private void startTimer(JFrame frame, JLabel timerLabel) {
        timeLeft = 20; // Set the time limit for each question
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                timeLeft--;
                if (timeLeft >= 0) {
                    timerLabel.setText("Time Left: " + timeLeft + " seconds");
                } else {
                    timer.cancel();
                    JOptionPane.showMessageDialog(frame, "Time's up!");

                    // Handle when time's up
                }
            }
        }, 0, 1000); // Run the timer every second
    }

    private void register() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(300, 200);
        registerFrame.setLayout(new FlowLayout());
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextField nameField = new JTextField(20);
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton registerButton = new JButton("Register");

        registerFrame.add(new JLabel("Name:"));
        registerFrame.add(nameField);
        registerFrame.add(new JLabel("Username:"));
        registerFrame.add(usernameField);
        registerFrame.add(new JLabel("Password:"));
        registerFrame.add(passwordField);
        registerFrame.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createdUsername = usernameField.getText();
                createdPass = new String(passwordField.getPassword());
                JOptionPane.showMessageDialog(null, "Registration successful");
                login(createdUsername, createdPass);
            }
        });

        registerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Exam();
    }
}
