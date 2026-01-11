import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserManagement extends JFrame {
    
    private int adminId;
    private String adminName;
    private JTable adminTable;
    private DefaultTableModel tableModel;
    
    public UserManagement(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        loadAdminData();
        
        setTitle("User Management - Uzima Borehole");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("USER MANAGEMENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setBackground(new Color(44, 62, 80));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(btnBack, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(236, 240, 241));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        infoPanel.setBackground(new Color(217, 237, 247));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblInfo = new JLabel("Manage admin and staff user accounts. Add, edit, or remove users.");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setForeground(new Color(102, 102, 102));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadAdminData());
        
        infoPanel.add(lblInfo);
        infoPanel.add(btnRefresh);
        
        contentPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columns = {"Admin ID", "User ID", "Full Name", "Email", "Phone", "Created Date"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        adminTable = new JTable(tableModel);
        adminTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        adminTable.setRowHeight(30);
        adminTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        adminTable.getTableHeader().setBackground(new Color(52, 73, 94));
        adminTable.getTableHeader().setForeground(Color.BLACK); // Changed from WHITE to BLACK
        adminTable.setSelectionBackground(new Color(52, 73, 94));
        adminTable.setSelectionForeground(Color.WHITE);
        adminTable.setGridColor(new Color(189, 195, 199));
        
        JScrollPane scrollPane = new JScrollPane(adminTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Action Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAddAdmin = new JButton("Add New Admin");
        btnAddAdmin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAddAdmin.setBackground(new Color(46, 204, 113));
        btnAddAdmin.setForeground(Color.WHITE);
        btnAddAdmin.setFocusPainted(false);
        btnAddAdmin.setBorderPainted(false);
        btnAddAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAddAdmin.setPreferredSize(new Dimension(180, 45));
        btnAddAdmin.addActionListener(e -> addNewAdmin());
        
        JButton btnViewDetails = new JButton("View Details");
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnViewDetails.setBackground(new Color(52, 152, 219));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.setBorderPainted(false);
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetails.setPreferredSize(new Dimension(180, 45));
        btnViewDetails.addActionListener(e -> viewAdminDetails());
        
        JButton btnResetPassword = new JButton("Reset Password");
        btnResetPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnResetPassword.setBackground(new Color(241, 196, 15));
        btnResetPassword.setForeground(Color.WHITE);
        btnResetPassword.setFocusPainted(false);
        btnResetPassword.setBorderPainted(false);
        btnResetPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnResetPassword.setPreferredSize(new Dimension(180, 45));
        btnResetPassword.addActionListener(e -> resetPassword());
        
        JButton btnDelete = new JButton("Delete Admin");
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new Dimension(180, 45));
        btnDelete.addActionListener(e -> deleteAdmin());
        
        buttonPanel.add(btnAddAdmin);
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnResetPassword);
        buttonPanel.add(btnDelete);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void loadAdminData() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT a.admin_id, a.user_id, u.Full_Name, u.Email, " +
                        "u.Telephone_Number, a.created_at " +
                        "FROM admins a " +
                        "JOIN users u ON a.user_id = u.UserID " +
                        "ORDER BY a.admin_id";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("admin_id"),
                    rs.getInt("user_id"),
                    rs.getString("Full_Name"),
                    rs.getString("Email"),
                    rs.getString("Telephone_Number"),
                    rs.getTimestamp("created_at")
                };
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading admin data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void addNewAdmin() {
        JTextField txtFullName = new JTextField(25);
        JTextField txtEmail = new JTextField(25);
        JTextField txtPhone = new JTextField(25);
        JComboBox<String> cmbGender = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        JPasswordField txtPassword = new JPasswordField(25);
        JPasswordField txtConfirmPassword = new JPasswordField(25);
        
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Full Name:"));
        inputPanel.add(txtFullName);
        inputPanel.add(new JLabel("Gender:"));
        inputPanel.add(cmbGender);
        inputPanel.add(new JLabel("Email:"));
        inputPanel.add(txtEmail);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(txtPhone);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(txtPassword);
        inputPanel.add(new JLabel("Confirm Password:"));
        inputPanel.add(txtConfirmPassword);
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel, 
            "Add New Admin User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String fullName = txtFullName.getText().trim();
            String email = txtEmail.getText().trim();
            String phone = txtPhone.getText().trim();
            String gender = (String) cmbGender.getSelectedItem();
            String password = new String(txtPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            
            // Validation
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Connection conn = DBConnection.getConnection();
                
                // Check if email already exists
                String checkSql = "SELECT COUNT(*) FROM users WHERE Email = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setString(1, email);
                ResultSet rs = checkStmt.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Email already exists!",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE);
                    rs.close();
                    checkStmt.close();
                    conn.close();
                    return;
                }
                rs.close();
                checkStmt.close();
                
                // Insert into users table
                String userSql = "INSERT INTO users (Full_Name, Gender, Telephone_Number, Email, Password) " +
                               "VALUES (?, ?, ?, ?, ?)";
                PreparedStatement userStmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
                userStmt.setString(1, fullName);
                userStmt.setString(2, gender);
                userStmt.setString(3, phone);
                userStmt.setString(4, email);
                userStmt.setString(5, password); // In production, hash this!
                userStmt.executeUpdate();
                
                // Get generated user ID
                ResultSet generatedKeys = userStmt.getGeneratedKeys();
                int userId = 0;
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1);
                }
                generatedKeys.close();
                userStmt.close();
                
                // Insert into admins table
                String adminSql = "INSERT INTO admins (user_id) VALUES (?)";
                PreparedStatement adminStmt = conn.prepareStatement(adminSql);
                adminStmt.setInt(1, userId);
                adminStmt.executeUpdate();
                adminStmt.close();
                
                conn.close();
                
                JOptionPane.showMessageDialog(this,
                    "New admin user created successfully!\n\nUser ID: " + userId,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadAdminData();
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error creating admin user: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewAdminDetails() {
        int selectedRow = adminTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an admin to view details.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selectedRow, 1);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT u.*, a.admin_id, a.created_at as admin_created " +
                        "FROM users u " +
                        "JOIN admins a ON u.UserID = a.user_id " +
                        "WHERE u.UserID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String details = String.format(
                    "═══════════════════════════════════════════════\n" +
                    "              ADMIN USER DETAILS\n" +
                    "═══════════════════════════════════════════════\n\n" +
                    "Admin ID:        %d\n" +
                    "User ID:         %d\n" +
                    "Full Name:       %s\n" +
                    "Gender:          %s\n" +
                    "Email:           %s\n" +
                    "Phone:           %s\n" +
                    "Admin Since:     %s\n" +
                    "\n═══════════════════════════════════════════════",
                    rs.getInt("admin_id"),
                    rs.getInt("UserID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getString("Email"),
                    rs.getString("Telephone_Number"),
                    rs.getTimestamp("admin_created")
                );
                
                JTextArea textArea = new JTextArea(details);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(450, 300));
                
                JOptionPane.showMessageDialog(this, scrollPane, 
                    "Admin Details", JOptionPane.INFORMATION_MESSAGE);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading admin details: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void resetPassword() {
        int selectedRow = adminTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an admin to reset password.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selectedRow, 1);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        
        JPasswordField txtNewPassword = new JPasswordField(25);
        JPasswordField txtConfirmPassword = new JPasswordField(25);
        
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Admin:"));
        inputPanel.add(new JLabel(fullName));
        inputPanel.add(new JLabel("New Password:"));
        inputPanel.add(txtNewPassword);
        inputPanel.add(new JLabel("Confirm Password:"));
        inputPanel.add(txtConfirmPassword);
        
        int result = JOptionPane.showConfirmDialog(this, inputPanel, 
            "Reset Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newPassword = new String(txtNewPassword.getPassword());
            String confirmPassword = new String(txtConfirmPassword.getPassword());
            
            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Password cannot be empty!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                    "Passwords do not match!",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "UPDATE users SET Password = ? WHERE UserID = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newPassword); // In production, hash this!
                pstmt.setInt(2, userId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this,
                    "Password reset successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error resetting password: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void deleteAdmin() {
        int selectedRow = adminTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an admin to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int selectedAdminId = (int) tableModel.getValueAt(selectedRow, 0);
        int userId = (int) tableModel.getValueAt(selectedRow, 1);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        
        // Prevent deleting yourself
        if (selectedAdminId == this.adminId) {
            JOptionPane.showMessageDialog(this,
                "You cannot delete your own admin account!",
                "Action Not Allowed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete admin:\n\n" +
            fullName + " (Admin ID: " + selectedAdminId + ")?\n\n" +
            "WARNING: This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Delete from admins table first
            String sql1 = "DELETE FROM admins WHERE admin_id = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, selectedAdminId);
            pstmt1.executeUpdate();
            pstmt1.close();
            
            // Delete from users table
            String sql2 = "DELETE FROM users WHERE UserID = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, userId);
            pstmt2.executeUpdate();
            pstmt2.close();
            
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Admin user deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadAdminData();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error deleting admin: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}