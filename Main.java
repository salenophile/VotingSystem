
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    // Register Panel components
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;

    // Login Panel components
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // Voting Panel components
    private JList<String> candidateList;
    private JButton submitButton;
    private JButton logoutButton;
    private JLabel statusLabel;
    private String[] candidates = {
            "Alice Johnson", "Bob Smith", "Charlie Davis", "David Lee", "Eva Green",
            "Frank Harris", "Grace White", "Hank Brown", "Ivy Parker", "Jack Taylor"
    };

    private HashMap<String, String> users = new HashMap<>();
    private String loggedInUser = null;
    private boolean hasVoted = false;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main window = new Main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        // Create the main frame with title and layout
        frame = new JFrame("Voting Application");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Light background color

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Add the login, registration, and voting panels
        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createRegisterPanel(), "Register");
        cardPanel.add(createVotingPanel(), "Voting");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        // Show the login screen first
        cardLayout.show(cardPanel, "Login");
    }

    // Register Panel with modern UI
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(233, 233, 255)); // Soft light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel regUsernameLabel = new JLabel("Username:");
        regUsernameField = new JTextField(20);
        regUsernameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel regPasswordLabel = new JLabel("Password:");
        regPasswordField = new JPasswordField(20);
        regPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(0, 123, 255)); // Bright blue button
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> handleRegistration());

        // GridBagLayout positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        registerPanel.add(regUsernameLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(regUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        registerPanel.add(regPasswordLabel, gbc);

        gbc.gridx = 1;
        registerPanel.add(regPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        registerPanel.add(registerButton, gbc);

        return registerPanel;
    }

    // Login Panel with sleek UI
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 250, 230)); // Warm light color
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel loginUsernameLabel = new JLabel("Username:");
        loginUsernameField = new JTextField(20);
        loginUsernameField.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel loginPasswordLabel = new JLabel("Password:");
        loginPasswordField = new JPasswordField(20);
        loginPasswordField.setFont(new Font("Arial", Font.PLAIN, 16));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(new Color(0, 123, 255)); // Bright blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(0, 204, 255)); // Light cyan
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));

        // GridBagLayout positioning
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(loginUsernameLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(loginUsernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(loginPasswordLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(loginPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        gbc.gridy = 3;
        loginPanel.add(registerButton, gbc);

        return loginPanel;
    }

    // Voting Panel with enhanced UI design
    private JPanel createVotingPanel() {
        JPanel votingPanel = new JPanel();
        votingPanel.setLayout(new BorderLayout(20, 20));
        votingPanel.setBackground(new Color(255, 255, 255)); // Clean white background

        JLabel headingLabel = new JLabel("Select a Candidate to Vote For:");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(new Color(0, 123, 255)); // Blue color
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Create the list of candidates with a scroll pane
        candidateList = new JList<>(candidates);
        candidateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        candidateList.setFont(new Font("Arial", Font.PLAIN, 16));
        candidateList.setBackground(new Color(240, 240, 255)); // Light blue
        JScrollPane scrollPane = new JScrollPane(candidateList);

        // Create a JPanel for the buttons at the bottom
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // Stack buttons vertically
        bottomPanel.setBackground(new Color(255, 255, 255)); // Match the background color

        submitButton = new JButton("Cast Vote");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(0, 204, 0)); // Green button
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> handleVoting());
        bottomPanel.add(submitButton); // Add Cast Vote button to the bottom panel

        // Add status label
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(new Color(255, 102, 102)); // Red color for warnings
        bottomPanel.add(statusLabel); // Add status label

        // Log Out Button
        logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(255, 69, 0)); // Red button
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());
        logoutButton.setVisible(false); // Initially hidden
        bottomPanel.add(logoutButton); // Add Log Out button to the bottom panel

        // Add all components to the main voting panel
        votingPanel.add(headingLabel, BorderLayout.NORTH);
        votingPanel.add(scrollPane, BorderLayout.CENTER);
        votingPanel.add(bottomPanel, BorderLayout.SOUTH); // Add button panel to the bottom of the layout

        return votingPanel;
    }

    // Handle user registration
    private void handleRegistration() {
        String username = regUsernameField.getText();
        String password = new String(regPasswordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Both fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(frame, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            users.put(username, password);
            JOptionPane.showMessageDialog(frame, "Registration Successful!");
            cardLayout.show(cardPanel, "Login");
        }
    }

    // Handle login and switch to the voting panel
    private void handleLogin() {
        String username = loginUsernameField.getText().trim(); // Trim to avoid leading/trailing spaces
        String password = new String(loginPasswordField.getPassword()).trim(); // Trim password as well

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter both username and password.", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (users.containsKey(username) && users.get(username).equals(password)) {
            loggedInUser = username;
            JOptionPane.showMessageDialog(frame, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(cardPanel, "Voting");
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Handle voting action
    private void handleVoting() {
        if (hasVoted) {
            JOptionPane.showMessageDialog(frame, "You have already voted!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedIndex = candidateList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedCandidate = candidates[selectedIndex];
            statusLabel.setText("You have voted for: " + selectedCandidate);
            submitButton.setEnabled(false); // Disable the submit button after voting
            hasVoted = true; // Mark that the user has voted
            logoutButton.setVisible(true); // Show the logout button
            JOptionPane.showMessageDialog(frame, "Vote Submitted Successfully!", "Thank You", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a candidate to vote for.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    // Handle logout action
    private void handleLogout() {
        loggedInUser = null;
        hasVoted = false;
        cardLayout.show(cardPanel, "Login");
        resetForm();
    }

    // Reset form and prepare for next use
    private void resetForm() {
        submitButton.setEnabled(true);
        statusLabel.setText("");
        logoutButton.setVisible(false); // Hide the logout button again
    }
}