/*

    java.sql.Statement
        - statyczne zapytanie do bazy danych
        - Statement.executeQuery(q)​ - pobranie danych: SELECT
        - Statement.executeUpdate(q)​ - aktualizowanie danych: UPDATE, INSERT...
        - try-with-resources ​ - zawsze zamykamy na koniec

    java.sql.ResultSet
        - odpowiedź z wynikami z bazy danych
        - try-with-resources ​ - jak zamkniemy Statement to i ResultSet

    Użycie:
    try(Statement statement = connection.createStatement()) {
        String query = "INSERT INTO table_1(id, name) VALUES(1, ‘Bob’);";
        statement.executeUpdate(query);
        query = "SELECT id FROM table_1;";
        ResultSet result = statement.executeQuery(query);
        while (result.next()) {
            int id = result.getInt("id");
            logger.info("id: {}", id);
        }
    }

    Konwersja typów:
    VARCHAR   - String
    INTEGER   - int
    DATE      - java.sql.Date
    TIMESTAMP - java.sql.Timestamp

1.  Otwórz klasę ​ ClassicModelsManager. Korzystając z dotychczas napisanych klas wypełnij ciała metod:

        - printAllOffices() - wypisuje na ekranie wszystkie informacje na temat wszystkich biur
        - updateProductPrices(double updateRate) - podnosi cenę wszystkich produktów o 10%
        - printAllCustomersWithSalesRepName() - wypisuje nazwy wszstkich klientów wraz z imieniem i nazwiskiem
            przedstawiciela handlowego
 */

package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class ClassicModelsManager {

    private static Logger logger = LoggerFactory.getLogger(ConnectionViaDataSource.class);

    public void printAllOffices() {

        //get metadeta  -  https://docs.oracle.com/javase/7/docs/api/java/sql/ResultSetMetaData.html
        //- printAllOffices() - wypisuje na ekranie wszystkie informacje na temat wszystkich biur.

        ConnectionFactory connection = new ConnectionFactory();
        try (Statement statement = connection.getConnection().createStatement()) {
            String query = "SELECT * FROM offices";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
                }
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//            while (result.next()) {
//                int officeCode = result.getInt("officeCode");
//                String city = result.getString("city");
//                String adressLine = result.getString("addressLine1");
//
//                logger.info("officeCode: {}", officeCode);
//                logger.info("city: {}", city);
//                logger.info("addressLine1: {}", adressLine);
//            }
//        } catch (SQLException e) {
//            logger.error("Error during closing connection", e);
//        }
//    }

    public void updateProductPrices() {

        //- updateProductPrices(double updateRate) - podnosi cenę wszystkich produktów o 10%
        ConnectionFactory connection = new ConnectionFactory();
        try (Statement statement = connection.getConnection().createStatement()) {
            String query = "UPDATE products SET buyPrice = buyPrice*1.1";
            statement.executeUpdate(query);
//                result = statement.executeUpdate(query);
//                ResultSet result = statement.executeUpdate(query);
//            while (result.next()) {
////                int officeCode = result.getInt("officeCode");
////                String city = result.getString("city");
////                String adressLine = result.getString("addressLine1");
////
////                logger.info("officeCode: {}", officeCode);
////                logger.info("city: {}", city);
////                logger.info("addressLine1: {}", adressLine);
////            }
        } catch (SQLException e) {
            logger.error("Error during closing connection", e);
        }
    }

    public void printAllCustomersWithSalesRepName() {
//        - printAllCustomersWithSalesRepName() - wypisuje nazwy wszstkich klientów wraz z imieniem i nazwiskiem przedstawiciela handlowego.

        ConnectionFactory connection = new ConnectionFactory();
        try (Statement statement = connection.getConnection().createStatement()) {
            String query = "SELECT customers.contactLastName,employees.lastName,employees.firstName FROM customers, employees WHERE customers.salesRepEmployeeNumber = employees.employeeNumber ORDER BY customers.contactLastName";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
                }
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> findProductByName(String nameMatcher) {

        ConnectionFactory connection = new ConnectionFactory();
        List<Product> productList = new ArrayList<>();
        String query = "SELECT * FROM products WHERE productName LIKE ?";

        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + nameMatcher + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Product product = new Product();
                product.setProductCode(result.getString("productCode"));
                product.setProductName(result.getString("productName"));
                product.setProductLine(result.getString("productLine"));
                product.setProductScale(result.getString("productScale"));
                product.setProductVendor(result.getString("productVendor"));
                product.setProductDescription(result.getString("productDescription"));
                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

//                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
//                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//        public static void main (String[]args){
//            Product product = new Product();
//            product.printAllProducts();
//
//        }
//

/*
        try (
                Statement statement = connection.getConnection().createStatement()) {
            String query = "Select * from products;";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + result.getMetaData().getColumnName(i) + " = " + result.getObject(i) + " | ");
                }
                System.out.println(" ");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Product product = new Product();
        product.printAllProducts();

    }
}
 */

        public static void main (String[]args){
            ClassicModelsManager classicModelsManager = new ClassicModelsManager();

            List<Product> productByName = classicModelsManager.findProductByName("1969");
            System.out.println(productByName);

//            System.out.println("What you want to do: \n 1. PrintAllOffices. \n 2. UpdateProductPrices. \n 3. PrintAllCustomersWithSalesRepName.");
//            Scanner scan = new Scanner(System.in);
//            int number = scan.nextInt();
//
//            switch (number) {
//                case 1: {
//                    System.out.println("I printAllOffices: \n");
//                    classicModelsManager.printAllOffices();
//                }
//                case 2: {
//                    System.out.println("I updateProductPrices: \n");
//                    classicModelsManager.updateProductPrices();
//                }
//                case 3: {
//                    System.out.println("I printAllCustomersWithSalesRepName \n");
//                    classicModelsManager.printAllCustomersWithSalesRepName();
//                }
//
//                default: {
//                    System.out.println("U dont choose anything. Good by.");
//                }
//            }
        }
}


