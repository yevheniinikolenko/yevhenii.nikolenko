package ua.nure.kn17.nikolenko.usermanagement.agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import java.util.*;

public class RequestServer extends CyclicBehaviour 
{
    private static final String STRING_SEPARATOR = ",";
    private static final String LINE_SEPARATOR = ";";
    private static final int VALID_COUNT_TOKENS_IN_MSG = 2;
    private static final int SIZE_OF_EMPTY_LIST = 0;

    @Override
    public void action() 
    {
        ACLMessage message = myAgent.receive();
        if (message != null) 
	{
            if (message.getPerformative() == ACLMessage.REQUEST) 
	    {
                myAgent.send(createReply(message));
            } 

	    else 
	    {
                Collection<User> users = parseMessage(message);
                System.out.println("In action, the list of arrived user:" + users);
                ((SearchAgent) myAgent).showUsers(users);
                ((SearchAgent) myAgent).setSender(message.getSender().getName());
            }
        }
 
	else {block();}
    }

    private Collection<User> parseMessage(ACLMessage message) 
    {
        Collection<User> users = new LinkedList<>();

        String content = message.getContent();
        System.out.println("Parsed message:" + content);

        if(content != null) 
	{
            StringTokenizer lineTokenizer = new StringTokenizer(content, LINE_SEPARATOR);
            while (lineTokenizer.hasMoreTokens()) {
                String userInfo = lineTokenizer.nextToken();
                StringTokenizer itemTokenizer  = new StringTokenizer(userInfo, STRING_SEPARATOR);
                String id = itemTokenizer.nextToken();
                String firstName = itemTokenizer.nextToken();
                String lastName = itemTokenizer.nextToken();

                if (MessageValidator.validateFirstName(firstName) &&
                        MessageValidator.validateLastName(lastName) &&
                            MessageValidator.validateId(id)) 
		{
                    users.add(new User(new Long(id), firstName, lastName, null));
                }
            }
        }
        return users;
    }

    private ACLMessage createReply(ACLMessage message) 
    {
        ACLMessage reply = message.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        String content = message.getContent();
        System.out.println("createReply message:" + content);
        StringTokenizer tokenizer = new StringTokenizer(content, STRING_SEPARATOR);
        if (tokenizer.countTokens() == VALID_COUNT_TOKENS_IN_MSG) 
	{
            String firstName = tokenizer.nextToken();
            String lastName = tokenizer.nextToken();
            Collection<User> users = null;

            try{users = DaoFactory.getInstance().getUserDAO().find(firstName, lastName);} 

	    catch (DatabaseException e) 
	    {
                e.printStackTrace();
                users = new ArrayList<>(SIZE_OF_EMPTY_LIST);
            }

            StringBuffer buffer = new StringBuffer();
            for (Iterator<User> it = users.iterator(); it.hasNext();) 
	    {
                User user = it.next();
                buffer.append(user.getId()).append(STRING_SEPARATOR);
                buffer.append(user.getFirstName()).append(STRING_SEPARATOR);
                buffer.append(user.getLastName()).append(LINE_SEPARATOR);
            }
            reply.setContent(buffer.toString());
        }
        return reply;
    }
}
