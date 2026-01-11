import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
    
    private String adminName;
    private int adminId;
    
    public AdminDashboard(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        
        setTitle("Admin Dashboard - Uzima Borehole Management System");
        setSize(1150, 700); // Fixed size
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); // Make window non-resizable
        setVisible(true);
    }
    
    private void initComponents() {
        // Main panel with fixed layout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setPreferredSize(new Dimension(1150, 700));
        
        // ========== TOP HEADER ==========
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        headerPanel.setPreferredSize(new Dimension(1150, 65));
        
        JLabel lblTitle = new JLabel("UZIMA BOREHOLE - ADMIN PANEL");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        
        JPanel adminInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        adminInfoPanel.setOpaque(false);
        
        JLabel lblWelcome = new JLabel("Welcome, " + adminName);
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblWelcome.setForeground(new Color(236, 240, 241));
        
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnLogout.setBackground(new Color(231, 76, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setPreferredSize(new Dimension(90, 32));
        btnLogout.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btnLogout.addActionListener(e -> logout());
        
        adminInfoPanel.add(lblWelcome);
        adminInfoPanel.add(Box.createHorizontalStrut(15));
        adminInfoPanel.add(btnLogout);
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(adminInfoPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== CENTER CONTENT ==========
        JPanel contentPanel = new JPanel(new BorderLayout(0, 20));
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        contentPanel.setPreferredSize(new Dimension(1100, 585));
        
        // Quick Stats Panel - Fixed size
        JPanel statsPanel = createStatsPanel();
        statsPanel.setPreferredSize(new Dimension(1100, 120));
        contentPanel.add(statsPanel, BorderLayout.NORTH);
        
        // Main Features Grid - Fixed size
        JPanel featuresPanel = createFeaturesPanel();
        featuresPanel.setPreferredSize(new Dimension(1080, 400));
        
        // Fixed size scroll pane
        JScrollPane scrollPane = new JScrollPane(featuresPanel);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(new Color(245, 247, 250));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setPreferredSize(new Dimension(1100, 410));
        
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // ========== FOOTER ==========
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(44, 62, 80));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        footerPanel.setPreferredSize(new Dimension(1150, 35));
        
        JLabel lblFooter = new JLabel("© 2025 Uzima Borehole Management System | Admin Panel");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFooter.setForeground(new Color(200, 200, 200));
        
        footerPanel.add(lblFooter);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        pack(); // Ensure window fits content exactly
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setOpaque(false);
        statsPanel.setPreferredSize(new Dimension(1100, 120));
        
        int[] stats = getQuickStats();
        
        // Fixed size stat cards
        statsPanel.add(createStatCard("Total Clients", String.valueOf(stats[0]), 
            new Color(41, 128, 185), 250, 120));
        statsPanel.add(createStatCard("Total Projects", String.valueOf(stats[1]), 
            new Color(39, 174, 96), 250, 120));
        statsPanel.add(createStatCard("Pending Approvals", String.valueOf(stats[2]), 
            new Color(243, 156, 18), 250, 120));
        statsPanel.add(createStatCard("Total Revenue", "Ksh " + String.format("%,.0f", (double)stats[3]), 
            new Color(142, 68, 173), 250, 120));
        
        return statsPanel;
    }
    
    private JPanel createStatCard(String label, String value, Color color, int width, int height) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setPreferredSize(new Dimension(width, height));
        card.setMinimumSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLabel.setForeground(new Color(80, 80, 80));
        
        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(color);
        
        card.add(lblLabel, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createFeaturesPanel() {
        JPanel featuresPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        featuresPanel.setOpaque(false);
        featuresPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        featuresPanel.setPreferredSize(new Dimension(1080, 400));
        
        // Fixed size feature cards (340x180)
        featuresPanel.add(createFeatureCard("CLIENT MANAGEMENT", 
            "View, search, and manage all registered clients. Add new clients, update details, and track client history.",
            new Color(41, 128, 185), 340, 180, e -> openClientManagement()));
            
        featuresPanel.add(createFeatureCard("PROJECT MANAGEMENT", 
            "View and manage all projects from all clients. Track project status, assign workers, and update progress.",
            new Color(39, 174, 96), 340, 180, e -> openProjectManagement()));
            
        featuresPanel.add(createFeatureCard("PENDING APPROVALS", 
            "Review and approve new project applications. Check pending requests and make approval decisions.",
            new Color(243, 156, 18), 340, 180, e -> openPendingApprovals()));
        
        featuresPanel.add(createFeatureCard("REPORTS & ANALYTICS", 
            "Generate revenue, tax, and performance reports. View statistics, charts, and export data.",
            new Color(142, 68, 173), 340, 180, e -> openReports()));
            
        featuresPanel.add(createFeatureCard("SYSTEM SETTINGS", 
            "Manage services, pumps, prices, and fees. Configure system parameters and update business rules.",
            new Color(230, 126, 34), 340, 180, e -> openSystemSettings()));
            
        featuresPanel.add(createFeatureCard("USER MANAGEMENT", 
            "Manage admin and staff user accounts. Add new users, reset passwords, and assign roles.",
            new Color(44, 62, 80), 340, 180, e -> openUserManagement()));
        
        return featuresPanel;
    }
    
    private JPanel createFeatureCard(String title, String description, Color color, int width, int height, ActionListener action) {
        JPanel card = new JPanel(new BorderLayout(0, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(0, 0, 0, 0)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));
        card.setPreferredSize(new Dimension(width, height));
        card.setMinimumSize(new Dimension(width, height));
        card.setMaximumSize(new Dimension(width, height));
        
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(color);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        titlePanel.setPreferredSize(new Dimension(width, 45));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(Color.WHITE);
        titlePanel.add(lblTitle, BorderLayout.WEST);
        
        JPanel descPanel = new JPanel(new BorderLayout());
        descPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        descPanel.setPreferredSize(new Dimension(width, height - 45));
        
        JTextArea txtDescription = new JTextArea(description);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescription.setForeground(new Color(100, 100, 100));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setOpaque(false);
        txtDescription.setMargin(new Insets(0, 0, 0, 0));
        
        descPanel.add(txtDescription, BorderLayout.CENTER);
        
        card.add(titlePanel, BorderLayout.NORTH);
        card.add(descPanel, BorderLayout.CENTER);
        
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color, 2),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
                titlePanel.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                    BorderFactory.createEmptyBorder(0, 0, 0, 0)
                ));
                titlePanel.setBackground(color);
            }
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        
        return card;
    }
    
    // Replace the getQuickStats() method in AdminDashboard.java with this:

