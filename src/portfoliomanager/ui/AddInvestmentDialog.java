package portfoliomanager.ui;

import portfoliomanager.model.Investment;

import javax.swing.*;
import java.awt.*;

public class AddInvestmentDialog extends JDialog {
    private JTextField idField = new JTextField(10);
    private JTextField nameField = new JTextField(20);
    private JTextField unitsField = new JTextField(8);
    private JTextField navField = new JTextField(8);

    private Investment createdInvestment = null;

    public AddInvestmentDialog(Frame owner) {
        super(owner, "Add Investment", true);
        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(4,2,6,6));
        p.add(new JLabel("ID:")); p.add(idField);
        p.add(new JLabel("Name:")); p.add(nameField);
        p.add(new JLabel("Units:")); p.add(unitsField);
        p.add(new JLabel("NAV:")); p.add(navField);

        JButton add = new JButton("Add");
        JButton cancel = new JButton("Cancel");
        JPanel btns = new JPanel(); btns.add(add); btns.add(cancel);

        add.addActionListener(e -> onAdd());
        cancel.addActionListener(e -> { createdInvestment = null; setVisible(false); });

        getContentPane().setLayout(new BorderLayout(8,8));
        getContentPane().add(p, BorderLayout.CENTER);
        getContentPane().add(btns, BorderLayout.SOUTH);
    }

    private void onAdd() {
        try {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            double units = Double.parseDouble(unitsField.getText().trim());
            double nav = Double.parseDouble(navField.getText().trim());
            if (id.isEmpty() || name.isEmpty()) throw new IllegalArgumentException("Empty fields");
            createdInvestment = new Investment(id.toUpperCase(), name, units, nav);
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Investment getCreatedInvestment() { return createdInvestment; }
}
