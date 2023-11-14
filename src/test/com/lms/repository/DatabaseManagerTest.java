package test.com.lms.repository;

import com.lms.repository.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DatabaseManagerTest {
    private DatabaseManager databaseManager;
    private static final String testDBName = "temp";

    @BeforeEach
    void setUp() throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(testDBName);

    }

    @AfterEach
    void tearDown() throws Exception {
        databaseManager.closeDatabase();
        File dir = new File(testDBName);

        File[] allContents = dir.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                file.delete();
            }
        }
        dir.delete();
    }

    @Test
    void testOk() throws Exception {
        databaseManager.executeUpdate(
                "create table test (" +
                        "  Entry      INTEGER      NOT NULL" +
                        ", Customer   VARCHAR (20) NOT NULL" +
                        ", DOW        VARCHAR (3)  NOT NULL" +
                        ", Cups       INTEGER      NOT NULL" +
                        ", Type       VARCHAR (10) NOT NULL" +
                        ", PRIMARY KEY( Entry )" +
                        ")"
        );

        databaseManager.executeUpdate(
                "insert into test VALUES (1,  'John',   'Mon', 1, 'JustJoe')");

        ResultSet result = databaseManager.executeQuery("select * from test");
        result.next();

        assertEquals("1, John, Mon, 1, JustJoe", result.getInt("Entry") + ", "
                + result.getString("Customer") + ", "
                + result.getString("DOW") + ", "
                + result.getInt("Cups") + ", "
                + result.getString("Type"));
    }
}
