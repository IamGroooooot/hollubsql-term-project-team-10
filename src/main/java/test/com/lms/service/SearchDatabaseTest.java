package test.com.lms.service;

import com.lms.repository.DatabaseManager;
import com.lms.service.SearchDatabase;
import com.lms.service.UpdateDatabase;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class SearchDatabaseTest {
    private SearchDatabase searchDatabase;
    private DatabaseManager databaseManager;
    private UpdateDatabase updateDatabase;
    private static final String databaseName = "test";

    @BeforeEach
    void Setup() throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(databaseName);
        updateDatabase = new UpdateDatabase(databaseManager);
        searchDatabase = new SearchDatabase(databaseManager);
        updateDatabase.AddData("users", "1", "1");
        updateDatabase.AddData("books", "2", "2");
        updateDatabase.AddData("1", "2");
    }

    @DisplayName("Test item exists in users table")
    @Test
    void TestItemExists() throws Exception {
        Assertions.assertTrue(searchDatabase.CheckItemExist("users", "1"));
        Assertions.assertFalse(searchDatabase.CheckItemExist("users", "2"));
    }

    @DisplayName("Test index error in database")
    @Test
    void TestItemExistsIndexError() throws Exception {
        Assertions.assertFalse(searchDatabase.CheckItemExist("test", "2"));
    }

    @DisplayName("Get users id with name")
    @Test
    void TestGetIdByName() throws Exception {
        Assertions.assertEquals("1", searchDatabase.GetItemString("users", "Id", "Name", "1"));
    }

    @DisplayName("Test errors when column not in table")
    @Test
    void TestGetIdByNameError() throws Exception {
        Assertions.assertNull(searchDatabase.GetItemString("users", "Id", "test", "1"));
    }

    @DisplayName("Test item exists in rents table")
    @Test
    void TestItemExistsInRents() throws Exception {
        Assertions.assertTrue(searchDatabase.CheckRentExist("1", "2"));
        Assertions.assertFalse(searchDatabase.CheckRentExist("2", "1"));
    }

    @DisplayName("Test data search in books table by id")
    @Test
    void TestDataSearchBooks() throws Exception {
        String[][] result = searchDatabase.SearchData("books", "2");
        String[][] compareValue = {{"2", "2"}};
        Assertions.assertTrue(Arrays.deepEquals(compareValue, result));
    }

    @DisplayName("Test search data not in users table by id")
    @Test
    void TestDataSearchUsersFail() throws Exception {
        String[][] result = searchDatabase.SearchData("users", "5");
        String[][] compareValue = {{}};
        Assertions.assertFalse(Arrays.deepEquals(compareValue, result));
    }

    @DisplayName("Test data search in rents table by user id")
    @Test
    void TestDataSearchRent() throws Exception {
        String[][] result = searchDatabase.SearchRentData("1");
        String[][] compareValue = {{"2", "rent"}};
        Assertions.assertTrue(Arrays.deepEquals(compareValue, result));
    }

    @DisplayName("Test data search not in rents table by user id")
    @Test
    void TestDataSearchRentFail() throws Exception {
        String[][] result = searchDatabase.SearchRentData("5");
        String[][] compareValue = {{}};
        Assertions.assertFalse(Arrays.deepEquals(compareValue, result));
    }

    @AfterEach
    void End() throws Exception {
        databaseManager.closeDatabase();
    }

}
