import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }

    private static final int WIDTH = 700;
    private static final int HEIGHT = 600;
    private static final int BUTTON_HEIGHT = 35;

    private JTable goodsTable;
    private JButton addButton;
    private JButton openButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton undoButton;
    private JButton homeButton;
    private List<JButton> buttons;

    public Main() {


        initFrame();
        initFrameContent();
    }

    private void initFrame() {
        setTitle("Some Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, WIDTH, HEIGHT);
        setMaximumSize(new Dimension((int) (1.1*WIDTH), (int) (1.1*HEIGHT)));
        setMinimumSize(new Dimension((int) (0.9*WIDTH), HEIGHT));
    }

    private void initFrameContent() {
        Panel panel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        String[] columnHeaders = new String[]{"Name", "Amount"};
        String[][] contents = new String[][]{};
        goodsTable = new JTable(contents, columnHeaders);


        JScrollPane goodsScrolledTable = new JScrollPane(goodsTable);


        addButton = new JButton("Add");
        openButton = new JButton("Open");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        undoButton = new JButton("Undo");
        homeButton = new JButton("Home");
        initButtons();
        buttons.forEach(button -> button.setPreferredSize(new Dimension(-1 ,BUTTON_HEIGHT)));


        add(panel, goodsScrolledTable, gbc, 0, 0, 3, 3, 1, 1);
        add(panel, openButton, gbc, 0, 3, 1, 1, 1, 1);
        add(panel, editButton, gbc, 1, 3, 1, 1, 1, 1);
        add(panel, addButton, gbc, 2, 3, 1, 1, 1, 1);
        add(panel, undoButton, gbc, 0, 4, 1, 1, 1, 1);
        add(panel, deleteButton, gbc, 1, 4, 1, 1, 1, 1);
        add(panel, homeButton, gbc, 2, 4, 1, 1, 1, 1);

        getContentPane().add(panel);
    }

    private void initButtons() {
        buttons = new ArrayList<>();
        buttons.add(addButton);
        buttons.add(openButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(undoButton);
        buttons.add(homeButton);
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
    public void add(Panel panel, Component component, GridBagConstraints gbc, int x, int y, int width, int height, double wx, double wy) {
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
