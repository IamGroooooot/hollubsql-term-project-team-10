package com.lms.service;

import com.main.lms.repository.DatabaseManager;
import com.main.lms.service.UpdateDatabase;
import org.junit.jupiter.api.*;

import java.sql.ResultSet;

public class UpdateDatabaseTest {
    private UpdateDatabase updateDatabase;
    private DatabaseManager databaseManager;
    private static final String databaseName = "test";

    @BeforeEach
    void SetUp() throws Exception {
        databaseManager = new DatabaseManager();
        databaseManager.openDatabase(databaseName);
        updateDatabase = new UpdateDatabase(databaseManager);
    }

    @DisplayName("Test Data Add to books/users success")
    @Test
    void TestAddData() throws Exception {
        int result = updateDatabase.AddData("books", "1", "book_name");
        Assertions.assertEquals(1, result);
        ResultSet resultSet = databaseManager.executeQuery("select * from books where Id='1' and Name='book_name'");
        Assertions.assertTrue(resultSet.next());
    }

    @DisplayName("Test Data Add to books/users fail")
    @Test
    void TestAddDataFail() throws Exception {
        int result = updateDatabase.AddData("words", "1", "book_name");
        System.out.println(result);
        Assertions.assertEquals(-1, result);
    }

    @DisplayName("Test Data Add to Rents")
    @Test
    void TestAddDataToRent() throws Exception {
        int result = updateDatabase.AddData("1", "2");
        Assertions.assertEquals(1, result);
        ResultSet resultSet = databaseManager.executeQuery("select * from rents where BookId='2' and UserId='1'");
        Assertions.assertTrue(resultSet.next());
    }

    @DisplayName("Test Return books")
    @Test
    void TestReturnBook() throws Exception {
        updateDatabase.AddData("1", "2");
        updateDatabase.ReturnUpdate("1", "2");
        ResultSet resultSet = databaseManager.executeQuery("select Rent from rents where BookId='2' and UserId='1'");
        if (resultSet.next()) {
            Assertions.assertEquals(0, resultSet.getInt("Rent"));
        }
    }

    @DisplayName("Test Return books fail")
    @Test
    void TestReturnBookFail() throws Exception {
        int result = updateDatabase.ReturnUpdate("1", "2");
        Assertions.assertEquals(0, result);
    }

    @AfterEach
    void End() throws Exception {
        databaseManager.closeDatabase();
    }
}
