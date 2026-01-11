/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author USER
 */
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import javax.swing.JOptionPane;
//import java.sql.Connection;

public class ApplyService extends javax.swing.JFrame {

    private int userId;
    private int clientId;
    private String clientName;
    private String clientCategory;
    
    public ApplyService() {
        initComponents();
        loadComboBoxData();
      // Simple scroll pane wrapper
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(getContentPane());
    setContentPane(scrollPane);
    
    // Set window to normal size
    setSize(500, 600);
    setLocationRelativeTo(null);

        
    }
// NEW CONSTRUCTOR that accepts client data
public ApplyService(int userId, int clientId, String clientName, String clientCategory) {
    // Store the client data
    this.userId = userId;
    this.clientId = clientId;
    this.clientName = clientName;
    this.clientCategory = clientCategory;
    
    // Call the normal setup methods
    initComponents();
    loadComboBoxData();
    javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(getContentPane());
    setContentPane(scrollPane);
    
    // Set window to normal size
    setSize(500, 600);
    setLocationRelativeTo(null);
    // Change them to show ACTUAL client data:
    jLabel2.setText("Client: " + clientName);      // Shows actual name
    jLabel3.setText("Category: " + clientCategory); // Shows actual category

addDepthListeners();
}


  
private void loadComboBoxData() {
    try {
        //Connect to database
        Connection conn = DBConnection.getConnection();
        
        
        //LOAD DRILLING SERVICES 
        String query1 = "SELECT Service_type, Down_payment FROM services";
        PreparedStatement pst1 = conn.prepareStatement(query1);
        ResultSet rs1 = pst1.executeQuery();
        
        jComboBox1.removeAllItems(); // Clear existing items
        while (rs1.next()) {
            String serviceName = rs1.getString("Service_type");
            double price = rs1.getDouble("Down_payment");
            jComboBox1.addItem(serviceName + " - Ksh " + String.format("%,.2f", price));
        }
        
        //LOAD PUMP TYPES 
        String query2 = "SELECT Pump_type, Pump_cost FROM pump_type";
        PreparedStatement pst2 = conn.prepareStatement(query2);
        ResultSet rs2 = pst2.executeQuery();
        
        jComboBox2.removeAllItems(); // Clear existing items
        while (rs2.next()) {
            String pumpName = rs2.getString("Pump_type");
            double price = rs2.getDouble("Pump_cost");
            jComboBox2.addItem(pumpName + " - Ksh " + String.format("%,.2f", price));
        }
        
        //  LOAD TANK CAPACITIES 
        String query3 = "SELECT Capacity_litres, Installation_fee FROM tanks ORDER BY Capacity_litres";
        PreparedStatement pst3 = conn.prepareStatement(query3);
        ResultSet rs3 = pst3.executeQuery();
        
        jComboBox3.removeAllItems(); // Clear existing items
        jComboBox3.addItem("Select capacity..."); // Default option
        while (rs3.next()) {
            int capacity = rs3.getInt("Capacity_litres");
            double fee = rs3.getDouble("Installation_fee");
            jComboBox3.addItem(capacity + " litres - Ksh " + String.format("%,.2f", fee));
        }
        
        //LOAD PIPE TYPES 
        String query4 = "SELECT Type_of_pipe, Cost_per_meter FROM pipe_types";
        PreparedStatement pst4 = conn.prepareStatement(query4);
        ResultSet rs4 = pst4.executeQuery();
        
        jComboBox4.removeAllItems(); // Clear existing items
        jComboBox4.addItem("Select pipe type..."); // Default option
        while (rs4.next()) {
            String pipeName = rs4.getString("Type_of_pipe");
            double costPerMeter = rs4.getDouble("cost_per_meter");
            jComboBox4.addItem(pipeName + " - Ksh " + String.format("%,.2f", costPerMeter) + "/meter");
        }
        
        conn.close();
        
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        e.printStackTrace();
    }
} 

    
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jCheckBoxMenuItem2.setSelected(true);
        jCheckBoxMenuItem2.setText("jCheckBoxMenuItem2");

