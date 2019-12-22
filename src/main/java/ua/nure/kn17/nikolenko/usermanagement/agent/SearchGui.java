package ua.nure.kn17.nikolenko.usermanagement.agent;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.gui.UserTableModel;
import ua.nure.kn17.nikolenko.usermanagement.util.Message;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


public class SearchGui extends JFrame 
{
    private SearchAgent agent;
    private JPanel contentPanel;
    private JPanel tablePanel;
    private JTable table;
    private JLabel senderLabel;

    public SearchGui(SearchAgent agent) 
    {
        this.agent = agent;
        initialize();
    }

    private void initialize() 
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setTitle("Searcher");
        this.setContentPane(getContentPanel());
    }

    private JPanel getContentPanel() 
    {
        if (contentPanel == null) 
	{
            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());
            contentPanel.add(getSearchPanel(), BorderLayout.NORTH);
            contentPanel.add(new JScrollPane(getTablePanel()), BorderLayout.CENTER);
            contentPanel.add(getSenderLabel(), BorderLayout.SOUTH);
        }
        return contentPanel;
    }

    private JLabel getSenderLabel() 
    {
        if (senderLabel == null) 
	{
            senderLabel = new JLabel();
            senderLabel.setText("");
            senderLabel.setName("senderLabel");
            senderLabel.setBackground(Color.GRAY);
        }
        return senderLabel;
    }

    private JPanel getTablePanel() 
    {
        if (tablePanel == null) 
	{
            tablePanel = new JPanel(new BorderLayout());
            tablePanel.add(getTable(), BorderLayout.CENTER);
        }
        return tablePanel;
    }

    private JTable getTable() 
    {
        if (table == null) 
	{
            table = new JTable(new UserTableModel(new LinkedList()));
        }
        return table;
    }

    private JPanel getSearchPanel() {
        return new SearchPanel(agent);
    }

    public static void main(String[] args) {
        SearchGui gui = new SearchGui(null);
        gui.setVisible(true);
    }

    class SearchPanel extends JPanel implements ActionListener 
    {
        SearchAgent agent;
        private JPanel buttonPanel;

        private JPanel fieldPanel;

        private JButton cancelButton;

        private JButton searchButton;

        private JTextField firstNameField;

        private JTextField dateOfBirthField;

        private JTextField lastNameField;

        public SearchPanel(SearchAgent agent) 
	{
            this.agent = agent;
            initialize();
        }

        private void initialize() 
	{
            this.setName("addPanel");
            this.setLayout(new BorderLayout());
            this.add(getFieldPanel(), BorderLayout.NORTH);

        }

        private JPanel getButtonPanel() 
	{
            if (buttonPanel == null) 
	    {
                buttonPanel = new JPanel();
                buttonPanel.add(getSearchButton(), null);
                buttonPanel.add(getCancelButton(), null);
            }
            return buttonPanel;
        }

        private JButton getCancelButton() 
	{
            if (cancelButton == null) 
	    {
                cancelButton = new JButton();
                cancelButton.setText(Message.getString("AddPanel.cancel"));
                cancelButton.setName("cancelButton");
                cancelButton.setActionCommand("cancel");
                cancelButton.addActionListener(this);
            }
            return cancelButton;
        }

        private JButton getSearchButton() 
	{
            if (searchButton == null) 
	    {
                searchButton = new JButton();
                searchButton.setText("Search"); //$NON-NLS-1$
                searchButton.setName("okButton"); //$NON-NLS-1$
                searchButton.setActionCommand("ok"); //$NON-NLS-1$
                searchButton.addActionListener(this);
            }
            return searchButton;
        }

        private JPanel getFieldPanel() 
	{
            if (fieldPanel == null) 
	    {
                fieldPanel = new JPanel();
                fieldPanel.setLayout(new GridLayout(2, 3));
                addLabeledField(fieldPanel, "FirstName", getFirstNameField()); //$NON-NLS-1$
                fieldPanel.add(getSearchButton());
                addLabeledField(fieldPanel, "LastName", getLastNameField()); //$NON-NLS-1$
                fieldPanel.add(getCancelButton());
            }
            return fieldPanel;
        }

        protected JTextField getLastNameField() 
	{
            if (lastNameField == null) 
	    {
                lastNameField = new JTextField();
                lastNameField.setName("lastNameField"); //$NON-NLS-1$
            }
            return lastNameField;
        }

        private void addLabeledField (JPanel panel, String labelText,JTextField textField) 
	{
            JLabel label = new JLabel(labelText);
            label.setLabelFor(textField);
            panel.add(label);
            panel.add(textField);
        }

        protected JTextField getFirstNameField() 
	{
            if (firstNameField == null) 
	    {
                firstNameField = new JTextField();
                firstNameField.setName("firstNameField"); //$NON-NLS-1$
            }
            return firstNameField;
        }

        protected void doAction(ActionEvent e) throws ParseException 
	{
            if ("ok".equalsIgnoreCase(e.getActionCommand())) 
	    {
                String firstName = getFirstNameField().getText();
                String lastName = getLastNameField().getText();
                try 
	 	{
                    clearUsers();
                    agent.search(firstName, lastName);
                } 

		catch (SearchException e1) {throw new RuntimeException(e1);}
            }
            clearFields();
        }

        public void actionPerformed(ActionEvent e) 
	{
            try {doAction(e);} 

	    catch (ParseException e1) {return;}
        }

        private void clearFields() 
 	{
            getFirstNameField().setText("");
            getLastNameField().setText("");
        }

    }

    public void addUsers(Collection<User> users) 
    {
        System.out.println("addUsers : " + users);
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.addUsers(users);
        this.repaint();
    }

    private void clearUsers() 
    {
        System.out.println("clearUsers : ");
        UserTableModel model = (UserTableModel) getTable().getModel();
        model.clearUsers();
        senderLabel.setText("none");
        this.repaint();
    }

    public void setSender(String name) 
    {
        senderLabel.setText("The last data obtained from " + name);
        this.repaint();
    }
}