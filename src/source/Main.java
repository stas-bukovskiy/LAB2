package source;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    public static void main(String[] args) {
        Main frame = new Main();
        frame.setVisible(true);
    }

    public static final int WIDTH = 700;
    public static final int HEIGHT = 600;
    public static final int BUTTON_HEIGHT = 35;
    public static final Font PLAIN_FONT_14 = new Font("Arial", Font.PLAIN, 14);
    public static final Font PLAIN_FONT_16 = new Font("Arial", Font.PLAIN, 16);

    private JTable goodsTable;
    private JButton addButton;
    private JButton openButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private List<JButton> buttons;

    public Main() {
        initFrame();
        initFrameContent();
        initListeners();
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

        goodsTable = getGroupsTable();
        JScrollPane goodsScrolledTable = new JScrollPane(goodsTable);


        addButton = new JButton("Add");
        openButton = new JButton("Open");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");
        initButtons();
        buttons.forEach(button -> {
            button.setPreferredSize(new Dimension(-1 ,BUTTON_HEIGHT));
            button.setFont(PLAIN_FONT_14);
        });


        add(panel, goodsScrolledTable, gbc, 0, 0, 3, 3, 1, 1);
        add(panel, openButton, gbc, 0, 3, 1, 1, 1, 1);
        add(panel, editButton, gbc, 1, 3, 1, 1, 1, 1);
        add(panel, addButton, gbc, 2, 3, 1, 1, 1, 1);
        add(panel, deleteButton, gbc, 0, 4, 1, 1, 1, 1);
        add(panel, backButton, gbc, 1, 4, 2, 1, 1, 1);

        getContentPane().add(panel);
    }

    private JTable getGroupsTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Amount");

        model.addRow(new String[] {"Name1", "23"});
        model.addRow(new String[] {"Name2", "23"});
        model.addRow(new String[] {"Name3", "23"});

        JTable table = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumnModel().getColumn(1).setCellRenderer(render);

        table.getColumnModel().getColumn(0).setPreferredWidth((int) (0.6*WIDTH));
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth((int) (0.2*WIDTH));
        table.setRowHeight(25);
        table.setFont(PLAIN_FONT_14);
        return table;
    }

    private JTable getProductsTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Producer");
        model.addColumn("Amount");
        model.addColumn("Price");
        model.addColumn("Overall Price");

        model.addRow(new String[] {"Name1", "Producer1", "10", "22.00", "220.00"});
        model.addRow(new String[] {"Name2", "Producer2", "10", "22.00", "220.00"});
        model.addRow(new String[] {"Name3", "Producer3", "10", "22.00", "220.00"});
        model.addRow(new String[] {"Name4", "Producer4", "10", "22.00", "220.00"});

        JTable table = getUneditableTable();

        table.getColumnModel().getColumn(1).setCellRenderer(render);

        table.getColumnModel().getColumn(0).setPreferredWidth((int) (0.6*WIDTH));
        table.getColumnModel().getColumn(0).setResizable(false);
        table.getColumnModel().getColumn(1).setPreferredWidth((int) (0.2*WIDTH));
        table.setRowHeight(25);
        table.setFont(PLAIN_FONT_14);
        return table;
    }

    private JTable getUneditableTable() {
        JTable table = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void initButtons() {
        buttons = new ArrayList<>();
        buttons.add(addButton);
        buttons.add(openButton);
        buttons.add(editButton);
        buttons.add(deleteButton);
        buttons.add(backButton);
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


    private void initListeners() {
        openButton.addActionListener(e -> {
            OpenDialog openDialog = new OpenDialog(this, true);
            openDialog.setVisible(true);
        });
        editButton.addActionListener(e -> {

        });
        addButton.addActionListener(e -> {

        });
        deleteButton.addActionListener(e -> {

        });
        backButton.addActionListener(e -> {

        });
    }
}
