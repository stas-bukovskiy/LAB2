import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }

    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;

    private JTable goodsTable;

    public Main() {
        initFrame();
        initFrameContent();
    }

    private void initFrame() {
        setTitle("Some Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, WIDTH, HEIGHT);
    }

    private void initFrameContent() {
        JPanel panel = new JPanel(new GridBagLayout());

    }

    /**
     * The method that helps to add components to GridBagLayout
     * @param panel     - panel where component that will be added.
     * @param component - component that will be added.
     * @param gbc       - GridBagConstraints instance.
     * @param x         - x-coordinate of cell.
     * @param y         - y-coordinate of cell.
     * @param width     - specifies the number of cells in a row for the component's display area.
     * @param height    - specifies the number of cells in a column for the component's display area.
     * @param wx        - specifies how to distribute extra horizontal space.
     * @param wy        - specifies how to distribute extra vertical space.
     */
    public static void add(Panel panel, Component component, GridBagConstraints gbc, int x, int y, int width, int height, double wx, double wy) {
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = wx;
        gbc.weighty = wy;
        panel.add(component, gbc);
    }
}