        jCheckBoxMenuItem3.setSelected(true);
        jCheckBoxMenuItem3.setText("jCheckBoxMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("UZIMA BOREHOLE SYSTEM");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("      APPLY FOR DRILLING SERVICE ");

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setText("Client: <<client>>");

        jLabel3.setText("Category: <<category>> ");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel5.setText("REQUIRED SERVICES");

        jLabel6.setText("DRILLING SERVICE (Required) *  ");

        jLabel7.setText("PUMP TYPE (Required) *");

        jLabel8.setText("DEPTH INPUT *");

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel10.setText("meters ");

        jLabel11.setText("Total Installation Distance: << >> meters");

        jLabel12.setText("Rate: Ksh << >>/meter");

        jLabel13.setText("Installation Charge: << >> ");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 3, 14)); // NOI18N
        jLabel14.setText("OPTIONAL SERVICES ");

        jLabel9.setText("WATER TANK (Optional)");

        jCheckBox1.setText("I need a water tank installed ");

        jLabel15.setText("Tank Capacity (litres): ");

        jLabel16.setText(" PLUMBING SERVICES (Optional)");

        jCheckBox2.setText("I need plumbing services");

        jLabel17.setText("Pipe Type:");

        jLabel18.setText("Pipe Length (meters): ");

        jTextField7.setMinimumSize(new java.awt.Dimension(70, 30));
        jTextField7.setPreferredSize(new java.awt.Dimension(70, 30));

        jLabel19.setText("meters");

        jLabel20.setText("Number of Outlets: ");

        jLabel21.setText("outlets @ Ksh 3,000 each");

        jButton1.setText("[Calculate Total Cost]");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(jComboBox4);

        jLabel4.setText(" Borehole Depth (meters): * ");

        jLabel22.setText("Tank Height Above Ground (meters): *");

        jLabel23.setText("meters");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(133, 133, 133)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 98, Short.MAX_VALUE))
                            .addComponent(jSeparator1))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addComponent(jSeparator2))
                        .addGap(61, 61, 61))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel10))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel23))
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator3)))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(142, 142, 142)
                                .addComponent(jLabel14))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jCheckBox1))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel18))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel20))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel21))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jLabel22)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                                       
                                             
    try {
        // ========== 1. GET USER INPUTS ==========
        // Drilling Service
        String drillingSelected = jComboBox1.getSelectedItem().toString();
        double drillingCost = 0;
        if (drillingSelected.contains("Symmetric")) drillingCost = 130000;
        else if (drillingSelected.contains("Core")) drillingCost = 225000;
        else if (drillingSelected.contains("Geo-Technical")) drillingCost = 335000;
        
        // Pump Type
        String pumpSelected = jComboBox2.getSelectedItem().toString();
        double pumpCost = 0;
        if (pumpSelected.contains("Submersible")) pumpCost = 90000;
        else if (pumpSelected.contains("Solar")) pumpCost = 65000;
        else if (pumpSelected.contains("Hand")) pumpCost = 30000;
        
        // Borehole Depth and Tank Height
        double boreholeDepth = Double.parseDouble(jTextField3.getText().trim());
        double tankHeight = Double.parseDouble(jTextField5.getText().trim());
        double totalMeters = boreholeDepth + tankHeight;
        
        // Calculate installation rate based on depth
        double costPerMeter = 0;
        if (totalMeters <= 100) costPerMeter = 1000;
        else if (totalMeters <= 200) costPerMeter = 1500;
        else if (totalMeters <= 300) costPerMeter = 2000;
        else costPerMeter = 2500;
        
        double pumpInstallationCost = totalMeters * costPerMeter;
        
        // Update the labels to show calculation
        jLabel11.setText("Total Installation Distance: " + totalMeters + " meters");
        jLabel12.setText("Rate: Ksh " + String.format("%,.0f", costPerMeter) + "/meter");
        jLabel13.setText("Installation Charge: Ksh " + String.format("%,.2f", pumpInstallationCost));
        
        // ========== 2. OPTIONAL TANK ==========
        double tankFee = 0;
        String tankSelected = ""; // NEW: Declare outside if block
        if (jCheckBox1.isSelected() && jComboBox3.getSelectedIndex() > 0) {
            tankSelected = jComboBox3.getSelectedItem().toString(); // CHANGED: removed "String" before tankSelected
            // Extract price from string like "5000 litres - Ksh 15,000.00"
            String[] parts = tankSelected.split("Ksh ");
            if (parts.length > 1) {
                String priceStr = parts[1].replace(",", "").trim();
                tankFee = Double.parseDouble(priceStr);
            }
        }
        
        // ========== 3. OPTIONAL PLUMBING ==========
        double pipeCost = 0;
        double outletCost = 0;
        double plumbingCost = 0;
        String pipeSelected = ""; // NEW: Declare outside if block
        double pipeLength = 0;    // NEW: Declare outside if block
        int outlets = 0;          // NEW: Declare outside if block
        
        if (jCheckBox2.isSelected() && jComboBox4.getSelectedIndex() > 0) {
            // Get pipe type cost
            pipeSelected = jComboBox4.getSelectedItem().toString(); // CHANGED: removed "String" before pipeSelected
            double pipeRate = 0;
            if (pipeSelected.contains("PVC Standard")) pipeRate = 500;
            else if (pipeSelected.contains("PVC Heavy Duty")) pipeRate = 700;
            else if (pipeSelected.contains("Galvanized")) pipeRate = 900;
            else if (pipeSelected.contains("HDPE")) pipeRate = 1200;
            else if (pipeSelected.contains("Stainless")) pipeRate = 1800;
            else if (pipeSelected.contains("Copper")) pipeRate = 2500;
            
            // Get pipe length
            pipeLength = Double.parseDouble(jTextField7.getText().trim()); // CHANGED: removed "double" before pipeLength
            pipeCost = pipeLength * pipeRate;
            
            // Get outlets cost
            outlets = Integer.parseInt(jTextField8.getText().trim()); // CHANGED: removed "int" before outlets
            outletCost = outlets * 3000; // Ksh 3,000 per outlet
            
            plumbingCost = pipeCost + outletCost;
        }
        
        // ========== 4. CALCULATE TAXABLE AMOUNT ==========
        double taxableSubtotal = drillingCost + pumpCost + pumpInstallationCost + tankFee + plumbingCost;
        double tax = taxableSubtotal * 0.16; // 16% tax
        
        // ========== 5. NON-TAXABLE FEES (Survey & Authority) ==========
        // Get fees from database based on client category
        double surveyFee = 0;
        double authorityFee = 0;
        try {
            Connection conn = DBConnection.getConnection();
            String feeQuery = "SELECT Survey_fees, Local_authority_fee FROM fee_structure WHERE Client_category = ?";
            PreparedStatement feePstmt = conn.prepareStatement(feeQuery);
            feePstmt.setString(1, clientCategory);
            ResultSet feeRs = feePstmt.executeQuery();
            
            if (feeRs.next()) {
                surveyFee = feeRs.getDouble("Survey_fees");
                authorityFee = feeRs.getDouble("Local_authority_fee");
            }
            conn.close();
        } catch (SQLException e) {
            // Use default values if database query fails
            if ("Domestic".equals(clientCategory)) {
                surveyFee = 7000;
                authorityFee = 10000;
            } else if ("Commercial".equals(clientCategory)) {
                surveyFee = 15000;
                authorityFee = 30000;
            } else if ("Industrial".equals(clientCategory)) {
                surveyFee = 20000;
                authorityFee = 50000;
            }
        }
        
        // ========== 6. GRAND TOTAL ==========
        double grandTotal = taxableSubtotal + tax + surveyFee + authorityFee;
        
        // ========== 7. SHOW THE COST BREAKDOWN WINDOW ==========
        // Get the actual selections
        String drillingSelection = jComboBox1.getSelectedItem().toString();
        String pumpSelection = jComboBox2.getSelectedItem().toString();
        String tankSelection = jComboBox3.getSelectedIndex() > 0 ? jComboBox3.getSelectedItem().toString() : "";
        String pipeSelection = jComboBox4.getSelectedIndex() > 0 ? jComboBox4.getSelectedItem().toString() : "";
        
        // UPDATED: Added all the new parameters
        CostBreakdownDialog dialog = new CostBreakdownDialog(
            this, // parent frame
            drillingCost, 
            pumpCost, 
            pumpInstallationCost, 
            totalMeters, 
            costPerMeter,
            tankFee, 
            pipeCost, 
            outletCost, 
            plumbingCost,
            taxableSubtotal, 
            tax, 
            surveyFee, 
            authorityFee, 
            grandTotal,
            userId,       // NEW: Pass userId
            clientId,     // NEW: Pass clientId
            clientName,   // NEW: Pass clientName
            clientCategory, // NEW: Pass clientCategory
            drillingSelection, // NEW: Pass drilling selection
            pumpSelection,     // NEW: Pass pump selection
            tankSelection,     // NEW: Pass tank selection
            pipeSelection,     // NEW: Pass pipe selection
            pipeLength,        // NEW: Pass pipe length
            outlets,           // NEW: Pass outlets
            boreholeDepth,     // NEW: Pass borehole depth
            tankHeight         // NEW: Pass tank height
        );
        dialog.setVisible(true);
        
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, 
            "Please enter valid numbers in all fields!\n" +
            "Check: Borehole Depth, Tank Height, Pipe Length, Outlets",
            "Input Error", 
            JOptionPane.ERROR_MESSAGE
        );
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        e.printStackTrace();
    }

