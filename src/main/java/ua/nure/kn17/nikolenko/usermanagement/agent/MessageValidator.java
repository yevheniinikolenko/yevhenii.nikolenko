package ua.nure.kn17.nikolenko.usermanagement.agent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class MessageValidator 
{

    private static final int LOWER_LIMIT = 0;
    private static Pattern patternName = Pattern.compile("^[A-Z][a-z]+$");

    public static Boolean validateFirstName(String firstName) 
    {
        Matcher matcher = patternName.matcher(firstName);
        if (firstName.isEmpty()) {return false;}

        return matcher.matches();
    }

    public static Boolean validateLastName(String lastName) 
    {
        Matcher matcher = patternName.matcher(lastName);
        if (lastName.isEmpty()) {return false;}

        return matcher.matches();
    }

    public static Boolean validateId(String id) 
    {
        Long idLong = null;

        try {idLong = Long.parseLong(id);} 
	catch (NumberFormatException e) {return false;}

        return idLong > LOWER_LIMIT;
    }
}