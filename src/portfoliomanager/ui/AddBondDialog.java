package portfoliomanager.ui;

import java.awt.*;
import javax.swing.*;
import portfoliomanager.model.Bond;

public class AddBondDialog extends JDialog {
    private JTextField idField = new JTextField(10);
    private JTextField nameField = new JTextField(20);
    private JTextField faceField = new JTextField(8);
    private JTextField couponField = new JTextField(6);
    private JTextField qtyField = new JTextField(6);
    private JTextField marketField = new JTextField(8);

    private Bond createdBond = null;

    public AddBondDialog(Frame owner) {
        super(owner, "Add Bond", true);
        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(6,2,6,6));
        p.add(new JLabel("Bond ID:")); p.add(idField);
        p.add(new JLabel("Name:")); p.add(nameField);
        p.add(new JLabel("Face Value:")); p.add(faceField);
        p.add(new JLabel("Coupon Rate (%) :")); p.add(couponField);
        p.add(new JLabel("Quantity:")); p.add(qtyField);
        p.add(new JLabel("Market Price:")); p.add(marketField);

        JButton add = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        JPanel btns = new JPanel(); btns.add(add); btns.add(cancel);

        add.addActionListener(e -> onAdd());
        cancel.addActionListener(e -> { createdBond = null; setVisible(false); });

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(btns, BorderLayout.SOUTH);
    }

    private void onAdd() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            double face = Double.parseDouble(faceField.getText().trim());
            double coupon = Double.parseDouble(couponField.getText().trim());
            int qty = Integer.parseInt(qtyField.getText().trim());
            double market = Double.parseDouble(marketField.getText().trim());
            if (id.isEmpty() || name.isEmpty()) throw new IllegalArgumentException("Empty fields");
            createdBond = new Bond(id.toUpperCase(), name, face, coupon, qty, market);
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Bond getCreatedBond() { return createdBond; }
}
