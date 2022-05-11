package source;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class OpenDialog extends JDialog {
    private JComboBox<String> group;
    private JTextField name;
    private JTextArea descriptions;
    private JTextField amount;
    private JTextField producer;
    private JTextField cost;
    private Map<String, JLabel> labels;

    private JButton saveButton;
    private JButton cancelButton;

    public OpenDialog(Frame owner, boolean modal, Group group) {
        super(owner, modal);
        initFrame();
        initFrameContent(group);
    }

    public OpenDialog(Frame owner, boolean modal) {
        super(owner, modal);
        initFrame();
        initFrameContent();
    }

    private void initFrameContent() {
        Panel panel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        labels = new HashMap<>();
        labels.put("Group", new JLabel("Group:", SwingConstants.CENTER));
        labels.put("Name", new JLabel("Name:", SwingConstants.CENTER));
        labels.put("Description", new JLabel("Description:", SwingConstants.CENTER));
        labels.put("Amount", new JLabel("Amount:", SwingConstants.CENTER));
        labels.put("Producer", new JLabel("Producer:", SwingConstants.CENTER));
        labels.put("Cost", new JLabel("Cost:", SwingConstants.CENTER));

        group = new JComboBox<>(new String[] {"group 1", "group 3","group 2"});
        group.setSelectedItem("group 3");
        name = new JTextField("product name");
        descriptions = new JTextArea("product descriptions");
        JScrollPane scrollPane = new JScrollPane(descriptions);
        scrollPane.setPreferredSize(new Dimension(-1, 200));
        amount = new JTextField("5355");
        cost = new JTextField("65");
        producer = new JTextField("product producer");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        Main.add(panel, labels.get("Group"), gbc, 0, 0, 1, 1, 0.1, 0.1);
        Main.add(panel, group, gbc, 1, 0, 1, 1, 1, 1);
        Main.add(panel, labels.get("Name"), gbc, 0, 1, 1, 1, 0.1, 0.1);
        Main.add(panel, name, gbc, 1, 1, 1, 1, 1, 0.1);
        Main.add(panel, labels.get("Description"), gbc, 0, 2, 2, 1, 1, 0.1);
        Main.add(panel, scrollPane, gbc, 0, 3, 2, 3, 1, 1);
        Main.add(panel, labels.get("Amount"), gbc, 0, 6, 1, 1, 0.1, 0.1);
        Main.add(panel, amount, gbc, 1, 6, 1, 1, 1, 0.1);
        Main.add(panel, labels.get("Cost"), gbc, 0, 7, 1, 1, 0.1, 0.1);
        Main.add(panel, cost, gbc, 1, 7, 1, 1, 1, 0.1);
        Main.add(panel, labels.get("Producer"), gbc, 0, 8, 1, 1, 0.1, 0.1);
        Main.add(panel, producer, gbc, 1, 8, 1, 1, 1, 0.1);
        Main.add(panel,  saveButton, gbc, 0, 9, 1, 1, 1, 0.1);
        Main.add(panel,  cancelButton, gbc, 1, 9, 1, 1, 1, 0.1);

        this.getContentPane().add(panel);
    }

    private void initFrameContent(Group group) {
        Panel panel = new Panel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        name = new JTextField("group name");
        descriptions = new JTextArea("group descriptions");
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");

        Main.add(panel, name, gbc, 0, 0, 1, 2, 1, 1);
        Main.add(panel, new JScrollPane(descriptions), gbc, 0, 1, 2, 2, 1, 1);
        Main.add(panel,  saveButton, gbc, 0, 3, 1, 1, 1, 1);
        Main.add(panel,  cancelButton, gbc, 1, 3, 1, 1, 1, 1);

        this.getContentPane().add(panel);
    }


    private void initFrame() {
        setTitle("Edit Frame");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(getOwner().getX(), getOwner().getY(), (int) (0.8*Main.WIDTH), (int) (0.95*Main.HEIGHT));
        setResizable(false);
    }
}
