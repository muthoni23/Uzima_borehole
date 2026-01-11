import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.*;

public class SystemSettings extends JFrame {
    
    private int adminId;
    private String adminName;
    
    public SystemSettings(int adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        
        initComponents();
        
        setTitle("System Settings - Uzima Borehole");
        setSize(1300, 750);
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
        headerPanel.setBackground(new Color(230, 126, 34)); // Orange
        headerPanel.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));
        
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(230, 126, 34));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        
        JLabel lblTitle = new JLabel("SYSTEM SETTINGS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblSubtitle = new JLabel("Configure system parameters and pricing");
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
        
        // ========== CONTENT PANEL WITH TABS ==========
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(Color.WHITE);
        
        // Add all 6 tabs as requested
        tabbedPane.addTab("Drilling Services", createServicesPanel());
        tabbedPane.addTab("Pump Types", createPumpPanel());
        tabbedPane.addTab("Fee Structure", createFeesPanel());
        tabbedPane.addTab("Depth/Height Rates", createDepthPanel());
        tabbedPane.addTab("Pipe Types", createPipePanel());
        tabbedPane.addTab("Tanks", createTankPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    // ========== DRILLING SERVICES PANEL ==========
    private JPanel createServicesPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Table setup
        String[] columns = {"Service ID", "Service Type", "Down Payment (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Load data
        loadServicesData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAdd = createStyledButton("Add Service", new Color(46, 204, 113));
        JButton btnEdit = createStyledButton("Edit Service", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("Delete Service", new Color(231, 76, 60));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnAdd.addActionListener(e -> addService(model));
        btnEdit.addActionListener(e -> editService(table, model));
        btnDelete.addActionListener(e -> deleteService(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadServicesData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM services ORDER BY ServiceID");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ServiceID"),
                    rs.getString("Service_type"),
                    String.format("%,.2f", rs.getDouble("Down_payment"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading services: " + e.getMessage());
        }
    }
    
    // ========== PUMP TYPES PANEL ==========
    private JPanel createPumpPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        String[] columns = {"Pump ID", "Pump Type", "Pump Cost (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadPumpData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAdd = createStyledButton("Add Pump", new Color(46, 204, 113));
        JButton btnEdit = createStyledButton("Edit Pump", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("Delete Pump", new Color(231, 76, 60));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnAdd.addActionListener(e -> addPump(model));
        btnEdit.addActionListener(e -> editPump(table, model));
        btnDelete.addActionListener(e -> deletePump(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadPumpData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pump_type ORDER BY PumpID");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("PumpID"),
                    rs.getString("Pump_type"),
                    String.format("%,.2f", rs.getDouble("Pump_cost"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading pumps: " + e.getMessage());
        }
    }
    
    // ========== FEE STRUCTURE PANEL ==========
    private JPanel createFeesPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        String[] columns = {"Fee ID", "Client Category", "Survey Fees (Ksh)", "Local Authority Fees (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadFeeData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnEdit = createStyledButton("Edit Fees", new Color(52, 152, 219));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnEdit.addActionListener(e -> editFees(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadFeeData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM fee_structure ORDER BY FeeID");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("FeeID"),
                    rs.getString("Client_category"),
                    String.format("%,.2f", rs.getDouble("Survey_fees")),
                    String.format("%,.2f", rs.getDouble("Local_authority_fee"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading fees: " + e.getMessage());
        }
    }
    
    // ========== DEPTH/HEIGHT RATES PANEL ==========
    private JPanel createDepthPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        String[] columns = {"Depth ID", "Min Metres", "Max Metres", "Cost per Metre (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadDepthData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnEdit = createStyledButton("Edit Rate", new Color(52, 152, 219));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnEdit.addActionListener(e -> editDepth(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadDepthData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM depth_height ORDER BY Min_metre");
            
            while (rs.next()) {
                String maxMetres = rs.getObject("Max_metre") != null ? 
                    String.valueOf(rs.getInt("Max_metre")) : "∞";
                
                model.addRow(new Object[]{
                    rs.getInt("DepthID"),
                    rs.getInt("Min_metre"),
                    maxMetres,
                    String.format("%,.2f", rs.getDouble("Cost_per_metre"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading depth rates: " + e.getMessage());
        }
    }
    
    // ========== PIPE TYPES PANEL ==========
    private JPanel createPipePanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        String[] columns = {"Pipe ID", "Type of Pipe", "Cost per Meter (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadPipeData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAdd = createStyledButton("Add Pipe", new Color(46, 204, 113));
        JButton btnEdit = createStyledButton("Edit Pipe", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("Delete Pipe", new Color(231, 76, 60));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnAdd.addActionListener(e -> addPipe(model));
        btnEdit.addActionListener(e -> editPipe(table, model));
        btnDelete.addActionListener(e -> deletePipe(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadPipeData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM pipe_types ORDER BY PipeID");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("PipeID"),
                    rs.getString("Type_of_pipe"),
                    String.format("%,.2f", rs.getDouble("Cost_per_meter"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading pipes: " + e.getMessage());
        }
    }
    
    // ========== TANKS PANEL ==========
    private JPanel createTankPanel() {
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBackground(new Color(236, 240, 241));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // ========== TABLE PANEL ==========
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        String[] columns = {"Tank ID", "Capacity (Litres)", "Installation Fee (Ksh)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable table = new JTable(model);
        setupTable(table, model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 2));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        loadTankData(model);
        
        // ========== BUTTON PANEL ==========
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setBackground(new Color(236, 240, 241));
        
        JButton btnAdd = createStyledButton("Add Tank", new Color(46, 204, 113));
        JButton btnEdit = createStyledButton("Edit Tank", new Color(52, 152, 219));
        JButton btnDelete = createStyledButton("Delete Tank", new Color(231, 76, 60));
        JButton btnBack = createStyledButton("Back to Dashboard", new Color(149, 165, 166));
        
        btnAdd.addActionListener(e -> addTank(model));
        btnEdit.addActionListener(e -> editTank(table, model));
        btnDelete.addActionListener(e -> deleteTank(table, model));
        btnBack.addActionListener(e -> dispose());
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnBack);
        
        panel.add(tablePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadTankData(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tanks ORDER BY Capacity_litres");
            
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("TankID"),
                    String.format("%,d", rs.getInt("Capacity_litres")),
                    String.format("%,.2f", rs.getDouble("Installation_fee"))
                });
            }
            
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error loading tanks: " + e.getMessage());
        }
    }
    
    // ========== TABLE SETUP METHOD ==========
    private void setupTable(JTable table, DefaultTableModel model) {
        table.setModel(model);
        table.setRowHeight(45);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240)); // Light gray background
        table.getTableHeader().setForeground(Color.BLACK); // Changed to black
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
        table.setSelectionBackground(new Color(230, 126, 34));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(189, 195, 199));
        table.setShowGrid(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Center align ID columns
        TableColumnModel columnModel = table.getColumnModel();
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        // Center align ID columns (first column usually)
        if (columnModel.getColumnCount() > 0) {
            columnModel.getColumn(0).setCellRenderer(centerRenderer);
        }
        
        // Right align price/cost columns
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            String colName = model.getColumnName(i);
            if (colName.contains("Cost") || colName.contains("Fee") || colName.contains("Payment") || colName.contains("Amount")) {
                columnModel.getColumn(i).setCellRenderer(rightRenderer);
            }
        }
    }
    
    // ========== BUTTON CREATION METHOD ==========
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
    
    // ========== DRILLING SERVICES METHODS ==========
private void addService(DefaultTableModel model) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtServiceType = new JTextField(20);
    JTextField txtDownPayment = new JTextField(20);
    
    panel.add(new JLabel("Service Type:"));
    panel.add(txtServiceType);
    panel.add(new JLabel("Down Payment (Ksh):"));
    panel.add(txtDownPayment);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Service", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String serviceType = txtServiceType.getText().trim();
            double downPayment = Double.parseDouble(txtDownPayment.getText().trim());
            
            if (serviceType.isEmpty()) {
                showWarning("Service type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO services (Service_type, Down_payment) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, serviceType);
            pstmt.setDouble(2, downPayment);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Service added successfully!");
                loadServicesData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error adding service: " + e.getMessage());
        }
    }
}

private void editService(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a service to edit!");
        return;
    }
    
    int serviceId = (int) model.getValueAt(selectedRow, 0);
    String currentService = (String) model.getValueAt(selectedRow, 1);
    String currentPayment = ((String) model.getValueAt(selectedRow, 2)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtServiceType = new JTextField(currentService, 20);
    JTextField txtDownPayment = new JTextField(currentPayment, 20);
    
    panel.add(new JLabel("Service Type:"));
    panel.add(txtServiceType);
    panel.add(new JLabel("Down Payment (Ksh):"));
    panel.add(txtDownPayment);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Service", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String serviceType = txtServiceType.getText().trim();
            double downPayment = Double.parseDouble(txtDownPayment.getText().trim());
            
            if (serviceType.isEmpty()) {
                showWarning("Service type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE services SET Service_type = ?, Down_payment = ? WHERE ServiceID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, serviceType);
            pstmt.setDouble(2, downPayment);
            pstmt.setInt(3, serviceId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Service updated successfully!");
                loadServicesData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating service: " + e.getMessage());
        }
    }
}

private void deleteService(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a service to delete!");
        return;
    }
    
    int serviceId = (int) model.getValueAt(selectedRow, 0);
    String serviceName = (String) model.getValueAt(selectedRow, 1);
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete '" + serviceName + "'?", 
        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM services WHERE ServiceID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, serviceId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Service deleted successfully!");
                loadServicesData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error deleting service: " + e.getMessage());
        }
    }
}

// ========== PUMP TYPES METHODS ==========
private void addPump(DefaultTableModel model) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtPumpType = new JTextField(20);
    JTextField txtPumpCost = new JTextField(20);
    
    panel.add(new JLabel("Pump Type:"));
    panel.add(txtPumpType);
    panel.add(new JLabel("Pump Cost (Ksh):"));
    panel.add(txtPumpCost);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Pump", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String pumpType = txtPumpType.getText().trim();
            double pumpCost = Double.parseDouble(txtPumpCost.getText().trim());
            
            if (pumpType.isEmpty()) {
                showWarning("Pump type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO pump_type (Pump_type, Pump_cost) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pumpType);
            pstmt.setDouble(2, pumpCost);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pump added successfully!");
                loadPumpData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error adding pump: " + e.getMessage());
        }
    }
}

private void editPump(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a pump to edit!");
        return;
    }
    
    int pumpId = (int) model.getValueAt(selectedRow, 0);
    String currentPump = (String) model.getValueAt(selectedRow, 1);
    String currentCost = ((String) model.getValueAt(selectedRow, 2)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtPumpType = new JTextField(currentPump, 20);
    JTextField txtPumpCost = new JTextField(currentCost, 20);
    
    panel.add(new JLabel("Pump Type:"));
    panel.add(txtPumpType);
    panel.add(new JLabel("Pump Cost (Ksh):"));
    panel.add(txtPumpCost);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Pump", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String pumpType = txtPumpType.getText().trim();
            double pumpCost = Double.parseDouble(txtPumpCost.getText().trim());
            
            if (pumpType.isEmpty()) {
                showWarning("Pump type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE pump_type SET Pump_type = ?, Pump_cost = ? WHERE PumpID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pumpType);
            pstmt.setDouble(2, pumpCost);
            pstmt.setInt(3, pumpId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pump updated successfully!");
                loadPumpData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating pump: " + e.getMessage());
        }
    }
}

private void deletePump(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a pump to delete!");
        return;
    }
    
    int pumpId = (int) model.getValueAt(selectedRow, 0);
    String pumpName = (String) model.getValueAt(selectedRow, 1);
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete '" + pumpName + "'?", 
        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM pump_type WHERE PumpID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pumpId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pump deleted successfully!");
                loadPumpData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error deleting pump: " + e.getMessage());
        }
    }
}

// ========== FEE STRUCTURE METHODS ==========
private void editFees(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a fee structure to edit!");
        return;
    }
    
    int feeId = (int) model.getValueAt(selectedRow, 0);
    String category = (String) model.getValueAt(selectedRow, 1);
    String currentSurvey = ((String) model.getValueAt(selectedRow, 2)).replace(",", "");
    String currentAuthority = ((String) model.getValueAt(selectedRow, 3)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtCategory = new JTextField(category, 20);
    txtCategory.setEnabled(false); // Don't allow changing category
    JTextField txtSurvey = new JTextField(currentSurvey, 20);
    JTextField txtAuthority = new JTextField(currentAuthority, 20);
    
    panel.add(new JLabel("Client Category:"));
    panel.add(txtCategory);
    panel.add(new JLabel("Survey Fees (Ksh):"));
    panel.add(txtSurvey);
    panel.add(new JLabel("Local Authority Fee (Ksh):"));
    panel.add(txtAuthority);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Fee Structure", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            double surveyFee = Double.parseDouble(txtSurvey.getText().trim());
            double authorityFee = Double.parseDouble(txtAuthority.getText().trim());
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE fee_structure SET Survey_fees = ?, Local_authority_fee = ? WHERE FeeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, surveyFee);
            pstmt.setDouble(2, authorityFee);
            pstmt.setInt(3, feeId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Fee structure updated successfully!");
                loadFeeData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating fee structure: " + e.getMessage());
        }
    }
}

// ========== DEPTH/HEIGHT METHODS ==========
private void editDepth(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a depth rate to edit!");
        return;
    }
    
    int depthId = (int) model.getValueAt(selectedRow, 0);
    int minMetre = (int) model.getValueAt(selectedRow, 1);
    String maxMetreStr = model.getValueAt(selectedRow, 2).toString();
    String currentCost = ((String) model.getValueAt(selectedRow, 3)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtMinMetre = new JTextField(String.valueOf(minMetre), 20);
    txtMinMetre.setEnabled(false); // Don't allow changing range
    JTextField txtMaxMetre = new JTextField(maxMetreStr, 20);
    txtMaxMetre.setEnabled(false); // Don't allow changing range
    JTextField txtCost = new JTextField(currentCost, 20);
    
    panel.add(new JLabel("Min Metres:"));
    panel.add(txtMinMetre);
    panel.add(new JLabel("Max Metres:"));
    panel.add(txtMaxMetre);
    panel.add(new JLabel("Cost per Metre (Ksh):"));
    panel.add(txtCost);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Depth Rate", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            double costPerMetre = Double.parseDouble(txtCost.getText().trim());
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE depth_height SET Cost_per_metre = ? WHERE DepthID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, costPerMetre);
            pstmt.setInt(2, depthId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Depth rate updated successfully!");
                loadDepthData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating depth rate: " + e.getMessage());
        }
    }
}