private int[] getQuickStats() {
    int[] stats = {0, 0, 0, 0};
    
    try {
        Connection conn = DBConnection.getConnection();
        
        Statement stmt = conn.createStatement();
        
        // Total Clients
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM clients");
        if (rs.next()) stats[0] = rs.getInt(1);
        rs.close();
        
        // Total Projects
        rs = stmt.executeQuery("SELECT COUNT(*) FROM client_projects");
        if (rs.next()) stats[1] = rs.getInt(1);
        rs.close();
        
        // Pending Approvals
        rs = stmt.executeQuery("SELECT COUNT(*) FROM client_projects WHERE Project_status = 'Pending'");
        if (rs.next()) stats[2] = rs.getInt(1);
        rs.close();
        
        // FIXED: Total Revenue - Now includes Approved projects and excludes only Rejected ones
        rs = stmt.executeQuery(
            "SELECT COALESCE(SUM(Grand_total), 0) FROM client_projects " +
            "WHERE Project_status IN ('Approved', 'In Progress', 'Completed')"
        );
        if (rs.next()) stats[3] = rs.getInt(1);
        rs.close();
        
        stmt.close();
        conn.close();
        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    return stats;
}
    
    private void openClientManagement() {
    SwingUtilities.invokeLater(() -> {
        new AdminClientManagement(adminId, adminName).setVisible(true);
    });
}

private void openProjectManagement() {
    SwingUtilities.invokeLater(() -> {
        new AdminProjectManagement(adminId, adminName).setVisible(true);
    });
}

private void openPendingApprovals() {
    SwingUtilities.invokeLater(() -> {
        new PendingApprovals(adminId, adminName).setVisible(true);
    });
}

private void openReports() {
    SwingUtilities.invokeLater(() -> {
        new AdminReports(adminId, adminName).setVisible(true);
    });
}

private void openSystemSettings() {
    SwingUtilities.invokeLater(() -> {
        new SystemSettings(adminId, adminName).setVisible(true);
    });
}

private void openUserManagement() {
    SwingUtilities.invokeLater(() -> {
        new UserManagement(adminId, adminName).setVisible(true);
    });
}
    
    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            SwingUtilities.invokeLater(() -> {
                new HomeForm().setVisible(true);
            });
        }
    }
    
    //public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> {
          //  new AdminDashboard(1, "Muthoni Waigumo").setVisible(true);
        //});
    //}
}