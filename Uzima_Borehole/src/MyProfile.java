import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MyProfile extends JFrame {
    
    private int userId;
    private int clientId;
    private String fullName;
    private String category;
    
    // UI Components
    private JTextField txtFullName, txtTelephone, txtEmail, txtAddress, txtBoreholeLocation;
    private JComboBox<String> cmbGender, cmbCategory;
    private JLabel lblClientId, lblTotalProjects, lblTotalSpent;
    private JButton btnUpdate, btnChangePassword, btnBack;
    private JPanel formPanel; // Panel for the scrollable content
    
    public MyProfile(int userId, int clientId, String fullName, String category) {
        this.userId = userId;
        this.clientId = clientId;
        this.fullName = fullName;
        this.category = category;
        
        initComponents();
        loadProfileData();
        loadStatistics();
        
        setTitle("My Profile - Uzima Borehole");
        setSize(750, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        // Main container with BorderLayout
        setLayout(new BorderLayout());
        
        // ========== HEADER ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        headerPanel.setPreferredSize(new Dimension(750, 70));
        
        JLabel lblTitle = new JLabel("MY PROFILE");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // ========== MAIN CONTENT AREA (Scrollable) ==========
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        
        // ========== PERSONAL INFORMATION SECTION ==========
        JLabel lblPersonalInfo = new JLabel("PERSONAL INFORMATION");
        lblPersonalInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPersonalInfo.setForeground(new Color(41, 128, 185));
        lblPersonalInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPersonalInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        formPanel.add(lblPersonalInfo);
        
        // Full Name
        formPanel.add(createLabelFieldPanel("Full Name:", txtFullName = createTextField()));
        
        // Gender
        JPanel genderPanel = new JPanel(new BorderLayout());
        genderPanel.setBackground(Color.WHITE);
        genderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        genderPanel.setMaximumSize(new Dimension(680, 60));
        
        JLabel genderLabel = createLabel("Gender:");
        genderPanel.add(genderLabel, BorderLayout.NORTH);
        
        cmbGender = new JComboBox<>(new String[]{"Male", "Female"});
        cmbGender.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbGender.setPreferredSize(new Dimension(680, 35));
        cmbGender.setMaximumSize(new Dimension(680, 35));
        cmbGender.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        genderPanel.add(cmbGender, BorderLayout.CENTER);
        
        formPanel.add(genderPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Telephone
        formPanel.add(createLabelFieldPanel("Telephone:", txtTelephone = createTextField()));
        
        // Email
        formPanel.add(createLabelFieldPanel("Email:", txtEmail = createTextField()));
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // ========== BUSINESS INFORMATION SECTION ==========
        JLabel lblBusinessInfo = new JLabel("BUSINESS INFORMATION");
        lblBusinessInfo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblBusinessInfo.setForeground(new Color(41, 128, 185));
        lblBusinessInfo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblBusinessInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        formPanel.add(lblBusinessInfo);
        
        // Client ID (read-only)
        JPanel clientIdPanel = new JPanel(new BorderLayout());
        clientIdPanel.setBackground(Color.WHITE);
        clientIdPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        clientIdPanel.setMaximumSize(new Dimension(680, 60));
        
        JLabel clientIdLabel = createLabel("Client ID:");
        clientIdPanel.add(clientIdLabel, BorderLayout.NORTH);
        
        lblClientId = new JLabel("#" + String.format("%05d", clientId));
        lblClientId.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblClientId.setForeground(new Color(52, 73, 94));
        lblClientId.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        lblClientId.setBackground(new Color(240, 240, 240));
        lblClientId.setOpaque(true);
        clientIdPanel.add(lblClientId, BorderLayout.CENTER);
        
        formPanel.add(clientIdPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Address
        formPanel.add(createLabelFieldPanel("Address:", txtAddress = createTextField()));
        
        // Client Category
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setBackground(Color.WHITE);
        categoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        categoryPanel.setMaximumSize(new Dimension(680, 60));
        
        JLabel categoryLabel = createLabel("Client Category:");
        categoryPanel.add(categoryLabel, BorderLayout.NORTH);
        
        cmbCategory = new JComboBox<>(new String[]{"Industrial", "Commercial", "Domestic"});
        cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbCategory.setPreferredSize(new Dimension(680, 35));
        cmbCategory.setMaximumSize(new Dimension(680, 35));
        cmbCategory.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        categoryPanel.add(cmbCategory, BorderLayout.CENTER);
        
        formPanel.add(categoryPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        
        // Borehole Location
        formPanel.add(createLabelFieldPanel("Borehole Location:", txtBoreholeLocation = createTextField()));
        
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // ========== STATISTICS SECTION ==========
        JLabel lblStats = new JLabel("ACCOUNT STATISTICS");
        lblStats.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblStats.setForeground(new Color(41, 128, 185));
        lblStats.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblStats.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        formPanel.add(lblStats);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        statsPanel.setBackground(new Color(236, 240, 241));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statsPanel.setMaximumSize(new Dimension(680, 80));
        
        lblTotalProjects = new JLabel("Total Projects: 0");
        lblTotalProjects.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalProjects.setForeground(new Color(52, 73, 94));
        
        lblTotalSpent = new JLabel("Total Spent: Ksh 0.00");
        lblTotalSpent.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalSpent.setForeground(new Color(52, 73, 94));
        
        statsPanel.add(lblTotalProjects);
        statsPanel.add(lblTotalSpent);
        
        formPanel.add(statsPanel);
        formPanel.add(Box.createVerticalGlue()); // Pushes everything up
        
        // Scroll pane for the form content
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        add(scrollPane, BorderLayout.CENTER);
        
        // ========== BUTTONS PANEL (Fixed at bottom) ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        
        btnUpdate = createStyledButton("Update Profile", new Color(46, 204, 113));
        btnChangePassword = createStyledButton("Change Password", new Color(52, 152, 219));
        btnBack = createStyledButton("← Back", new Color(149, 165, 166));
        
        btnUpdate.addActionListener(e -> updateProfile());
        btnChangePassword.addActionListener(e -> changePassword());
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnChangePassword);
        buttonPanel.add(btnBack);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Helper method to create label + field panels with proper alignment
    private JPanel createLabelFieldPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(680, 60));
        
        JLabel label = createLabel(labelText);
        panel.add(label, BorderLayout.NORTH);
        
        textField.setMaximumSize(new Dimension(680, 35));
        panel.add(textField, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(70, 70, 70));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }
    
    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return textField;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 45));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void loadProfileData() {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Load user data
            String userQuery = "SELECT full_name, gender, Telephone_Number, email FROM users WHERE UserID = ?";
            PreparedStatement pst1 = conn.prepareStatement(userQuery);
            pst1.setInt(1, userId);
            ResultSet rs1 = pst1.executeQuery();
            
            if (rs1.next()) {
                txtFullName.setText(rs1.getString("full_name"));
                cmbGender.setSelectedItem(rs1.getString("gender"));
                txtTelephone.setText(rs1.getString("Telephone_Number"));
                txtEmail.setText(rs1.getString("email"));
            }
            
            // Load client data
            String clientQuery = "SELECT Address, Client_category, Borehole_location FROM clients WHERE ClientID = ?";
            PreparedStatement pst2 = conn.prepareStatement(clientQuery);
            pst2.setInt(1, clientId);
            ResultSet rs2 = pst2.executeQuery();
            
            if (rs2.next()) {
                txtAddress.setText(rs2.getString("Address"));
                cmbCategory.setSelectedItem(rs2.getString("Client_category"));
                String boreholeLocation = rs2.getString("Borehole_location");
                txtBoreholeLocation.setText(boreholeLocation != null ? boreholeLocation : "");
            }
            
            rs1.close();
            rs2.close();
            pst1.close();
            pst2.close();
            conn.close();
            
            System.out.println("✓ Profile data loaded successfully");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading profile:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void loadStatistics() {
        try {
            Connection conn = DBConnection.getConnection();
            
            String statsQuery = "SELECT COUNT(*) as total_projects, SUM(Grand_total) as total_spent " +
                              "FROM client_projects WHERE ClientID = ?";
            PreparedStatement pst = conn.prepareStatement(statsQuery);
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int totalProjects = rs.getInt("total_projects");
                double totalSpent = rs.getDouble("total_spent");
                
                lblTotalProjects.setText("Total Projects: " + totalProjects);
                lblTotalSpent.setText("Total Spent: Ksh " + String.format("%,.2f", totalSpent));
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void updateProfile() {
        // Validation
        if (txtFullName.getText().trim().isEmpty() || txtTelephone.getText().trim().isEmpty() || 
            txtEmail.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill all required fields!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Update user table
            String userQuery = "UPDATE users SET full_name = ?, gender = ?, Telephone_Number = ?, email = ? WHERE UserID = ?";
            PreparedStatement pst1 = conn.prepareStatement(userQuery);
            pst1.setString(1, txtFullName.getText().trim());
            pst1.setString(2, (String) cmbGender.getSelectedItem());
            pst1.setString(3, txtTelephone.getText().trim());
            pst1.setString(4, txtEmail.getText().trim());
            pst1.setInt(5, userId);
            pst1.executeUpdate();
            
            // Update clients table
            String clientQuery = "UPDATE clients SET Address = ?, Client_category = ?, Borehole_location = ? WHERE ClientID = ?";
            PreparedStatement pst2 = conn.prepareStatement(clientQuery);
            pst2.setString(1, txtAddress.getText().trim());
            pst2.setString(2, (String) cmbCategory.getSelectedItem());
            pst2.setString(3, txtBoreholeLocation.getText().trim());
            pst2.setInt(4, clientId);
            pst2.executeUpdate();
            
            pst1.close();
            pst2.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Profile updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            System.out.println("✓ Profile updated for user: " + userId);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error updating profile:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void changePassword() {
        JPasswordField oldPassword = new JPasswordField();
        JPasswordField newPassword = new JPasswordField();
        JPasswordField confirmPassword = new JPasswordField();
        
        Object[] message = {
            "Current Password:", oldPassword,
            "New Password:", newPassword,
            "Confirm New Password:", confirmPassword
        };
        
        int option = JOptionPane.showConfirmDialog(this, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            String oldPass = new String(oldPassword.getPassword());
            String newPass = new String(newPassword.getPassword());
            String confirmPass = new String(confirmPassword.getPassword());
            
            if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }
            
            if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(this, "New passwords do not match!");
                return;
            }
            
            if (newPass.length() < 6) {
                JOptionPane.showMessageDialog(this, "New password must be at least 6 characters!");
                return;
            }
            
            try {
                Connection conn = DBConnection.getConnection();
                
                // Verify old password
                String checkQuery = "SELECT password FROM users WHERE UserID = ?";
                PreparedStatement pst1 = conn.prepareStatement(checkQuery);
                pst1.setInt(1, userId);
                ResultSet rs = pst1.executeQuery();
                
                if (rs.next()) {
                    String currentPassword = rs.getString("password");
                    
                    if (!currentPassword.equals(oldPass)) {
                        JOptionPane.showMessageDialog(this, "Current password is incorrect!");
                        rs.close();
                        pst1.close();
                        conn.close();
                        return;
                    }
                }
                
                // Update password
                String updateQuery = "UPDATE users SET password = ? WHERE UserID = ?";
                PreparedStatement pst2 = conn.prepareStatement(updateQuery);
                pst2.setString(1, newPass);
                pst2.setInt(2, userId);
                pst2.executeUpdate();
                
                rs.close();
                pst1.close();
                pst2.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this,
                    "Password changed successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this,
                    "Error changing password:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyProfile(1, 1, "Test Client", "Domestic").setVisible(true);
        });
    }
}