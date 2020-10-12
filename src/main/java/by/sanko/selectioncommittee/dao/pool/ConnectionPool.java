package by.sanko.selectioncommittee.dao.pool;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ConnectionPool {
    private static final String FILE_DATABASE_CONFIG = "database";
    private static final String URL = "url";
    private static final String POOL_SIZE = "poolSize";
    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String TIMEZONE = "serverTimezone";
    private static final String ENCODING = "encoding";
    private static final String UNICODE = "useUnicode";
    private static Logger logger = LogManager.getLogger();
    private int poolSize;
    private static ConnectionPool instance = new ConnectionPool();
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenConnections;

    public static ConnectionPool getINSTANCE() {
        return instance;
    }

    private ConnectionPool(){
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(FILE_DATABASE_CONFIG);
            //String url = resourceBundle.getString(URL);
            String url = "jdbc:mysql://localhost:3306/mydb";
            String user = resourceBundle.getString(USER);
            String password = resourceBundle.getString(PASSWORD);
            String serverTimezone = resourceBundle.getString(TIMEZONE);
            String encoding = resourceBundle.getString(ENCODING);
            String useUnicode = resourceBundle.getString(UNICODE);
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            freeConnections = new LinkedBlockingDeque<>();
            givenConnections = new ArrayDeque<>();
            Properties properties = new Properties();
            properties.put(USER, user);
            properties.put(PASSWORD, password);
            properties.put(TIMEZONE, serverTimezone);
            properties.put(ENCODING, encoding);
            properties.put(UNICODE, useUnicode);
            Integer poolSize = Integer.parseInt(resourceBundle.getString(POOL_SIZE));
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, properties);
                freeConnections.offer(new ProxyConnection(connection));
            }
        }catch (SQLException ex){
            logger.log(Level.FATAL, "Database connection wasn't established");
            throw new RuntimeException("Database connection wasn't established", ex);
        }
    }

    public Connection getConnection(){
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenConnections.offer(connection);
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while taking connection from queue");
        }
        return connection;
    }


    public void releaseConnection(ProxyConnection proxyConnection) {
        if (proxyConnection instanceof ProxyConnection && givenConnections.remove(proxyConnection)) {
            freeConnections.offer((ProxyConnection) proxyConnection);
        } else {
            logger.log(Level.ERROR, "Invalid connection to release");
        }
    }

    public void destroyPool() {
        try {
            for (int i = 0; i < poolSize; i++) {
                freeConnections.take().reallyClose();
            }
        } catch (InterruptedException e) {
            logger.log(Level.ERROR, "Error while taking connection from queue");
        } finally {
            while (DriverManager.getDrivers().hasMoreElements()) {
                try {
                    DriverManager.deregisterDriver(DriverManager.getDrivers().nextElement());
                } catch (SQLException e) {
                    logger.log(Level.ERROR, "Cannot deregister driver", e);
                }
            }
        }
    }
}
