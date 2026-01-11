import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MyProjects extends JFrame {
    
    private int clientId;
    private String clientName;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnBack, btnRefresh, btnViewDetails;
    
    public MyProjects(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
        
        initComponents();
        setupTable();
        loadProjects();
        
        setTitle("My Projects - Uzima Borehole");
        setSize(950, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== HEADER PANEL ==========
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(41, 128, 185));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        JLabel lblTitle = new JLabel("MY PROJECTS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblClient = new JLabel("Client: " + clientName);
        lblClient.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblClient.setForeground(Color.WHITE);
        
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblClient, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== TABLE PANEL ==========
        table = new JTable();
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setForeground(Color.BLACK); // Set table text to black
        
        // Table header - Set to black font
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(240, 240, 240)); // Light gray background
        header.setForeground(Color.BLACK); // Black font for column headers
        header.setPreferredSize(new Dimension(0, 40));
        
        table.setSelectionBackground(new Color(52, 152, 219));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
        
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(Color.WHITE);
        
        // Create buttons with black text
        btnViewDetails = createStyledButton("View Details", new Color(46, 204, 113), Color.BLACK);
        btnRefresh = createStyledButton("Refresh", new Color(52, 152, 219), Color.BLACK);
        btnBack = createStyledButton("← Back to Dashboard", new Color(149, 165, 166), Color.BLACK);
        
        btnViewDetails.addActionListener(e -> viewProjectDetails());
        btnRefresh.addActionListener(e -> {
            loadProjects();
            JOptionPane.showMessageDialog(this, "Projects refreshed!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnViewDetails);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(textColor); // Use the textColor parameter
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        
        // Hover effect - keep text black on hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
                button.setForeground(textColor); // Ensure text stays black
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
                button.setForeground(textColor); // Ensure text stays black
            }
        });
        
        return button;
    }
    
    private void setupTable() {
        String[] columns = {"Project ID", "Service", "Pump Type", "Status", "Date", "Total (Ksh)"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            // Optional: Customize cell rendering for specific columns
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }
        };
        
        table.setModel(tableModel);
        
        // Custom cell renderer to ensure black text for all cells
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                        isSelected, hasFocus, row, column);
                
                // Set text color to black for all cells
                c.setForeground(Color.BLACK);
                
                // Status column with colored backgrounds but black text
                if (column == 3 && value != null) {
                    String status = value.toString();
                    if (status.contains("✅")) {
                        c.setBackground(new Color(220, 255, 220)); // Light green
                    } else if (status.contains("🔄")) {
                        c.setBackground(new Color(255, 255, 200)); // Light yellow
                    } else if (status.contains("⏳")) {
                        c.setBackground(new Color(255, 230, 200)); // Light orange
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(Color.WHITE);
                }
                
                // If selected, use default selection colors
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(table.getSelectionForeground());
                }
                
                return c;
            }
        };
        
        // Apply renderer to all columns
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
        
        // Set column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(180);
        table.getColumnModel().getColumn(2).setPreferredWidth(180);
        table.getColumnModel().getColumn(3).setPreferredWidth(140);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
    }
    
    private void loadProjects() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String query = "SELECT p.ProjectID, s.Service_type, pt.Pump_type, " +
                          "p.Project_status, p.Project_date, p.Grand_total " +
                          "FROM client_projects p " +
                          "JOIN services s ON p.ServiceID = s.ServiceID " +
                          "JOIN pump_type pt ON p.PumpID = pt.PumpID " +
                          "WHERE p.ClientID = ? " +
                          "ORDER BY p.ProjectID DESC";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, clientId);
            ResultSet rs = pst.executeQuery();
            
            int rowCount = 0;
            while (rs.next()) {
                rowCount++;
                
                int projectId = rs.getInt("ProjectID");
                String service = rs.getString("Service_type");
                String pump = rs.getString("Pump_type");
                String status = rs.getString("Project_status");
                String date = rs.getString("Project_date");
                double total = rs.getDouble("Grand_total");
                
                String statusDisplay = "";
                if ("Completed".equalsIgnoreCase(status)) {
                    statusDisplay = "✅Completed";
                } else if ("In Progress".equalsIgnoreCase(status)) {
                    statusDisplay = "In Progress";
                } else {
                    statusDisplay = "Pending";
                }
                
                Object[] row = {
                    "PRJ" + String.format("%05d", projectId),
                    service,
                    pump,
                    statusDisplay,
                    date != null ? date : "N/A",
                    String.format("%,.2f", total)
                };
                
                tableModel.addRow(row);
            }
            
            if (rowCount == 0) {
                Object[] emptyRow = {"No projects yet", "Apply for service!", "", "", "", ""};
                tableModel.addRow(emptyRow);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
            System.out.println("✓ Loaded " + rowCount + " projects for client: " + clientName);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading projects:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void viewProjectDetails() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a project from the table first!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String projectRef = (String) tableModel.getValueAt(selectedRow, 0);
        
        if (projectRef.equals("No projects yet")) {
            JOptionPane.showMessageDialog(this, 
                "You have no projects yet.\nPlease apply for a drilling service first!");
            return;
        }
        
        int projectId = Integer.parseInt(projectRef.replace("PRJ", ""));
        showProjectDetails(projectId);
    }
    
    private void showProjectDetails(int projectId) {
        try {
            Connection conn = DBConnection.getConnection();
            
            String query = "SELECT p.*, s.Service_type, pt.Pump_type " +
                          "FROM client_projects p " +
                          "JOIN services s ON p.ServiceID = s.ServiceID " +
                          "JOIN pump_type pt ON p.PumpID = pt.PumpID " +
                          "WHERE p.ProjectID = ?";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Create a custom dialog for better formatting
                JDialog detailsDialog = new JDialog(this, "Project Details", true);
                detailsDialog.setSize(600, 700);
                detailsDialog.setLocationRelativeTo(this);
                
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                textArea.setForeground(Color.BLACK); // Set text color to black
                textArea.setMargin(new Insets(20, 20, 20, 20));
                
                StringBuilder details = new StringBuilder();
                details.append("═══════════════════════════════════════════════════\n");
                details.append("           PROJECT DETAILS\n");
                details.append("═══════════════════════════════════════════════════\n\n");
                
                details.append("Project Reference: PRJ").append(String.format("%05d", projectId)).append("\n");
                details.append("Status: ").append(rs.getString("Project_status")).append("\n");
                details.append("Date Applied: ").append(rs.getString("Created_at")).append("\n");
                details.append("Project Date: ").append(rs.getString("Project_date")).append("\n\n");
                
                details.append("CLIENT INFORMATION:\n");
                details.append("───────────────────────────────────────────────────\n");
                details.append("Client: ").append(clientName).append("\n");
                details.append("Client ID: #").append(String.format("%05d", clientId)).append("\n\n");
                
                details.append("SERVICE DETAILS:\n");
                details.append("───────────────────────────────────────────────────\n");
                details.append("• Drilling Service:    ").append(rs.getString("Service_type")).append("\n");
                details.append("• Pump Type:           ").append(rs.getString("Pump_type")).append("\n");
                details.append("• Borehole Depth:      ").append(rs.getDouble("Depth_of_borehole")).append(" meters\n");
                details.append("• Tank Height:         ").append(rs.getDouble("Height_of_tank")).append(" meters\n");
                
                int tankCapacity = rs.getInt("Tank_capacity_litres");
                if (tankCapacity > 0) {
                    details.append("• Tank Capacity:       ").append(tankCapacity).append(" litres\n");
                }
                details.append("\n");
                
                details.append("COST BREAKDOWN:\n");
                details.append("───────────────────────────────────────────────────\n");
                details.append(String.format("• Drilling Cost:       Ksh %,15.2f\n", rs.getDouble("Drilling_cost")));
                details.append(String.format("• Pump Cost:           Ksh %,15.2f\n", rs.getDouble("Pump_cost")));
                details.append(String.format("• Pump Installation:   Ksh %,15.2f\n", rs.getDouble("Pump_installation_cost")));
                
                double tankFee = rs.getDouble("Tank_installation_fee");
                if (tankFee > 0) {
                    details.append(String.format("• Tank Installation:   Ksh %,15.2f\n", tankFee));
                }
                
                double plumbingCost = rs.getDouble("Plumbing_cost");
                if (plumbingCost > 0) {
                    details.append(String.format("• Plumbing Services:   Ksh %,15.2f\n", plumbingCost));
                }
                
                details.append(String.format("• Survey Fee:          Ksh %,15.2f\n", rs.getDouble("Survey_fee")));
                details.append(String.format("• Authority Fee:       Ksh %,15.2f\n", rs.getDouble("Authority_fee")));
                details.append("───────────────────────────────────────────────────\n");
                details.append(String.format("• Subtotal:            Ksh %,15.2f\n", rs.getDouble("Subtotal")));
                details.append(String.format("• Tax (16%%):           Ksh %,15.2f\n", rs.getDouble("Tax_amount")));
                details.append("═══════════════════════════════════════════════════\n");
                details.append(String.format("  GRAND TOTAL:         Ksh %,15.2f\n", rs.getDouble("Grand_total")));
                details.append("═══════════════════════════════════════════════════\n");
                
                textArea.setText(details.toString());
                textArea.setCaretPosition(0);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                
                // Close button with black text
                JButton closeBtn = new JButton("Close");
                closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                closeBtn.setForeground(Color.BLACK); // Black text
                closeBtn.setBackground(new Color(200, 200, 200));
                closeBtn.setPreferredSize(new Dimension(100, 35));
                closeBtn.addActionListener(e -> detailsDialog.dispose());
                
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(closeBtn);
                
                detailsDialog.add(scrollPane, BorderLayout.CENTER);
                detailsDialog.add(buttonPanel, BorderLayout.SOUTH);
                
                detailsDialog.setVisible(true);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading project details:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MyProjects(1, "Test Client").setVisible(true);
        });
    }
}