// TODO add your handling code here:
    
    }//GEN-LAST:event_jButton1ActionPerformed
private void addDepthListeners() {
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calculateDepthCharge();
                filterPumpsByDepth();
            }
        });
        
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                calculateDepthCharge();
            }
        });
    }
    
    private void calculateDepthCharge() {
        try {
            double depth = Double.parseDouble(jTextField3.getText().trim());
            double height = Double.parseDouble(jTextField5.getText().trim());
            double totalMeters = depth + height;
            
            // Determine cost per meter
            double costPerMeter = 0;
            if (totalMeters <= 100) costPerMeter = 1000;
            else if (totalMeters <= 200) costPerMeter = 1500;
            else if (totalMeters <= 300) costPerMeter = 2000;
            else costPerMeter = 2500;
            
            double installationCharge = totalMeters * costPerMeter;
            
            // Update the labels
            jLabel11.setText("Total Installation Distance: " + totalMeters + " meters");
            jLabel12.setText("Rate: Ksh " + String.format("%,.0f", costPerMeter) + "/meter");
            jLabel13.setText("Installation Charge: Ksh " + String.format("%,.2f", installationCharge));
            
        } catch (NumberFormatException e) {
            // Do nothing if fields are empty or invalid
        }
    }
    
    private void filterPumpsByDepth() {
        try {
            double depth = Double.parseDouble(jTextField3.getText().trim());
            
            // Save current selection
            String currentSelection = "";
            if (jComboBox2.getSelectedItem() != null) {
                currentSelection = jComboBox2.getSelectedItem().toString();
            }
            
            Connection conn = DBConnection.getConnection();
            String query = "SELECT Pump_type, Pump_cost FROM pump_type ORDER BY Pump_type";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            jComboBox2.removeAllItems();
            
            while (rs.next()) {
                String pumpName = rs.getString("Pump_type");
                double price = rs.getDouble("Pump_cost");
                
                // Apply depth rules
                boolean allowPump = true;
                
                if (pumpName.contains("Hand") && depth > 60) {
                    allowPump = false;
                }
                if (pumpName.contains("Solar") && depth > 250) {
                    allowPump = false;
                }
                if (depth > 250 && !pumpName.contains("Submersible")) {
                    allowPump = false;
                }
                
                if (allowPump) {
                    jComboBox2.addItem(pumpName + " - Ksh " + String.format("%,.2f", price));
                }
            }
            
            // Try to restore previous selection if it's still valid
            if (!currentSelection.isEmpty()) {
                for (int i = 0; i < jComboBox2.getItemCount(); i++) {
                    if (jComboBox2.getItemAt(i).equals(currentSelection)) {
                        jComboBox2.setSelectedIndex(i);
                        break;
                    }
                }
            }
            
            conn.close();
            
        } catch (Exception e) {
            // If there's an error, just reload all pumps
            try {
                Connection conn = DBConnection.getConnection();
                String query = "SELECT Pump_type, Pump_cost FROM pump_type ORDER BY Pump_type";
                PreparedStatement pst = conn.prepareStatement(query);
                ResultSet rs = pst.executeQuery();
                
                jComboBox2.removeAllItems();
                while (rs.next()) {
                    String pumpName = rs.getString("Pump_type");
                    double price = rs.getDouble("Pump_cost");
                    jComboBox2.addItem(pumpName + " - Ksh " + String.format("%,.2f", price));
                }
                conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ApplyService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ApplyService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ApplyService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ApplyService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ApplyService().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
