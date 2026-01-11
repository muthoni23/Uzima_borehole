import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;

public class AdminProjectManagement extends JFrame {
    
    private int adminId;
    private String adminName;
    private Integer filterClientId; // Optional: filter by specific client
    private String filterClientName; // Optional: client name for display
    
    private JTable projectTable;
    private DefaultTableModel tableModel;
    private JTextField txtSearch;
    private JComboBox<String> cmbStatusFilter;
    private JLabel lblFilterInfo;
    
    public AdminProjectManagement(int adminId, String adminName) {
        this(adminId, adminName, null, null);
    }
    
    public AdminProjectManagement(int adminId, String adminName, Integer clientId, String clientName) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.filterClientId = clientId;
        this.filterClientName = clientName;
        
        initComponents();
        loadProjectData();
        
        String title = "Project Management - Uzima Borehole";
        if (clientName != null) {
            title += " - Client: " + clientName;
        }
        setTitle(title);
        setSize(1400, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(46, 204, 113));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("PROJECT MANAGEMENT");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);
        
        lblFilterInfo = new JLabel("");
        lblFilterInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFilterInfo.setForeground(Color.WHITE);
        
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setBackground(new Color(39, 174, 96));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        
        rightPanel.add(lblFilterInfo);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(btnBack);
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(236, 240, 241));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(Color.WHITE);
        filterPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setPreferredSize(new Dimension(250, 35));
        
        JLabel lblStatus = new JLabel("Status Filter:");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbStatusFilter = new JComboBox<>(new String[]{"All", "Pending", "Approved", "In Progress", "Completed", "Rejected"});
        cmbStatusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cmbStatusFilter.setPreferredSize(new Dimension(150, 35));
        cmbStatusFilter.addActionListener(e -> loadProjectData());
        
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSearch.setBackground(new Color(52, 152, 219));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        btnSearch.setBorderPainted(false);
        btnSearch.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSearch.addActionListener(e -> loadProjectData());
        
        JButton btnClear = new JButton("Clear Filters");
        btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnClear.setBackground(new Color(149, 165, 166));
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        btnClear.setBorderPainted(false);
        btnClear.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            cmbStatusFilter.setSelectedIndex(0);
            if (filterClientId != null) {
                // If we came from client management, go back
                dispose();
                new AdminProjectManagement(adminId, adminName).setVisible(true);
            } else {
                loadProjectData();
            }
        });
        
        filterPanel.add(lblSearch);
        filterPanel.add(txtSearch);
        filterPanel.add(lblStatus);
        filterPanel.add(cmbStatusFilter);
        filterPanel.add(btnSearch);
        filterPanel.add(btnClear);
        
        contentPanel.add(filterPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columns = {"Project ID", "Client Name", "Category", "Service", "Pump Type", 
                           "Status", "Grand Total", "Created Date", "Last Updated"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        projectTable = new JTable(tableModel);
        projectTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        projectTable.setRowHeight(30);
        projectTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        projectTable.getTableHeader().setBackground(new Color(52, 73, 94));
        projectTable.getTableHeader().setForeground(Color.BLACK); // Changed from WHITE to BLACK
        projectTable.setGridColor(new Color(189, 195, 199));
        
        // Color rows by status
        projectTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                
                Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String status = (String) table.getValueAt(row, 5); // Status column
                    Color backgroundColor = Color.WHITE;
                    
                    switch (status) {
                        case "Pending":
                            backgroundColor = new Color(255, 243, 205); // Light yellow
                            break;
                        case "Approved":
                            backgroundColor = new Color(212, 237, 218); // Light green
                            break;
                        case "In Progress":
                            backgroundColor = new Color(209, 236, 241); // Light blue
                            break;
                        case "Completed":
                            backgroundColor = new Color(212, 237, 218); // Light green
                            break;
                        case "Rejected":
                            backgroundColor = new Color(248, 215, 218); // Light red
                            break;
                    }
                    
                    c.setBackground(backgroundColor);
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(projectTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Action Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnViewDetails = new JButton("View Details");
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnViewDetails.setBackground(new Color(52, 152, 219));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.setBorderPainted(false);
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetails.setPreferredSize(new Dimension(180, 45));
        btnViewDetails.addActionListener(e -> viewProjectDetails());
        
        JButton btnUpdateStatus = new JButton("Update Status");
        btnUpdateStatus.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnUpdateStatus.setBackground(new Color(241, 196, 15));
        btnUpdateStatus.setForeground(Color.WHITE);
        btnUpdateStatus.setFocusPainted(false);
        btnUpdateStatus.setBorderPainted(false);
        btnUpdateStatus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnUpdateStatus.setPreferredSize(new Dimension(180, 45));
        btnUpdateStatus.addActionListener(e -> updateProjectStatus());
        
        JButton btnViewClient = new JButton("View Client");
        btnViewClient.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnViewClient.setBackground(new Color(155, 89, 182));
        btnViewClient.setForeground(Color.WHITE);
        btnViewClient.setFocusPainted(false);
        btnViewClient.setBorderPainted(false);
        btnViewClient.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewClient.setPreferredSize(new Dimension(180, 45));
        btnViewClient.addActionListener(e -> viewClientDetails());
        
        JButton btnDelete = new JButton("Delete Project");
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new Dimension(180, 45));
        btnDelete.addActionListener(e -> deleteProject());
        
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnUpdateStatus);
        buttonPanel.add(btnViewClient);
        buttonPanel.add(btnDelete);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void loadProjectData() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Build SQL query with filters - CORRECTED: Using Project_date instead of Updated_at
            StringBuilder sql = new StringBuilder(
                "SELECT cp.ProjectID, u.Full_Name, c.Client_category, s.Service_type, " +
                "p.Pump_type, cp.Project_status, cp.Grand_total, cp.Created_at, cp.Project_date " +
                "FROM client_projects cp " +
                "JOIN clients c ON cp.ClientID = c.ClientID " +
                "JOIN users u ON c.UserID = u.UserID " +
                "JOIN services s ON cp.ServiceID = s.ServiceID " +
                "JOIN pump_type p ON cp.PumpID = p.PumpID " +
                "WHERE 1=1"
            );
            
            // Apply filters
            if (filterClientId != null) {
                sql.append(" AND cp.ClientID = ").append(filterClientId);
                lblFilterInfo.setText("Viewing projects for: " + filterClientName);
            }
            
            String searchTerm = txtSearch.getText().trim();
            if (!searchTerm.isEmpty()) {
                sql.append(" AND (u.Full_Name LIKE '%").append(searchTerm).append("%'")
                   .append(" OR cp.ProjectID LIKE '%").append(searchTerm).append("%'")
                   .append(" OR u.Email LIKE '%").append(searchTerm).append("%')");
            }
            
            String statusFilter = (String) cmbStatusFilter.getSelectedItem();
            if (!statusFilter.equals("All")) {
                sql.append(" AND cp.Project_status = '").append(statusFilter).append("'");
            }
            
            sql.append(" ORDER BY cp.Project_date DESC, cp.Created_at DESC");
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql.toString());
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("ProjectID"),
                    rs.getString("Full_Name"),
                    rs.getString("Client_category"),
                    rs.getString("Service_type"),
                    rs.getString("Pump_type"),
                    rs.getString("Project_status"),
                    String.format("Ksh %,.2f", rs.getDouble("Grand_total")),
                    dateFormat.format(rs.getTimestamp("Created_at")),
                    rs.getTimestamp("Project_date") != null ? 
                        dateFormat.format(rs.getTimestamp("Project_date")) : "N/A"
                };
                tableModel.addRow(row);
            }
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading project data: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewProjectDetails() {
        int selectedRow = projectTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to view details.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int projectId = (int) tableModel.getValueAt(selectedRow, 0);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT cp.*, u.Full_Name, u.Telephone_Number, u.Email, " +
                        "c.Client_category, c.Address, c.Borehole_location, " +
                        "s.Service_type, p.Pump_type " +
                        "FROM client_projects cp " +
                        "JOIN clients c ON cp.ClientID = c.ClientID " +
                        "JOIN users u ON c.UserID = u.UserID " +
                        "JOIN services s ON cp.ServiceID = s.ServiceID " +
                        "JOIN pump_type p ON cp.PumpID = p.PumpID " +
                        "WHERE cp.ProjectID = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
                
                String details = String.format(
                    "═══════════════════════════════════════════════════════════════\n" +
                    "                   PROJECT DETAILS\n" +
                    "                UZIMA BOREHOLE DRILLING COMPANY\n" +
                    "═══════════════════════════════════════════════════════════════\n\n" +
                    "PROJECT ID: %d\n" +
                    "STATUS: %s\n\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "CLIENT INFORMATION\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "Name: %s\n" +
                    "Category: %s\n" +
                    "Phone: %s\n" +
                    "Email: %s\n" +
                    "Address: %s\n" +
                    "Borehole Location: %s\n\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "PROJECT SPECIFICATIONS\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "Service Type: %s\n" +
                    "Pump Type: %s\n" +
                    "Borehole Depth: %.2f meters\n" +
                    "Tank Height: %.2f meters\n" +
                    "Tank Capacity: %d litres\n\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "COST BREAKDOWN\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "Drilling Cost:          Ksh %,12.2f\n" +
                    "Pump Cost:              Ksh %,12.2f\n" +
                    "Pump Installation:      Ksh %,12.2f\n" +
                    "Tank Installation:      Ksh %,12.2f\n" +
                    "Plumbing Cost:          Ksh %,12.2f\n" +
                    "Survey Fee:             Ksh %,12.2f\n" +
                    "Authority Fee:          Ksh %,12.2f\n" +
                    "                        ─────────────────\n" +
                    "Subtotal:               Ksh %,12.2f\n" +
                    "Tax (16%%):              Ksh %,12.2f\n" +
                    "═══════════════════════════════════════════════════\n" +
                    "GRAND TOTAL:            Ksh %,12.2f\n" +
                    "═══════════════════════════════════════════════════\n\n" +
                    "Created: %s\n" +
                    "Last Updated: %s\n" +
                    "═══════════════════════════════════════════════════",
                    rs.getInt("ProjectID"),
                    rs.getString("Project_status"),
                    rs.getString("Full_Name"),
                    rs.getString("Client_category"),
                    rs.getString("Telephone_Number"),
                    rs.getString("Email"),
                    rs.getString("Address"),
                    rs.getString("Borehole_location"),
                    rs.getString("Service_type"),
                    rs.getString("Pump_type"),
                    rs.getDouble("Depth_of_borehole"),
                    rs.getDouble("Height_of_tank"),
                    rs.getInt("Tank_capacity_litres"),
                    rs.getDouble("Drilling_cost"),
                    rs.getDouble("Pump_cost"),
                    rs.getDouble("Pump_installation_cost"),
                    rs.getDouble("Tank_installation_fee"),
                    rs.getDouble("Plumbing_cost"),
                    rs.getDouble("Survey_fee"),
                    rs.getDouble("Authority_fee"),
                    rs.getDouble("Subtotal"),
                    rs.getDouble("Tax_amount"),
                    rs.getDouble("Grand_total"),
                    dateFormat.format(rs.getTimestamp("Created_at")),
                    rs.getTimestamp("Project_date") != null ? 
                        dateFormat.format(rs.getTimestamp("Project_date")) : "Not updated"
                );
                JTextArea textArea = new JTextArea(details);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(650, 700));
                
                JOptionPane.showMessageDialog(this, scrollPane, 
                    "Project Details - ID: " + projectId, 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading project details: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void updateProjectStatus() {
        int selectedRow = projectTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to update status.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int projectId = (int) tableModel.getValueAt(selectedRow, 0);
        String currentStatus = (String) tableModel.getValueAt(selectedRow, 5);
        String clientName = (String) tableModel.getValueAt(selectedRow, 1);
        
        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Project: #" + projectId + " - " + clientName));
        panel.add(new JLabel("Current Status: " + currentStatus));
        
        JComboBox<String> cmbNewStatus = new JComboBox<>(new String[]{
            "Pending", "Approved", "In Progress", "Completed", "Rejected"
        });
        cmbNewStatus.setSelectedItem(currentStatus);
        panel.add(cmbNewStatus);
        
        int result = JOptionPane.showConfirmDialog(this, panel,
            "Update Project Status", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String newStatus = (String) cmbNewStatus.getSelectedItem();
            
            if (newStatus.equals(currentStatus)) {
                JOptionPane.showMessageDialog(this,
                    "Status is already set to: " + currentStatus,
                    "No Change",
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            try {
                Connection conn = DBConnection.getConnection();
                String sql = "UPDATE client_projects SET Project_status = ?, Project_date = NOW() WHERE ProjectID = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newStatus);
                pstmt.setInt(2, projectId);
                pstmt.executeUpdate();
                pstmt.close();
                conn.close();
                
                JOptionPane.showMessageDialog(this,
                    "Project status updated!\n\n" +
                    "Project #" + projectId + "\n" +
                    "Status changed from '" + currentStatus + "' to '" + newStatus + "'",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                loadProjectData();
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this,
                    "Error updating project status: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewClientDetails() {
        int selectedRow = projectTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to view client details.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String clientName = (String) tableModel.getValueAt(selectedRow, 1);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT u.*, c.* " +
                        "FROM users u " +
                        "JOIN clients c ON u.UserID = c.UserID " +
                        "WHERE u.Full_Name = ?";
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, clientName);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String details = String.format(
                    "═══════════════════════════════════════════════\n" +
                    "             CLIENT DETAILS\n" +
                    "═══════════════════════════════════════════════\n\n" +
                    "USER INFORMATION:\n" +
                    "─────────────────────────────────────────────\n" +
                    "User ID:         %d\n" +
                    "Full Name:       %s\n" +
                    "Gender:          %s\n" +
                    "Email:           %s\n" +
                    "Phone:           %s\n\n" +
                    "CLIENT INFORMATION:\n" +
                    "─────────────────────────────────────────────\n" +
                    "Client ID:       %d\n" +
                    "Category:        %s\n" +
                    "Address:         %s\n" +
                    "Borehole Location: %s\n" +
                    "═══════════════════════════════════════════════",
                    rs.getInt("UserID"),
                    rs.getString("Full_Name"),
                    rs.getString("Gender"),
                    rs.getString("Email"),
                    rs.getString("Telephone_Number"),
                    rs.getInt("ClientID"),
                    rs.getString("Client_category"),
                    rs.getString("Address"),
                    rs.getString("Borehole_location")
                );
                
                JTextArea textArea = new JTextArea(details);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(450, 300));
                
                JOptionPane.showMessageDialog(this, scrollPane, 
                    "Client Details - " + clientName, JOptionPane.INFORMATION_MESSAGE);
            }
            
            rs.close();
            pstmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading client details: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteProject() {
        int selectedRow = projectTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to delete.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int projectId = (int) tableModel.getValueAt(selectedRow, 0);
        String clientName = (String) tableModel.getValueAt(selectedRow, 1);
        String status = (String) tableModel.getValueAt(selectedRow, 5);
        String amount = (String) tableModel.getValueAt(selectedRow, 6);
        
        if (status.equals("In Progress") || status.equals("Completed")) {
            JOptionPane.showMessageDialog(this,
                "Cannot delete projects that are 'In Progress' or 'Completed'.\n" +
                "Please update the status first.",
                "Action Not Allowed",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete this project?\n\n" +
            "Project ID: " + projectId + "\n" +
            "Client: " + clientName + "\n" +
            "Status: " + status + "\n" +
            "Amount: " + amount + "\n\n" +
            "WARNING: This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM client_projects WHERE ProjectID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Project deleted successfully!\n\n" +
                "Project #" + projectId + " has been removed from the system.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadProjectData();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error deleting project: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}