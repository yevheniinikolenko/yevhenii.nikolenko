package ua.nure.kn17.nikolenko.usermanioshchenkoagement.agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;

public class SearchRequestBehaviour extends Behaviour 
{
    private static final String STRING_SEPARATOR = ",";
    private AID[] aids;
    private String firstName;
    private String lastName;

    public SearchRequestBehaviour(AID[] aids, String firstName, String lastName) 
    {
        this.aids = aids;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public void action() 
    {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.setContent(firstName + STRING_SEPARATOR + lastName);

        if (aids != null) 
	{
            for (int i = 0; i < aids.length; i++) 
	    {
                System.out.println(message.getContent() + " debug!");
                message.addReceiver(aids[i]);
            }
            myAgent.send(message);
        }
    }

    @Override
    public boolean done() {return true;}
}
