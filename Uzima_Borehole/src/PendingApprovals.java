import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class PendingApprovals extends JFrame {
    
    private int adminId;
    private String adminName;
    private JTable pendingTable;
    private DefaultTableModel tableModel;
    private JLabel lblPendingCount;
    
    public PendingApprovals(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        loadPendingProjects();
        
        setTitle("Pending Approvals - Uzima Borehole");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(241, 196, 15));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        titlePanel.setOpaque(false);
        
        JLabel lblTitle = new JLabel("PENDING APPROVALS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        lblPendingCount = new JLabel("(0)");
        lblPendingCount.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblPendingCount.setForeground(Color.WHITE);
        
        titlePanel.add(lblTitle);
        titlePanel.add(lblPendingCount);
        
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setBackground(new Color(243, 156, 18));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.setBorderPainted(false);
        btnBack.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBack.addActionListener(e -> dispose());
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(btnBack, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(new Color(236, 240, 241));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Info Panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        infoPanel.setBackground(new Color(255, 243, 205));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(241, 196, 15), 2),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel lblInfo = new JLabel("Review and approve/reject client project applications below.");
        lblInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblInfo.setForeground(new Color(102, 102, 102));
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnRefresh.setBackground(new Color(52, 152, 219));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        btnRefresh.setBorderPainted(false);
        btnRefresh.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(e -> loadPendingProjects());
        
        infoPanel.add(lblInfo);
        infoPanel.add(btnRefresh);
        
        contentPanel.add(infoPanel, BorderLayout.NORTH);
        
        // Table Panel
        String[] columns = {"Project ID", "Client Name", "Category", "Service", "Pump Type", 
                           "Grand Total (Ksh)", "Applied Date", "Actions"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        pendingTable = new JTable(tableModel);
        pendingTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        pendingTable.setRowHeight(35);
        pendingTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        pendingTable.getTableHeader().setBackground(new Color(240, 240, 240)); // Changed to light gray
        pendingTable.getTableHeader().setForeground(Color.BLACK); // Changed to black
        pendingTable.setSelectionBackground(new Color(241, 196, 15));
        pendingTable.setSelectionForeground(Color.WHITE);
        pendingTable.setGridColor(new Color(189, 195, 199));
        
        // Set column widths
        pendingTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        pendingTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        pendingTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        pendingTable.getColumnModel().getColumn(3).setPreferredWidth(150);
        pendingTable.getColumnModel().getColumn(4).setPreferredWidth(130);
        pendingTable.getColumnModel().getColumn(5).setPreferredWidth(130);
        pendingTable.getColumnModel().getColumn(6).setPreferredWidth(120);
        pendingTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        
        JScrollPane scrollPane = new JScrollPane(pendingTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Action Buttons Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnViewDetails = new JButton("View Full Details");
        btnViewDetails.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnViewDetails.setBackground(new Color(52, 152, 219));
        btnViewDetails.setForeground(Color.WHITE);
        btnViewDetails.setFocusPainted(false);
        btnViewDetails.setBorderPainted(false);
        btnViewDetails.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnViewDetails.setPreferredSize(new Dimension(180, 45));
        btnViewDetails.addActionListener(e -> viewProjectDetails());
        
        JButton btnApprove = new JButton("APPROVE");
        btnApprove.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnApprove.setBackground(new Color(46, 204, 113));
        btnApprove.setForeground(Color.WHITE);
        btnApprove.setFocusPainted(false);
        btnApprove.setBorderPainted(false);
        btnApprove.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnApprove.setPreferredSize(new Dimension(180, 45));
        btnApprove.addActionListener(e -> approveProject());
        
        JButton btnReject = new JButton("REJECT");
        btnReject.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnReject.setBackground(new Color(231, 76, 60));
        btnReject.setForeground(Color.WHITE);
        btnReject.setFocusPainted(false);
        btnReject.setBorderPainted(false);
        btnReject.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnReject.setPreferredSize(new Dimension(180, 45));
        btnReject.addActionListener(e -> rejectProject());
        
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnApprove);
        buttonPanel.add(btnReject);
        
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void loadPendingProjects() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT cp.ProjectID, u.Full_Name, c.Client_category, s.Service_type, " +
                        "p.Pump_type, cp.Grand_total, cp.Created_at " +
                        "FROM client_projects cp " +
                        "JOIN clients c ON cp.ClientID = c.ClientID " +
                        "JOIN users u ON c.UserID = u.UserID " +
                        "JOIN services s ON cp.ServiceID = s.ServiceID " +
                        "JOIN pump_type p ON cp.PumpID = p.PumpID " +
                        "WHERE cp.Project_status = 'Pending' " +
                        "ORDER BY cp.Created_at ASC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;
            while (rs.next()) {
                count++;
                Object[] row = {
                    rs.getInt("ProjectID"),
                    rs.getString("Full_Name"),
                    rs.getString("Client_category"),
                    rs.getString("Service_type"),
                    rs.getString("Pump_type"),
                    String.format("%,.2f", rs.getDouble("Grand_total")),
                    rs.getTimestamp("Created_at"),
                    "Pending"
                };
                tableModel.addRow(row);
            }
            
            lblPendingCount.setText("(" + count + ")");
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading pending projects: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void viewProjectDetails() {
        int selectedRow = pendingTable.getSelectedRow();
        
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
                String details = String.format(
                    "═══════════════════════════════════════════════════\n" +
                    "        PENDING PROJECT APPLICATION DETAILS\n" +
                    "═══════════════════════════════════════════════════\n\n" +
                    "PROJECT ID: %d\n" +
                    "STATUS: PENDING APPROVAL\n\n" +
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
                    "SERVICE DETAILS\n" +
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
                    "Application Date: %s\n",
                    rs.getInt("ProjectID"),
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
                    rs.getTimestamp("Created_at")
                );
                
                JTextArea textArea = new JTextArea(details);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 650));
                
                JOptionPane.showMessageDialog(this, scrollPane, 
                    "Project Application Details", 
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
    
    private void approveProject() {
        int selectedRow = pendingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to approve.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int projectId = (int) tableModel.getValueAt(selectedRow, 0);
        String clientName = (String) tableModel.getValueAt(selectedRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Approve Project #" + projectId + " for " + clientName + "?\n\n" +
            "This will change the status to 'Approved' and the client will be notified.",
            "Confirm Approval",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE client_projects SET Project_status = 'Approved' WHERE ProjectID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Project approved successfully!\n\nProject #" + projectId + " has been approved.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadPendingProjects();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error approving project: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void rejectProject() {
        int selectedRow = pendingTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select a project to reject.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int projectId = (int) tableModel.getValueAt(selectedRow, 0);
        String clientName = (String) tableModel.getValueAt(selectedRow, 1);
        
        String reason = JOptionPane.showInputDialog(this,
            "Reject Project #" + projectId + " for " + clientName + "?\n\n" +
            "Please provide a reason for rejection:",
            "Rejection Reason",
            JOptionPane.QUESTION_MESSAGE);
        
        if (reason == null || reason.trim().isEmpty()) {
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to reject this project?\n\n" +
            "Reason: " + reason,
            "Confirm Rejection",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE client_projects SET Project_status = 'Rejected' WHERE ProjectID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, projectId);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            
            JOptionPane.showMessageDialog(this,
                "Project rejected.\n\nProject #" + projectId + " has been rejected.\nReason: " + reason,
                "Rejected",
                JOptionPane.INFORMATION_MESSAGE);
            
            loadPendingProjects();
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error rejecting project: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}