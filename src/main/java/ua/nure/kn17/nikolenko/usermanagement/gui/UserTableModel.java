package ua.nure.kn17.nikolenko.usermanagement.gui;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.util.Message;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
	private static final String[] COLUMN_NAMES = {
            Messages.getString("UserTableModel.id"),
            Messages.getString("UserTableModel.first_name"),
            Messages.getString("UserTableModel.last_name")};
    private static final Class[] COLUMN_CLASSES = {Long.class, String.class, String.class};
    private List<User> users = null;

    public UserTableModel(Collection<User> users) {
        this.users = new ArrayList<>(users);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User user = users.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return user.getId();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
        }
        return null;
    }

    public void addUsers(Collection<User> users) {
        this.users.addAll(users);
    }

    public void clearUsers() {
        this.users.clear();
    }
}