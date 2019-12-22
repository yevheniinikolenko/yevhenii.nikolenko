package ua.nure.kn17.nikolenko.usermanagement.gui;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import ua.nure.kn17.nikolenko.usermanagement.util.Message;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BrowsePanel extends JPanel implements ActionListener {
    private static final String MESSAGE = "Are you sure you want to delete this user?";
    private JScrollPane tablePanel;
    private JButton detailsButton;
    private JButton deleteButton;
    private JPanel buttonsPanel;
    private JButton editButton;
    private JButton addButton;
    private JTable userTable;
    private MainFrame parent;

    public BrowsePanel(MainFrame mainFrame) {
    parent = mainFrame;
    initialize();
    }

    private void initialize() {
        this.setName("browserPanel");
        this.setLayout(new BorderLayout());
        this.add(getTablePanel(), BorderLayout.CENTER);
        this.add(getButtonsPanel(), BorderLayout.SOUTH);

    }

    private JPanel getButtonsPanel() {
        if (buttonsPanel == null) {
            buttonsPanel = new JPanel();
            buttonsPanel.add(getDetailsButton(), null);
            buttonsPanel.add(getDeleteButton(), null);
            buttonsPanel.add(getEditButton(), null);
            buttonsPanel.add(getAddButton(), null);
        }

        return buttonsPanel;
    }

    private JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton();
            addButton.setText(Message.getString("add.user_button"));
            addButton.setName("addButton");
            addButton.setActionCommand("add");
            addButton.addActionListener(this);
        }

        return addButton;
    }

    private JButton getEditButton() {
        if (editButton == null) {
            editButton = new JButton();
            editButton.setText(Message.getString("edit.user_button"));
            editButton.setName("editButton");
            editButton.setActionCommand("edit");
            editButton.addActionListener(this);
        }

        return editButton;
    }

    private JButton getDeleteButton() {
        if (deleteButton == null) {
            deleteButton = new JButton();
            deleteButton.setText(Message.getString("delete.user_button"));
            deleteButton.setName("deleteButton");
            deleteButton.setActionCommand("delete");
            deleteButton.addActionListener(this);
        }

        return deleteButton;
    }

    private JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText(Message.getString("user.details_button"));
            detailsButton.setName("detailsButton");
            detailsButton.setActionCommand("details");
            detailsButton.addActionListener(this);
        }

        return detailsButton;
    }

    private JScrollPane getTablePanel() {
        if (tablePanel == null) {
            tablePanel = new JScrollPane(getUserTable());
        }

        return tablePanel;
    }

    private JTable getUserTable() {
        if (userTable == null) {
            userTable = new JTable();
            userTable.setName("userTable");
        }

        return userTable;
    }

    public void initTable() {
        UserTableModel model = null;
        try {
            model = new UserTableModel(parent.getUserDAO().findAll());
        } catch (DatabaseException e) {
            model = new UserTableModel(new ArrayList());
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        getUserTable().setModel(model);
    }

    public User getSelectedUser() {
        int selectedRow = getUserTable().getSelectedRow();
        int selectedColumn = 0;
        Long userId = null;
        User user = null;

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please, select row of user, details of whom you want to see",
                                            "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                userId = (Long) userTable.getValueAt(userTable.getSelectedRow(), selectedColumn);
                user =  parent.getUserDAO().find(userId);
            } catch (DatabaseException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return user;
    }

    private void deleteUser(User user) {
        int result = JOptionPane.showConfirmDialog(this, MESSAGE, "Confirm deleting",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                parent.getUserDAO().delete(user);
                getUserTable().setModel(new UserTableModel(parent.getUserDAO().findAll()));
            } catch (DatabaseException e1) {
                JOptionPane.showMessageDialog(this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        if (action.equalsIgnoreCase("add")) {
            this.setVisible(false);
            parent.showAddPanel();
        } else if (action.equalsIgnoreCase("details")) {
            User selectedUser = getSelectedUser();
            JOptionPane.showMessageDialog(this, selectedUser.toString(), "User info", JOptionPane.INFORMATION_MESSAGE);
        } else if (action.equalsIgnoreCase("delete")) {
            User selectedUser = getSelectedUser();
            if (selectedUser != null) {
                deleteUser(selectedUser);
            }
        } else if (action.equalsIgnoreCase("edit")) {
            int selectedRow = userTable.getSelectedRow();
            int selectedColumn = userTable.getSelectedColumn();

            if (selectedRow != -1 | selectedColumn != -1) {
                this.setVisible(false);
                parent.showEditPanel();
            } else {
                JOptionPane.showMessageDialog(this, "Please, select row of user, details of whom you want to see",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}