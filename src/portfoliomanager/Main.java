package portfoliomanager;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import portfoliomanager.model.Portfolio;
import portfoliomanager.ui.MainFrame;

/**
 * Entry point for Portfolio Manager.
 * Launches GUI and creates Portfolio model.
 */
public class Main {
    public static void main(String[] args) {
        // Optional: set system look & feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            // ignore, use default
        }

        SwingUtilities.invokeLater(() -> {
            Portfolio portfolio = new Portfolio();
            MainFrame mainFrame = new MainFrame(portfolio);
            mainFrame.setVisible(true);
        });
    }
} 
