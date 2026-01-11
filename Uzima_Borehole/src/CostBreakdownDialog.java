import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CostBreakdownDialog extends JDialog {
    
    // Calculation values
    private double drillingCost, pumpCost, pumpInstallationCost, totalMeters, costPerMeter;
    private double tankFee, pipeCost, outletCost, plumbingCost, taxableSubtotal, tax;
    private double surveyFee, authorityFee, grandTotal;
    
    // Client and project details
    private int userId, clientId;
    private String clientName, clientCategory;
    private String drillingSelection, pumpSelection, tankSelection, pipeSelection;
    private double pipeLength, boreholeDepth, tankHeight;
    private int outlets;
    
    // UI Components
    private JTextArea breakdownTextArea;
    private JButton btnSubmit, btnCancel;

    public CostBreakdownDialog(JFrame parent, 
                              double drillingCost, double pumpCost, double pumpInstallationCost,
                              double totalMeters, double costPerMeter,
                              double tankFee, double pipeCost, double outletCost, double plumbingCost,
                              double taxableSubtotal, double tax, double surveyFee, double authorityFee,
                              double grandTotal,
                              int userId, int clientId, String clientName, String clientCategory,
                              String drillingSelection, String pumpSelection, String tankSelection,
                              String pipeSelection, double pipeLength, int outlets,
                              double boreholeDepth, double tankHeight) {
        
        super(parent, "Cost Breakdown", true);
        
        // Store all the values
        this.drillingCost = drillingCost;
        this.pumpCost = pumpCost;
        this.pumpInstallationCost = pumpInstallationCost;
        this.totalMeters = totalMeters;
        this.costPerMeter = costPerMeter;
        this.tankFee = tankFee;
        this.pipeCost = pipeCost;
        this.outletCost = outletCost;
        this.plumbingCost = plumbingCost;
        this.taxableSubtotal = taxableSubtotal;
        this.tax = tax;
        this.surveyFee = surveyFee;
        this.authorityFee = authorityFee;
        this.grandTotal = grandTotal;
        this.userId = userId;
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientCategory = clientCategory;
        this.drillingSelection = drillingSelection;
        this.pumpSelection = pumpSelection;
        this.tankSelection = tankSelection;
        this.pipeSelection = pipeSelection;
        this.pipeLength = pipeLength;
        this.outlets = outlets;
        this.boreholeDepth = boreholeDepth;
        this.tankHeight = tankHeight;
        
        initComponents();
        createBreakdownText();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(0, 0));
        mainPanel.setBackground(new Color(240, 245, 250));
        
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(41, 128, 185));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        
        JLabel titleLabel = new JLabel("COST BREAKDOWN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        
        // CENTER: Text area with scroll pane
        breakdownTextArea = new JTextArea(20, 60);
        breakdownTextArea.setEditable(false);
        breakdownTextArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        breakdownTextArea.setForeground(new Color(44, 62, 80));
        breakdownTextArea.setBackground(Color.WHITE);
        breakdownTextArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JScrollPane scrollPane = new JScrollPane(breakdownTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(700, 500));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // SOUTH: Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 245, 250));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        btnSubmit = new JButton("Submit Application");
        btnSubmit.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSubmit.setBackground(new Color(46, 204, 113));
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnSubmit.setFocusPainted(false);
        btnSubmit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSubmit.addActionListener(e -> submitApplication());
        
        btnCancel = new JButton("Cancel");
        btnCancel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancel.setBackground(new Color(231, 76, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        btnCancel.setFocusPainted(false);
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnSubmit);
        buttonPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        buttonPanel.add(btnCancel);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        getContentPane().add(mainPanel);
        
        pack();
        setSize(750, 650);
        setResizable(true);
    }
    
    private void createBreakdownText() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("=".repeat(65)).append("\n");
        sb.append(String.format("%" + (65/2 + "COST BREAKDOWN".length()/2) + "s\n", "COST BREAKDOWN"));
        sb.append("=".repeat(65)).append("\n\n");
        
        sb.append("CLIENT INFORMATION:\n");
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("Client:   %s\n", clientName));
        sb.append(String.format("Category: %s\n\n", clientCategory));
        
        sb.append("TAXABLE SERVICES:\n");
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("• Drilling Service:     Ksh %,12.2f\n", drillingCost));
        sb.append(String.format("• Pump Base Cost:       Ksh %,12.2f\n", pumpCost));
        sb.append(String.format("• Pump Installation:    Ksh %,12.2f\n", pumpInstallationCost));
        sb.append(String.format("    (%.0fm @ Ksh %,.0f/m)\n", totalMeters, costPerMeter));
        
        if (tankFee > 0) {
            sb.append(String.format("• Tank Installation:    Ksh %,12.2f\n", tankFee));
        }
        
        if (plumbingCost > 0) {
            sb.append(String.format("• Plumbing Services:    Ksh %,12.2f\n", plumbingCost));
            if (pipeCost > 0) {
                sb.append(String.format("    - Pipes:           Ksh %,12.2f\n", pipeCost));
            }
            if (outletCost > 0) {
                sb.append(String.format("    - Outlets:         Ksh %,12.2f\n", outletCost));
            }
        }
        
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("SUBTOTAL (Taxable):     Ksh %,12.2f\n", taxableSubtotal));
        sb.append(String.format("TAX (16%%):              Ksh %,12.2f\n", tax));
        sb.append("=".repeat(65)).append("\n");
        sb.append(String.format("TOTAL AFTER TAX:        Ksh %,12.2f\n\n", taxableSubtotal + tax));
        
        sb.append("NON-TAXABLE FEES:\n");
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("• Survey Fee:           Ksh %,12.2f\n", surveyFee));
        sb.append(String.format("• Local Authority Fee:  Ksh %,12.2f\n", authorityFee));
        sb.append("-".repeat(65)).append("\n");
        sb.append(String.format("TOTAL FEES:             Ksh %,12.2f\n\n", surveyFee + authorityFee));
        
        sb.append("=".repeat(65)).append("\n");
        sb.append(String.format("GRAND TOTAL:            Ksh %,12.2f\n", grandTotal));
        sb.append("=".repeat(65)).append("\n");
        
        breakdownTextArea.setText(sb.toString());
        breakdownTextArea.setCaretPosition(0);
    }
    
    private void submitApplication() {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            // Get a FRESH connection
            conn = DBConnection.getConnection();
            if (conn == null) {
                JOptionPane.showMessageDialog(this,
                    "Database connection failed!\n\n" +
                    "Please check:\n" +
                    "1. MySQL is running (Start XAMPP)\n" +
                    "2. Database 'uzima_borehole' exists\n" +
                    "3. Username: root, Password: 5678",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Test if connection is actually working
            try {
                if (conn.isClosed()) {
                    JOptionPane.showMessageDialog(this,
                        "Connection was closed immediately!",
                        "Connection Error",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this,
                    "Cannot verify connection: " + e.getMessage(),
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            System.out.println("✓ Connection is valid and open!");
            
            // Get service_id
            String serviceType = drillingSelection.split(" - ")[0];
            int serviceId = getServiceId(conn, serviceType);
            System.out.println("✓ Service ID: " + serviceId);
            
            // Get pump_id
            String pumpType = pumpSelection.split(" - ")[0];
            int pumpId = getPumpId(conn, pumpType);
            System.out.println("✓ Pump ID: " + pumpId);
            
            // ===== MAIN INSERT QUERY =====
            String projectQuery = "INSERT INTO client_projects (" +
                "ClientID, ServiceID, PumpID, Depth_of_borehole, Height_of_tank, " +
                "Tank_capacity_litres, Tank_installation_fee, Drilling_cost, Pump_cost, " +
                "Pump_installation_cost, Plumbing_cost, Survey_fee, Authority_fee, " +
                "Subtotal, Tax_amount, Grand_total, Project_date, Project_status, Created_at" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURDATE(), 'Pending', NOW())";
            
            pst = conn.prepareStatement(projectQuery, Statement.RETURN_GENERATED_KEYS);
            
            pst.setInt(1, clientId);
            pst.setInt(2, serviceId);
            pst.setInt(3, pumpId);
            pst.setDouble(4, boreholeDepth);
            pst.setDouble(5, tankHeight);
            
            // Tank information (optional)
            if (tankFee > 0 && !tankSelection.isEmpty()) {
                try {
                    int tankCapacity = Integer.parseInt(tankSelection.split(" ")[0]);
                    pst.setInt(6, tankCapacity);
                    pst.setDouble(7, tankFee);
                } catch (NumberFormatException e) {
                    pst.setNull(6, Types.INTEGER);
                    pst.setNull(7, Types.DECIMAL);
                }
            } else {
                pst.setNull(6, Types.INTEGER);
                pst.setNull(7, Types.DECIMAL);
            }
            
            pst.setDouble(8, drillingCost);
            pst.setDouble(9, pumpCost);
            pst.setDouble(10, pumpInstallationCost);
            pst.setDouble(11, plumbingCost);  // Plumbing cost is saved here in client_projects
            pst.setDouble(12, surveyFee);
            pst.setDouble(13, authorityFee);
            pst.setDouble(14, taxableSubtotal);
            pst.setDouble(15, tax);
            pst.setDouble(16, grandTotal);
            
            System.out.println("✓ Parameters set, executing insert...");
            
            int rowsAffected = pst.executeUpdate();
            System.out.println("✓ Rows affected: " + rowsAffected);
            
            if (rowsAffected > 0) {
                // Get the generated project_id
                rs = pst.getGeneratedKeys();
                int projectId = 0;
                if (rs.next()) {
                    projectId = rs.getInt(1);
                    System.out.println("✓ Project ID: " + projectId);
                }
                
                // NO plumbing details insertion - everything is already in client_projects
                // The plumbing_cost, pipe_cost, outlet_cost are already in the main table
                
                // Show success message
                JOptionPane.showMessageDialog(this,
                    String.format("✅ Application submitted successfully!\n\n" +
                                 "Project Reference: PRJ%05d\n" +
                                 "Grand Total: Ksh %,.2f\n\n" +
                                 "Your application is now pending review.",
                                 projectId, grandTotal),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                dispose();
                
                // Close the ApplyService form
                java.awt.Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null && window != this) {
                    window.dispose();
                }
                
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to save application. No rows affected.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(this,
                "Database Error:\n" + e.getMessage() + 
                "\n\nSQL State: " + e.getSQLState() +
                "\nError Code: " + e.getErrorCode(),
                "SQL Error",
                JOptionPane.ERROR_MESSAGE);
                
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
            
            JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
                
        } finally {
            // Close resources
            try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (pst != null) pst.close(); } catch (SQLException e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
            
            System.out.println("✓ Resources closed");
        }
    }
    
    private int getServiceId(Connection conn, String serviceType) throws SQLException {
        String query = "SELECT ServiceID FROM services WHERE Service_type = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, serviceType);
        ResultSet rs = pst.executeQuery();
        
        if (!rs.next()) {
            throw new SQLException("Service not found: " + serviceType);
        }
        
        int id = rs.getInt("ServiceID");
        rs.close();
        pst.close();
        return id;
    }
    
    private int getPumpId(Connection conn, String pumpType) throws SQLException {
        String query = "SELECT PumpID FROM pump_type WHERE Pump_type = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, pumpType);
        ResultSet rs = pst.executeQuery();
        
        if (!rs.next()) {
            throw new SQLException("Pump type not found: " + pumpType);
        }
        
        int id = rs.getInt("PumpID");
        rs.close();
        pst.close();
        return id;
    }
    
   
}