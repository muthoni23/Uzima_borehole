import javax.swing.*;
import javax.swing.table.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.io.*;

public class Invoices extends JFrame {
    
    private int clientId;
    private String clientName;
    private String clientCategory;
    private DefaultTableModel tableModel;
    private JTable table;
    private JButton btnBack, btnRefresh, btnViewInvoice, btnDownloadPDF;
    
    public Invoices(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
        
        initComponents();
        setupTable();
        loadInvoices();
        
        setTitle("My Invoices - Uzima Borehole");
        setSize(1000, 650);
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
        headerPanel.setBackground(new Color(142, 68, 173)); // Purple
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(142, 68, 173));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel lblTitle = new JLabel("MY INVOICES");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("View and download your project invoices");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(230, 230, 230));
        
        titlePanel.add(lblTitle);
        titlePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        titlePanel.add(lblSubtitle);
        
        JLabel lblClient = new JLabel("Client: " + clientName);
        lblClient.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblClient.setForeground(Color.WHITE);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(lblClient, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ========== INFO PANEL ==========
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        JLabel lblInfo = new JLabel("Select an invoice and click 'View Invoice' to see full details");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblInfo.setForeground(new Color(127, 140, 141));
        
        infoPanel.add(lblInfo);
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        table = new JTable();
        table.setRowHeight(50);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.setSelectionBackground(new Color(142, 68, 173));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Add double-click listener
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewInvoice();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // ========== CENTER CONTAINER ==========
        JPanel centerContainer = new JPanel(new BorderLayout(0, 10));
        centerContainer.setBackground(new Color(236, 240, 241));
        centerContainer.add(infoPanel, BorderLayout.NORTH);
        centerContainer.add(tablePanel, BorderLayout.CENTER);
        
        mainPanel.add(centerContainer, BorderLayout.CENTER);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        btnViewInvoice = createStyledButton("View Invoice", new Color(46, 204, 113));
        btnDownloadPDF = createStyledButton("Download PDF", new Color(52, 152, 219));
        btnRefresh = createStyledButton("Refresh", new Color(241, 196, 15));
        btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnViewInvoice.addActionListener(e -> viewInvoice());
        btnDownloadPDF.addActionListener(e -> downloadInvoice());
        btnRefresh.addActionListener(e -> refreshInvoices());
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnViewInvoice);
        buttonPanel.add(btnDownloadPDF);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnBack);
        
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
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
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = bgColor;
            
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupTable() {
        String[] columns = {"Invoice #", "Project Ref", "Service", "Date", "Amount (Ksh)", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table.setModel(tableModel);
        
        // Set column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(130); // Invoice #
        columnModel.getColumn(1).setPreferredWidth(130); // Project Ref
        columnModel.getColumn(2).setPreferredWidth(200); // Service
        columnModel.getColumn(3).setPreferredWidth(130); // Date
        columnModel.getColumn(4).setPreferredWidth(150); // Amount
        columnModel.getColumn(5).setPreferredWidth(150); // Status
        
        // Center align certain columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        columnModel.getColumn(0).setCellRenderer(centerRenderer);
        columnModel.getColumn(1).setCellRenderer(centerRenderer);
        columnModel.getColumn(3).setCellRenderer(centerRenderer);
        columnModel.getColumn(5).setCellRenderer(centerRenderer);
        
        // Right align amount column
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        columnModel.getColumn(4).setCellRenderer(rightRenderer);
    }
    
    private void loadInvoices() {
        tableModel.setRowCount(0);
        
        try {
            Connection conn = DBConnection.getConnection();
            
            String query = "SELECT p.ProjectID, p.Project_date, p.Grand_total, p.Project_status, " +
                          "p.Created_at, s.Service_type " +
                          "FROM client_projects p " +
                          "JOIN services s ON p.ServiceID = s.ServiceID " +
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
                String date = rs.getString("Project_date");
                double amount = rs.getDouble("Grand_total");
                String status = rs.getString("Project_status");
                
                // Determine status display
                String statusDisplay = "";
                if ("Completed".equalsIgnoreCase(status)) {
                    statusDisplay = "Paid";
                } else if ("In Progress".equalsIgnoreCase(status)) {
                    statusDisplay = "Pending";
                } else {
                    statusDisplay = "Pending";
                }
                
                Object[] row = {
                    "INV-" + String.format("%05d", projectId),
                    "PRJ-" + String.format("%05d", projectId),
                    service,
                    date != null ? date : "N/A",
                    String.format("%,.2f", amount),
                    statusDisplay
                };
                
                tableModel.addRow(row);
            }
            
            if (rowCount == 0) {
                Object[] emptyRow = {
                    "No invoices yet", 
                    "Apply for service!", 
                    "", 
                    "", 
                    "", 
                    ""
                };
                tableModel.addRow(emptyRow);
                btnViewInvoice.setEnabled(false);
                btnDownloadPDF.setEnabled(false);
            } else {
                btnViewInvoice.setEnabled(true);
                btnDownloadPDF.setEnabled(true);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
            System.out.println("Loaded " + rowCount + " invoices for client: " + clientName);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading invoices:\n" + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void refreshInvoices() {
        loadInvoices();
        JOptionPane.showMessageDialog(this, 
            "Invoices refreshed successfully!", 
            "Refreshed", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void viewInvoice() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an invoice from the table first!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String invoiceRef = (String) tableModel.getValueAt(selectedRow, 0);
        
        if (invoiceRef.equals("No invoices yet")) {
            JOptionPane.showMessageDialog(this,
                "You have no invoices yet.\nPlease apply for a drilling service first!",
                "No Invoices",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Extract project ID from invoice reference
        int projectId = Integer.parseInt(invoiceRef.replace("INV-", ""));
        showInvoiceDetails(projectId);
    }
    
    private void downloadInvoice() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an invoice to download!",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String invoiceRef = (String) tableModel.getValueAt(selectedRow, 0);
        
        if (invoiceRef.equals("No invoices yet")) {
            return;
        }
        
        // Create a file chooser that opens the actual file explorer
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Invoice as PDF");
        
        // Set default file name
        fileChooser.setSelectedFile(new File(invoiceRef + ".pdf"));
        
        // Filter for PDF files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf", "PDF");
        fileChooser.setFileFilter(filter);
        
        // Show the file explorer dialog
        int userChoice = fileChooser.showSaveDialog(this);
        
        if (userChoice == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            
            // Show success message
            JOptionPane.showMessageDialog(this,
                "Invoice would be saved as:\n" +
                selectedFile.getAbsolutePath() + "\n\n" +
                "(This is a simulation. In real implementation,\n" +
                "PDF would be generated and saved to the selected location.)",
                "Save Confirmation",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showInvoiceDetails(int projectId) {
        try {
            Connection conn = DBConnection.getConnection();
            
            // Get project and client details
            String query = "SELECT p.*, s.Service_type, pt.Pump_type, c.Address, c.Client_category " +
                          "FROM client_projects p " +
                          "JOIN services s ON p.ServiceID = s.ServiceID " +
                          "JOIN pump_type pt ON p.PumpID = pt.PumpID " +
                          "JOIN clients c ON p.ClientID = c.ClientID " +
                          "WHERE p.ProjectID = ?";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, projectId);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Create custom dialog
                JDialog invoiceDialog = new JDialog(this, "Invoice Details - INV-" + String.format("%05d", projectId), true);
                invoiceDialog.setSize(750, 800);
                invoiceDialog.setLocationRelativeTo(this);
                
                // Create text area with invoice
                JTextArea textArea = new JTextArea();
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                textArea.setMargin(new Insets(25, 25, 25, 25));
                textArea.setBackground(Color.WHITE);
                
                // Build invoice content
                StringBuilder invoice = new StringBuilder();
                
                invoice.append("===============================================================\n");
                invoice.append("                                                               \n");
                invoice.append("           UZIMA BOREHOLE DRILLING SERVICES                    \n");
                invoice.append("                  OFFICIAL INVOICE                             \n");
                invoice.append("                                                               \n");
                invoice.append("===============================================================\n\n");
                
                // Invoice Header
                invoice.append("---------------------------------------------------------------\n");
                invoice.append(String.format("Invoice Number:    INV-%05d\n", projectId));
                invoice.append(String.format("Project Reference: PRJ-%05d\n", projectId));
                invoice.append("Invoice Date:      ").append(rs.getString("Project_date")).append("\n");
                invoice.append("Issue Date:        ").append(rs.getString("Created_at")).append("\n");
                invoice.append("Status:            ").append(rs.getString("Project_status")).append("\n");
                invoice.append("---------------------------------------------------------------\n\n");
                
                // Bill To Section
                invoice.append("BILL TO:\n");
                invoice.append("---------------------------------------------------------------\n");
                invoice.append("Client Name:       ").append(clientName).append("\n");
                invoice.append("Client ID:         #").append(String.format("%05d", clientId)).append("\n");
                invoice.append("Category:          ").append(rs.getString("Client_category")).append("\n");
                invoice.append("Address:           ").append(rs.getString("Address")).append("\n\n");
                
                // Service Description
                invoice.append("SERVICE DESCRIPTION:\n");
                invoice.append("---------------------------------------------------------------\n");
                invoice.append("Drilling Service:  ").append(rs.getString("Service_type")).append("\n");
                invoice.append("Pump Type:         ").append(rs.getString("Pump_type")).append("\n");
                invoice.append("Borehole Depth:    ").append(String.format("%.2f", rs.getDouble("Depth_of_borehole"))).append(" meters\n");
                invoice.append("Tank Height:       ").append(String.format("%.2f", rs.getDouble("Height_of_tank"))).append(" meters\n");
                
                int tankCapacity = rs.getInt("Tank_capacity_litres");
                if (tankCapacity > 0) {
                    invoice.append("Tank Capacity:     ").append(tankCapacity).append(" litres\n");
                }
                invoice.append("\n");
                
                // Itemized Charges
                invoice.append("===============================================================\n");
                invoice.append("ITEMIZED CHARGES:\n");
                invoice.append("===============================================================\n\n");
                
                double drillingCost = rs.getDouble("Drilling_cost");
                double pumpCost = rs.getDouble("Pump_cost");
                double pumpInstallation = rs.getDouble("Pump_installation_cost");
                double tankFee = rs.getDouble("Tank_installation_fee");
                double plumbingCost = rs.getDouble("Plumbing_cost");
                double surveyFee = rs.getDouble("Survey_fee");
                double authorityFee = rs.getDouble("Authority_fee");
                double subtotal = rs.getDouble("Subtotal");
                double tax = rs.getDouble("Tax_amount");
                double grandTotal = rs.getDouble("Grand_total");
                
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "Drilling Service", drillingCost));
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "Pump Equipment", pumpCost));
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "Pump Installation", pumpInstallation));
                
                if (tankFee > 0) {
                    invoice.append(String.format("%-40s Ksh %,15.2f\n", "Tank Installation", tankFee));
                }
                
                if (plumbingCost > 0) {
                    invoice.append(String.format("%-40s Ksh %,15.2f\n", "Plumbing Services", plumbingCost));
                }
                
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "Survey Fee", surveyFee));
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "Local Authority Fee", authorityFee));
                
                invoice.append("\n---------------------------------------------------------------\n");
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "SUBTOTAL (Taxable Services)", subtotal));
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "TAX (16%)", tax));
                invoice.append("===============================================================\n");
                invoice.append(String.format("%-40s Ksh %,15.2f\n", "TOTAL AMOUNT DUE", grandTotal));
                invoice.append("===============================================================\n\n");
                
                // Payment Terms
                invoice.append("PAYMENT TERMS:\n");
                invoice.append("---------------------------------------------------------------\n");
                invoice.append("  * 50% deposit required to commence work\n");
                invoice.append("  * Balance payable upon project completion\n");
                invoice.append("  * Payment methods: Cash, Bank Transfer, M-Pesa\n");
                invoice.append("  * Estimated completion: 14-21 working days\n\n");
                
                // Footer
                invoice.append("---------------------------------------------------------------\n");
                invoice.append("           Thank you for choosing Uzima Borehole!\n");
                invoice.append("     Contact: info@uzimaborehole.com | +254 712 345 678\n");
                invoice.append("           P.O. Box 12345, Nairobi, Kenya\n");
                invoice.append("===============================================================\n");
                
                textArea.setText(invoice.toString());
                textArea.setCaretPosition(0);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
                
                // Button panel
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
                buttonPanel.setBackground(new Color(236, 240, 241));
                
                JButton printBtn = createStyledButton("Print", new Color(52, 152, 219));
                printBtn.addActionListener(e -> {
                    try {
                        textArea.print();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(invoiceDialog, 
                            "Print error: " + ex.getMessage());
                    }
                });
                
                JButton downloadBtn = createStyledButton("Download PDF", new Color(46, 204, 113));
                downloadBtn.addActionListener(e -> {
                    // Extract project ID and show file chooser
                    String invoiceRef = "INV-" + String.format("%05d", projectId);
                    
                    // Create file chooser
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Save Invoice as PDF");
                    fileChooser.setSelectedFile(new File(invoiceRef + ".pdf"));
                    
                    // Filter for PDF files
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files (*.pdf)", "pdf", "PDF");
                    fileChooser.setFileFilter(filter);
                    
                    // Show file explorer
                    int userChoice = fileChooser.showSaveDialog(invoiceDialog);
                    
                    if (userChoice == JFileChooser.APPROVE_OPTION) {
                        File selectedFile = fileChooser.getSelectedFile();
                        
                        JOptionPane.showMessageDialog(invoiceDialog,
                            "Invoice would be saved as:\n" +
                            selectedFile.getAbsolutePath() + "\n\n" +
                            "(This is a simulation. In real implementation,\n" +
                            "PDF would be generated and saved to the selected location.)",
                            "Save Confirmation",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                
                JButton closeBtn = createStyledButton("Close", new Color(149, 165, 166));
                closeBtn.addActionListener(e -> invoiceDialog.dispose());
                
                buttonPanel.add(printBtn);
                buttonPanel.add(downloadBtn);
                buttonPanel.add(closeBtn);
                
                invoiceDialog.add(scrollPane, BorderLayout.CENTER);
                invoiceDialog.add(buttonPanel, BorderLayout.SOUTH);
                
                invoiceDialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Invoice not found!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading invoice:\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Invoices(1, "Test Client").setVisible(true);
        });
    }
}