import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AdminReports extends JFrame {
    
    private int adminId;
    private String adminName;
    private JTextArea reportArea;
    
    public AdminReports(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        
        setTitle("Reports & Analytics - Uzima Borehole");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(236, 240, 241));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(155, 89, 182));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitle = new JLabel("REPORTS & ANALYTICS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        JButton btnBack = new JButton("Back to Dashboard");
        btnBack.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnBack.setBackground(new Color(142, 68, 173));
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
        
        // Left Panel - Report Types
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        leftPanel.setPreferredSize(new Dimension(300, 0));
        
        JLabel lblReportTypes = new JLabel("REPORT TYPES");
        lblReportTypes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblReportTypes.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(lblReportTypes);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        // Report Buttons
        addReportButton(leftPanel, "Revenue Summary", new Color(46, 204, 113), 
            e -> generateRevenueSummary());
        
        addReportButton(leftPanel, "Tax Report", new Color(52, 152, 219), 
            e -> generateTaxReport());
        
        addReportButton(leftPanel, "Service Analysis", new Color(155, 89, 182), 
            e -> generateServiceAnalysis());
        
        addReportButton(leftPanel, "Client Category Report", new Color(241, 196, 15), 
            e -> generateClientCategoryReport());
        
        addReportButton(leftPanel, "Project Status Report", new Color(230, 126, 34), 
            e -> generateProjectStatusReport());
        
        addReportButton(leftPanel, "Top Clients", new Color(231, 76, 60), 
            e -> generateTopClients());
        
        addReportButton(leftPanel, "Monthly Summary", new Color(52, 73, 94), 
            e -> generateMonthlySummary());
        
        contentPanel.add(leftPanel, BorderLayout.WEST);
        
        // Right Panel - Report Display
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblReportDisplay = new JLabel("REPORT OUTPUT");
        lblReportDisplay.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        reportArea = new JTextArea();
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        reportArea.setEditable(false);
        reportArea.setLineWrap(false);
        reportArea.setText("Select a report type from the left to generate a report.");
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199)));
        
        JButton btnPrint = new JButton("Print Report");
        btnPrint.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPrint.setBackground(new Color(52, 152, 219));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFocusPainted(false);
        btnPrint.setBorderPainted(false);
        btnPrint.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPrint.addActionListener(e -> printReport());
        
        rightPanel.add(lblReportDisplay, BorderLayout.NORTH);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        rightPanel.add(btnPrint, BorderLayout.SOUTH);
        
        contentPanel.add(rightPanel, BorderLayout.CENTER);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void addReportButton(JPanel panel, String text, Color color, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        btn.addActionListener(action);
        
        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
    }
    
    private void generateRevenueSummary() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                    REVENUE SUMMARY REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            // Total Revenue
            ResultSet rs = stmt.executeQuery(
                "SELECT " +
                "SUM(Drilling_cost) as total_drilling, " +
                "SUM(Pump_cost) as total_pump, " +
                "SUM(Pump_installation_cost) as total_pump_install, " +
                "SUM(Tank_installation_fee) as total_tank, " +
                "SUM(Plumbing_cost) as total_plumbing, " +
                "SUM(Survey_fee) as total_survey, " +
                "SUM(Authority_fee) as total_authority, " +
                "SUM(Subtotal) as total_subtotal, " +
                "SUM(Tax_amount) as total_tax, " +
                "SUM(Grand_total) as total_grand " +
                "FROM client_projects " +
                "WHERE Project_status IN ('Approved', 'In Progress', 'Completed')"
            );
            
            if (rs.next()) {
                report.append("REVENUE BREAKDOWN BY SERVICE COMPONENT:\n");
                report.append("───────────────────────────────────────────────────────────────\n");
                report.append(String.format("Drilling Services:          Ksh %,15.2f\n", rs.getDouble("total_drilling")));
                report.append(String.format("Pump Sales:                 Ksh %,15.2f\n", rs.getDouble("total_pump")));
                report.append(String.format("Pump Installation:          Ksh %,15.2f\n", rs.getDouble("total_pump_install")));
                report.append(String.format("Tank Installation:          Ksh %,15.2f\n", rs.getDouble("total_tank")));
                report.append(String.format("Plumbing Services:          Ksh %,15.2f\n", rs.getDouble("total_plumbing")));
                report.append(String.format("Survey Fees:                Ksh %,15.2f\n", rs.getDouble("total_survey")));
                report.append(String.format("Authority Fees:             Ksh %,15.2f\n", rs.getDouble("total_authority")));
                report.append("───────────────────────────────────────────────────────────────\n");
                report.append(String.format("SUBTOTAL:                   Ksh %,15.2f\n", rs.getDouble("total_subtotal")));
                report.append(String.format("TOTAL TAX COLLECTED (16%%): Ksh %,15.2f\n", rs.getDouble("total_tax")));
                report.append("═══════════════════════════════════════════════════════════════\n");
                report.append(String.format("TOTAL REVENUE:              Ksh %,15.2f\n", rs.getDouble("total_grand")));
                report.append("═══════════════════════════════════════════════════════════════\n\n");
            }
            rs.close();
            
            // Project Count by Status
            report.append("\nPROJECT STATISTICS:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            rs = stmt.executeQuery(
                "SELECT Project_status, COUNT(*) as count, SUM(Grand_total) as total " +
                "FROM client_projects GROUP BY Project_status"
            );
            
            while (rs.next()) {
                report.append(String.format("%-20s: %3d projects | Ksh %,12.2f\n", 
                    rs.getString("Project_status"), 
                    rs.getInt("count"),
                    rs.getDouble("total")));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateTaxReport() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                      TAX COLLECTION REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("Tax Rate: 16%\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            
            // Tax Summary
            String sql = "SELECT c.Client_category, COUNT(*) as num_projects, " +
                        "SUM(cp.Subtotal) as total_taxable, SUM(cp.Tax_amount) as total_tax " +
                        "FROM client_projects cp " +
                        "JOIN clients c ON cp.ClientID = c.ClientID " +
                        "WHERE cp.Project_status IN ('Approved', 'In Progress', 'Completed') " +
                        "GROUP BY c.Client_category";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            report.append("TAX COLLECTED BY CLIENT CATEGORY:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            report.append(String.format("%-15s %10s %18s %18s\n", 
                "Category", "Projects", "Taxable Amount", "Tax Collected"));
            report.append("───────────────────────────────────────────────────────────────\n");
            
            double grandTaxable = 0, grandTax = 0;
            while (rs.next()) {
                String category = rs.getString("Client_category");
                int projects = rs.getInt("num_projects");
                double taxable = rs.getDouble("total_taxable");
                double tax = rs.getDouble("total_tax");
                
                grandTaxable += taxable;
                grandTax += tax;
                
                report.append(String.format("%-15s %10d Ksh %,14.2f Ksh %,14.2f\n", 
                    category, projects, taxable, tax));
            }
            rs.close();
            
            report.append("───────────────────────────────────────────────────────────────\n");
            report.append(String.format("%-15s %10s Ksh %,14.2f Ksh %,14.2f\n", 
                "TOTAL", "", grandTaxable, grandTax));
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            
            // Monthly Tax Collection
            report.append("\nMONTHLY TAX COLLECTION:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            
            sql = "SELECT DATE_FORMAT(Project_date, '%Y-%m') as month, " +
                  "SUM(Tax_amount) as monthly_tax " +
                  "FROM client_projects " +
                  "WHERE Project_status IN ('Approved', 'In Progress', 'Completed') " +
                  "GROUP BY DATE_FORMAT(Project_date, '%Y-%m') " +
                  "ORDER BY month DESC LIMIT 12";
            
            rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                report.append(String.format("%s: Ksh %,12.2f\n", 
                    rs.getString("month"), 
                    rs.getDouble("monthly_tax")));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateServiceAnalysis() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                    SERVICE ANALYSIS REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            // Drilling Services
            report.append("DRILLING SERVICE ANALYSIS:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            ResultSet rs = stmt.executeQuery(
                "SELECT s.Service_type, COUNT(*) as count, SUM(cp.Grand_total) as revenue " +
                "FROM client_projects cp " +
                "JOIN services s ON cp.ServiceID = s.ServiceID " +
                "WHERE cp.Project_status IN ('Approved', 'In Progress', 'Completed') " +
                "GROUP BY s.Service_type"
            );
            
            while (rs.next()) {
                report.append(String.format("%-25s: %3d projects | Revenue: Ksh %,12.2f\n",
                    rs.getString("Service_type"),
                    rs.getInt("count"),
                    rs.getDouble("revenue")));
            }
            rs.close();
            
            // Pump Types
            report.append("\n\nPUMP TYPE ANALYSIS:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            rs = stmt.executeQuery(
                "SELECT p.Pump_type, COUNT(*) as count, SUM(cp.Pump_cost) as revenue " +
                "FROM client_projects cp " +
                "JOIN pump_type p ON cp.PumpID = p.PumpID " +
                "WHERE cp.Project_status IN ('Approved', 'In Progress', 'Completed') " +
                "GROUP BY p.Pump_type"
            );
            
            while (rs.next()) {
                report.append(String.format("%-25s: %3d units | Revenue: Ksh %,12.2f\n",
                    rs.getString("Pump_type"),
                    rs.getInt("count"),
                    rs.getDouble("revenue")));
            }
            rs.close();
            
            // Average Project Values
            report.append("\n\nAVERAGE PROJECT METRICS:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            rs = stmt.executeQuery(
                "SELECT AVG(Depth_of_borehole) as avg_depth, " +
                "AVG(Height_of_tank) as avg_height, " +
                "AVG(Tank_capacity_litres) as avg_capacity, " +
                "AVG(Grand_total) as avg_value " +
                "FROM client_projects " +
                "WHERE Project_status IN ('Approved', 'In Progress', 'Completed')"
            );
            
            if (rs.next()) {
                report.append(String.format("Average Borehole Depth:     %.2f meters\n", rs.getDouble("avg_depth")));
                report.append(String.format("Average Tank Height:        %.2f meters\n", rs.getDouble("avg_height")));
                report.append(String.format("Average Tank Capacity:      %.0f litres\n", rs.getDouble("avg_capacity")));
                report.append(String.format("Average Project Value:      Ksh %,.2f\n", rs.getDouble("avg_value")));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateClientCategoryReport() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                 CLIENT CATEGORY REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT c.Client_category, COUNT(DISTINCT c.ClientID) as num_clients, " +
                        "COUNT(cp.ProjectID) as num_projects, " +
                        "SUM(cp.Survey_fee) as total_survey, " +
                        "SUM(cp.Authority_fee) as total_authority, " +
                        "SUM(cp.Grand_total) as total_revenue " +
                        "FROM clients c " +
                        "LEFT JOIN client_projects cp ON c.ClientID = cp.ClientID " +
                        "AND cp.Project_status IN ('Approved', 'In Progress', 'Completed') " +
                        "GROUP BY c.Client_category";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            report.append(String.format("%-12s %8s %10s %18s %18s %18s\n",
                "Category", "Clients", "Projects", "Survey Fees", "Authority Fees", "Total Revenue"));
            report.append("───────────────────────────────────────────────────────────────\n");
            
            double totalSurvey = 0, totalAuthority = 0, totalRevenue = 0;
            int totalClients = 0, totalProjects = 0;
            
            while (rs.next()) {
                String category = rs.getString("Client_category");
                int clients = rs.getInt("num_clients");
                int projects = rs.getInt("num_projects");
                double survey = rs.getDouble("total_survey");
                double authority = rs.getDouble("total_authority");
                double revenue = rs.getDouble("total_revenue");
                
                totalClients += clients;
                totalProjects += projects;
                totalSurvey += survey;
                totalAuthority += authority;
                totalRevenue += revenue;
                
                report.append(String.format("%-12s %8d %10d Ksh %,12.2f Ksh %,12.2f Ksh %,12.2f\n",
                    category, clients, projects, survey, authority, revenue));
            }
            rs.close();
            
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append(String.format("%-12s %8d %10d Ksh %,12.2f Ksh %,12.2f Ksh %,12.2f\n",
                "TOTAL", totalClients, totalProjects, totalSurvey, totalAuthority, totalRevenue));
            report.append("═══════════════════════════════════════════════════════════════\n");
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateProjectStatusReport() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                   PROJECT STATUS REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(
                "SELECT Project_status, COUNT(*) as count, " +
                "SUM(Grand_total) as total_value, " +
                "AVG(Grand_total) as avg_value " +
                "FROM client_projects " +
                "GROUP BY Project_status " +
                "ORDER BY count DESC"
            );
            
            report.append(String.format("%-15s %10s %20s %20s\n",
                "Status", "Count", "Total Value", "Average Value"));
            report.append("───────────────────────────────────────────────────────────────\n");
            
            int totalProjects = 0;
            double totalValue = 0;
            
            while (rs.next()) {
                String status = rs.getString("Project_status");
                int count = rs.getInt("count");
                double total = rs.getDouble("total_value");
                double avg = rs.getDouble("avg_value");
                
                totalProjects += count;
                totalValue += total;
                
                report.append(String.format("%-15s %10d Ksh %,15.2f Ksh %,15.2f\n",
                    status, count, total, avg));
            }
            rs.close();
            
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append(String.format("%-15s %10d Ksh %,15.2f\n",
                "TOTAL", totalProjects, totalValue));
            report.append("═══════════════════════════════════════════════════════════════\n");
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateTopClients() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                      TOP CLIENTS REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT u.Full_Name, c.Client_category, COUNT(cp.ProjectID) as num_projects, " +
                        "SUM(cp.Grand_total) as total_spent " +
                        "FROM clients c " +
                        "JOIN users u ON c.UserID = u.UserID " +
                        "JOIN client_projects cp ON c.ClientID = cp.ClientID " +
                        "WHERE cp.Project_status IN ('Approved', 'In Progress', 'Completed') " +
                        "GROUP BY c.ClientID " +
                        "ORDER BY total_spent DESC " +
                        "LIMIT 20";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            report.append("TOP 20 CLIENTS BY REVENUE:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            report.append(String.format("%-4s %-30s %-12s %10s %18s\n",
                "Rank", "Client Name", "Category", "Projects", "Total Spent"));
            report.append("───────────────────────────────────────────────────────────────\n");
            
            int rank = 1;
            while (rs.next()) {
                report.append(String.format("%-4d %-30s %-12s %10d Ksh %,12.2f\n",
                    rank++,
                    rs.getString("Full_Name"),
                    rs.getString("Client_category"),
                    rs.getInt("num_projects"),
                    rs.getDouble("total_spent")));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void generateMonthlySummary() {
        StringBuilder report = new StringBuilder();
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("                    MONTHLY SUMMARY REPORT\n");
        report.append("                UZIMA BOREHOLE DRILLING COMPANY\n");
        report.append("═══════════════════════════════════════════════════════════════\n");
        report.append("Generated: ").append(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new java.util.Date())).append("\n");
        report.append("═══════════════════════════════════════════════════════════════\n\n");
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String sql = "SELECT DATE_FORMAT(Project_date, '%Y-%m') as month, " +
                        "COUNT(*) as num_projects, " +
                        "SUM(Grand_total) as monthly_revenue, " +
                        "AVG(Grand_total) as avg_project_value " +
                        "FROM client_projects " +
                        "WHERE Project_status IN ('Approved', 'In Progress', 'Completed') " +
                        "GROUP BY DATE_FORMAT(Project_date, '%Y-%m') " +
                        "ORDER BY month DESC LIMIT 12";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            report.append("LAST 12 MONTHS PERFORMANCE:\n");
            report.append("───────────────────────────────────────────────────────────────\n");
            report.append(String.format("%-10s %10s %20s %20s\n",
                "Month", "Projects", "Revenue", "Avg Value"));
            report.append("───────────────────────────────────────────────────────────────\n");
            
            while (rs.next()) {
                report.append(String.format("%-10s %10d Ksh %,15.2f Ksh %,15.2f\n",
                    rs.getString("month"),
                    rs.getInt("num_projects"),
                    rs.getDouble("monthly_revenue"),
                    rs.getDouble("avg_project_value")));
            }
            rs.close();
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            report.append("\n\nERROR: Unable to generate report.\n");
            report.append(e.getMessage());
            e.printStackTrace();
        }
        
        reportArea.setText(report.toString());
    }
    
    private void printReport() {
        try {
            boolean complete = reportArea.print();
            if (complete) {
                JOptionPane.showMessageDialog(this,
                    "Report printed successfully!",
                    "Print Complete",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Printing was cancelled.",
                    "Print Cancelled",
                    JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error printing report: " + e.getMessage(),
                "Print Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}