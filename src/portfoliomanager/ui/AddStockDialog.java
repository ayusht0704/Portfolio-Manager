package portfoliomanager.ui;

import portfoliomanager.model.Stock;
import javax.swing.*;
import java.awt.*;

public class AddStockDialog extends JDialog {
    private JTextField symbolField = new JTextField(10);
    private JTextField nameField = new JTextField(20);
    private JTextField qtyField = new JTextField(6);
    private JTextField priceField = new JTextField(8);

    private Stock createdStock = null;

    public AddStockDialog(Frame owner) {
        super(owner, "Add New Stock", true);
        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(4, 2, 6, 6));
        form.add(new JLabel("Symbol:")); form.add(symbolField);
        form.add(new JLabel("Company Name:")); form.add(nameField);
        form.add(new JLabel("Quantity:")); form.add(qtyField);
        form.add(new JLabel("Price per share:")); form.add(priceField);

        JButton addBtn = new JButton("Add");
        JButton cancelBtn = new JButton("Cancel");
        JPanel buttons = new JPanel();
        buttons.add(addBtn); buttons.add(cancelBtn);

        addBtn.addActionListener(e -> onAdd());
        cancelBtn.addActionListener(e -> { createdStock = null; setVisible(false); });

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(buttons, BorderLayout.SOUTH);
    }

    private void onAdd() {
        try {
            String symbol = symbolField.getText().trim();
            String name = nameField.getText().trim();
            int qty = Integer.parseInt(qtyField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());
            if(symbol.isEmpty() || name.isEmpty() || qty < 0 || price < 0)
                throw new IllegalArgumentException("Invalid input");
            createdStock = new Stock(symbol.toUpperCase(), name, qty, price);
            setVisible(false);
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(this,"Invalid input: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public Stock getCreatedStock() { return createdStock; }
}
