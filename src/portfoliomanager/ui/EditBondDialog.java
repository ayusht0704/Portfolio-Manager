package portfoliomanager.ui;

import java.awt.*;
import javax.swing.*;
import portfoliomanager.model.Bond;
import portfoliomanager.model.Portfolio;

public class EditBondDialog extends JDialog {
    private JTextField nameField = new JTextField(20);
    private JTextField faceField = new JTextField(8);
    private JTextField couponField = new JTextField(6);
    private JTextField qtyField = new JTextField(6);
    private JTextField marketField = new JTextField(8);

    private Bond bond;

    public EditBondDialog(Frame owner, Portfolio portfolio, String bondId) {
        super(owner, "Edit Bond - " + bondId, true);
        this.bond = portfolio.getBonds().stream()
                .filter(b -> b.getId().equalsIgnoreCase(bondId))
                .findFirst().orElse(null);

        if (bond == null) {
            JOptionPane.showMessageDialog(owner, "Bond not found: " + bondId);
            dispose();
            return;
        }

        nameField.setText(bond.getName());
        faceField.setText(String.valueOf(bond.getFaceValue()));
        couponField.setText(String.valueOf(bond.getCouponRate()));
        qtyField.setText(String.valueOf(bond.getQuantity()));
        marketField.setText(String.valueOf(bond.getMarketPrice()));

        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(6,2,6,6));
        p.add(new JLabel("Bond ID:")); p.add(new JLabel(bond.getId()));
        p.add(new JLabel("Name:")); p.add(nameField);
        p.add(new JLabel("Face Value:")); p.add(faceField);
        p.add(new JLabel("Coupon Rate (%):")); p.add(couponField);
        p.add(new JLabel("Quantity:")); p.add(qtyField);
        p.add(new JLabel("Market Price:")); p.add(marketField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        JPanel btns = new JPanel(); btns.add(save); btns.add(cancel);

        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> setVisible(false));

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(btns, BorderLayout.SOUTH);
    }

    private void onSave() {
        try {
            bond.setName(nameField.getText().trim());
            bond.setFaceValue(Double.parseDouble(faceField.getText().trim()));
            bond.setCouponRate(Double.parseDouble(couponField.getText().trim()));
            bond.setQuantity(Integer.parseInt(qtyField.getText().trim()));
            bond.setMarketPrice(Double.parseDouble(marketField.getText().trim()));
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