// ========== PIPE TYPES METHODS ==========
private void addPipe(DefaultTableModel model) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtPipeType = new JTextField(20);
    JTextField txtCostPerMeter = new JTextField(20);
    
    panel.add(new JLabel("Type of Pipe:"));
    panel.add(txtPipeType);
    panel.add(new JLabel("Cost per Meter (Ksh):"));
    panel.add(txtCostPerMeter);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Pipe", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String pipeType = txtPipeType.getText().trim();
            double costPerMeter = Double.parseDouble(txtCostPerMeter.getText().trim());
            
            if (pipeType.isEmpty()) {
                showWarning("Pipe type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO pipe_types (Type_of_pipe, Cost_per_meter) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pipeType);
            pstmt.setDouble(2, costPerMeter);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pipe added successfully!");
                loadPipeData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error adding pipe: " + e.getMessage());
        }
    }
}

private void editPipe(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a pipe to edit!");
        return;
    }
    
    int pipeId = (int) model.getValueAt(selectedRow, 0);
    String currentPipe = (String) model.getValueAt(selectedRow, 1);
    String currentCost = ((String) model.getValueAt(selectedRow, 2)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtPipeType = new JTextField(currentPipe, 20);
    JTextField txtCostPerMeter = new JTextField(currentCost, 20);
    
    panel.add(new JLabel("Type of Pipe:"));
    panel.add(txtPipeType);
    panel.add(new JLabel("Cost per Meter (Ksh):"));
    panel.add(txtCostPerMeter);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Pipe", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            String pipeType = txtPipeType.getText().trim();
            double costPerMeter = Double.parseDouble(txtCostPerMeter.getText().trim());
            
            if (pipeType.isEmpty()) {
                showWarning("Pipe type cannot be empty!");
                return;
            }
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE pipe_types SET Type_of_pipe = ?, Cost_per_meter = ? WHERE PipeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pipeType);
            pstmt.setDouble(2, costPerMeter);
            pstmt.setInt(3, pipeId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pipe updated successfully!");
                loadPipeData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating pipe: " + e.getMessage());
        }
    }
}

