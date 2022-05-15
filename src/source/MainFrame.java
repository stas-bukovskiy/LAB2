package source;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
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

    GridBagConstraints gbc;

    private JButton saveButton;
    private JButton cancelButton;
    private JButton addButton;
    private JButton openButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton backButton;
    private JButton writeOffButton;
    private JButton searchButton;
    private JButton printALlInfoButton;
    private JButton[] buttons;

    private Map<String, JLabel> labels;

    private JComboBox<String> groupComboBox;
    private ButtonGroup searchProperty;
    private JTextField searchField;
    private JRadioButton groupRadioButton;
    private JRadioButton productRadioButton;
    private JTextField nameField;
    private JTextArea descriptionsTextArea;
    private JTextArea allInfoTextArea;
    private JTextField amountField;
    private JTextField producerField;
    private JTextField priceField;

    private JTable groupsTable;
    private JTable productsTable;
    private DefaultTableCellRenderer centralRenderer;

    private State current;
    private State previous;
    private int previousGroupIndex;
    private int editIndex;



    public MainFrame(){
        initFrame();
        initComponents();
        DataIO.readInfoFromFile();
        getContentPane().add(getGroupsPanel());
        initListeners();
        current = State.GROUPS;
    }

    private void initComponents() {
        groupComboBox = new JComboBox<>();
        groupComboBox.setPreferredSize(new Dimension(-1, LABEL_HEIGHT));
        nameField = new JTextField();
        nameField.setName("Name Field");
        descriptionsTextArea = new JTextArea();
        descriptionsTextArea.setName("Descriptions Field");
        descriptionsTextArea.setFont(PLAIN_FONT_14);
        allInfoTextArea = new JTextArea();
        allInfoTextArea.setFont(PLAIN_FONT_14);
        allInfoTextArea.setBorder(new EmptyBorder(0, 0, 0, 0));
        allInfoTextArea.setBackground(this.getBackground());
        allInfoTextArea.setFocusable(false);
        amountField = new JTextField();
        amountField.setName("Amount Field");
        acceptOnlyInteger(amountField);
        producerField = new JTextField();
        producerField.setName("Producer Field");
        priceField = new JTextField();
        priceField.setName("Price Field");
        acceptOnlyDouble(priceField);
        searchField = new JTextField();

        searchProperty = new ButtonGroup();
        groupRadioButton = new JRadioButton("Group");
        groupRadioButton.setFont(PLAIN_FONT_16);
        productRadioButton = new JRadioButton("Product");
        productRadioButton.setFont(PLAIN_FONT_16);
        searchProperty.add(groupRadioButton);
        searchProperty.add(productRadioButton);

        for(JComponent component: new JComponent[]{nameField, amountField, producerField, priceField, searchField}){
            component.setFont(Main.PLAIN_FONT_14);
            component.setPreferredSize(new Dimension(-1, LABEL_HEIGHT));
        }

        initTableComponents();
        initButtons();
        initLabels();

        gbc = new GridBagConstraints();
    }

    private JPanel getOpenAndEditGroupPanel() {
        JPanel res = new JPanel(new GridBagLayout());
        res.setBorder(new EmptyBorder(50, 50, 50, 50));
        nameField.setText("");
        descriptionsTextArea.setText("");
        JScrollPane scrolledDescriptionsTextArea = new JScrollPane(descriptionsTextArea);
        scrolledDescriptionsTextArea.setPreferredSize(new Dimension(-1, 200));
        add(res, labels.get("Name"), gbc, 0, 0, 1, 1, 1, 1, BIG_INSERTS);
        add(res, nameField, gbc, 1, 0, 1, 1,1, 1, BIG_INSERTS);
        add(res, labels.get("Description"), gbc, 0, 1, 2, 1,1, 1, BIG_INSERTS);
        add(res, scrolledDescriptionsTextArea, gbc, 0, 2, 2, 3, 1, 1, BIG_INSERTS);
        add(res, saveButton, gbc, 0, 5, 1, 1, 1, 1, BIG_INSERTS);
        add(res, cancelButton, gbc, 1, 5, 1, 1, 1, 1, BIG_INSERTS);
        return res;
    }

    private void fillOpenAndEditGroupPanel(int index) {
        Group group = Group.getGroups().get(index);
        nameField.setText(group.getGroupName());
        descriptionsTextArea.setText(group.getGroupDescription());
    }

    private JPanel getSearchPanel() {
        JPanel res = new JPanel(new GridBagLayout());

        res.setBorder(new EmptyBorder(125, 75, 125, 75));

        add(res, labels.get("Name"), gbc, 0, 0, 1, 1, 1, 1, BIG_INSERTS);
        add(res, searchField, gbc, 1, 0, 1, 1, 1, 1, BIG_INSERTS);
        add(res, labels.get("Filter"), gbc, 0, 2, 1, 2, 1, 1, BIG_INSERTS);
        add(res, productRadioButton, gbc, 1, 2, 1, 1, 1, 1, BIG_INSERTS);
        add(res, groupRadioButton, gbc, 1, 3, 1, 1, 1, 1, BIG_INSERTS);
        add(res, searchButton, gbc, 0, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, cancelButton, gbc, 1, 4, 1, 1, 1, 1, BIG_INSERTS);
        return res;
    }

    private JPanel getGroupsPanel() {
        JPanel res = new JPanel(new GridBagLayout());

        fillGroupTable();

        JScrollPane scrolledGroupsTable = new JScrollPane(groupsTable);
        scrolledGroupsTable.setPreferredSize(new Dimension(-1, 360));

        add(res, scrolledGroupsTable, gbc, 0, 0, 3, 3, 1, 1, BIG_INSERTS);
        add(res, openButton, gbc, 0, 3, 1, 1, 1, 1, BIG_INSERTS);
        add(res, editButton, gbc, 1, 3, 1, 1, 1, 1, BIG_INSERTS);
        add(res, deleteButton, gbc, 2, 3, 1, 1, 1, 1, BIG_INSERTS);
        add(res, addButton, gbc, 0, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, searchButton, gbc, 1, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, backButton, gbc, 2, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, printALlInfoButton, gbc, 1, 5, 1, 1, 1, 1, BIG_INSERTS);
        return res;
    }

    private void fillGroupTable() {
        DefaultTableModel model = (DefaultTableModel) groupsTable.getModel();
        model.setRowCount(0);
        for (Group group: Group.getGroups()) {
            model.addRow(new String[] {group.getGroupName()});
        }
        groupsTable.setModel(model);
    }

    private void fillGroupTable(List<Group> groups) {
        DefaultTableModel model = (DefaultTableModel) groupsTable.getModel();
        model.setRowCount(0);
        for (Group group: groups) {
            model.addRow(new String[] {group.getGroupName()});
        }
        groupsTable.setModel(model);
    }

    private void fillProductTable(String group) {
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        model.setRowCount(0);
        try {
            for (Product product : ActionWithData.findProduct(group)) {
                if (product.getGroupNameInProduct().equals(group))
                    model.addRow(new String[]{product.getProductName(), product.getProducer(),
                            String.valueOf(product.getProductQuantityOnStock()), String.valueOf(product.getProductPrice()),
                            String.valueOf(product.totalProductCost())});
            }
        }catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
        productsTable.setModel(model);
    }

    private void fillProductTable(List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
        model.setRowCount(0);
        for (Product product: products) {
            model.addRow(new String[] {product.getProductName(), product.getProducer(),
                        String.valueOf(product.getProductQuantityOnStock()), String.valueOf(product.getProductPrice()),
                        String.valueOf(product.totalProductCost())});
        }
        productsTable.setModel(model);
    }



    private JPanel getOpenAndEditProductPanel() {
        JPanel res  = new JPanel(new GridBagLayout());
        nameField.setText("");
        descriptionsTextArea.setText("");
        amountField.setText("");
        priceField.setText("");
        producerField.setText("");
        for (Group group :Group.getGroups()) groupComboBox.addItem(group.getGroupName());
        res.setBorder(new EmptyBorder(0, 50, 0, 50));
        JScrollPane scrolledDescriptionsTextArea = new JScrollPane(descriptionsTextArea);
        scrolledDescriptionsTextArea.setPreferredSize(new Dimension(-1, 200));

        add(res, labels.get("Group"), gbc, 0, 0, 1, 1, 0.1, 0.1, BIG_INSERTS);
        add(res, groupComboBox, gbc, 1, 0, 1, 1, 1, 1, BIG_INSERTS);
        add(res, labels.get("Name"), gbc, 0, 1, 1, 1, 0.1, 0.1, BIG_INSERTS);
        add(res, nameField, gbc, 1, 1, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, labels.get("Description"), gbc, 0, 2, 2, 1, 1, 0.1, BIG_INSERTS);
        add(res, scrolledDescriptionsTextArea, gbc, 0, 3, 2, 3, 1, 1, BIG_INSERTS);
        add(res, labels.get("Amount"), gbc, 0, 6, 1, 1, 0.1, 0.1, BIG_INSERTS);
        add(res, amountField, gbc, 1, 6, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, labels.get("Cost"), gbc, 0, 7, 1, 1, 0.1, 0.1, BIG_INSERTS);
        add(res, priceField, gbc, 1, 7, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, labels.get("Producer"), gbc, 0, 8, 1, 1, 0.1, 0.1, BIG_INSERTS);
        add(res, producerField, gbc, 1, 8, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, saveButton, gbc, 0, 9, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, cancelButton, gbc, 1, 9, 1, 1, 1, 0.1, BIG_INSERTS);
        return res;
    }

    private void fillOpenAndEditProductPanel(int index) {
        Product product = Product.getProducts().get(index);
        groupComboBox.setSelectedIndex(index);
        nameField.setText(product.getProductName());
        descriptionsTextArea.setText(product.getProductDescription());
        amountField.setText(String.valueOf(product.getProductQuantityOnStock()));
        priceField.setText(String.valueOf(product.getProductPrice()));
        producerField.setText(product.getProducer());
    }

    private JPanel getAllInfoPanel(){
        JPanel res = new JPanel(new GridBagLayout());
        allInfoTextArea.setText(ActionWithData.getAllInfo());
        JScrollPane scrolledAllInfoTextArea = new JScrollPane(allInfoTextArea);
        scrolledAllInfoTextArea.setPreferredSize(new Dimension(-1, 400));
        add(res, scrolledAllInfoTextArea, gbc, 0, 0, 2, 4, 1, 1, BIG_INSERTS);
        add(res, saveButton, gbc, 0, 5, 1, 1, 1, 0.1, BIG_INSERTS);
        add(res, backButton, gbc, 1, 5, 1, 1, 1, 0.1, BIG_INSERTS);
        return res;
    }

    private JPanel getProductsPanel(int groupIndex) {
        JPanel res = new JPanel(new GridBagLayout());

       fillProductTable(Group.getGroups().get(groupIndex).getGroupName());

        JScrollPane scrolledGroupsTable = new JScrollPane(productsTable);
        scrolledGroupsTable.setPreferredSize(new Dimension(-1, 380));
        JLabel groupNameLabel = new JLabel("Group: " + Group.getGroups().get(groupIndex).getGroupName(), SwingConstants.CENTER);
        groupNameLabel.setFont(PLAIN_FONT_16);

        add(res, groupNameLabel, gbc, 0, 0, 3, 1, 1, 1, BIG_INSERTS);
        add(res, scrolledGroupsTable, gbc, 0, 1, 3, 3, 1, 1, BIG_INSERTS);
        add(res, editButton, gbc, 0, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, writeOffButton, gbc, 1, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, deleteButton, gbc, 2, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, addButton, gbc, 0, 5, 1, 1, 1, 1, BIG_INSERTS);
        add(res, searchButton, gbc, 1, 5, 1, 1, 1, 1, BIG_INSERTS);
        add(res, backButton, gbc, 2, 5, 1, 1, 1, 1, BIG_INSERTS);
        return res;
    }

    private JPanel getProductsPanel(List<Product> products) {
        JPanel res = new JPanel(new GridBagLayout());

        fillProductTable(products);

        JScrollPane scrolledGroupsTable = new JScrollPane(productsTable);
        scrolledGroupsTable.setPreferredSize(new Dimension(-1, 380));
        JLabel groupNameLabel = new JLabel("", SwingConstants.CENTER);
        groupNameLabel.setFont(PLAIN_FONT_16);

        add(res, groupNameLabel, gbc, 0, 0, 3, 1, 1, 1, BIG_INSERTS);
        add(res, scrolledGroupsTable, gbc, 0, 1, 3, 3, 1, 1, BIG_INSERTS);
        add(res, editButton, gbc, 0, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, writeOffButton, gbc, 1, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, deleteButton, gbc, 2, 4, 1, 1, 1, 1, BIG_INSERTS);
        add(res, addButton, gbc, 0, 5, 1, 1, 1, 1, BIG_INSERTS);
        add(res, searchButton, gbc, 1, 5, 1, 1, 1, 1, BIG_INSERTS);
        add(res, backButton, gbc, 2, 5, 1, 1, 1, 1, BIG_INSERTS);
        return res;
    }

    private void initTableComponents() {
        centralRenderer = new DefaultTableCellRenderer();
        centralRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Name");
        groupsTable = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        groupsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (0.6*WIDTH));
        groupsTable.getColumnModel().getColumn(0).setResizable(false);

        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Producer");
        model.addColumn("Amount");
        model.addColumn("Price");
        model.addColumn("Overall Price");
        productsTable = new JTable(model) {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        productsTable.getColumnModel().getColumn(0).setPreferredWidth((int) (0.35*WIDTH));
        productsTable.getColumnModel().getColumn(0).setResizable(false);
        productsTable.getColumnModel().getColumn(1).setPreferredWidth((int) (0.35*WIDTH));
        productsTable.getColumnModel().getColumn(1).setResizable(false);
        productsTable.getColumnModel().getColumn(2).setPreferredWidth((int) (0.1*WIDTH));
        productsTable.getColumnModel().getColumn(2).setResizable(false);
        productsTable.getColumnModel().getColumn(3).setPreferredWidth((int) (0.1*WIDTH));
        productsTable.getColumnModel().getColumn(3).setResizable(false);


        productsTable.getColumnModel().getColumn(2).setCellRenderer(centralRenderer);
        productsTable.getColumnModel().getColumn(3).setCellRenderer(centralRenderer);
        productsTable.getColumnModel().getColumn(4).setCellRenderer(centralRenderer);

        groupsTable.setRowHeight(25);
        productsTable.setRowHeight(25);
        groupsTable.setFont(PLAIN_FONT_14);
        productsTable.setFont(PLAIN_FONT_14);
    }


    private void initButtons() {
        searchButton = new JButton("Search");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        addButton = new JButton("Add");
        openButton = new JButton("Open");
        editButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        backButton = new JButton("Back");
        writeOffButton = new JButton("Write Off");
        printALlInfoButton = new JButton("Print All Info");
        buttons = new JButton[]{saveButton, cancelButton, addButton, openButton, editButton, deleteButton, backButton, writeOffButton, searchButton, printALlInfoButton};
        for (JButton button : buttons) {
            button.setFont(PLAIN_FONT_14);
            button.setPreferredSize(new Dimension(-1, BUTTON_HEIGHT));
        }
    }

    private void initListeners(){
        openButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1) {
                changePanel(getProductsPanel(index));
                changeState(State.PRODUCTS);
            }
            previousGroupIndex = index;
        });
        deleteButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1 && isConfirmation("Do you really want to delete " + ((current == State.GROUPS) ? groupsTable.getModel().getValueAt(index, 0) : productsTable.getModel().getValueAt(index, 0)) + "?")) {
                if(current == State.GROUPS) {
                    Group.deleteGroup(Group.getGroups().get(index).getGroupName());
                    fillGroupTable();
                }else if (current == State.PRODUCTS) {
                    Product.delete((String) productsTable.getModel().getValueAt(index, 0));
                    fillProductTable(Group.getGroups().get(previousGroupIndex).getGroupName());
                }
            }
        });
        addButton.addActionListener(e -> {
            if(current == State.GROUPS) {
                changePanel(getOpenAndEditGroupPanel());
            }else if (current == State.PRODUCTS){
                changePanel(getOpenAndEditProductPanel());
            }
            changeState(State.ADDING);
        });
        backButton.addActionListener(e -> {
            if(current != State.GROUPS) {
                changePanel(getGroupsPanel());
                changeState(State.GROUPS);
            }
        });
        cancelButton.addActionListener(e -> {
            if(current != State.GROUPS && current != State.PRODUCTS) {
                if(previous == State.GROUPS) changePanel(getGroupsPanel());
                else if (previous == State.PRODUCTS) changePanel(getProductsPanel(previousGroupIndex));
                changeState(previous);
            }
        });
        searchButton.addActionListener(e -> {
            if(current != State.SEARCHING) {
                changePanel(getSearchPanel());
                changeState(State.SEARCHING);
            } else if (current == State.SEARCHING) {
                if(groupRadioButton.isSelected()) {
                    try {
                        ArrayList<Group> groups = ActionWithData.findGroup(searchField.getText());
                        changePanel(getGroupsPanel());
                        fillGroupTable(groups);
                        changeState(State.GROUPS);
                    }catch (RuntimeException ex) {
                        showMessage(ex.getMessage(), "Empty result!");
                    }
                }else if(productRadioButton.isSelected()) {
                    ArrayList<Product> products;
                    try {
                        products = ActionWithData.findProduct(searchField.getText());
                        changePanel(getProductsPanel(products));
                        changeState(State.PRODUCTS);
                    }catch (RuntimeException ex) {
                        showMessage(ex.getMessage(), "Empty result!");
                    }
                }

            }
        });
        editButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1) {
                if (current == State.GROUPS) {
                    changePanel(getOpenAndEditGroupPanel());
                    fillOpenAndEditGroupPanel(index);
                    editIndex = index;
                } else if (current == State.PRODUCTS) {
                    changePanel(getOpenAndEditProductPanel());
                    fillOpenAndEditProductPanel(index);
                    editIndex = index;
                }
                changeState(State.OPENING_AND_EDITING);
            }
        });
        saveButton.addActionListener(e -> {
            if(current == State.OPENING_AND_EDITING) {
                try {
                    if(previous == State.GROUPS) {
                        checkIfIsNotEmpty(new JTextField[] {nameField});
                        checkIfIsNotEmpty(descriptionsTextArea);
                        Group.editGroup(editIndex, nameField.getText(), descriptionsTextArea.getText());
                    } else if (previous == State.PRODUCTS) {
                        checkIfIsNotEmpty(new JTextField[] {nameField, producerField, amountField, priceField});
                        checkIfIsNotEmpty(descriptionsTextArea);
                        Product.getProducts().get(editIndex).editProduct(Group.getGroups().get(groupComboBox.getSelectedIndex()).getGroupName(), nameField.getText(), descriptionsTextArea.getText(), producerField.getText(), Integer.parseInt(amountField.getText()), Double.parseDouble(priceField.getText()));
                    }
                    if(previous == State.GROUPS) changePanel(getGroupsPanel());
                    else if (previous == State.PRODUCTS) changePanel(getProductsPanel(previousGroupIndex));
                    changeState(previous);
                }catch (RuntimeException ex) {
                    showMessage(ex.getMessage(), "Some trouble");
                }
            } else if (current == State.ADDING) {
                try {
                    if (previous == State.GROUPS) {
                        checkIfIsNotEmpty(new JTextField[] {nameField});
                        checkIfIsNotEmpty(descriptionsTextArea);
                        Group.addGroup(nameField.getText(), descriptionsTextArea.getText());
                    } else if (previous == State.PRODUCTS) {
                        checkIfIsNotEmpty(new JTextField[] {nameField, producerField, amountField, priceField});
                        checkIfIsNotEmpty(descriptionsTextArea);
                        Product.addProduct(Group.getGroups().get(groupComboBox.getSelectedIndex()).getGroupName(), nameField.getText(), descriptionsTextArea.getText(), producerField.getText(), Integer.parseInt(amountField.getText()), Double.parseDouble(priceField.getText()));
                    }
                    if(previous == State.GROUPS) changePanel(getGroupsPanel());
                    else if (previous == State.PRODUCTS) changePanel(getProductsPanel(previousGroupIndex));
                    changeState(previous);
                }catch (RuntimeException ex) {
                    showMessage(ex.getMessage(), "Some trouble");
                }
            } else if (current == State.ALL_INFO) {
                JFileChooser fileChooser = getFileChooser();
                if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try (FileWriter writer = new FileWriter(file)){
                        writer.write(ActionWithData.getAllInfo());
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if(previous == State.GROUPS) changePanel(getGroupsPanel());
                else if (previous == State.PRODUCTS) changePanel(getProductsPanel(previousGroupIndex));
                changeState(previous);
            }
        });
        writeOffButton.addActionListener(e -> {
            int index = getSelectedRow();
            if(index != -1) {
                String answer = JOptionPane.showInputDialog(this, "How much do you want to write " + "sth" + " off?", "", JOptionPane.QUESTION_MESSAGE);
                if(answer!= null) {
                    if(!isInteger(answer)) showMessage("You should enter a number!", "Invalid input!");
                    else if(Integer.parseInt(answer) <= 0)  showMessage("You should enter a number that is larger than 0!", "Invalid input!");
                    else {
                        try {
                            Product.getProducts().get(index).writeOffFromStock(Product.getProducts().get(index).getProductName(), (Integer.parseInt(answer)));
                            fillProductTable(Group.getGroups().get(previousGroupIndex).getGroupName());
                        }catch (RuntimeException ex) {
                            showMessage(ex.getMessage(), "Invalid input!");
                        }
                    }
                }
            }
        });
        printALlInfoButton.addActionListener(e -> {
            changePanel(getAllInfoPanel());
            changeState(State.ALL_INFO);
        });
    }

    private void checkIfIsNotEmpty(JTextField[] textFields) {
        for(JTextField textField: textFields){
            if (textField.getText().isEmpty()) {
                throw new RuntimeException("Fields '" + textField.getName() + "' is  empty!\nYou should fill it out");
            } else if (textField.getName().equals("Amount Field")) {
                if(!isInteger(textField.getText()))
                    throw new RuntimeException("The value '" + textField.getText()+ "' is invalid!");
            } else if (textField.getName().equals("Price Field")) {
                if(!isDouble(textField.getText())) throw new RuntimeException("The value '" + textField.getText()+ "' is invalid!");
            }
        }
    }

    private void checkIfIsNotEmpty(JTextArea textArea) {
        if (textArea.getText().isEmpty()) {
            throw new RuntimeException("Fields '" + textArea.getName() + "' is  empty!\nYou should fill it out");
        }
    }

    private void showMessage(String text, String title) {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.ERROR_MESSAGE);
    }

    private JFileChooser getFileChooser() {
        JFileChooser fileChooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        return fileChooser;
    }

    private void initLabels() {
        labels = new HashMap<>();
        labels.put("Name", new JLabel("Name:", SwingConstants.CENTER));
        labels.put("Description", new JLabel("Description:", SwingConstants.CENTER));
        labels.put("Group", new JLabel("Group:", SwingConstants.CENTER));
        labels.put("Amount", new JLabel("Amount:", SwingConstants.CENTER));
        labels.put("Producer", new JLabel("Producer:", SwingConstants.CENTER));
        labels.put("Cost", new JLabel("Cost:", SwingConstants.CENTER));
        labels.put("Filter", new JLabel("Filter:", SwingConstants.CENTER));
        labels.values().forEach(l -> l.setFont(PLAIN_FONT_16));
    }

    private void initFrame() {
        setTitle("Some Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, WIDTH, HEIGHT);
        setMaximumSize(new Dimension((int) (1.1*WIDTH), (int) (1.1*HEIGHT)));
        setMinimumSize(new Dimension((int) (0.9*WIDTH), HEIGHT));
    }

    public static void add(JPanel panel, Component component, GridBagConstraints gbc, int x, int y, int width, int height, double wx, double wy, int insert) {
        gbc.insets = new Insets(insert, insert, insert, insert);
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

    private void changePanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        this.revalidate();
        this.repaint();
    }

    public static void add(JPanel panel,GridBagLayout gb, Component component, GridBagConstraints gbc, int x, int y, int width, int height, double wx, double wy, int insert) {
        gbc.insets = new Insets(insert, insert, insert, insert);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = wx;
        gbc.weighty = wy;
        gb.setConstraints(component, gbc);
        panel.add(component, gbc);
    }

    private void changeState(State newCurrentState) {
        previous = current;
        current = newCurrentState;
    }

    private int getSelectedRow() {
        int index = -1;
        JTable table = null;
        if(current == State.PRODUCTS) table = productsTable;
        else if (current == State.GROUPS) table = groupsTable;
        if (table != null) index = table.getSelectedRow();
        if(index == -1) {
            if (table.getRowCount() > 0) table.setRowSelectionInterval(0, 0);
            else JOptionPane.showMessageDialog(this, "The list is empty!");
        }
        return index;
    }

    private boolean isConfirmation(String text) {
        return JOptionPane.showConfirmDialog(this, text, "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private boolean isInteger(String answer) {
        try{
            Integer.parseInt(answer);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    private boolean isDouble(String answer) {
        try{
            Double.parseDouble(answer);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }



    private void acceptOnlyInteger(JTextField textField) {
        textField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
            }
        });
    }

    private void acceptOnlyDouble(JTextField textField) {
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0') && (c <= '9') || (c == '.') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
                    getToolkit().beep();
                    e.consume();
                }
            }
        });
    }

    private enum State {
        GROUPS, SEARCHING, OPENING_AND_EDITING, ADDING, ALL_INFO, PRODUCTS
    }
}
