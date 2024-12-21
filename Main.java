
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class Main {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    
    private JTextField regUsernameField;
    private JPasswordField regPasswordField;

    
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    
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
        
        frame = new JFrame("Voting Application");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(245, 245, 245)); 

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        
        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createRegisterPanel(), "Register");
        cardPanel.add(createVotingPanel(), "Voting");

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);

        
        cardLayout.show(cardPanel, "Login");
    }

    
    private JPanel createRegisterPanel() {
        JPanel registerPanel = new JPanel();
        registerPanel.setLayout(new GridBagLayout());
        registerPanel.setBackground(new Color(233, 233, 255)); 
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
        registerButton.setBackground(new Color(0, 123, 255)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> handleRegistration());

        
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

    
    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setBackground(new Color(255, 250, 230)); 
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
        loginButton.setBackground(new Color(0, 123, 255)); 
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(e -> handleLogin());

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 16));
        registerButton.setBackground(new Color(0, 204, 255)); 
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(e -> cardLayout.show(cardPanel, "Register"));

        
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

    
    private JPanel createVotingPanel() {
        JPanel votingPanel = new JPanel();
        votingPanel.setLayout(new BorderLayout(20, 20));
        votingPanel.setBackground(new Color(255, 255, 255)); 

        JLabel headingLabel = new JLabel("Select a Candidate to Vote For:");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headingLabel.setForeground(new Color(0, 123, 255)); 
        headingLabel.setHorizontalAlignment(SwingConstants.CENTER);

        
        candidateList = new JList<>(candidates);
        candidateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        candidateList.setFont(new Font("Arial", Font.PLAIN, 16));
        candidateList.setBackground(new Color(240, 240, 255)); 
        JScrollPane scrollPane = new JScrollPane(candidateList);

        
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); 
        bottomPanel.setBackground(new Color(255, 255, 255)); 

        submitButton = new JButton("Cast Vote");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(0, 204, 0)); 
        submitButton.setForeground(Color.WHITE);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> handleVoting());
        bottomPanel.add(submitButton); 

        
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 14));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(new Color(255, 102, 102)); 
        bottomPanel.add(statusLabel); 

        
        logoutButton = new JButton("Log Out");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.addActionListener(e -> handleLogout());
        logoutButton.setVisible(false); 
        bottomPanel.add(logoutButton); 

        
        votingPanel.add(headingLabel, BorderLayout.NORTH);
        votingPanel.add(scrollPane, BorderLayout.CENTER);
        votingPanel.add(bottomPanel, BorderLayout.SOUTH); 

        return votingPanel;
    }

    
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

    private void handleLogin() {
        String username = loginUsernameField.getText().trim(); 
        String password = new String(loginPasswordField.getPassword()).trim(); 

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

    
    private void handleVoting() {
        if (hasVoted) {
            JOptionPane.showMessageDialog(frame, "You have already voted!", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int selectedIndex = candidateList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedCandidate = candidates[selectedIndex];
            statusLabel.setText("You have voted for: " + selectedCandidate);
            submitButton.setEnabled(false); 
            hasVoted = true; 
            logoutButton.setVisible(true); 
            JOptionPane.showMessageDialog(frame, "Vote Submitted Successfully!", "Thank You", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a candidate to vote for.", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }

   
    private void handleLogout() {
        loggedInUser = null;
        hasVoted = false;
        cardLayout.show(cardPanel, "Login");
        resetForm();
    }

    
    private void resetForm() {
        submitButton.setEnabled(true);
        statusLabel.setText("");
        logoutButton.setVisible(false); 
    }
}
