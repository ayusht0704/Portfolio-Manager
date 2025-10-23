package portfoliomanager.ui;

import portfoliomanager.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MainFrame extends JFrame {
    private Portfolio portfolio;

    // Tables and models for three asset types
    private JTable stockTable, bondTable, investTable;
    private DefaultTableModel stockModel, bondModel, investModel;
    private JLabel totalValueLabel;

    public MainFrame(Portfolio portfolio) {
        this.portfolio = portfolio;
        setTitle("Portfolio Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        // --- STOCKS TAB ---
        stockModel = new DefaultTableModel(new String[]{"Symbol","Company","Qty","Price","Value"},0);
        stockTable = new JTable(stockModel);
        JPanel stockPanel = new JPanel(new BorderLayout());
        stockPanel.add(new JScrollPane(stockTable), BorderLayout.CENTER);
        stockPanel.add(createStockButtonPanel(), BorderLayout.NORTH);
        tabs.add("Stocks", stockPanel);

        // --- BONDS TAB ---
        bondModel = new DefaultTableModel(new String[]{"ID","Name","Face","Coupon %","Qty","Market Price","Value"},0);
        bondTable = new JTable(bondModel);
        JPanel bondPanel = new JPanel(new BorderLayout());
        bondPanel.add(new JScrollPane(bondTable), BorderLayout.CENTER);
        bondPanel.add(createBondButtonPanel(), BorderLayout.NORTH);
        tabs.add("Bonds", bondPanel);

        // --- INVESTMENTS TAB ---
        investModel = new DefaultTableModel(new String[]{"ID","Name","Units","NAV","Value"},0);
        investTable = new JTable(investModel);
        JPanel investPanel = new JPanel(new BorderLayout());
        investPanel.add(new JScrollPane(investTable), BorderLayout.CENTER);
        investPanel.add(createInvestmentButtonPanel(), BorderLayout.NORTH);
        tabs.add("Investments", investPanel);

        // Total Value Label
        totalValueLabel = new JLabel("Total Portfolio Value: ₹0.00");
        totalValueLabel.setFont(new Font("Arial", Font.BOLD, 16));

        add(tabs, BorderLayout.CENTER);
        add(totalValueLabel, BorderLayout.SOUTH);

        refreshTables();
    }

    // ---------- STOCK BUTTON PANEL ----------
    private JPanel createStockButtonPanel() {
        JButton add = new JButton("Add Stock");
        JButton edit = new JButton("Edit Stock");
        JButton remove = new JButton("Remove Stock");
        JButton save = new JButton("Save All");
        JButton load = new JButton("Load All");

        add.addActionListener(e -> {
            AddStockDialog dlg = new AddStockDialog(this);
            dlg.setVisible(true);
            Stock s = dlg.getCreatedStock();
            if (s != null) { portfolio.addStock(s); refreshTables(); }
        });

        edit.addActionListener(e -> {
            int row = stockTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select a stock to edit"); return; }
            String sym = (String) stockModel.getValueAt(row,0);
            EditStockDialog dlg = new EditStockDialog(this, portfolio, sym);
            dlg.setVisible(true);
            refreshTables();
        });

        remove.addActionListener(e -> {
            int row = stockTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select a stock to remove"); return; }
            String sym = (String) stockModel.getValueAt(row,0);
            portfolio.removeStock(sym);
            refreshTables();
        });

        save.addActionListener(e -> savePortfolio());
        load.addActionListener(e -> loadPortfolio());

        JPanel p = new JPanel();
        p.add(add); p.add(edit); p.add(remove); p.add(save); p.add(load);
        return p;
    }

    // ---------- BOND BUTTON PANEL ----------
    private JPanel createBondButtonPanel() {
        JButton add = new JButton("Add Bond");
        JButton edit = new JButton("Edit Bond");
        JButton remove = new JButton("Remove Bond");

        add.addActionListener(e -> {
            AddBondDialog dlg = new AddBondDialog(this);
            dlg.setVisible(true);
            Bond b = dlg.getCreatedBond();
            if (b != null) { portfolio.addBond(b); refreshTables(); }
        });

        edit.addActionListener(e -> {
            int row = bondTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select a bond to edit"); return; }
            String id = (String) bondModel.getValueAt(row,0);
            EditBondDialog dlg = new EditBondDialog(this, portfolio, id);
            dlg.setVisible(true);
            refreshTables();
        });

        remove.addActionListener(e -> {
            int row = bondTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select a bond to remove"); return; }
            String id = (String) bondModel.getValueAt(row,0);
            portfolio.removeBond(id);
            refreshTables();
        });

        JPanel p = new JPanel();
        p.add(add); p.add(edit); p.add(remove);
        return p;
    }

    // ---------- INVESTMENT BUTTON PANEL ----------
    private JPanel createInvestmentButtonPanel() {
        JButton add = new JButton("Add Investment");
        JButton edit = new JButton("Edit Investment");
        JButton remove = new JButton("Remove Investment");

        add.addActionListener(e -> {
            AddInvestmentDialog dlg = new AddInvestmentDialog(this);
            dlg.setVisible(true);
            Investment i = dlg.getCreatedInvestment();
            if (i != null) { portfolio.addInvestment(i); refreshTables(); }
        });

        edit.addActionListener(e -> {
            int row = investTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select an investment to edit"); return; }
            String id = (String) investModel.getValueAt(row,0);
            EditInvestmentDialog dlg = new EditInvestmentDialog(this, portfolio, id);
            dlg.setVisible(true);
            refreshTables();
        });

        remove.addActionListener(e -> {
            int row = investTable.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(this,"Select an investment to remove"); return; }
            String id = (String) investModel.getValueAt(row,0);
            portfolio.removeInvestment(id);
            refreshTables();
        });

        JPanel p = new JPanel();
        p.add(add); p.add(edit); p.add(remove);
        return p;
    }

    // ---------- REFRESH TABLES ----------
    private void refreshTables() {
        // Stocks
        stockModel.setRowCount(0);
        for (Stock s : portfolio.getStocks()) {
            stockModel.addRow(new Object[]{s.getSymbol(),s.getCompanyName(),s.getQuantity(),s.getPrice(),s.getValue()});
        }
        // Bonds
        bondModel.setRowCount(0);
        for (Bond b : portfolio.getBonds()) {
            bondModel.addRow(new Object[]{b.getId(),b.getName(),b.getFaceValue(),b.getCouponRate(),b.getQuantity(),b.getMarketPrice(),b.getValue()});
        }
        // Investments
        investModel.setRowCount(0);
        for (Investment i : portfolio.getInvestments()) {
            investModel.addRow(new Object[]{i.getId(),i.getName(),i.getUnits(),i.getNav(),i.getValue()});
        }

        totalValueLabel.setText("Total Portfolio Value: ₹" + String.format("%.2f", portfolio.getTotalValue()));
    }

    // ---------- SAVE / LOAD ----------
    private void savePortfolio() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            portfolio.saveAll(dir.getAbsolutePath());
            JOptionPane.showMessageDialog(this,"Portfolio saved to "+dir.getAbsolutePath());
        }
    }

    private void loadPortfolio() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
            portfolio.loadAll(dir.getAbsolutePath());
            refreshTables();
            JOptionPane.showMessageDialog(this,"Portfolio loaded from "+dir.getAbsolutePath());
        }
    }
}
