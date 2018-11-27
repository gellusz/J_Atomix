package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ScoreBoardPanel extends JPanel {
    public JPanel panel1;
    private JTable table1;
    public JButton backButton;
    private JScrollPane scroller;

    /**
     * Constructor:
     * Sets up form bound (created with Intellij Idea) panel
     * @param listener listener that waits the generated ActionEvent
     */
    public ScoreBoardPanel(JTable table, ActionListener listener) {
        table1 = table;
        table1.setAutoCreateRowSorter(true);
        table1.setFillsViewportHeight(true);
        table1.setCellSelectionEnabled(false);
        table1.getTableHeader().setReorderingAllowed(false);
        table1.setFocusable(false);
        $$$setupUI$$$();
        add(panel1);
        backButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        backButton.addActionListener(listener);
    }

    /**
     * Intellij Idea GUI form method Called by Constructor.
     * Creates custom-create components
     */
    private void createUIComponents() {
        scroller = new JScrollPane(table1);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        panel1 = new JPanel();
        panel1.setLayout(new BorderLayout(0, 100));
        backButton = new JButton();
        backButton.setText("Back");
        panel1.add(backButton, BorderLayout.SOUTH);
        panel1.add(scroller, BorderLayout.CENTER);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$("Stencil", -1, 24, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setHorizontalAlignment(0);
        label1.setHorizontalTextPosition(11);
        label1.setText("Scores");
        panel1.add(label1, BorderLayout.NORTH);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel1;
    }
}
