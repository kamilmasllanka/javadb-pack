/*
3.  Wzorując się na klasie ​ ConnectionViaDataSource uzupełnij kod w klasie
    ConnectionFactory. Klasa ta ma stać się naszą fabryką do tworzenia połączeń do
    wybranej przez nas bazy danych. Wewnątrz klasy ​ ConnectionFactory ​ należy
    stworzyć obiekt DataSource (pamiętaj: tworzymy raz, wykorzystujemy wielokrotnie) i
    następnie użyć go w metodzie ​ getConnection.


    4.  Przenieś parametry bazy danych do zewnętrznego pliku:
        - Przeanalizuj plik ​ database.properties ​ w katalogu ​ resources
        - Zmień wartości (po prawej stronie znaku ‘=’) na parametry twojej lokalnej bazy
        - Utwórz konstruktory:
            ConnectionFactory(String filename)
            ConnectionFactory()
        - Drugi konstruktor ma wywołać pierwszy konstruktor z argumentem
            filename=”​database.properties​”
        - Pobierz parametry bazy danych z pliku za pomocą metody
            getDataBaseProperties(String fileName)
        - Użyj parametrów bazy danych pobranych z pliku przy pomocy metody:
            Properties.getProperty(String key)​ np.:
            properties.getProperty("pl.sda.jdbc.db.server")
        - W metodzie main stwórz obiekt ​ ConnectionFactory ​ pobierz połączenie i
            sprawdź czy działa - możesz użyć kodu z ​ ConnectionViaDataSource



 */


package pl.sda.jdbc.starter;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final String DB_SERVER_NAME = "127.0.0.1";
    private static final String DB_NAME = "classicmodels";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "qwerty";
    private static final int DB_PORT = 3306;

    private static Logger logger = LoggerFactory.getLogger(ConnectionFactory.class);

//    private MysqlDataSource dataSource = createDataSource();
////    private MysqlDataSource createDataSource() {
////        try {
////            dataSource = new MysqlDataSource();
////            dataSource.setServerName(DB_SERVER_NAME);
////            dataSource.setDatabaseName(DB_NAME);
////            dataSource.setUser(DB_USER);
////            dataSource.setPassword(DB_PASSWORD);
////            dataSource.setPort(DB_PORT);
////            dataSource.setServerTimezone("Europe/Warsaw");
////            dataSource.setUseSSL(false);
////            dataSource.setCharacterEncoding("UTF-8");
////        } catch (SQLException e) {
////            logger.error("Error during creating MysqlDataSource", e);
////        }
////            return dataSource;
////    }

    private MysqlDataSource dataSource = new MysqlDataSource();
    public ConnectionFactory(String filename){
        Properties dataBaseProperties = getDataBaseProperties(filename);
        try {
            dataSource.setServerName(dataBaseProperties.getProperty("pl.sda.jdbc.db.server"));
            dataSource.setDatabaseName(dataBaseProperties.getProperty("pl.sda.jdbc.db.name"));
            dataSource.setUser(dataBaseProperties.getProperty("pl.sda.jdbc.db.user"));
            dataSource.setPassword(dataBaseProperties.getProperty("pl.sda.jdbc.db.password"));
            dataSource.setPort(Integer.parseInt(dataBaseProperties.getProperty("pl.sda.jdbc.db.port")));
            dataSource.setServerTimezone("Europe/Warsaw");
            dataSource.setUseSSL(false);
            dataSource.setCharacterEncoding("UTF-8");
        } catch (SQLException e) {
            logger.error("Error during load MysqlDataSource", e);
        }
    }

    public ConnectionFactory(){
        this("/database.properties");   //poprzez "/" ścieżka bezwzględna -> w katalogu z zasobami.
    }

    private Properties getDataBaseProperties(String filename) {
        Properties properties = new Properties();
        try {
            /**
             * Pobieramy zawartość pliku za pomocą classloadera, plik musi znajdować się w katalogu ustawionym w CLASSPATH
             */
            InputStream propertiesStream = ConnectionFactory.class.getResourceAsStream(filename);  // lub wprowadzenie metody .getClassLoader -> wtedy nie uzywamy "/".
            if(propertiesStream == null) {
                throw new IllegalArgumentException("Can't find file: " + filename);
            }
            /**
             * Pobieramy dane z pliku i umieszczamy w obiekcie klasy Properties
             */
            properties.load(propertiesStream);
        } catch (IOException e) {
            logger.error("Error during fetching properties for database", e);
            return null;
        }

        return properties;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        try (Connection connection = connectionFactory.getConnection())
        {
            logger.info("Connected database successfully...");
            logger.info("Connection = " + connection);
            logger.info("Database name = " + connection.getCatalog());
        } catch (SQLException e) {
            logger.error("Error during closing connection", e);
        }
    }
}