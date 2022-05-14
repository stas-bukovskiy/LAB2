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
    public static final int LABEL_HEIGHT = 25;
    public static final Font PLAIN_FONT_14 = new Font("Arial", Font.PLAIN, 14);
    public static final Font PLAIN_FONT_16 = new Font("Arial", Font.PLAIN, 16);
    public static final int BIG_INSERTS = 10;
    public static final int SMALL_INSERTS = 5;

    DefaultTableCellRenderer centralRenderer;
    private DefaultTableModel tableModel;
    private JTable goodsTable;
    private JButton addButton;
    private JButton openButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private List<JButton> buttons;
    private State current;

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

        centralRenderer = new DefaultTableCellRenderer();
        centralRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        current = State.GROUPS;
        tableModel = new DefaultTableModel();
        goodsTable = new JTable(tableModel) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        goodsTable.setRowHeight(25);
        goodsTable.setFont(PLAIN_FONT_14);
        setGroupsTableProperties();
        JScrollPane goodsScrolledTable = new JScrollPane(goodsTable);


        addButton = new JButton("Add");
        openButton = new JButton("Open");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");
        initButtons();
        buttons.forEach(button -> {
            button.setPreferredSize(new Dimension(-1, BUTTON_HEIGHT));
            button.setFont(PLAIN_FONT_14);
        });


        add(panel, goodsScrolledTable, gbc, 0, 0, 3, 3);
        add(panel, openButton, gbc, 0, 3, 1, 1);
        add(panel, editButton, gbc, 1, 3, 1, 1);
        add(panel, addButton, gbc, 2, 3, 1, 1);
        add(panel, deleteButton, gbc, 0, 4, 1, 1);
        add(panel, backButton, gbc, 1, 4, 2, 1);

        getContentPane().add(panel);
    }

    private void setGroupsTableProperties() {
        clearTable();
        tableModel.addColumn("Name");
        tableModel.addColumn("Amount");

        tableModel.addRow(new String[] {"Name1", "23"});
        tableModel.addRow(new String[] {"Name2", "23"});
        tableModel.addRow(new String[] {"Name3", "23"});

        goodsTable.getColumnModel().getColumn(1).setCellRenderer(centralRenderer);

        goodsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (0.6*WIDTH));
        goodsTable.getColumnModel().getColumn(0).setResizable(false);
        goodsTable.getColumnModel().getColumn(1).setPreferredWidth((int) (0.2*WIDTH));

    }


    private void clearTable() {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);
    }

    private void setProductsTableProperties() {
        clearTable();
        tableModel.addColumn("Name");
        tableModel.addColumn("Producer");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Price");
        tableModel.addColumn("Overall Price");

        tableModel.addRow(new String[] {"Name1", "Producer1", "10", "22.00", "220.00"});
        tableModel.addRow(new String[] {"Name2", "Producer2", "10", "22.00", "220.00"});
        tableModel.addRow(new String[] {"Name3", "Producer3", "10", "22.00", "220.00"});
        tableModel.addRow(new String[] {"Name4", "Producer4", "10", "22.00", "220.00"});

        goodsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (0.35*goodsTable.getWidth()));
        goodsTable.getColumnModel().getColumn(0).setResizable(false);
        goodsTable.getColumnModel().getColumn(1).setPreferredWidth((int) (0.35*WIDTH));
        goodsTable.getColumnModel().getColumn(2).setPreferredWidth((int) (0.1*WIDTH));
        goodsTable.getColumnModel().getColumn(2).setResizable(false);
        goodsTable.getColumnModel().getColumn(3).setPreferredWidth((int) (0.1*WIDTH));

        goodsTable.getColumnModel().getColumn(2).setCellRenderer(centralRenderer);
        goodsTable.getColumnModel().getColumn(3).setCellRenderer(centralRenderer);
        goodsTable.getColumnModel().getColumn(4).setCellRenderer(centralRenderer);
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
        addWithInserts(gbc, x, y, width, height, SMALL_INSERTS);
        gbc.weightx = wx;
        gbc.weighty = wy;
        panel.add(component, gbc);
    }

    public static void add(Panel panel, Component component, GridBagConstraints gbc, int x, int y, int width, int height) {
        addWithInserts(gbc, x, y, width, height, BIG_INSERTS);
        gbc.weightx = 1;
        gbc.weighty = 1;
        panel.add(component, gbc);
    }

    public static void addWithInserts(GridBagConstraints gbc, int x, int y, int width, int height, int inserts) {
        gbc.insets = new Insets(inserts, inserts, inserts, inserts);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
    }


    private void initListeners() {
        openButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1) {
                if (current == State.GROUPS) {
                    setProductsTableProperties();
                    current = State.PRODUCTS;
                } else {
                    int selectedProductIndex = goodsTable.getSelectedRow();
                    OpenDialog openDialog = new OpenDialog(this, true);
                    openDialog.setVisible(true);
                }
            }
        });
        editButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1) {
                if (current == State.GROUPS) {
                    OpenDialog openDialog = new OpenDialog(this, true, 1);
                    openDialog.setVisible(true);
                } else {
                    OpenDialog openDialog = new OpenDialog(this, true);
                    openDialog.setVisible(true);
                }
            }
        });
        addButton.addActionListener(e -> {
            if (current == State.GROUPS) {
                setProductsTableProperties();
                current = State.PRODUCTS;
            } else {
                int selectedProductIndex = goodsTable.getSelectedRow();
                OpenDialog openDialog = new OpenDialog(this, true);
                openDialog.setVisible(true);
            }
        });
        deleteButton.addActionListener(e -> {
            int item = getSelectedRow();
            if(item != -1 && isConfirmation("Do you really want to delete " + item + "?")) {

            }

        });
        backButton.addActionListener(e -> {
            System.out.println(State.PRODUCTS == current);;
            if(current == State.PRODUCTS) {
                setGroupsTableProperties();
                current = State.GROUPS;
            }
        });
    }

    private void changePanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        update(getGraphics());
    }


    private int getSelectedRow() {
        int index = goodsTable.getSelectedRow();
        if(index == -1) {
            if (goodsTable.getRowCount() > 0) goodsTable.setRowSelectionInterval(0, 0);
            else JOptionPane.showMessageDialog(this, "The list is empty!");
        }
        return index;
    }

    private boolean isConfirmation(String text) {
        return JOptionPane.showConfirmDialog(this, text, "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private enum State {
        GROUPS,
        PRODUCTS
    }
}
