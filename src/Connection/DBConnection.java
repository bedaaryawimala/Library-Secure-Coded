package Connection;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author Beda Arya Wimala
 */
public class DBConnection {

    public static final String URL = "jdbc:mysql://";
    public static final String DBNAME = "op2_tipe_b";
    public static final String PATH = "localhost:3306/" + DBNAME;
    private static final String DB_USER = System.getenv().getOrDefault("LIBRARY_DB_USER", "root");
    private static final String DB_PASSWORD = System.getenv().getOrDefault("LIBRARY_DB_PASSWORD", "");

    public Connection makeConnection() {
        System.out.println("Opening database...");
        try {
            Connection con = DriverManager.getConnection(URL + PATH, DB_USER, DB_PASSWORD);
            System.out.println("success...");
            return con;
        } catch (Exception e) {
            System.out.println("error opening database");
            throw new IllegalStateException("Database tidak bisa dibuka.", e);
        }
    }

    public void closeConnection() {
        System.out.println("Connection ditutup otomatis oleh try-with-resources.");
    }
}
