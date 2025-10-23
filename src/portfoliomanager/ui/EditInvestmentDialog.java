package portfoliomanager.ui;

import portfoliomanager.model.Investment;
import portfoliomanager.model.Portfolio;

import javax.swing.*;
import java.awt.*;

public class EditInvestmentDialog extends JDialog {
    private JTextField nameField = new JTextField(20);
    private JTextField unitsField = new JTextField(8);
    private JTextField navField = new JTextField(8);

    private Investment investment;

    public EditInvestmentDialog(Frame owner, Portfolio portfolio, String id) {
        super(owner, "Edit Investment - " + id, true);
        this.investment = portfolio.getInvestments().stream()
                .filter(i -> i.getId().equalsIgnoreCase(id))
                .findFirst().orElse(null);

        if (investment == null) {
            JOptionPane.showMessageDialog(owner, "Investment not found: " + id);
            dispose();
            return;
        }

        nameField.setText(investment.getName());
        unitsField.setText(String.valueOf(investment.getUnits()));
        navField.setText(String.valueOf(investment.getNav()));

        initUI();
        pack();
        setLocationRelativeTo(owner);
    }

    private void initUI() {
        JPanel p = new JPanel(new GridLayout(4,2,6,6));
        p.add(new JLabel("ID:")); p.add(new JLabel(investment.getId()));
        p.add(new JLabel("Name:")); p.add(nameField);
        p.add(new JLabel("Units:")); p.add(unitsField);
        p.add(new JLabel("NAV:")); p.add(navField);

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
            investment.setName(nameField.getText().trim());
            investment.setUnits(Double.parseDouble(unitsField.getText().trim()));
            investment.setNav(Double.parseDouble(navField.getText().trim()));
            setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
