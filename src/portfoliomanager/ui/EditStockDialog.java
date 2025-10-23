package portfoliomanager.ui;

import portfoliomanager.model.Stock;
import portfoliomanager.model.Portfolio;
import javax.swing.*;
import java.awt.*;

public class EditStockDialog extends JDialog {
    private JTextField nameField = new JTextField(20);
    private JTextField qtyField = new JTextField(6);
    private JTextField priceField = new JTextField(8);
    private Stock stock;

    public EditStockDialog(Frame owner, Portfolio portfolio, String symbol) {
        super(owner, "Edit Stock - " + symbol, true);
        this.stock = portfolio.getStocks().stream()
                .filter(s -> s.getSymbol().equalsIgnoreCase(symbol))
                .findFirst().orElse(null);
        if(stock==null){ JOptionPane.showMessageDialog(owner,"Stock not found"); dispose(); return; }

        nameField.setText(stock.getCompanyName());
        qtyField.setText(String.valueOf(stock.getQuantity()));
        priceField.setText(String.valueOf(stock.getPrice()));

        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel form = new JPanel(new GridLayout(4,2,6,6));
        form.add(new JLabel("Symbol:")); form.add(new JLabel(stock.getSymbol()));
        form.add(new JLabel("Company Name:")); form.add(nameField);
        form.add(new JLabel("Quantity:")); form.add(qtyField);
        form.add(new JLabel("Price per share:")); form.add(priceField);

        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");
        JPanel btns = new JPanel(); btns.add(saveBtn); btns.add(cancelBtn);

        saveBtn.addActionListener(e -> onSave());
        cancelBtn.addActionListener(e -> setVisible(false));

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(form, BorderLayout.CENTER);
        getContentPane().add(btns, BorderLayout.SOUTH);
    }

    private void onSave() {
        try {
            stock.setQuantity(Integer.parseInt(qtyField.getText().trim()));
            stock.setPrice(Double.parseDouble(priceField.getText().trim()));
            stock.setCompanyName(nameField.getText().trim());
            setVisible(false);
        } catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid input: "+ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
