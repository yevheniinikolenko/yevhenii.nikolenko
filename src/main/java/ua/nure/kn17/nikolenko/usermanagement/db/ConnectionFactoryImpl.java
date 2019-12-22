package ua.nure.kn17.nikolenko.usermanagement.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Class to setup connection to DB via interface ConnectionFactory
 */
public class ConnectionFactoryImpl implements ConnectionFactory 
{
    private String user;
    private String password;
    private String url;
    private String driver;

    ConnectionFactoryImpl() {}

    ConnectionFactoryImpl(String user, String password, String url, String driver) 
    {
        this.user = user;
        this.password = password;
        this.url =  url;
        this.driver = driver;
    }

    ConnectionFactoryImpl(Properties properties) 
    {
        this.user = properties.getProperty("connection.user");
        this.password = properties.getProperty("connection.password");
        this.url = properties.getProperty("connection.url");
        this.driver = properties.getProperty("connection.driver");
    }

    /**
     * Method to
     * @return Connection to DB via driver
     * @throws DatabaseException
     */
    @Override
    public Connection createConnection() throws DatabaseException 
    {
        try {Class.forName(driver);}	
	catch (ClassNotFoundException e) {throw new RuntimeException(e);}

        try {return DriverManager.getConnection(url, user, password);}
	catch (SQLException e) {throw new DatabaseException(e.toString());}
    }
}