import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;

public class AdminClientManagement extends JFrame {
    
    private int adminId;
    private String adminName;
    private JTable clientTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    
    public AdminClientManagement(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        loadClientData();
        
        setTitle("Client Management - Uzima Borehole");
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(236, 240, 241));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER PANEL ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(52, 152, 219)); // Blue
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(52, 152, 219));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel lblTitle = new JLabel("CLIENT MANAGEMENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Manage all client accounts and information");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(230, 230, 230));
        
        titlePanel.add(lblTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(lblSubtitle);
        
        JLabel lblAdmin = new JLabel("Admin: " + adminName);
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblAdmin.setForeground(Color.WHITE);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(lblAdmin, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== INFO PANEL ==========
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblInfo = new JLabel("Select a client to view details, projects, or delete");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblInfo.setForeground(new Color(127, 140, 141));
        
        infoPanel.add(lblInfo);
        
        // ========== SEARCH PANEL ==========
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        txtSearch = new JTextField(30);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(300, 40));
        
        JButton btnSearch = createStyledButton("Search", new Color(52, 152, 219));
        btnSearch.addActionListener(e -> searchClients());
        
        JButton btnRefresh = createStyledButton("Refresh", new Color(46, 204, 113));
        btnRefresh.addActionListener(e -> loadClientData());
        
        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        searchPanel.add(btnRefresh);
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Table setup
        String[] columns = {"Client ID", "User ID", "Full Name", "Gender", "Phone", "Email", 
                           "Category", "Address", "Borehole Location"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        clientTable = new JTable(tableModel);
        setupTable(clientTable);
        
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // ========== CENTER CONTAINER ==========
        JPanel centerContainer = new JPanel(new BorderLayout(0, 10));
        centerContainer.setBackground(new Color(236, 240, 241));
        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(searchPanel, BorderLayout.CENTER);
        
        // ========== ACTION BUTTONS PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnViewDetails = createStyledButton("View Details", new Color(52, 152, 219));
        btnViewDetails.addActionListener(e -> viewClientDetails());
        
        JButton btnViewProjects = createStyledButton("View Projects", new Color(46, 204, 113));
        btnViewProjects.addActionListener(e -> viewClientProjects());
        
        JButton btnDelete = createStyledButton("Delete Client", new Color(231, 76, 60));
        btnDelete.addActionListener(e -> deleteClient());
        
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnViewProjects);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        
        // ========== ASSEMBLE MAIN LAYOUT ==========
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(236, 240, 241));
        contentPanel.add(centerContainer, BorderLayout.NORTH);
        contentPanel.add(tablePanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
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
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;
            
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupTable(JTable table) {
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240)); // Changed to light gray
        table.getTableHeader().setForeground(Color.BLACK); // Changed to black
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Center align ID columns
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Center align ID columns (Client ID and User ID)
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setCellRenderer(centerRenderer); // Client ID
            columnModel.getColumn(1).setCellRenderer(centerRenderer); // User ID
            columnModel.getColumn(3).setCellRenderer(centerRenderer); // Gender
            columnModel.getColumn(6).setCellRenderer(centerRenderer); // Category
        }
        
        // Set column widths
        columnModel.getColumn(0).setPreferredWidth(100); // Client ID
        columnModel.getColumn(1).setPreferredWidth(80);  // User ID
        columnModel.getColumn(2).setPreferredWidth(200); // Full Name
        columnModel.getColumn(3).setPreferredWidth(80);  // Gender
        columnModel.getColumn(4).setPreferredWidth(120); // Phone
        columnModel.getColumn(5).setPreferredWidth(200); // Email
        columnModel.getColumn(6).setPreferredWidth(120); // Category
        columnModel.getColumn(7).setPreferredWidth(250); // Address
        columnModel.getColumn(8).setPreferredWidth(250); // Borehole Location
    }
    
    private void loadClientData() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT c.ClientID, c.UserID, u.Full_Name, u.Gender, u.Telephone_Number, " +
                        "u.Email, c.Client_category, c.Address, c.Borehole_location " +
                        "FROM clients c " +
                        "JOIN users u ON c.UserID = u.UserID " +
                        "ORDER BY c.ClientID DESC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ClientID"),
                    rs.getInt("UserID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getString("Telephone_Number"),
                    rs.getString("Email"),
                    rs.getString("Client_category"),
                    rs.getString("Address"),
                    rs.getString("Borehole_location")
                };
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading client data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void searchClients() {
        String searchTerm = txtSearch.getText().trim();
        
        if (searchTerm.isEmpty()) {
            loadClientData();
            return;
        }
        
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT c.ClientID, c.UserID, u.Full_Name, u.Gender, u.Telephone_Number, " +
                        "u.Email, c.Client_category, c.Address, c.Borehole_location " +
                        "FROM clients c " +
                        "JOIN users u ON c.UserID = u.UserID " +
                        "WHERE u.Full_Name LIKE ? OR u.Telephone_Number LIKE ? OR u.Email LIKE ? " +
                        "OR c.Client_category LIKE ? OR c.ClientID LIKE ? " +
                        "ORDER BY c.ClientID DESC";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            String searchPattern = "%" + searchTerm + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            pstmt.setString(3, searchPattern);
            pstmt.setString(4, searchPattern);
            pstmt.setString(5, searchPattern);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ClientID"),
                    rs.getInt("UserID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getString("Telephone_Number"),
                    rs.getString("Email"),
                    rs.getString("Client_category"),
                    rs.getString("Address"),
                    rs.getString("Borehole_location")
                };
                tableModel.addRow(row);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error searching clients: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewClientDetails() {
        int selectedRow = clientTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a client to view details.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int clientId = (int) tableModel.getValueAt(selectedRow, 0);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        String gender = (String) tableModel.getValueAt(selectedRow, 3);
        String phone = (String) tableModel.getValueAt(selectedRow, 4);
        String email = (String) tableModel.getValueAt(selectedRow, 5);
        String category = (String) tableModel.getValueAt(selectedRow, 6);
        String address = (String) tableModel.getValueAt(selectedRow, 7);
        String location = (String) tableModel.getValueAt(selectedRow, 8);
        
        String message = String.format(
            "CLIENT DETAILS\n\n" +
            "Client ID: %d\n" +
            "Full Name: %s\n" +
            "Gender: %s\n" +
            "Phone: %s\n" +
            "Email: %s\n" +
            "Category: %s\n" +
            "Address: %s\n" +
            "Borehole Location: %s",
            clientId, fullName, gender, phone, email, category, address, location
        );
        
        JOptionPane.showMessageDialog(this, message, "Client Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void viewClientProjects() {
        int selectedRow = clientTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a client to view their projects.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int clientId = (int) tableModel.getValueAt(selectedRow, 0);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        
        // Open project view for this specific client
        new AdminProjectManagement(adminId, adminName, clientId, fullName).setVisible(true);
    }
    
    private void deleteClient() {
        int selectedRow = clientTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a client to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int clientId = (int) tableModel.getValueAt(selectedRow, 0);
        String fullName = (String) tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete client:\n" + fullName + " (ID: " + clientId + ")?\n\n" +
            "WARNING: This will also delete all their projects!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Delete projects first (foreign key constraint)
            String sql1 = "DELETE FROM client_projects WHERE ClientID = ?";
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setInt(1, clientId);
            pstmt1.executeUpdate();
            pstmt1.close();
            
            // Delete client
            String sql2 = "DELETE FROM clients WHERE ClientID = ?";
            PreparedStatement pstmt2 = conn.prepareStatement(sql2);
            pstmt2.setInt(1, clientId);
            pstmt2.executeUpdate();
            pstmt2.close();
            
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Client deleted successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadClientData();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error deleting client: " + e.getMessage(),
                "Delete Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}