private void deletePipe(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a pipe to delete!");
        return;
    }
    
    int pipeId = (int) model.getValueAt(selectedRow, 0);
    String pipeName = (String) model.getValueAt(selectedRow, 1);
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete '" + pipeName + "'?", 
        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM pipe_types WHERE PipeID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pipeId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Pipe deleted successfully!");
                loadPipeData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error deleting pipe: " + e.getMessage());
        }
    }
}

// ========== TANK METHODS ==========
private void addTank(DefaultTableModel model) {
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtCapacity = new JTextField(20);
    JTextField txtInstallationFee = new JTextField(20);
    
    panel.add(new JLabel("Capacity (Litres):"));
    panel.add(txtCapacity);
    panel.add(new JLabel("Installation Fee (Ksh):"));
    panel.add(txtInstallationFee);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Add New Tank", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            double installationFee = Double.parseDouble(txtInstallationFee.getText().trim());
            
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO tanks (Capacity_litres, Installation_fee) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, capacity);
            pstmt.setDouble(2, installationFee);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Tank added successfully!");
                loadTankData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error adding tank: " + e.getMessage());
        }
    }
}

private void editTank(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a tank to edit!");
        return;
    }
    
    int tankId = (int) model.getValueAt(selectedRow, 0);
    String currentCapacity = ((String) model.getValueAt(selectedRow, 1)).replace(",", "");
    String currentFee = ((String) model.getValueAt(selectedRow, 2)).replace(",", "");
    
    JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    JTextField txtCapacity = new JTextField(currentCapacity, 20);
    JTextField txtInstallationFee = new JTextField(currentFee, 20);
    
    panel.add(new JLabel("Capacity (Litres):"));
    panel.add(txtCapacity);
    panel.add(new JLabel("Installation Fee (Ksh):"));
    panel.add(txtInstallationFee);
    
    int result = JOptionPane.showConfirmDialog(this, panel, "Edit Tank", 
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    
    if (result == JOptionPane.OK_OPTION) {
        try {
            int capacity = Integer.parseInt(txtCapacity.getText().trim());
            double installationFee = Double.parseDouble(txtInstallationFee.getText().trim());
            
            Connection conn = DBConnection.getConnection();
            String sql = "UPDATE tanks SET Capacity_litres = ?, Installation_fee = ? WHERE TankID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, capacity);
            pstmt.setDouble(2, installationFee);
            pstmt.setInt(3, tankId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Tank updated successfully!");
                loadTankData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (NumberFormatException e) {
            showError("Invalid number format!");
        } catch (Exception e) {
            showError("Error updating tank: " + e.getMessage());
        }
    }
}

private void deleteTank(JTable table, DefaultTableModel model) {
    int selectedRow = table.getSelectedRow();
    if (selectedRow == -1) {
        showWarning("Please select a tank to delete!");
        return;
    }
    
    int tankId = (int) model.getValueAt(selectedRow, 0);
    String capacity = (String) model.getValueAt(selectedRow, 1);
    
    int confirm = JOptionPane.showConfirmDialog(this, 
        "Are you sure you want to delete tank with capacity " + capacity + " litres?", 
        "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    
    if (confirm == JOptionPane.YES_OPTION) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "DELETE FROM tanks WHERE TankID = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, tankId);
            
            int rows = pstmt.executeUpdate();
            
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Tank deleted successfully!");
                loadTankData(model);
            }
            
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            showError("Error deleting tank: " + e.getMessage());
        }
    }
}

// ========== UTILITY METHODS ==========
private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
}

private void showWarning(String message) {
    JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
}
}